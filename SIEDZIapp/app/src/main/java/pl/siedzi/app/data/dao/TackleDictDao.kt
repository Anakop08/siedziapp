package pl.siedzi.app.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import pl.siedzi.app.data.entity.TackleDictItem

@Dao
interface TackleDictDao {

    @Query("SELECT * FROM tackle_dict WHERE type = :type ORDER BY name ASC")
    fun getAllByType(type: String): Flow<List<TackleDictItem>>

    @Query("SELECT * FROM tackle_dict WHERE id = :id")
    suspend fun getById(id: String): TackleDictItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TackleDictItem)

    @Update
    suspend fun update(item: TackleDictItem)

    @Query("DELETE FROM tackle_dict WHERE id = :id")
    suspend fun delete(id: String)
}
