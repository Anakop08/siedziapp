package pl.siedzi.app

import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import org.osmdroid.config.Configuration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import pl.siedzi.app.data.dao.AppMetaDao
import pl.siedzi.app.data.dao.SettingsDao
import pl.siedzi.app.data.entity.AppMeta
import pl.siedzi.app.data.entity.Settings

@HiltAndroidApp
class SiedziApplication : android.app.Application() {

    override fun onCreate() {
        super.onCreate()
        Configuration.getInstance().load(this, getSharedPreferences("osmdroid", MODE_PRIVATE))
        // Seed DB will be done from MainActivity/ViewModel for Hilt injection
        Log.d(TAG, "SIEDZI! Application started")
    }

    companion object {
        private const val TAG = "SiedziApp"
    }
}
