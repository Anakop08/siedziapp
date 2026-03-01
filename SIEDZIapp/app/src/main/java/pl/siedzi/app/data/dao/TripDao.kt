package pl.siedzi.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.siedzi.app.data.entity.Trip

@Dao
interface TripDao {

    @Query("SELECT * FROM trips ORDER BY start_date DESC")
    fun getAll(): Flow<List<Trip>>

    @Query("SELECT * FROM trips WHERE id = :id")
    suspend fun getById(id: String): Trip?

    @Query("SELECT * FROM trips WHERE fishery_id = :fisheryId ORDER BY start_date DESC")
    fun getByFisheryId(fisheryId: String): Flow<List<Trip>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(trip: Trip)

    @Query("DELETE FROM trips WHERE id = :id")
    suspend fun deleteById(id: String)
}
