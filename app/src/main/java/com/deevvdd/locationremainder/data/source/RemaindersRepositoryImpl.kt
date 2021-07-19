package com.deevvdd.locationremainder.data.source

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.deevvdd.locationremainder.data.source.local.RemaindersDatabase
import com.deevvdd.locationremainder.data.source.local.RemaindersLocalDataSource
import com.deevvdd.locationremainder.domain.model.Remainder

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
class RemaindersRepositoryImpl(private val remaindersLocalDataSource: RemainderDataSource) :
    RemaindersRepository {


    companion object {
        @Volatile
        private var INSTANCE: RemaindersRepositoryImpl? = null
        fun getRepository(app: Application): RemaindersRepositoryImpl {
            return INSTANCE ?: synchronized(this) {
                val database = Room.databaseBuilder(
                    app.applicationContext,
                    RemaindersDatabase::class.java, "Remainders.db"
                )
                    .build()
                RemaindersRepositoryImpl(
                    RemaindersLocalDataSource(database.remaindersDao())
                ).also {
                    INSTANCE = it
                }
            }
        }
    }

    override fun observeRemainders() = remaindersLocalDataSource.observeRemainders()

    override suspend fun saveReminder(remainder: Remainder) {
        remaindersLocalDataSource.saveRemainder(remainder)
    }
}