package com.deevvdd.locationremainder.ui.addRemainder

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.deevvdd.locationremainder.getOrAwaitValue
import com.deevvdd.locationremainder.source.FakeRepository
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import org.hamcrest.Matchers.`is`
import org.hamcrest.Matchers.nullValue
import org.hamcrest.core.IsNot.not
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * Created by heinhtet deevvdd@gmail.com on 20,July,2021
 */
@RunWith(AndroidJUnit4::class)
class AddRemainderViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var addRemainderViewModel: AddRemainderViewModel
    private lateinit var repository: FakeRepository


    @Before
    fun setup() {
        repository = FakeRepository()
        addRemainderViewModel = AddRemainderViewModel(repository)
    }

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
            PointOfInterest(
                LatLng(0.0, 0.0),
                "placeId",
                "testPlace"
            )
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
}