package com.udacity.project4.source

import com.udacity.project4.TestModelUtils
import com.udacity.project4.data.source.RemaindersRepository
import com.udacity.project4.data.source.RemaindersRepositoryImpl
import com.udacity.project4.domain.model.Remainder
import com.udacity.project4.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.notNullValue
import org.junit.Before
import org.junit.Test

/**
 * Created by heinhtet deevvdd@gmail.com on 21,July,2021
 */
@ExperimentalCoroutinesApi
class RemainderRepositoryTest {

    private lateinit var fakeDataSource: FakeDataSource
    private lateinit var remainderRepository: RemaindersRepository

    @Before
    fun init() {
        fakeDataSource = FakeDataSource()
        remainderRepository = RemaindersRepositoryImpl(fakeDataSource)
    }

    @Test
    fun saveRemainder_return_notEmptyList() = runBlockingTest {
        remainderRepository.saveReminder(TestModelUtils.getTestRemainder())
        val list = remainderRepository.getRemainders() as Result.Success<List<Remainder>>
        assertThat(list.data.isNotEmpty(), `is`(true))
    }

    @Test
    fun saveRemainder_retrieveWithId_return_notNull() = runBlockingTest {
        remainderRepository.saveReminder(TestModelUtils.getTestRemainder())
        val list = remainderRepository.getRemainderById("1")
        assertThat(list, `is`(notNullValue()))
    }

    @Test
    fun saveRemainder_andDeleteRemainder_return_EmptyList() = runBlockingTest {
        remainderRepository.saveReminder(TestModelUtils.getTestRemainder())
        val list = remainderRepository.getRemainders() as Result.Success<List<Remainder>>
        assertThat(list.data.isEmpty(), `is`(false))
        remainderRepository.deleteRemainder(TestModelUtils.getTestRemainder())
        assertThat(list.data.isEmpty(), `is`(true))
    }


    @Test
    fun returnNullForRemainderById_whenError() = runBlockingTest {
        fakeDataSource.setShouldReturnError(true)
        val remainder = remainderRepository.getRemainderById("1")
        assertThat(remainder == null, `is`(true))
    }

}