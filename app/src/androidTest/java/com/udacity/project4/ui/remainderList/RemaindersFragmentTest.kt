package com.udacity.project4.ui.remainderList

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.R
import com.udacity.project4.ServiceLocator
import com.udacity.project4.TestAndroidModelUtils
import com.udacity.project4.data.source.RemainderDataSource
import com.udacity.project4.data.source.RemaindersRepository
import com.udacity.project4.data.source.RemaindersRepositoryImpl
import com.udacity.project4.data.source.local.DB
import com.udacity.project4.data.source.local.RemaindersDao
import com.udacity.project4.data.source.local.RemaindersLocalDataSource
import com.udacity.project4.source.FakeAndroidRepository
import com.udacity.project4.ui.reminderDetail.RemainderDetailViewModel
import com.udacity.project4.utils.*
import kotlinx.coroutines.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Created by heinhtet deevvdd@gmail.com on 10,August,2021
 */
//@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RemaindersFragmentTest : TestWatcher() {

    private lateinit var repository: RemaindersRepository
    private val dataBindingIdlingResource = DataBindingIdlingResource()


    @Before
    fun setup() {
        stopKoin()
        val appModule = module {
            viewModel {
                RemainderViewModel(
                    get() as RemaindersRepository
                )
            }
            single {
                DB.createRemainderDatabase(ApplicationProvider.getApplicationContext())
            }
            single<RemaindersRepository> { RemaindersRepositoryImpl(get() as RemainderDataSource) }
            single<RemainderDataSource> { RemaindersLocalDataSource(get() as RemaindersDao) }
        }
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(listOf(appModule))
        }
        repository = GlobalContext.get().koin.get()
    }

    @Test
    fun clickTask_navigateToDetailFragment() = runBlocking {
        val navController = mock(NavController::class.java)
        val remainder = TestAndroidModelUtils.getTestRemainder()
        repository.saveReminder(remainder)
        val scenario =
            launchFragmentInContainer<RemaindersFragment>(
                Bundle.EMPTY,
                R.style.AppTheme
            ).onFragment {
                Navigation.setViewNavController(it.view!!, navController)
            }
        dataBindingIdlingResource.monitorFragment(scenario)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
        onView(withId(R.id.rvRemainders))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
            )
        verify(navController).safeNavigate(
            RemaindersFragmentDirections.actionRemaindersFragmentToRemainderDetailFragment(
                remainder.id
            )
        )
    }

    @Test
    fun clickTask_navigateToAddNewRemainderFragment() = runBlocking {
        val navController = mock(NavController::class.java)
        val remainder = TestAndroidModelUtils.getTestRemainder()
        repository.saveReminder(remainder)
        val scenario =
            launchFragmentInContainer<RemaindersFragment>(Bundle(), R.style.AppTheme).onFragment {
                Navigation.setViewNavController(it.view!!, navController)
            }
        dataBindingIdlingResource.monitorFragment(scenario)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
        onView(withId(R.id.fabAddRemainder)).perform(click())
        verify(navController).safeNavigate(
            RemaindersFragmentDirections.actionRemaindersFragmentToAddNewRemainder()
        )
    }


    @Test
    fun delete_remainder_FromRecyclerView_Show_SnackBar() = runBlocking {
        val navController = mock(NavController::class.java)
        withTimeoutOrNull(1300L) {
            repository.saveReminder(TestAndroidModelUtils.getTestRemainder())
        }
        val scenario =
            launchFragmentInContainer<RemaindersFragment>(Bundle(), R.style.AppTheme).onFragment {
                Navigation.setViewNavController(it.view!!, navController)
            }
        dataBindingIdlingResource.monitorFragment(scenario)
        scenario.onFragment {
            Navigation.setViewNavController(it.view!!, navController)
        }
        onView(withId(R.id.rvRemainders)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                RecyclerViewAction.clickChildViewWithId(R.id.ivDelete)
            )
        )
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(ViewAssertions.matches(ViewMatchers.withText(R.string.remainder_deleted)))
        Unit
    }
}