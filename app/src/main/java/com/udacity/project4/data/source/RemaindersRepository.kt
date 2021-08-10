package com.udacity.project4.data.source

import androidx.lifecycle.LiveData
import com.udacity.project4.domain.model.Remainder

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
interface RemaindersRepository {

    fun observeRemainders(): LiveData<List<Remainder>>

    suspend fun saveReminder(remainder: Remainder)

    suspend fun deleteRemainder(remainder: Remainder)

    suspend fun getRemainderById(id  : String) : Remainder?

    fun getRemainders() : List<Remainder>

}