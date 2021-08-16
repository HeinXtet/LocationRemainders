package com.udacity.project4.ui.remainderList

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.udacity.project4.domain.model.Remainder
import com.udacity.project4.getOrAwaitValue
import com.udacity.project4.source.FakeRepository
import com.udacity.project4.MainCoroutineRule
import com.udacity.project4.utils.Result
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.context.stopKoin

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
        stopKoin()
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
        viewModel.loadReminders()
        val isEmpty = viewModel.showNoData.getOrAwaitValue()
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
        assertThat(remainder is Result.Error, `is`(true))
    }


    @Test
    fun data_showsLoading() = runBlockingTest {
        mainCoroutineRule.pauseDispatcher()
        viewModel.loadReminders()
        assertThat(viewModel.loading.getOrAwaitValue(), `is`(true))
    }

    @Test
    fun remindersUnavailable_showsError() = runBlockingTest {
        fakeRepository.setShouldReturnError(true)
        viewModel.loadReminders()
        assertThat(viewModel.showSnackBar.getOrAwaitValue(), `is`(notNullValue()))
    }
}