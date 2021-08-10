package com.udacity.project4.ui.addRemainder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import com.udacity.project4.R
import com.udacity.project4.utils.launchFragmentInHiltContainer
import com.udacity.project4.utils.safeNavigate
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

/**
 * Created by heinhtet deevvdd@gmail.com on 10,August,2021
 */
@HiltAndroidTest
@MediumTest
@RunWith(AndroidJUnit4::class)
class AddNewRemainderFragmentTest {
    @get:Rule()
    var hiltAndroidRule = HiltAndroidRule(this)

    @Test
    fun addNewRemainderFragment_isDisplayedSnackBarAndNavigateMap() {
        val mockNavController = mock(NavController::class.java)

        launchFragmentInHiltContainer<AddNewRemainderFragment>(Bundle(), R.style.AppTheme) {
            Navigation.setViewNavController(this.view!!, mockNavController)
        }

        onView(withId(R.id.fabAddRemainder)).perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.error_message_title_empty)))


        onView(withId(R.id.edTitle)).perform(typeText("TITLE"), closeSoftKeyboard())
        onView(withId(R.id.fabAddRemainder)).perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.error_message_description_empty)))


        onView(withId(R.id.edDescription)).perform(typeText("DESCRIPTION"), closeSoftKeyboard())
        onView(withId(R.id.fabAddRemainder)).perform(click())
        onView(withId(com.google.android.material.R.id.snackbar_text))
            .check(matches(withText(R.string.error_message_poi_empty)))

        onView(withId(R.id.btnLocation)).perform(click())

        verify(mockNavController).safeNavigate(AddNewRemainderFragmentDirections.actionAddNewRemainderToMapFragment())
        Thread.sleep(2000)
    }
}