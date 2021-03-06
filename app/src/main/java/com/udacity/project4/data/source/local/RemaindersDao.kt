package com.udacity.project4.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.udacity.project4.domain.model.Remainder

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
@Dao
interface RemaindersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemainder(remainder: Remainder)

    @Query("SELECT * FROM remainder")
    fun observeRemainders(): LiveData<List<Remainder>>

    @Query("SELECT * FROM remainder")
    fun getRemainders(): List<Remainder>

    @Delete
    fun deleteRemainder(remainder: Remainder)


    @Query("SELECT * FROM remainder WHERE remainderId = :id LIMIT 1")
    suspend fun getRemainderById(id: String): Remainder?


    @Query("DELETE FROM remainder")
    fun deleteAllRemainders(): Int

    @Update
    fun updateRemainder(remainder: Remainder)
}