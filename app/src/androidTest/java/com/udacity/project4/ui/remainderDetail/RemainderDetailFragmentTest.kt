package com.udacity.project4.ui.remainderDetail

import android.os.Bundle
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.R
import com.udacity.project4.RemainderApp
import com.udacity.project4.ServiceLocator
import com.udacity.project4.TestAndroidModelUtils
import com.udacity.project4.data.source.RemainderDataSource
import com.udacity.project4.data.source.RemaindersRepository
import com.udacity.project4.data.source.local.RemaindersDatabase
import com.udacity.project4.data.source.local.RemaindersLocalDataSource
import com.udacity.project4.geofence.GeofenceUtils
import com.udacity.project4.source.FakeAndroidRepository
import com.udacity.project4.ui.reminderDetail.RemainderDetailFragment
import com.udacity.project4.ui.reminderDetail.RemainderDetailViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest


/**
 * Created by heinhtet deevvdd@gmail.com on 09,August,2021
 */
@ExperimentalCoroutinesApi
@MediumTest
@RunWith(AndroidJUnit4::class)
class RemainderDetailFragmentTest : KoinTest {

    private lateinit var repository: RemaindersRepository

    @Before
    fun setup() {
        stopKoin()
        val appModule = module {
            viewModel {
                RemainderDetailViewModel(
                    get() as RemaindersRepository
                )
            }
            single {
                Room.databaseBuilder(
                    get(),
                    RemaindersDatabase::class.java, "Remainders.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
            }
            single<RemainderDataSource> { RemaindersLocalDataSource(get()) }
        }
        startKoin {
            androidContext(ApplicationProvider.getApplicationContext())
            modules(listOf(appModule))
        }
        repository = GlobalContext.get().koin.get()

    }


    @After
    fun cleanUpRepository() {
        ServiceLocator.resetRepository()
    }

    @Test
    fun remainderDetails_DisplayedInUi() = runBlockingTest {
        val remainder = TestAndroidModelUtils.getTestRemainder()
        repository.saveReminder(remainder)
        val bundle = Bundle()
        bundle.putString(GeofenceUtils.GEOFENCE_EXTRA, remainder.id)
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