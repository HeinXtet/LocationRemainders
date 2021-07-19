package com.deevvdd.locationremainder.di

import android.content.Context
import androidx.room.Room
import com.deevvdd.locationremainder.data.source.local.RemaindersDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext app: Context): RemaindersDatabase {
        return Room.databaseBuilder(
            app.applicationContext,
            RemaindersDatabase::class.java,
            "Remainders.db"
        ).build()
    }


    @Singleton
    @Provides
    fun provideRemainderDao(db: RemaindersDatabase) = db.remaindersDao()
}