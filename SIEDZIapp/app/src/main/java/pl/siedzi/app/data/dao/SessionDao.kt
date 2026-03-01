package pl.siedzi.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.siedzi.app.data.entity.Session

@Dao
interface SessionDao {

    @Query("SELECT * FROM sessions WHERE is_active = 1 LIMIT 1")
    suspend fun getActiveSession(): Session?

    @Query("SELECT * FROM sessions WHERE is_active = 1 LIMIT 1")
    fun getActiveSessionFlow(): Flow<Session?>

    @Query("SELECT * FROM sessions WHERE trip_id = :tripId ORDER BY start_time DESC")
    fun getByTripId(tripId: String): Flow<List<Session>>

    @Query("SELECT * FROM sessions WHERE fishery_id = :fisheryId ORDER BY start_time DESC")
    fun getByFisheryId(fisheryId: String): Flow<List<Session>>

    @Query("SELECT * FROM sessions WHERE id = :id")
    suspend fun getById(id: String): Session?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: Session)

    @Query("UPDATE sessions SET end_time = :endTime, is_active = 0 WHERE id = :id")
    suspend fun endSession(id: String, endTime: Long)

    @Query("UPDATE sessions SET lat = :lat, lon = :lon WHERE id = :id")
    suspend fun updateLocation(id: String, lat: Double, lon: Double)
}
