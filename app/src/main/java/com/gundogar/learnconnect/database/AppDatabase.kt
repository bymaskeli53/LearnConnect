package com.gundogar.learnconnect.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.gundogar.learnconnect.model.VideoProgress

@Database(entities = [VideoProgress::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun videoProgressDao(): VideoProgressDao
}