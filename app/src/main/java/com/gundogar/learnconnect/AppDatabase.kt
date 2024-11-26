package com.gundogar.learnconnect

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [VideoProgress::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun videoProgressDao(): VideoProgressDao
}