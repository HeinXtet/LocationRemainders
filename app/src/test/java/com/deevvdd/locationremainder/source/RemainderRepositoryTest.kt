package com.deevvdd.locationremainder.source

import com.deevvdd.locationremainder.TestModelUtils
import com.deevvdd.locationremainder.data.source.RemaindersRepository
import com.deevvdd.locationremainder.data.source.RemaindersRepositoryImpl
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
        val list = remainderRepository.getRemainders()
        assertThat(list.isNotEmpty(), `is`(true))
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
        assertThat(remainderRepository.getRemainders().isEmpty(), `is`(false))
        remainderRepository.deleteRemainder(TestModelUtils.getTestRemainder())
        assertThat(remainderRepository.getRemainders().isEmpty(), `is`(true))
    }

}