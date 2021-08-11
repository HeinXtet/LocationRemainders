package com.udacity.project4.di

import androidx.room.Room
import com.udacity.project4.data.source.local.RemaindersDao
import com.udacity.project4.data.source.local.RemaindersDatabase
import org.koin.dsl.module

/**
 * Created by heinhtet deevvdd@gmail.com on 11,August,2021
 */
val databaseModule = module {
    single<RemaindersDatabase> {
        Room.databaseBuilder(get(), RemaindersDatabase::class.java, "Remainders.db")
            .fallbackToDestructiveMigration().build()
    }

    single<RemaindersDao> {
        (get() as RemaindersDatabase).remaindersDao()
    }
}