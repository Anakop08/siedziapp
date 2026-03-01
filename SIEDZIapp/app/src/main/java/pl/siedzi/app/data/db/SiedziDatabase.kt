package pl.siedzi.app.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import net.sqlcipher.database.SupportFactory
import pl.siedzi.app.data.dao.AppMetaDao
import pl.siedzi.app.data.dao.FishSpeciesDao
import pl.siedzi.app.data.dao.SettingsDao
import pl.siedzi.app.data.dao.FisheryDao
import pl.siedzi.app.data.dao.SessionDao
import pl.siedzi.app.data.dao.TackleDictDao
import pl.siedzi.app.data.dao.TackleSetupDao
import pl.siedzi.app.data.dao.TimelineEntryDao
import pl.siedzi.app.data.dao.CatchDao
import pl.siedzi.app.data.dao.TripDao
import pl.siedzi.app.data.entity.AppMeta
import pl.siedzi.app.data.entity.FishSpecies
import pl.siedzi.app.data.entity.Fishery
import pl.siedzi.app.data.entity.Session
import pl.siedzi.app.data.entity.Settings
import pl.siedzi.app.data.entity.TackleDictItem
import pl.siedzi.app.data.entity.TackleSetup
import pl.siedzi.app.data.entity.FishCatch
import pl.siedzi.app.data.entity.TimelineEntry
import pl.siedzi.app.data.entity.Trip
import pl.siedzi.app.util.EncryptionProvider
import pl.siedzi.app.util.DatabaseInitializer

private val MIGRATION_5_6 = object : Migration(5, 6) {
    override fun migrate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS catches (
                id TEXT NOT NULL PRIMARY KEY,
                session_id TEXT NOT NULL,
                timeline_entry_id TEXT NOT NULL,
                species_id TEXT NOT NULL,
                weight_kg REAL NOT NULL,
                length_cm INTEGER,
                nickname TEXT,
                lat REAL,
                lon REAL,
                timestamp INTEGER NOT NULL,
                weather_snapshot TEXT,
                solunar_snapshot TEXT,
                tackle_setup_id TEXT NOT NULL,
                photo_uris_json TEXT,
                FOREIGN KEY(session_id) REFERENCES sessions(id) ON DELETE CASCADE
            )
        """.trimIndent())
        db.execSQL("CREATE INDEX IF NOT EXISTS index_catches_session_id ON catches(session_id)")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_catches_timestamp ON catches(timestamp)")
    }
}

private val MIGRATION_4_5 = object : Migration(4, 5) {
    override fun migrate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS tackle_dict (
                id TEXT NOT NULL PRIMARY KEY,
                type TEXT NOT NULL,
                name TEXT NOT NULL,
                details TEXT,
                created_at INTEGER NOT NULL
            )
        """.trimIndent())
        db.execSQL("CREATE INDEX IF NOT EXISTS index_tackle_dict_type ON tackle_dict(type)")
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS tackle_setups (
                id TEXT NOT NULL PRIMARY KEY,
                rod_id TEXT,
                reel_id TEXT,
                line_id TEXT,
                bait_id TEXT,
                hook_id TEXT,
                has_boat INTEGER NOT NULL DEFAULT 0,
                has_echosounder INTEGER NOT NULL DEFAULT 0,
                custom_json TEXT
            )
        """.trimIndent())
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS timeline_entries (
                id TEXT NOT NULL PRIMARY KEY,
                session_id TEXT NOT NULL,
                tackle_setup_id TEXT,
                timestamp INTEGER NOT NULL,
                type TEXT NOT NULL,
                catch_id TEXT,
                FOREIGN KEY(session_id) REFERENCES sessions(id) ON DELETE CASCADE
            )
        """.trimIndent())
        db.execSQL("CREATE INDEX IF NOT EXISTS index_timeline_entries_session_id ON timeline_entries(session_id)")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_timeline_entries_timestamp ON timeline_entries(timestamp)")
    }
}

