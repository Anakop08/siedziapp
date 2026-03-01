package pl.siedzi.app.data.map

import android.content.Context
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.modules.SqlTileWriter
import java.io.File

/**
 * Zarządzanie cache kafelków mapy dla trybu offline.
 * OsmDroid domyślnie cachuje oglądane kafelki.
 * Ta klasa konfiguruje ścieżkę cache i umożliwia wstępne pobieranie obszaru.
 */
object OfflineTileCache {

    private const val CACHE_DIR = "osmdroid_tiles"
    private const val CACHE_MAX_SIZE_MB = 100L

    /**
     * Inicjalizuje cache OsmDroid. Wywołać w Application.onCreate().
     */
    fun init(context: Context) {
        val basePath = File(context.filesDir, CACHE_DIR).apply { mkdirs() }
        Configuration.getInstance().osmdroidBasePath = basePath
        Configuration.getInstance().tileDownloadThreads = 4
        Configuration.getInstance().tileDownloadMaxQueueSize = 64
        Configuration.getInstance().tileFileSystemCacheMaxBytes = CACHE_MAX_SIZE_MB * 1024 * 1024
    }

    /** Zwraca ścieżkę do cache (np. do wyświetlenia w ustawieniach). */
    fun getCachePath(context: Context): File =
        File(context.filesDir, CACHE_DIR)

    /** Rozmiar cache w bajtach (szacunkowo). */
    fun getCacheSize(context: Context): Long =
        getCachePath(context).walkTopDown().sumOf { it.length() }

    /** Czyści cache kafelków. */
    fun clearCache(context: Context) {
        getCachePath(context).listFiles()?.forEach { it.deleteRecursively() }
    }
}
