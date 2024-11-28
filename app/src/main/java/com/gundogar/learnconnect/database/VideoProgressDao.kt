package com.gundogar.learnconnect.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gundogar.learnconnect.model.VideoProgress

@Dao
interface VideoProgressDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProgress(videoProgress: VideoProgress)

    @Query("SELECT progress FROM video_progress WHERE videoId = :videoId")
    suspend fun getProgress(videoId: String): Long?

}