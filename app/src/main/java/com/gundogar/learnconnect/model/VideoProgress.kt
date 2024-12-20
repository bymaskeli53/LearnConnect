package com.gundogar.learnconnect.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_progress")
data class VideoProgress(
    @PrimaryKey val videoId: String,
    val progress: Long
) {
}