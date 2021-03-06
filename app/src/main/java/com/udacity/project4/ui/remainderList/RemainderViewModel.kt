package com.udacity.project4.ui.remainderList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.viewModelScope
import com.udacity.project4.R
import com.udacity.project4.data.source.RemaindersRepository
import com.udacity.project4.domain.model.Remainder
import com.udacity.project4.ui.base.BaseViewModel
import com.udacity.project4.utils.Event
import com.firebase.ui.auth.AuthUI
import com.udacity.project4.data.source.local.RemaindersDao
import com.udacity.project4.utils.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
class RemainderViewModel constructor(private val repository: RemaindersRepository) :
    BaseViewModel() {
    val remindersList = MutableLiveData<List<Remainder>>()
    private val _logoutEvent = MutableLiveData<Event<Unit>>()
    val loading = MutableLiveData<Boolean>(false)


    private val _remainders: LiveData<List<Remainder>> =
        repository.observeRemainders()

    val remainders: LiveData<List<Remainder>>
        get() = _remainders


    val logoutEvent: LiveData<Event<Unit>>
        get() = _logoutEvent


    fun logoutEvent() {
        _logoutEvent.value = Event(Unit)
    }


    private val _isEmptyRemainders = Transformations.map(remindersList) {
        it.isNullOrEmpty()
    }


    val isEmptyRemainders: LiveData<Boolean>
        get() = _isEmptyRemainders


    fun logout(context: Context) {
        AuthUI.getInstance().signOut(context)
            .addOnSuccessListener { logoutEvent() }
    }

    fun deleteRemainder(remainder: Remainder) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteRemainder(remainder)
        }
        showSnackBarInt.value = Event(R.string.remainder_deleted)
        loadReminders()
    }


    fun loadReminders() {
        loading.value = true
        viewModelScope.launch {
            //interacting with the dataSource has to be through a coroutine
            val result = repository.getRemainders()
            loading.postValue(false)
            when (result) {
                is Result.Success<*> -> {
                    val dataList = ArrayList<Remainder>()
                    @Suppress("UNCHECKED_CAST")
                    dataList.addAll((result.data as List<Remainder>).map { reminder ->
                        //map the reminder data from the DB to the be ready to be displayed on the UI
                        Remainder(
                            title = reminder.title,
                            description = reminder.description,
                            latitude = reminder.latitude,
                            longitude = reminder.longitude,
                            place = reminder.place,
                            id = reminder.id
                        )
                    })
                    remindersList.value = dataList
                }
                is Result.Error ->
                    showSnackBar.value = Event(result.message.orEmpty())
            }
            invalidateShowNoData()
        }
    }

    /**
     * Inform the user that there's not any data if the remindersList is empty
     */
    private fun invalidateShowNoData() {
        showNoData.value = remindersList.value == null || remindersList.value!!.isEmpty()
    }

}