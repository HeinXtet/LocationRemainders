package com.deevvdd.locationremainder.data.source

import androidx.lifecycle.LiveData
import com.deevvdd.locationremainder.domain.model.Remainder

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
interface RemainderDataSource {

    suspend fun saveRemainder(remainder: Remainder)

     fun observeRemainders(): LiveData<List<Remainder>>

    suspend fun deleteRemainder(remainder: Remainder)
    suspend fun getRemainderById(id: String) : Remainder?

}