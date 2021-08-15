package com.udacity.project4.data.source.local

import android.content.Context
import androidx.room.Room

/**
 * Created by heinhtet deevvdd@gmail.com on 15,August,2021
 */
object DB {
    fun createRemainderDatabase(context: Context): RemaindersDao {
        return Room.databaseBuilder(
            context.applicationContext,
            RemaindersDatabase::class.java, "Remainders.db"
        ).build().remaindersDao()
    }
}