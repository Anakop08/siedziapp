package pl.siedzi.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "catches",
    foreignKeys = [
        ForeignKey(
            entity = Session::class,
            parentColumns = ["id"],
            childColumns = ["session_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("session_id"), Index("timestamp")]
)
data class FishCatch(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "session_id") val sessionId: String,
    @ColumnInfo(name = "timeline_entry_id") val timelineEntryId: String,
    @ColumnInfo(name = "species_id") val speciesId: String,
    @ColumnInfo(name = "weight_kg") val weightKg: Double,
    @ColumnInfo(name = "length_cm") val lengthCm: Int? = null,
    val nickname: String? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    val timestamp: Long,
    @ColumnInfo(name = "weather_snapshot") val weatherSnapshot: String? = null,
    @ColumnInfo(name = "solunar_snapshot") val solunarSnapshot: String? = null,
    @ColumnInfo(name = "tackle_setup_id") val tackleSetupId: String,
    @ColumnInfo(name = "photo_uris_json") val photoUrisJson: String? = null
) {
    val photoUris: List<String>
        get() = photoUrisJson?.let { json ->
            if (json.isBlank()) emptyList()
            else json.split(",").map { it.trim() }.filter { it.isNotEmpty() }
        } ?: emptyList()
}
