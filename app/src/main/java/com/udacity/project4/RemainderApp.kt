package com.udacity.project4

import android.app.Application
import com.udacity.project4.data.source.RemaindersRepository
import com.udacity.project4.di.dataSourceModulee
import com.udacity.project4.di.databaseModule
import com.udacity.project4.di.repositoryyModule
import com.udacity.project4.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import timber.log.Timber

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
class RemainderApp : Application() {

    val remainderRepository: RemaindersRepository
        get() = ServiceLocator.provideTasksRepository(this)


    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@RemainderApp)
            modules(
                databaseModule,
                repositoryyModule,
                dataSourceModulee,
                viewModelModule
            )
        }
    }
}