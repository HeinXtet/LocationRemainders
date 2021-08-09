package com.deevvdd.locationremainder.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.deevvdd.locationremainder.data.source.RemaindersRepository
import com.deevvdd.locationremainder.domain.model.Remainder
import timber.log.Timber

/**
 * Created by heinhtet deevvdd@gmail.com on 09,August,2021
 */
class FakeAndroidRepository : RemaindersRepository {

    private var remainders: MutableList<Remainder> = mutableListOf()
    private var remainderLiveData = MutableLiveData<List<Remainder>>()


    override fun observeRemainders(): LiveData<List<Remainder>> {
        return remainderLiveData
    }

    override suspend fun saveReminder(remainder: Remainder) {
        remainders.add(remainder)
        remainderLiveData.postValue( remainders)
    }

    override suspend fun deleteRemainder(remainder: Remainder) {
        remainders.remove(remainder)
    }

    override suspend fun getRemainderById(id: String): Remainder? {
        val remainder  = remainders.findLast { it.placeId == id }
        Timber.d("getRemainderById ${remainder?.title}")
        return remainder
    }

    override fun getRemainders(): List<Remainder> {
        return remainders
    }
}