package pl.siedzi.app.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "app_meta")
data class AppMeta(
    @PrimaryKey val id: Int = 1,
    @ColumnInfo(name = "onboarding_completed") val onboardingCompleted: Boolean = false
)
