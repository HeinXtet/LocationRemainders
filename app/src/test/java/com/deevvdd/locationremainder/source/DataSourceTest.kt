package com.deevvdd.locationremainder.source

import com.deevvdd.locationremainder.TestModelUtils
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
        assertThat(dataSource.getRemainders().isNotEmpty(), `is`(true))
    }


    @Test
    fun saveAndRetrieveById() = runBlockingTest {
        val remainder = TestModelUtils.getTestRemainder()
        dataSource.saveRemainder(remainder)

        val savedRemainder = dataSource.getRemainderById(remainder.placeId)

        assertThat(remainder.title, `is`(savedRemainder?.title))
    }


    @Test
    fun saveAndDeleteByID() = runBlockingTest {
        val remainder = TestModelUtils.getTestRemainder()


        dataSource.saveRemainder(remainder)


        assertThat(dataSource.getRemainders().isNotEmpty(), `is`(true))

        dataSource.deleteRemainder(remainder)

        assertThat(dataSource.getRemainderById(remainder.placeId) == null, `is`(true))

    }

}