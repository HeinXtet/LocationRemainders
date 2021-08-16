package com.udacity.project4.source

import com.udacity.project4.TestModelUtils
import com.udacity.project4.domain.model.Remainder
import com.udacity.project4.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.junit.Before
import org.junit.Test

/**
 * Created by heinhtet deevvdd@gmail.com on 10,August,2021
 */


@ExperimentalCoroutinesApi
class DataSourceTest {

    private lateinit var dataSource: FakeDataSource


    @Before
    fun init() {
        dataSource = FakeDataSource()
    }


    @Test
    fun addRemainderAndRetrieve_listNotEmpty() = runBlockingTest {
        dataSource.saveRemainder(TestModelUtils.getTestRemainder())
        val list = dataSource.getRemainders() as Result.Success<List<Remainder>>
        assertThat(list.data.isEmpty(), `is`(false))
    }


    @Test
    fun saveAndRetrieveById() = runBlockingTest {
        val remainder = TestModelUtils.getTestRemainder()
        dataSource.saveRemainder(remainder)

        val savedRemainder = dataSource.getRemainderById(remainder.id)

        assertThat(remainder.title, `is`(savedRemainder?.title))
    }


    @Test
    fun saveAndDeleteByID() = runBlockingTest {
        val remainder = TestModelUtils.getTestRemainder()
        dataSource.saveRemainder(remainder)
        val list = dataSource.getRemainders() as Result.Success<List<Remainder>>
        assertThat(list.data.isNotEmpty(), `is`(true))
        dataSource.deleteRemainder(remainder)

        assertThat(dataSource.getRemainderById(remainder.id) == null, `is`(true))

    }

}