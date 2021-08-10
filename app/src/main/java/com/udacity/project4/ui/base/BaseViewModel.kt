package com.udacity.project4.ui.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.udacity.project4.utils.Event

/**
 * Created by heinhtet deevvdd@gmail.com on 20,July,2021
 */
abstract class BaseViewModel : ViewModel() {
    val showSnackBar = MutableLiveData<Event<String>>()
    val showSnackBarInt = MutableLiveData<Event<Int>>()
    val toastInt = MutableLiveData<Event<Int>>()
}