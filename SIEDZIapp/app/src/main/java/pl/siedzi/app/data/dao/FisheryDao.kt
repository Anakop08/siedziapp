package pl.siedzi.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.siedzi.app.data.entity.Fishery

@Dao
interface FisheryDao {

    @Query("SELECT * FROM fisheries ORDER BY name ASC")
    fun getAll(): Flow<List<Fishery>>

    @Query("SELECT * FROM fisheries WHERE name LIKE '%' || :query || '%' OR address LIKE '%' || :query || '%' ORDER BY name ASC")
    fun search(query: String): Flow<List<Fishery>>

    @Query("SELECT * FROM fisheries WHERE id = :id")
    suspend fun getById(id: String): Fishery?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(fishery: Fishery)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(fisheries: List<Fishery>)

    @Query("DELETE FROM fisheries WHERE id = :id")
    suspend fun deleteById(id: String)

    @Query("SELECT COUNT(*) FROM fisheries")
    suspend fun count(): Int
}
