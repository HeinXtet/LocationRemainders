package com.deevvdd.locationremainder.ui.remainderList

import android.content.Context
import androidx.lifecycle.*
import com.deevvdd.locationremainder.data.source.RemaindersRepository
import com.deevvdd.locationremainder.domain.model.Remainder
import com.deevvdd.locationremainder.utils.Event
import com.firebase.ui.auth.AuthUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
@HiltViewModel
class RemainderViewModel @Inject constructor(private val repository: RemaindersRepository) :
    ViewModel() {


    init {

        viewModelScope.launch {
            val reminders = repository.getRemainderById("ChIJAAAAAAAAAAARCVVwg5cT7c0")
            Timber.d("Geofence Remainder data $reminders")
        }
    }

    private val _logoutEvent = MutableLiveData<Event<Unit>>()

    private val _remainders: LiveData<List<Remainder>> =
        repository.observeRemainders()

    val remainders: LiveData<List<Remainder>>
        get() = _remainders


    val logoutEvent: LiveData<Event<Unit>>
        get() = _logoutEvent


    private val _isEmptyRemainders = Transformations.map(_remainders) {
        it.isNullOrEmpty()
    }

    val isEmptyRemainders: LiveData<Boolean>
        get() = _isEmptyRemainders


    fun logout(context: Context) {
        AuthUI.getInstance().signOut(context)
            .addOnSuccessListener { _logoutEvent.value = Event(Unit) }
    }

    fun deleteRemainder(remainder: Remainder) {
        CoroutineScope(Dispatchers.IO).launch {
            repository.deleteRemainder(remainder)
        }
    }
}