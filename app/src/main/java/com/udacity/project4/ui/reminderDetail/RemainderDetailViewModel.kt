package com.udacity.project4.ui.reminderDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.project4.data.source.RemaindersRepository
import com.udacity.project4.domain.model.Remainder
import com.udacity.project4.ui.base.BaseViewModel
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