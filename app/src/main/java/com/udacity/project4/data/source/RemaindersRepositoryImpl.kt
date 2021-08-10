package com.udacity.project4.data.source

import android.content.Context
import androidx.room.Room
import com.udacity.project4.data.source.local.RemaindersDatabase
import com.udacity.project4.data.source.local.RemaindersLocalDataSource
import com.udacity.project4.domain.model.Remainder
import javax.inject.Inject

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
class RemaindersRepositoryImpl @Inject constructor(private val remaindersLocalDataSource: RemainderDataSource) :
    RemaindersRepository {


    companion object {
        @Volatile
        private var INSTANCE: RemaindersRepositoryImpl? = null
        fun getRepository(app: Context): RemaindersRepositoryImpl {
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

    override suspend fun deleteRemainder(remainder: Remainder) {
        remaindersLocalDataSource.deleteRemainder(remainder)
    }

    override suspend fun getRemainderById(id: String): Remainder? {
        return remaindersLocalDataSource.getRemainderById(id)
    }

    override fun getRemainders(): List<Remainder> {
        return remaindersLocalDataSource.getRemainders()
    }
}