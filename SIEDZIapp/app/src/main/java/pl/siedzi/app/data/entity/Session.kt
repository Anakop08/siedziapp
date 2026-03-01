package pl.siedzi.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "sessions",
    foreignKeys = [
        ForeignKey(
            entity = Trip::class,
            parentColumns = ["id"],
            childColumns = ["trip_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Fishery::class,
            parentColumns = ["id"],
            childColumns = ["fishery_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("trip_id"), Index("fishery_id"), Index("is_active")]
)
data class Session(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "trip_id") val tripId: String,
    @ColumnInfo(name = "fishery_id") val fisheryId: String,
    @ColumnInfo(name = "start_time") val startTime: Long,
    @ColumnInfo(name = "end_time") val endTime: Long? = null,
    val lat: Double? = null,
    val lon: Double? = null,
    @ColumnInfo(name = "is_active") val isActive: Boolean = true
)
