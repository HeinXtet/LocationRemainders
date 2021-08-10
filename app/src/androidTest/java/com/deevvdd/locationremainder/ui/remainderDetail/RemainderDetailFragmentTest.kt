package com.deevvdd.locationremainder.ui.remainderDetail

import android.os.Bundle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.deevvdd.locationremainder.R
import com.deevvdd.locationremainder.TestAndroidModelUtils
import com.deevvdd.locationremainder.data.source.RemaindersRepository
import com.deevvdd.locationremainder.geofence.GeofenceUtils
import com.deevvdd.locationremainder.ui.reminderDetail.RemainderDetailFragment
import com.deevvdd.locationremainder.utils.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject


/**
 * Created by heinhtet deevvdd@gmail.com on 09,August,2021
 */
@HiltAndroidTest
@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
class RemainderDetailFragmentTest {

    @get:Rule()
    var hiltAndroidRule = HiltAndroidRule(this)


    @Inject
    lateinit var repository: RemaindersRepository


    @Before
    fun initRepository() {
        hiltAndroidRule.inject()
//        repository = FakeAndroidRepository()
//        ServiceLocator.repository = repository
    }


    @Test
    fun remainderDetails_DisplayedInUi() = runBlocking {
        val remainder = TestAndroidModelUtils.getTestRemainder()
        repository.saveReminder(remainder)
        val bundle = Bundle()
        bundle.putString(GeofenceUtils.GEOFENCE_EXTRA, remainder.placeId)
        launchFragmentInHiltContainer<RemainderDetailFragment>(bundle, R.style.AppTheme)

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