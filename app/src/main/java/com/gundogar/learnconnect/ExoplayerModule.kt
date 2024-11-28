package com.gundogar.learnconnect

import android.app.Application
import android.content.Context
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.room.ProvidedAutoMigrationSpec
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
    fun provideExoplayerRepository(exoPlayer: ExoPlayer): ExoplayerRepository {
        return ExoplayerRepository(exoPlayer)
    }



}