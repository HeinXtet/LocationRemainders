package com.udacity.project4

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.Room
import com.udacity.project4.data.source.RemaindersRepository
import com.udacity.project4.data.source.RemaindersRepositoryImpl
import com.udacity.project4.data.source.local.RemaindersDatabase
import com.udacity.project4.data.source.local.RemaindersLocalDataSource

/**
 * Created by heinhtet deevvdd@gmail.com on 09,August,2021
 */
object ServiceLocator {
    private var database: RemaindersDatabase? = null

    @Volatile
    var repository: RemaindersRepository? = null
        @VisibleForTesting set
    private val lock = Any()


    fun provideTasksRepository(context: Context): RemaindersRepository {
        synchronized(this) {
            return repository ?: createTasksRepository(context)
        }
    }

    private fun createTasksRepository(context: Context): RemaindersRepository {
        val newRepo = RemaindersRepositoryImpl(createTaskLocalDataSource(context))
        repository = newRepo
        return newRepo
    }

    private fun createTaskLocalDataSource(context: Context): RemaindersLocalDataSource {
        val database = database ?: createDataBase(context)
        return RemaindersLocalDataSource(database.remaindersDao())
    }

    private fun createDataBase(context: Context): RemaindersDatabase {
        val result = Room.databaseBuilder(
            context.applicationContext,
            RemaindersDatabase::class.java, "Remainders.db"
        ).build()
        database = result
        return result
    }


    @VisibleForTesting
    fun resetRepository() {
        synchronized(lock) {
            database?.apply {
                clearAllTables()
                close()
            }
            database = null
            repository = null
        }
    }


}