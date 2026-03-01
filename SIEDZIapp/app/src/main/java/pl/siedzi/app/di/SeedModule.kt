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
import pl.siedzi.app.data.FishSpeciesLoader
import pl.siedzi.app.data.FisheryJsonImporter
import pl.siedzi.app.data.dao.AppMetaDao
import pl.siedzi.app.data.dao.FishSpeciesDao
import pl.siedzi.app.data.dao.FisheryDao
import pl.siedzi.app.data.dao.SettingsDao
import pl.siedzi.app.data.entity.AppMeta
import pl.siedzi.app.data.entity.Settings
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SeedModule {

    @Provides
    @Singleton
    fun provideDatabaseSeeder(
        @ApplicationContext context: Context,
        appMetaDao: AppMetaDao,
        settingsDao: SettingsDao,
        fishSpeciesDao: FishSpeciesDao,
        fisheryDao: FisheryDao
    ): DatabaseSeeder = DatabaseSeeder(context, appMetaDao, settingsDao, fishSpeciesDao, fisheryDao)
}

class DatabaseSeeder @Inject constructor(
    private val context: Context,
    private val appMetaDao: AppMetaDao,
    private val settingsDao: SettingsDao,
    private val fishSpeciesDao: FishSpeciesDao,
    private val fisheryDao: FisheryDao
) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    fun seedIfNeeded() {
        scope.launch {
            try {
                if (appMetaDao.get() == null) {
                    appMetaDao.insert(AppMeta(id = 1, onboardingCompleted = false))
                }
                if (settingsDao.get() == null) {
                    settingsDao.insert(Settings(id = 1, theme = "dark"))
                }
                if (fishSpeciesDao.count() == 0) {
                    val species = FishSpeciesLoader.loadFromAssets(context)
                    fishSpeciesDao.insertAll(species)
                }
                if (fisheryDao.count() == 0) {
                    try {
                        FisheryJsonImporter.importFromAssets(context, fisheryDao)
                    } catch (_: Exception) { /* asset może nie istnieć */ }
                }
            } catch (e: Exception) {
                // DB may not be ready yet
            }
        }
    }
}
