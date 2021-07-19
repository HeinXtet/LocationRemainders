package com.deevvdd.locationremainder.data.source.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.deevvdd.locationremainder.domain.model.Remainder

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
@Dao
interface RemaindersDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemainder(remainder: Remainder)

    @Query("SELECT * FROM remainder")
    fun observeRemainders(): LiveData<List<Remainder>>
}