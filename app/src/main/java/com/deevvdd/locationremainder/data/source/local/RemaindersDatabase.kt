package com.deevvdd.locationremainder.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.deevvdd.locationremainder.domain.model.Remainder

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
@Database(entities = [Remainder::class], version = 1, exportSchema = false)
abstract class RemaindersDatabase : RoomDatabase() {
    abstract fun remaindersDao(): RemaindersDao
}