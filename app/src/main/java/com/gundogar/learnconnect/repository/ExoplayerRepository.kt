package com.gundogar.learnconnect.repository

import android.net.Uri
import android.util.Log
import androidx.media3.common.MediaItem
import androidx.media3.common.MimeTypes
import androidx.media3.common.PlaybackException
import androidx.media3.common.Player
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import com.gundogar.learnconnect.database.VideoProgressDao
import com.gundogar.learnconnect.model.VideoProgress
import javax.inject.Inject


const val VIDEO_URL =
    "http://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4"

class ExoplayerRepository @Inject constructor(private val exoPlayer: ExoPlayer, private val videoProgressDao: VideoProgressDao) {

    suspend fun watchVideo(playerView: PlayerView,videoId: String) {
        playerView.player = exoPlayer
        val videoUri = Uri.parse(VIDEO_URL)
        val mediaItem = MediaItem.Builder().setUri(videoUri).setMimeType(MimeTypes.APPLICATION_MP4)
            .build()

        val lastPosition = videoProgressDao.getProgress(videoId) ?: 0L // Pozisyon al


        exoPlayer.apply {
            setMediaItem(mediaItem)
            seekTo(lastPosition)

            addListener(object : Player.Listener {
                override fun onPlayerError(error: PlaybackException) {
                    super.onPlayerError(error)
                    Log.e("Exoplayer error","Error: ${error.message}")
                }

                override fun onPlaybackStateChanged(playbackState: Int) {
                    super.onPlaybackStateChanged(playbackState)
                    when (playbackState) {
                        Player.STATE_IDLE -> Log.d("ExoPlayer State", "Idle")
                        Player.STATE_BUFFERING -> Log.d("ExoPlayer State", "Buffering")
                        Player.STATE_READY -> Log.d("ExoPlayer State", "Ready")
                        Player.STATE_ENDED -> Log.d("ExoPlayer State", "Ended")
                    }
                }

            })

            prepare()
            playWhenReady = true

        }
    }

    suspend fun saveVideoProgress(videoId: String) {
        val currentPosition = exoPlayer.currentPosition
        videoProgressDao.insertProgress(VideoProgress(videoId, currentPosition))
    }
}