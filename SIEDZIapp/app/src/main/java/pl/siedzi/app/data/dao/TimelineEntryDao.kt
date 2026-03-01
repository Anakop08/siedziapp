package pl.siedzi.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import pl.siedzi.app.data.entity.TimelineEntry

@Dao
interface TimelineEntryDao {

    @Query("SELECT * FROM timeline_entries WHERE session_id = :sessionId ORDER BY timestamp ASC")
    fun getBySessionId(sessionId: String): Flow<List<TimelineEntry>>

    @Query("SELECT * FROM timeline_entries WHERE id = :id")
    suspend fun getById(id: String): TimelineEntry?

    @Query("SELECT * FROM timeline_entries WHERE session_id = :sessionId AND type = 'cast' ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLastCastForSession(sessionId: String): TimelineEntry?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entry: TimelineEntry)
}
