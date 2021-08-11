package com.udacity.project4.ui.remainderDetail

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.R
import com.udacity.project4.RemainderApp
import com.udacity.project4.ServiceLocator
import com.udacity.project4.TestAndroidModelUtils
import com.udacity.project4.data.source.RemaindersRepository
import com.udacity.project4.geofence.GeofenceUtils
import com.udacity.project4.ui.reminderDetail.RemainderDetailFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.core.context.unloadKoinModules
import org.koin.test.KoinTest
import org.koin.test.inject


/**
 * Created by heinhtet deevvdd@gmail.com on 09,August,2021
 */
@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
class RemainderDetailFragmentTest : KoinTest {

    private val repository: RemaindersRepository by inject()

    @Before
    fun initRepository() {

    }


    @After
    fun cleanUpRepository() {
        ServiceLocator.resetRepository()
    }

    @Test
    fun remainderDetails_DisplayedInUi() = runBlocking {
        val remainder = TestAndroidModelUtils.getTestRemainder()
        repository.saveReminder(remainder)
        val bundle = Bundle()
        bundle.putString(GeofenceUtils.GEOFENCE_EXTRA, remainder.placeId)
        launchFragmentInContainer<RemainderDetailFragment>(bundle, R.style.AppTheme)

        onView(withId(R.id.tvTitleValue)).check(matches(isDisplayed()))
        onView(withId(R.id.tvTitleValue)).check(matches(withText(remainder.title)))

        onView(withId(R.id.tvDescriptionValue)).check(matches(isDisplayed()))
        onView(withId(R.id.tvDescriptionValue)).check(matches(withText(remainder.description)))

        onView(withId(R.id.tvPlaceValue)).check(matches(isDisplayed()))
        onView(withId(R.id.tvPlaceValue)).check(matches(withText(remainder.place)))

        onView(withId(R.id.tvLatLngValue)).check(matches(isDisplayed()))
        onView(withId(R.id.tvLatLngValue)).check(matches(withText("${remainder.latitude}, ${remainder.longitude}")))

        Thread.sleep(2000)

    }
}