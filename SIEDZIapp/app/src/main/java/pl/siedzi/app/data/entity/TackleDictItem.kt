package pl.siedzi.app.data.entity

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Słownikowy element sprzętu wędkarskiego.
 * type: rod, reel, line, bait, hook
 */
@Entity(
    tableName = "tackle_dict",
    indices = [Index("type")]
)
data class TackleDictItem(
    @PrimaryKey val id: String,
    val type: String, // rod, reel, line, bait, hook
    val name: String,
    val details: String? = null, // np. "0,18 mm", "3.5 lb"
    @androidx.room.ColumnInfo(name = "created_at") val createdAt: Long = System.currentTimeMillis()
)
