package com.udacity.project4.data.source

import androidx.lifecycle.LiveData
import com.udacity.project4.domain.model.Remainder
import  com.udacity.project4.utils.Result

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
interface RemainderDataSource {

    suspend fun saveRemainder(remainder: Remainder)

    fun observeRemainders(): LiveData<List<Remainder>>


    suspend fun deleteRemainder(remainder: Remainder)
    suspend fun getRemainderById(id: String): Result<Remainder>


    suspend fun getRemainders(): Result<List<Remainder>>
    suspend fun deleteAllRemainder()

}