package com.udacity.project4.ui.remainderList

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.udacity.project4.R
import com.udacity.project4.data.source.RemaindersRepository
import com.udacity.project4.domain.model.Remainder
import com.udacity.project4.ui.base.BaseViewModel
import com.udacity.project4.utils.Event
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
class RemainderViewModel constructor(private val repository: RemaindersRepository) :
    BaseViewModel() {

    private val _logoutEvent = MutableLiveData<Event<Unit>>()
    private val _permissionStatus = MutableLiveData<Boolean>()


    private val _remainders: LiveData<List<Remainder>> =
        repository.observeRemainders()

    val remainders: LiveData<List<Remainder>>
        get() = _remainders


    val logoutEvent: LiveData<Event<Unit>>
        get() = _logoutEvent


    fun logoutEvent() {
        _logoutEvent.value = Event(Unit)
    }


    private val _isEmptyRemainders = Transformations.map(_remainders) {
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
    }
}