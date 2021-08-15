package com.udacity.project4.ui.remainderList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.udacity.project4.domain.model.Remainder
import com.udacity.project4.getOrAwaitValue
import com.udacity.project4.source.FakeRepository
import com.udacity.project4.MainCoroutineRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by heinhtet deevvdd@gmail.com on 20,July,2021
 */


@ExperimentalCoroutinesApi
class RemainderViewModelTest {


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: RemainderViewModel
    private lateinit var fakeRepository: FakeRepository

    @Before
    fun setup() {
        fakeRepository = FakeRepository()
        viewModel = RemainderViewModel(fakeRepository)
    }

    @Test
    fun checkRemainders_is_return_false() = runBlockingTest {
        fakeRepository.saveReminder(
            Remainder(
                "1",
                "title",
                "description",
                0.0,
                0.0,
                "place",
            )
        )
        val isEmpty = viewModel.isEmptyRemainders.getOrAwaitValue()
        assertThat(isEmpty, `is`(false))
    }

    @Test
    fun logoutEvent_is_not_null() {
        viewModel.logoutEvent()
        val value = viewModel.logoutEvent.getOrAwaitValue()
        assertThat(value, not(nullValue()))
    }


    @Test
    fun returnNullForRemainderById_whenNull() = runBlockingTest {
        fakeRepository.setShouldReturnError(true)
        val remainder = fakeRepository.getRemainderById("1")
        assertThat(remainder == null, `is`(true))
    }


    @Test
    fun data_showsLoading() = runBlockingTest {
        mainCoroutineRule.pauseDispatcher()
        viewModel.updateLoading(true)
        assertThat(viewModel.loading.getOrAwaitValue(), `is`(true))
    }

}