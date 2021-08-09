package com.deevvdd.locationremainder.ui.reminderDetail

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.deevvdd.locationremainder.data.source.RemaindersRepository
import com.deevvdd.locationremainder.domain.model.Remainder
import com.deevvdd.locationremainder.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by heinhtet deevvdd@gmail.com on 23,July,2021
 */
@HiltViewModel
class RemainderDetailViewModel @Inject constructor(private val repository: RemaindersRepository) :
    BaseViewModel() {
    private val _remainder = MutableLiveData<Remainder>()

    val remainder: LiveData<Remainder>
        get() = _remainder

    fun getRemainderByPlaceId(placeId: String) {
        viewModelScope.launch {
            repository.getRemainderById(placeId)?.let {
                _remainder.value = it
            }
        }
    }
}