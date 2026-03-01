package pl.siedzi.app.util

import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase

class DatabaseInitializer : RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        Log.d(TAG, "SiedziDatabase created successfully")
    }

    override fun onOpen(db: SupportSQLiteDatabase) {
        super.onOpen(db)
        Log.d(TAG, "SiedziDatabase opened - connection OK")
    }

    companion object {
        private const val TAG = "SiedziDatabase"
    }
}
