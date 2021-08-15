package com.udacity.project4.source

import androidx.lifecycle.LiveData
import com.udacity.project4.data.source.RemainderDataSource
import com.udacity.project4.domain.model.Remainder
import com.udacity.project4.utils.Result

/**
 * Created by heinhtet deevvdd@gmail.com on 20,July,2021
 */
class FakeDataSource : RemainderDataSource {


    private var remainders: MutableList<Remainder> = mutableListOf()
    private var shouldReturnError = false

    fun setShouldReturnError(shouldReturn: Boolean) {
        this.shouldReturnError = shouldReturn
    }

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
        if (shouldReturnError) {
            return null
        }
        return remainders.findLast { id == it.id }
    }

    override suspend fun getRemainders(): Result<List<Remainder>> {
        if (shouldReturnError) {
            return Result.Error(message = "no remainder found exception")
        }
        return Result.Success(remainders.toList())
    }
}