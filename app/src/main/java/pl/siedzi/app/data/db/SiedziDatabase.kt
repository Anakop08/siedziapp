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
import pl.siedzi.app.data.entity.AppMeta
import pl.siedzi.app.data.entity.FishSpecies
import pl.siedzi.app.data.entity.Fishery
import pl.siedzi.app.data.entity.Settings
import pl.siedzi.app.util.EncryptionProvider
import pl.siedzi.app.util.DatabaseInitializer

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
    entities = [AppMeta::class, Settings::class, FishSpecies::class, Fishery::class],
    version = 3,
    exportSchema = false
)
abstract class SiedziDatabase : RoomDatabase() {

    abstract fun appMetaDao(): AppMetaDao
    abstract fun settingsDao(): SettingsDao
    abstract fun fishSpeciesDao(): FishSpeciesDao
    abstract fun fisheryDao(): FisheryDao

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
                .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                .addCallback(DatabaseInitializer())
                .build()
        }
    }
}