private val MIGRATION_3_4 = object : Migration(3, 4) {
    override fun migrate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS trips (
                id TEXT NOT NULL PRIMARY KEY,
                fishery_id TEXT NOT NULL,
                start_date INTEGER NOT NULL,
                end_date INTEGER NOT NULL,
                strategy_note TEXT,
                created_at INTEGER NOT NULL,
                FOREIGN KEY(fishery_id) REFERENCES fisheries(id) ON DELETE CASCADE
            )
        """.trimIndent())
        db.execSQL("CREATE INDEX IF NOT EXISTS index_trips_fishery_id ON trips(fishery_id)")
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS sessions (
                id TEXT NOT NULL PRIMARY KEY,
                trip_id TEXT NOT NULL,
                fishery_id TEXT NOT NULL,
                start_time INTEGER NOT NULL,
                end_time INTEGER,
                lat REAL,
                lon REAL,
                is_active INTEGER NOT NULL DEFAULT 1,
                FOREIGN KEY(trip_id) REFERENCES trips(id) ON DELETE CASCADE,
                FOREIGN KEY(fishery_id) REFERENCES fisheries(id) ON DELETE CASCADE
            )
        """.trimIndent())
        db.execSQL("CREATE INDEX IF NOT EXISTS index_sessions_trip_id ON sessions(trip_id)")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_sessions_fishery_id ON sessions(fishery_id)")
        db.execSQL("CREATE INDEX IF NOT EXISTS index_sessions_is_active ON sessions(is_active)")
    }
}

private val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS fisheries (
                id TEXT NOT NULL PRIMARY KEY,
                name TEXT NOT NULL,
                address TEXT,
                lat REAL NOT NULL,
                lon REAL NOT NULL,
                station_note TEXT,
                species_ids_json TEXT,
                topo_clip_uri TEXT,
                created_at INTEGER NOT NULL
            )
        """.trimIndent())
    }
}

private val MIGRATION_1_2 = object : Migration(1, 2) {
    override fun migrate(db: androidx.sqlite.db.SupportSQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE IF NOT EXISTS fish_species (
                id TEXT NOT NULL PRIMARY KEY,
                name TEXT NOT NULL,
                name_latin TEXT,
                description TEXT,
                min_size_cm INTEGER,
                closed_season TEXT,
                image_uri TEXT
            )
        """.trimIndent())
    }
}

@Database(
    entities = [
        AppMeta::class, Settings::class, FishSpecies::class, Fishery::class,
        Trip::class, Session::class, TackleDictItem::class, TackleSetup::class, TimelineEntry::class,
        FishCatch::class
    ],
    version = 6,
    exportSchema = false
)
abstract class SiedziDatabase : RoomDatabase() {

    abstract fun appMetaDao(): AppMetaDao
    abstract fun settingsDao(): SettingsDao
    abstract fun fishSpeciesDao(): FishSpeciesDao
    abstract fun fisheryDao(): FisheryDao
    abstract fun tripDao(): TripDao
    abstract fun sessionDao(): SessionDao
    abstract fun tackleDictDao(): TackleDictDao
    abstract fun tackleSetupDao(): TackleSetupDao
    abstract fun timelineEntryDao(): TimelineEntryDao
    abstract fun catchDao(): CatchDao

    companion object {
        private const val DB_NAME = "siedzi.db"

        @Volatile
        private var INSTANCE: SiedziDatabase? = null

        fun getInstance(context: Context): SiedziDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): SiedziDatabase {
            // Load SQLCipher native libs before any DB operation
            System.loadLibrary("sqlcipher")

            val encryptionProvider = EncryptionProvider(context)
            val passphrase = encryptionProvider.getDatabasePassphrase()
            val factory = SupportFactory(passphrase)

            return Room.databaseBuilder(context, SiedziDatabase::class.java, DB_NAME)
                .openHelperFactory(factory)
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3, MIGRATION_3_4, MIGRATION_4_5, MIGRATION_5_6)
                .addCallback(DatabaseInitializer())
                .build()
        }
    }
}
