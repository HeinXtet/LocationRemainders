package com.deevvdd.locationremainder.di

import android.app.Application
import androidx.room.Room
import com.deevvdd.locationremainder.data.source.local.RemaindersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    fun provideDatabase(@ApplicationContext app: Application): RemaindersDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            RemaindersDatabase::class.java,
            "Remainder.db"
        ).build()
    }
}