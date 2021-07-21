package com.deevvdd.locationremainder.data.source.local

import androidx.lifecycle.LiveData
import com.deevvdd.locationremainder.data.source.RemainderDataSource
import com.deevvdd.locationremainder.domain.model.Remainder
import javax.inject.Inject

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */

class RemaindersLocalDataSource @Inject constructor(private val dao: RemaindersDao) :
    RemainderDataSource {
    override suspend fun saveRemainder(remainder: Remainder) {
        dao.insertRemainder(remainder)
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

    override fun getRemainders(): List<Remainder> {
        return dao.getRemainders()
    }
}