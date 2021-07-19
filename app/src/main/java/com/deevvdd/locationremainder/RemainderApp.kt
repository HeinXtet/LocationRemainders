package com.deevvdd.locationremainder

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
@HiltAndroidApp
class RemainderApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}