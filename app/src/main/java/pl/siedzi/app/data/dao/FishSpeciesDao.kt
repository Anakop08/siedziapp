package pl.siedzi.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.siedzi.app.data.entity.FishSpecies

@Dao
interface FishSpeciesDao {

    @Query("SELECT * FROM fish_species ORDER BY name ASC")
    fun getAll(): Flow<List<FishSpecies>>

    @Query("SELECT * FROM fish_species WHERE name LIKE '%' || :query || '%' OR name_latin LIKE '%' || :query || '%' ORDER BY name ASC")
    fun search(query: String): Flow<List<FishSpecies>>

    @Query("SELECT * FROM fish_species WHERE id = :id")
    suspend fun getById(id: String): FishSpecies?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(species: List<FishSpecies>)

    @Query("SELECT COUNT(*) FROM fish_species")
    suspend fun count(): Int
}
