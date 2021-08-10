package com.udacity.project4.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.PointOfInterest

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
class MapViewModel : ViewModel() {


    private val _selectedPoi = MutableLiveData<PointOfInterest>()

    val selectPoi: LiveData<PointOfInterest>
        get() = _selectedPoi


    private val _isSaveEnabled = Transformations.map(_selectedPoi) {
        it != null
    }

    val isSaveEnabled: LiveData<Boolean>
        get() = _isSaveEnabled

    fun updatePoi(poi: PointOfInterest) {
        _selectedPoi.value = poi
    }
}