package com.udacity.project4.ui.reminderDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.udacity.project4.ServiceLocator
import com.udacity.project4.data.source.RemaindersRepository
import com.udacity.project4.domain.model.Remainder
import com.udacity.project4.ui.base.BaseViewModel
import kotlinx.coroutines.launch

/**
 * Created by heinhtet deevvdd@gmail.com on 23,July,2021
 */
class RemainderDetailViewModel constructor(private val repository: RemaindersRepository) :
    BaseViewModel() {
    private val _remainder = MutableLiveData<Remainder>()

    val remaindersRepository = repository

    val remainder: LiveData<Remainder>
        get() = _remainder

    fun getRemainderByPlaceId(placeId: String) {
        viewModelScope.launch {
            remaindersRepository.getRemainderById(placeId)?.let {
                _remainder.value = it
            }
        }
    }
}