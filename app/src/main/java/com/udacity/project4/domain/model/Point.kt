package com.udacity.project4.domain.model

import android.location.Address
import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.parcelize.Parcelize

/**
 * Created by heinhtet deevvdd@gmail.com on 11,August,2021
 */

@Parcelize
data class Point(
    val latLng: LatLng,
    val address: Address?
) : Parcelable