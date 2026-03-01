package pl.siedzi.app.data

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import pl.siedzi.app.data.dao.FisheryDao
import pl.siedzi.app.data.entity.Fishery
import java.io.BufferedReader
import java.io.InputStream
import java.util.UUID

/**
 * Import łowisk z pliku CSV.
 * Format: name,address,lat,lon,station_note (opcjonalnie species_ids oddzielone przecinkami)
 */
object FisheryImporter {

    suspend fun importFromCsv(context: Context, inputStream: InputStream, fisheryDao: FisheryDao): Int = withContext(Dispatchers.IO) {
        val reader = BufferedReader(inputStream.reader())
        val header = reader.readLine() ?: return@withContext 0
        val columns = header.split(",").map { it.trim().lowercase() }
        val nameIdx = columns.indexOf("name").takeIf { it >= 0 } ?: 0
        val addressIdx = columns.indexOf("address").takeIf { it >= 0 } ?: 1
        val latIdx = columns.indexOf("lat").takeIf { it >= 0 } ?: 2
        val lonIdx = columns.indexOf("lon").takeIf { it >= 0 } ?: 3
        val stationIdx = columns.indexOf("station_note").takeIf { it >= 0 }
        val speciesIdx = columns.indexOf("species_ids").takeIf { it >= 0 }

        val fisheries = mutableListOf<Fishery>()
        reader.forEachLine { line ->
            val values = line.split(",").map { it.trim() }
            if (values.size >= 4) {
                val name = values.getOrNull(nameIdx) ?: ""
                val lat = values.getOrNull(latIdx)?.toDoubleOrNull() ?: 0.0
                val lon = values.getOrNull(lonIdx)?.toDoubleOrNull() ?: 0.0
                if (name.isNotEmpty()) {
                    fisheries.add(Fishery(
                        id = UUID.randomUUID().toString(),
                        name = name,
                        address = values.getOrNull(addressIdx)?.takeIf { it.isNotEmpty() },
                        lat = lat,
                        lon = lon,
                        stationNote = stationIdx?.let { values.getOrNull(it) }?.takeIf { it.isNotEmpty() },
                        speciesIdsJson = speciesIdx?.let { values.getOrNull(it) }?.takeIf { it.isNotEmpty() },
                        createdAt = System.currentTimeMillis()
                    ))
                }
            }
        }
        fisheries.forEach { fisheryDao.insert(it) }
        fisheries.size
    }
}
