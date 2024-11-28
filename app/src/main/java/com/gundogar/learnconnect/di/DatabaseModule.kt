package com.gundogar.learnconnect.di

import android.content.Context
import androidx.room.Room
import com.gundogar.learnconnect.database.AppDatabase
import com.gundogar.learnconnect.database.VideoProgressDao

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
     fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "app_database")
            .fallbackToDestructiveMigration()
            .build()
     }

    @Provides
    @Singleton
    fun provideVideoProgressDao(appDatabase: AppDatabase): VideoProgressDao {
        return appDatabase.videoProgressDao()

    }
}