package com.udacity.project4.ui.addRemainder

import android.location.Address
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.udacity.project4.MainCoroutineRule
import com.udacity.project4.R
import com.udacity.project4.getOrAwaitValue
import com.udacity.project4.source.FakeRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import com.udacity.project4.domain.model.Point
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.nullValue
import org.hamcrest.core.IsNot.not
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import java.util.*

/**
 * Created by heinhtet deevvdd@gmail.com on 20,July,2021
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class AddRemainderViewModelTest {


    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainCoroutineRule()

//    val coroutineDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()


    private lateinit var addRemainderViewModel: AddRemainderViewModel
    private lateinit var repository: FakeRepository


    @Before
    fun setup() {
        repository = FakeRepository()
        addRemainderViewModel = AddRemainderViewModel(repository)
        //  Dispatchers.setMain(coroutineDispatcher)
    }


//    @After
//    fun tearDownDispatcher() {
//        Dispatchers.resetMain()
//        coroutineDispatcher.cleanupTestCoroutines()
//    }


    @Test
    fun addNewRemainder_setSavedRemainderEvent() {
        addRemainderViewModel.savedRemainder()
        val value = addRemainderViewModel.savedRemainderEvent.getOrAwaitValue()
        assertThat(value, not(nullValue()))
    }

    @Test
    fun checkValidToSaveNewRemainder_return_valid() {
        addRemainderViewModel.title.value = "test title"
        addRemainderViewModel.description.value = "test description"
        addRemainderViewModel.updatePOI(
            Point(LatLng(0.0, 0.0),Address(Locale.ENGLISH))
        )
        val isValid = addRemainderViewModel.isValidToSave()
        assertThat(isValid, `is`(true))
    }
    @Test
    fun checkValidToSaveNewRemainder_return_notValid() {
        addRemainderViewModel.title.value = "test title"
        addRemainderViewModel.description.value = "test description"
        val isValid = addRemainderViewModel.isValidToSave()
        assertThat(addRemainderViewModel.showSnackBarInt.getOrAwaitValue(), not(nullValue()))
        assertThat(isValid, `is`(false))
    }

    @Test
    fun addNewRemainder_dataAndSnackbarUpdated() = mainCoroutineRule.runBlockingTest {
        addRemainderViewModel.title.value = "test title"
        addRemainderViewModel.description.value = "test description"
        addRemainderViewModel.savedRemainder()

        val snackBarValue = addRemainderViewModel.savedRemainderEvent.getOrAwaitValue()
        assertThat(
            snackBarValue.getContentIfNotHandled(),
            `is`(R.string.text_add_new_remainder_sucess)
        )
    }
}


