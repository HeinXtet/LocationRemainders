package com.deevvdd.locationremainder.geofence

import android.content.Context
import com.deevvdd.locationremainder.R
import com.google.android.gms.location.GeofenceStatusCodes
import com.google.android.gms.location.GeofencingClient
import java.util.concurrent.TimeUnit

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
object GeofenceUtils {

    const val GEOFENCE_RADIUS_IN_METERS = 100f
    val GEOFENCE_EXPIRATION_IN_MILLISECONDS: Long = TimeUnit.HOURS.toMillis(1)
    const val GEOFENCE_EXTRA = "GEOFENCE_EXTRA"

    fun errorMessage(context: Context, errorCode: Int): String {
        val resources = context.resources
        return when (errorCode) {
            GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE -> resources.getString(
                R.string.geofence_not_available
            )
            GeofenceStatusCodes.GEOFENCE_TOO_MANY_GEOFENCES -> resources.getString(
                R.string.geofence_too_many_geofences
            )
            GeofenceStatusCodes.GEOFENCE_TOO_MANY_PENDING_INTENTS -> resources.getString(
                R.string.geofence_too_many_pending_intents
            )
            else -> resources.getString(R.string.unknown_geofence_error)
        }
    }

}