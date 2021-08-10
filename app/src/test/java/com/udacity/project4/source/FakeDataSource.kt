package com.udacity.project4.source

import androidx.lifecycle.LiveData
import com.udacity.project4.data.source.RemainderDataSource
import com.udacity.project4.domain.model.Remainder

/**
 * Created by heinhtet deevvdd@gmail.com on 20,July,2021
 */
class FakeDataSource : RemainderDataSource {


    private var remainders: MutableList<Remainder> = mutableListOf()


    override suspend fun saveRemainder(remainder: Remainder) {
        remainders.add(remainder)
    }

    override fun observeRemainders(): LiveData<List<Remainder>> {
        TODO("Not yet implemented")
    }

    override suspend fun deleteRemainder(remainder: Remainder) {
        remainders.remove(remainder)
    }

    override suspend fun getRemainderById(id: String): Remainder? {
        return remainders.findLast { id == it.placeId }
    }

    override fun getRemainders(): List<Remainder> {
        return remainders.toList()
    }
}