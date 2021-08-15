package com.udacity.project4.geofence

import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import com.udacity.project4.R
import com.udacity.project4.RemainderApp
import com.udacity.project4.data.source.RemaindersRepository
import com.udacity.project4.geofence.GeofenceUtils.errorMessage
import com.google.android.gms.location.Geofence
import com.google.android.gms.location.GeofencingEvent
import com.udacity.project4.ui.addRemainder.AddNewRemainderFragment.Companion.ACTION_GEOFENCE_EVENT
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
class GeofenceBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == ACTION_GEOFENCE_EVENT) {
            GeoJobService.enqueueWork(context, intent)
        }
    }

    companion object {
        const val TAG = "GeofenceBroadcastReceiver"
    }
}