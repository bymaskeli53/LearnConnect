package com.gundogar.learnconnect.di

import android.content.Context
import androidx.media3.exoplayer.ExoPlayer
import com.gundogar.learnconnect.repository.ExoplayerRepository

import com.gundogar.learnconnect.database.VideoProgressDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object ExoplayerModule {

    @Provides
    @Singleton
    fun provideExoPlayer(@ApplicationContext app: Context): ExoPlayer {
        return ExoPlayer.Builder(app).build()
    }

    @Provides
    @Singleton
    fun provideExoplayerRepository(exoPlayer: ExoPlayer,videoProgressDao: VideoProgressDao): ExoplayerRepository {
        return ExoplayerRepository(exoPlayer, videoProgressDao)
    }



}