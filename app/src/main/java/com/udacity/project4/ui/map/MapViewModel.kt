package com.udacity.project4.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.PointOfInterest
import com.udacity.project4.domain.model.Point

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
class MapViewModel : ViewModel() {


    private val _selectedPoi = MutableLiveData<Point>()

    private val _permissionStatus = MutableLiveData<Boolean>()

    val isPermissionGranted: LiveData<Boolean>
        get() = _permissionStatus

    val selectPoi: LiveData<Point>
        get() = _selectedPoi


    private val _isSaveEnabled = Transformations.map(_selectedPoi) {
        it != null
    }

    val isSaveEnabled: LiveData<Boolean>
        get() = _isSaveEnabled

    fun updatePoi(poi: Point) {
        _selectedPoi.value = poi
    }


    fun updatePermissionStatus(status: Boolean) {
        _permissionStatus.value = status
    }
}