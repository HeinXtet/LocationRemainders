package com.deevvdd.locationremainder.ui.remainderList

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.deevvdd.locationremainder.R
import com.deevvdd.locationremainder.TestAndroidModelUtils
import com.deevvdd.locationremainder.data.source.RemaindersRepository
import com.deevvdd.locationremainder.utils.RecyclerViewAction
import com.deevvdd.locationremainder.utils.launchFragmentInHiltContainer
import com.deevvdd.locationremainder.utils.safeNavigate
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

/**
 * Created by heinhtet deevvdd@gmail.com on 10,August,2021
 */
@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class RemaindersFragmentTest : TestWatcher() {

    @get:Rule()
    var hiltAndroidRule = HiltAndroidRule(this)


    @Inject
    lateinit var repository: RemaindersRepository


    @Before
    fun initRepository() {
        hiltAndroidRule.inject()
    }

    @Test
    fun clickTask_navigateToDetailFragment() = runBlocking {
        val navController = mock(NavController::class.java)
        val remainder = TestAndroidModelUtils.getTestRemainder()
        repository.saveReminder(remainder)
        launchFragmentInHiltContainer<RemaindersFragment>(Bundle(), R.style.AppTheme) {
            Navigation.setViewNavController(this.view!!, navController)
        }
        onView(withId(R.id.rvRemainders))
            .perform(
                RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click())
            )
        verify(navController).safeNavigate(
            RemaindersFragmentDirections.actionRemaindersFragmentToRemainderDetailFragment(
                remainder.placeId
            )
        )
    }

    @Test
    fun clickTask_navigateToAddNewRemainderFragment() = runBlocking {
        val navController = mock(NavController::class.java)
        val remainder = TestAndroidModelUtils.getTestRemainder()
        repository.saveReminder(remainder)
        launchFragmentInHiltContainer<RemaindersFragment>(Bundle(), R.style.AppTheme) {
            Navigation.setViewNavController(this.view!!, navController)
        }
        onView(withId(R.id.fabAddRemainder)).perform(click())
        verify(navController).safeNavigate(
            RemaindersFragmentDirections.actionRemaindersFragmentToAddNewRemainder()
        )
    }


    @Test
    fun delete_remainder_FromRecyclerView_Show_SnackBar() = runBlocking {
        withTimeoutOrNull(1300L) {
            repository.saveReminder(TestAndroidModelUtils.getTestRemainder())
        }
        launchFragmentInHiltContainer<RemaindersFragment>(Bundle(), R.style.AppTheme)
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