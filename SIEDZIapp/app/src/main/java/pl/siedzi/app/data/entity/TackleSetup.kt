package pl.siedzi.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Snapshot zestawu wędkarskiego użytego przy rzucie.
 * Każdy wpis na osi czasu (cast) referencjonuje jeden TackleSetup.
 */
@Entity(tableName = "tackle_setups")
data class TackleSetup(
    @PrimaryKey val id: String,
    @ColumnInfo(name = "rod_id") val rodId: String? = null,
    @ColumnInfo(name = "reel_id") val reelId: String? = null,
    @ColumnInfo(name = "line_id") val lineId: String? = null,
    @ColumnInfo(name = "bait_id") val baitId: String? = null,
    @ColumnInfo(name = "hook_id") val hookId: String? = null,
    @ColumnInfo(name = "has_boat") val hasBoat: Boolean = false,
    @ColumnInfo(name = "has_echosounder") val hasEchosounder: Boolean = false,
    @ColumnInfo(name = "custom_json") val customJson: String? = null
)
