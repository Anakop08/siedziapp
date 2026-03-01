package pl.siedzi.app.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import pl.siedzi.app.data.entity.AppMeta

@Dao
interface AppMetaDao {

    @Query("SELECT * FROM app_meta WHERE id = 1")
    suspend fun get(): AppMeta?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(meta: AppMeta)

    @Query("UPDATE app_meta SET onboarding_completed = :completed WHERE id = 1")
    suspend fun setOnboardingCompleted(completed: Boolean)
}
