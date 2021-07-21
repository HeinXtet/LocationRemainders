package com.deevvdd.locationremainder.source

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.deevvdd.locationremainder.TestModelUtils
import com.deevvdd.locationremainder.data.source.local.RemaindersDao
import com.deevvdd.locationremainder.data.source.local.RemaindersDatabase
import com.deevvdd.locationremainder.domain.model.Remainder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers
import org.hamcrest.Matchers.equalTo
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

/**
 * Created by heinhtet deevvdd@gmail.com on 21,July,2021
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RoomDatabaseTest {
    private lateinit var remainderDao: RemaindersDao
    private lateinit var remaindersDatabase: RemaindersDatabase


    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        remaindersDatabase = Room.inMemoryDatabaseBuilder(
            context, RemaindersDatabase::class.java
        ).build()
        remainderDao = remaindersDatabase.remaindersDao()
    }


    @After
    @Throws(IOException::class)
    fun closeDb() {
        remaindersDatabase.close()
    }


    @Test
    @Throws(Exception::class)
    fun saveRemainder_read_returnEqual() = runBlocking {
        val remainder: Remainder = TestModelUtils.getTestRemainder()
        remainderDao.insertRemainder(remainder)
        val byPlaceId = remainderDao.getRemainderById(remainder.placeId)
        assertThat(byPlaceId, equalTo(remainder))
    }


    @Test
    @Throws(Exception::class)
    fun saveRemainder_readList_returnNotEmpty() = runBlocking(Dispatchers.IO) {
        val remainder: Remainder = TestModelUtils.getTestRemainder()
        remainderDao.insertRemainder(remainder)
        val remainders = remainderDao.getRemainders()
        assertThat(remainders.isEmpty(), `is`(false))
    }


    @Test
    @Throws(Exception::class)
    fun deleteAllRemainders_should_return_empty() = runBlocking(Dispatchers.IO) {
        val remainder: Remainder = TestModelUtils.getTestRemainder()
        remainderDao.insertRemainder(remainder)
        val deleted = remainderDao.deleteAllRemainders()
        assertThat(deleted, `is`(1))
    }
}