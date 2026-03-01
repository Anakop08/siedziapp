package pl.siedzi.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fisheries")
data class Fishery(
    @PrimaryKey val id: String,
    val name: String,
    val address: String? = null,
    val lat: Double,
    val lon: Double,
    @ColumnInfo(name = "station_note") val stationNote: String? = null,
    @ColumnInfo(name = "species_ids_json") val speciesIdsJson: String? = null,
    @ColumnInfo(name = "topo_clip_uri") val topoClipUri: String? = null,
    @ColumnInfo(name = "created_at") val createdAt: Long
) {
    val speciesIds: List<String>
        get() = speciesIdsJson?.let { json ->
            if (json.isBlank()) emptyList()
            else json.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        } ?: emptyList()
}
