package pl.siedzi.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "fish_species")
data class FishSpecies(
    @PrimaryKey val id: String,
    val name: String,
    @ColumnInfo(name = "name_latin") val nameLatin: String? = null,
    val description: String? = null,
    @ColumnInfo(name = "min_size_cm") val minSize: Int? = null,
    @ColumnInfo(name = "closed_season") val closedSeason: String? = null,
    @ColumnInfo(name = "image_uri") val imageUri: String? = null
)
