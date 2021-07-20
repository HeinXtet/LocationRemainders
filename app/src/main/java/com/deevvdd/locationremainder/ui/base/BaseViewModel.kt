package com.deevvdd.locationremainder.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.deevvdd.locationremainder.utils.Event

/**
 * Created by heinhtet deevvdd@gmail.com on 20,July,2021
 */
abstract class BaseViewModel : ViewModel() {
    val showSnackBar = MutableLiveData<Event<String>>()
    val showSnackBarInt = MutableLiveData<Event<Int>>()
    val toastInt = MutableLiveData<Event<Int>>()
}