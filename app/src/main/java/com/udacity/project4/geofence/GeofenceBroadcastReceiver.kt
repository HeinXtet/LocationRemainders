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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
class GeofenceBroadcastReceiver : BroadcastReceiver() {


    lateinit var repository: RemaindersRepository

    override fun onReceive(context: Context, intent: Intent?) {
        repository = (context.applicationContext as RemainderApp).remainderRepository
        val geofencingEvent = GeofencingEvent.fromIntent(intent)
        Timber.d("Geofence Receiver ${geofencingEvent.triggeringGeofences.size}")
        if (geofencingEvent.hasError()) {
            val errorMessage = errorMessage(context, geofencingEvent.errorCode)
            Timber.d("$TAG error ${errorMessage}")
            return
        }

        if (geofencingEvent.geofenceTransition == Geofence.GEOFENCE_TRANSITION_ENTER) {
            Timber.d("$TAG error ${context.getString(R.string.geofence_entered)}")
            val fenceId = when {
                geofencingEvent.triggeringGeofences.isNotEmpty() ->
                    geofencingEvent.triggeringGeofences[0].requestId
                else -> {
                    Timber.d("$TAG No Geofence Trigger Found! Abort mission!")
                    return
                }
            }
            Timber.d("request ID $fenceId")
            CoroutineScope(Dispatchers.IO).launch {
                val remainder = repository.getRemainderById(fenceId)
                Timber.d("Geofence Remainder $remainder")
                if (remainder != null) {
                    val notificationManager = ContextCompat.getSystemService(
                        context,
                        NotificationManager::class.java
                    ) as NotificationManager

                    notificationManager.sendGeofenceEnteredNotification(
                        context,
                        remainder
                    )
                }
            }
        }
    }

    companion object {
        const val TAG = "GeofenceBroadcastReceiver"
    }
}