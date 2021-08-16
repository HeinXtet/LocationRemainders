package com.udacity.project4

import android.view.View
import androidx.test.core.app.ActivityScenario
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import androidx.test.espresso.Espresso.closeSoftKeyboard
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.UiController
import androidx.test.espresso.ViewAction
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.udacity.project4.data.source.RemainderDataSource
import com.udacity.project4.data.source.RemaindersRepository
import com.udacity.project4.data.source.RemaindersRepositoryImpl
import com.udacity.project4.data.source.local.DB
import com.udacity.project4.data.source.local.RemaindersLocalDataSource
import com.udacity.project4.ui.addRemainder.AddRemainderViewModel
import com.udacity.project4.ui.map.MapViewModel
import com.udacity.project4.ui.remainderList.RemainderViewModel
import com.udacity.project4.util.EspressoIdlingResource
import com.udacity.project4.utils.DataBindingIdlingResource
import com.udacity.project4.utils.monitorActivity
import kotlinx.coroutines.runBlocking
import org.hamcrest.Matcher
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.AutoCloseKoinTest
import org.koin.test.get

@RunWith(AndroidJUnit4::class)
@LargeTest
//END TO END test to black box test the app
class MainActivityTest :
    AutoCloseKoinTest() {

    private lateinit var repository: RemainderDataSource

    private val dataBindingIdlingResource = DataBindingIdlingResource()

    @Before
    fun registerIdlingResource(): Unit = IdlingRegistry.getInstance().run {
        register(EspressoIdlingResource.countingIdlingResource)
        register(dataBindingIdlingResource)
    }

    @After
    fun unregisterIdlingResource(): Unit = IdlingRegistry.getInstance().run {
        unregister(EspressoIdlingResource.countingIdlingResource)
        unregister(dataBindingIdlingResource)
    }

    /**
     * As we use Koin as a Service Locator Library to develop our code, we'll also use Koin to test our code.
     * at this step we will initialize Koin related code to be able to use it in out testing.
     */
    @Before
    fun init() {
        stopKoin()//stop the original app koin
        val myModule = module {
            viewModel {
                RemainderViewModel(
                    get() as RemaindersRepository
                )
            }


            viewModel { MapViewModel() }

            single {
                AddRemainderViewModel(
                    get(),
                    get() as RemaindersRepository
                )
            }

            single<RemaindersRepository> { RemaindersRepositoryImpl(get()) }
            single<RemainderDataSource> { RemaindersLocalDataSource(get()) }
            single { DB.createRemainderDatabase(getApplicationContext()) }
        }

        //declare a new koin module
        startKoin {
            androidContext(getApplicationContext())
            modules(listOf(myModule))
        }

        //Get our real repository
        repository = get()

        //clear the data to start fresh
        runBlocking {
            repository.deleteAllRemainder()
        }
    }

    @Test
    fun saveRemainderPerform() {
        val scenario = ActivityScenario.launch(MainActivity::class.java)
        dataBindingIdlingResource.monitorActivity(scenario)

        onView(withId(R.id.tvEmpty)).check(matches(withEffectiveVisibility(Visibility.VISIBLE)))
        onView(withId(R.id.fabAddRemainder)).perform(click())
        onView(withId(R.id.edTitle)).perform(typeText("Title"))
        onView(withId(R.id.edDescription)).perform(typeText("Description"))

        closeSoftKeyboard()

        onView(withId(R.id.btnLocation)).perform(click())

        onView(withId(R.id.mapView)).perform(click())
        onView(withId(R.id.btnSave)).perform(click())

//        onView(withId(R.id.tvSelectedPoi)).check(matches(isDisplayed()))


//        onView(isRoot()).perform(waitFor(2000))

//        onView(withId(R.id.fabSaveRemainder)).perform(click())
//
//        onView(withId(R.id.tvEmpty)).check(matches(withEffectiveVisibility(Visibility.GONE)))
//        onView(withText("Title")).check(matches(isDisplayed()))
//        onView(withText("Description")).check(matches(isDisplayed()))
    }
}

fun waitFor(delay: Long): ViewAction? {
    return object : ViewAction {
        override fun getConstraints(): Matcher<View> = isRoot()
        override fun getDescription(): String = "wait for $delay milliseconds"
        override fun perform(uiController: UiController, v: View?) {
            uiController.loopMainThreadForAtLeast(delay)
        }
    }
}