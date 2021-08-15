package com.udacity.project4.data.source.local

import androidx.lifecycle.LiveData
import com.udacity.project4.data.source.RemainderDataSource
import com.udacity.project4.domain.model.Remainder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import  com.udacity.project4.utils.Result

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */

class RemaindersLocalDataSource constructor(private val dao: RemaindersDao) :
    RemainderDataSource {
    override suspend fun saveRemainder(remainder: Remainder) {
        withContext(Dispatchers.IO) {
            dao.insertRemainder(remainder)
        }
    }

    override fun observeRemainders(): LiveData<List<Remainder>> {
        return dao.observeRemainders()
    }

    override suspend fun deleteRemainder(remainder: Remainder) {
        dao.deleteRemainder(remainder)
    }

    override suspend fun getRemainderById(id: String): Remainder? {
        return dao.getRemainderById(id)
    }

    override suspend fun getRemainders() = withContext(Dispatchers.IO) {
        return@withContext try {
            Result.Success(dao.getRemainders())
        } catch (e: Exception) {
            Result.Error(e.localizedMessage)
        }
    }
}