package com.deevvdd.locationremainder.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deevvdd.locationremainder.data.source.RemaindersRepository
import com.deevvdd.locationremainder.domain.model.Remainder

/**
 * Created by heinhtet deevvdd@gmail.com on 20,July,2021
 */
class FakeRepository : RemaindersRepository {

    private var remainders: MutableList<Remainder> = mutableListOf()
    private var remainderLiveData = MutableLiveData<List<Remainder>>()


    override fun observeRemainders(): LiveData<List<Remainder>> {
        return remainderLiveData
    }

    override suspend fun saveReminder(remainder: Remainder) {
        remainders.add(remainder)
        remainderLiveData.value = remainders
    }

    override suspend fun deleteRemainder(remainder: Remainder) {
        TODO("Not yet implemented")
    }

    override suspend fun getRemainderById(id: String): Remainder? {
        TODO("Not yet implemented")
    }
}