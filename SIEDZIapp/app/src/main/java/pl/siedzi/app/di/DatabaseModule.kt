package pl.siedzi.app.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import pl.siedzi.app.data.dao.CatchDao
import pl.siedzi.app.data.dao.AppMetaDao
import pl.siedzi.app.data.dao.FishSpeciesDao
import pl.siedzi.app.data.dao.FisheryDao
import pl.siedzi.app.data.dao.SessionDao
import pl.siedzi.app.data.dao.SettingsDao
import pl.siedzi.app.data.dao.TackleDictDao
import pl.siedzi.app.data.dao.TackleSetupDao
import pl.siedzi.app.data.dao.TimelineEntryDao
import pl.siedzi.app.data.dao.TripDao
import pl.siedzi.app.data.db.SiedziDatabase
import pl.siedzi.app.data.entity.AppMeta
import pl.siedzi.app.data.entity.Settings
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): SiedziDatabase {
        return SiedziDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideAppMetaDao(database: SiedziDatabase): AppMetaDao = database.appMetaDao()

    @Provides
    @Singleton
    fun provideSettingsDao(database: SiedziDatabase): SettingsDao = database.settingsDao()

    @Provides
    @Singleton
    fun provideFishSpeciesDao(database: SiedziDatabase): FishSpeciesDao = database.fishSpeciesDao()

    @Provides
    @Singleton
    fun provideFisheryDao(database: SiedziDatabase): FisheryDao = database.fisheryDao()

    @Provides
    @Singleton
    fun provideTripDao(database: SiedziDatabase): TripDao = database.tripDao()

    @Provides
    @Singleton
    fun provideSessionDao(database: SiedziDatabase): SessionDao = database.sessionDao()

    @Provides
    @Singleton
    fun provideTackleDictDao(database: SiedziDatabase): TackleDictDao = database.tackleDictDao()

    @Provides
    @Singleton
    fun provideTackleSetupDao(database: SiedziDatabase): TackleSetupDao = database.tackleSetupDao()

    @Provides
    @Singleton
    fun provideTimelineEntryDao(database: SiedziDatabase): TimelineEntryDao = database.timelineEntryDao()

    @Provides
    @Singleton
    fun provideCatchDao(database: SiedziDatabase): CatchDao = database.catchDao()
}
