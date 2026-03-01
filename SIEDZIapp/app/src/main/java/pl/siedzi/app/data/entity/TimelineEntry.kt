package pl.siedzi.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Wpis na osi czasu sesji – zarzucenie (cast) lub połów (catch).
 */
@Entity(
    tableName = "timeline_entries",
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
data class TimelineEntry(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "session_id") val sessionId: String,
    @ColumnInfo(name = "tackle_setup_id") val tackleSetupId: String? = null,
    val timestamp: Long,
    val type: String, // "cast" | "catch"
    @ColumnInfo(name = "catch_id") val catchId: String? = null // gdy type == "catch"
)
