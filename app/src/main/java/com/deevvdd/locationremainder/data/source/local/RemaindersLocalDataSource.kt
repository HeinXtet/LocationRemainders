package com.deevvdd.locationremainder.data.source.local

import androidx.lifecycle.LiveData
import com.deevvdd.locationremainder.data.source.RemainderDataSource
import com.deevvdd.locationremainder.domain.model.Remainder

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */

class RemaindersLocalDataSource(private val dao: RemaindersDao) : RemainderDataSource {
    override suspend fun saveRemainder(remainder: Remainder) {
        dao.insertRemainder(remainder)
    }

    override fun observeRemainders(): LiveData<List<Remainder>> {
        return dao.observeRemainders()
    }
}