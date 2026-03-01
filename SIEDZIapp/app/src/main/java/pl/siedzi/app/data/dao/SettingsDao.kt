package pl.siedzi.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.siedzi.app.data.entity.Settings

@Dao
interface SettingsDao {

    @Query("SELECT * FROM settings WHERE id = 1")
    suspend fun get(): Settings?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(settings: Settings)

    @Query("UPDATE settings SET theme = :theme WHERE id = 1")
    suspend fun setTheme(theme: String)
}
