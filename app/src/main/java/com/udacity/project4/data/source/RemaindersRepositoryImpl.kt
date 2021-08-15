package com.udacity.project4.data.source

import android.content.Context
import androidx.room.Room
import com.udacity.project4.data.source.local.RemaindersDatabase
import com.udacity.project4.data.source.local.RemaindersLocalDataSource
import com.udacity.project4.domain.model.Remainder
import com.udacity.project4.utils.Result

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
class RemaindersRepositoryImpl constructor(private val remaindersLocalDataSource: RemainderDataSource) :
    RemaindersRepository {

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

    override suspend fun getRemainders(): Result<List<Remainder>> {
        return remaindersLocalDataSource.getRemainders()
    }
}