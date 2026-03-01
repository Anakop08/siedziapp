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
import pl.siedzi.app.data.dao.AppMetaDao
import pl.siedzi.app.data.dao.FishSpeciesDao
import pl.siedzi.app.data.dao.FisheryDao
import pl.siedzi.app.data.dao.SettingsDao
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
}
