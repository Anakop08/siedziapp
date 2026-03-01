package pl.siedzi.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "trips",
    foreignKeys = [
        ForeignKey(
            entity = Fishery::class,
            parentColumns = ["id"],
            childColumns = ["fishery_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("fishery_id")]
)
data class Trip(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "fishery_id") val fisheryId: String,
    @ColumnInfo(name = "start_date") val startDate: Long,
    @ColumnInfo(name = "end_date") val endDate: Long,
    @ColumnInfo(name = "strategy_note") val strategyNote: String? = null,
    @ColumnInfo(name = "created_at") val createdAt: Long
)
