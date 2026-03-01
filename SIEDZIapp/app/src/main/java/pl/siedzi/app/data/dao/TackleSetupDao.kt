package pl.siedzi.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.siedzi.app.data.entity.TackleSetup

@Dao
interface TackleSetupDao {

    @Query("SELECT * FROM tackle_setups WHERE id = :id")
    suspend fun getById(id: String): TackleSetup?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(setup: TackleSetup)
}
