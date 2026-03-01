package pl.siedzi.app.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray
import pl.siedzi.app.data.dao.FisheryDao
import pl.siedzi.app.data.entity.Fishery
import java.util.UUID

/**
 * Import łowisk z pliku JSON (fisheries_clean.json).
 * Format: [{ name, region, city, address, latitude, longitude, ... }]
 */
object FisheryJsonImporter {

    private const val ASSET_NAME = "fisheries_clean.json"

    suspend fun importFromAssets(context: Context, fisheryDao: FisheryDao): Int = withContext(Dispatchers.IO) {
        try {
            context.assets.open(ASSET_NAME).use { stream ->
                val json = stream.bufferedReader().use { it.readText() }
                val array = JSONArray(json)
                val fisheries = mutableListOf<Fishery>()
                for (i in 0 until array.length()) {
                    val obj = array.optJSONObject(i) ?: continue
                    val name = obj.optString("name", "").trim()
                    if (name.isEmpty()) continue
                    val lat = obj.optDouble("latitude", 0.0)
                    val lon = obj.optDouble("longitude", 0.0)
                    val address = obj.optString("address", "").takeIf { it.isNotBlank() }
                    val region = obj.optString("region", "").takeIf { it.isNotBlank() }
                    val city = obj.optString("city", "").takeIf { it.isNotBlank() }
                    val stationNote = listOfNotNull(region, city).joinToString(", ").takeIf { it.isNotEmpty() }
                    fisheries.add(Fishery(
                        id = UUID.randomUUID().toString(),
                        name = name,
                        address = address,
                        lat = lat,
                        lon = lon,
                        stationNote = stationNote,
                        speciesIdsJson = null,
                        topoClipUri = null,
                        createdAt = System.currentTimeMillis()
                    ))
                }
                if (fisheries.isNotEmpty()) {
                    fisheryDao.insertAll(fisheries)
                }
                fisheries.size
            }
        } catch (e: Exception) {
            0
        }
    }
}
