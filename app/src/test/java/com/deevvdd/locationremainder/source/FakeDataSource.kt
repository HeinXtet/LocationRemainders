package com.deevvdd.locationremainder.source

import androidx.lifecycle.LiveData
import com.deevvdd.locationremainder.data.source.RemainderDataSource
import com.deevvdd.locationremainder.domain.model.Remainder

/**
 * Created by heinhtet deevvdd@gmail.com on 20,July,2021
 */
class FakeDataSource : RemainderDataSource {
    override suspend fun saveRemainder(remainder: Remainder) {
        TODO("Not yet implemented")
    }

    override fun observeRemainders(): LiveData<List<Remainder>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRemainder(remainder: Remainder) {
        TODO("Not yet implemented")
    }

    override suspend fun getRemainderById(id: String): Remainder? {
        TODO("Not yet implemented")
    }
}