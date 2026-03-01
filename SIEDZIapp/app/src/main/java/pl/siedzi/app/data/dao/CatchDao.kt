package pl.siedzi.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.siedzi.app.data.entity.FishCatch

@Dao
interface CatchDao {

    @Query("SELECT * FROM catches WHERE session_id = :sessionId ORDER BY timestamp DESC")
    fun getBySessionId(sessionId: String): Flow<List<FishCatch>>

    @Query("SELECT * FROM catches WHERE photo_uris_json IS NOT NULL AND LENGTH(photo_uris_json) > 0 ORDER BY timestamp DESC")
    fun getCatchesWithPhotos(): Flow<List<FishCatch>>

    @Query("SELECT * FROM catches WHERE id = :id")
    suspend fun getById(id: String): FishCatch?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: FishCatch)
}
