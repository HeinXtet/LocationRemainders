package com.udacity.project4.source

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.udacity.project4.data.source.local.RemaindersDao
import com.udacity.project4.data.source.local.RemaindersDatabase
import com.udacity.project4.utils.TestModelUtils
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class RemindersDaoTest {

    private lateinit var database: RemaindersDatabase
    private lateinit var dao: RemaindersDao

    private val reminderData = TestModelUtils.getTestRemainder()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun initDatabase() {
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            RemaindersDatabase::class.java
        ).build()

        dao = database.remaindersDao()
    }

    @After
    fun closeDatabase() {
        database.close()
    }

    @Test
    fun insertIntoDBSucceeds() = runBlockingTest {
        dao.insertRemainder(reminderData)
        assertThat(dao.getRemainders()).hasSize(1)
        assertThat(dao.getRemainders()).contains(reminderData)
    }

//    @Test
//    fun retrieveFromDBSucceeds() = runBlockingTest {
//        dao.saveReminder(reminderData)
//
//        val reminder = dao.getReminderById(reminderData.id)
//
//        assertThat(reminder).isNotNull()
//        assertThat(reminder?.title).isEqualTo(reminderData.title)
//        assertThat(reminder?.description).isEqualTo(reminderData.description)
//        assertThat(reminder?.location).isEqualTo(reminderData.location)
//        assertThat(reminder?.latitude).isEqualTo(reminderData.latitude)
//        assertThat(reminder?.longitude).isEqualTo(reminderData.longitude)
//        assertThat(reminder?.radius).isEqualTo(reminderData.radius)
//    }
//
//    @Test
//    fun deleteFromDBSucceeds() = runBlockingTest {
//        dao.saveReminder(reminderData)
//        assertThat(dao.getReminders()).hasSize(1)
//
//        dao.deleteAllReminders()
//        assertThat(dao.getReminders()).isEmpty()
//    }
}