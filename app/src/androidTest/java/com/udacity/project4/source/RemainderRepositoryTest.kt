package com.udacity.project4.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.utils.MainCoroutineRule
import com.udacity.project4.utils.TestModelUtils
import com.udacity.project4.data.source.RemaindersRepository
import com.udacity.project4.data.source.RemaindersRepositoryImpl
import com.udacity.project4.data.source.local.RemaindersDatabase
import com.udacity.project4.data.source.local.RemaindersLocalDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by heinhtet deevvdd@gmail.com on 21,July,2021
 */
@MediumTest
@RunWith(AndroidJUnit4::class)
@ExperimentalCoroutinesApi
class RemainderTestRepositoryTest {

    private lateinit var remainderRepository: RemaindersRepository
    private lateinit var database: RemaindersDatabase

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    @Before
    fun initDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemaindersDatabase::class.java
        ).allowMainThreadQueries().build()

        val dataSource = RemaindersLocalDataSource(database.remaindersDao())
        remainderRepository = RemaindersRepositoryImpl(dataSource)
    }

    @Test
    fun saveRemainderSuccess() = runBlocking {
        remainderRepository.saveReminder(TestModelUtils.getTestRemainder())
        val result = remainderRepository.getRemainderById(TestModelUtils.getTestRemainder().id)
        assertThat(result?.id, `is`(TestModelUtils.getTestRemainder().id))
        assertThat(result?.title, `is`(notNullValue()))
        assertThat(result?.description, `is`(notNullValue()))
        assertThat(result?.latitude, `is`(notNullValue()))
        assertThat(result?.longitude, `is`(notNullValue()))
    }



    @Test
    fun saveRemainder_return_notEmptyList() = runBlocking {
        remainderRepository.saveReminder(TestModelUtils.getTestRemainder())
        val list = remainderRepository.getRemainders()
        assertThat(list.isNotEmpty(), `is`(true))
    }

    @Test
    fun saveRemainder_retrieveWithId_return_notNull() = runBlocking {
        remainderRepository.saveReminder(TestModelUtils.getTestRemainder())
        val list = remainderRepository.getRemainderById("1")
        assertThat(list, `is`(notNullValue()))
    }

    @Test
    fun saveRemainder_andDeleteRemainder_return_EmptyList() = runBlocking {
        remainderRepository.saveReminder(TestModelUtils.getTestRemainder())
        assertThat(remainderRepository.getRemainders().isEmpty(), `is`(false))
        remainderRepository.deleteRemainder(TestModelUtils.getTestRemainder())
        assertThat(remainderRepository.getRemainders().isEmpty(), `is`(true))
    }
}

