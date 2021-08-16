package com.udacity.project4

import android.app.Application
import com.udacity.project4.data.source.RemainderDataSource
import com.udacity.project4.data.source.RemaindersRepository
import com.udacity.project4.data.source.RemaindersRepositoryImpl
import com.udacity.project4.data.source.local.DB
import com.udacity.project4.data.source.local.RemaindersLocalDataSource
import com.udacity.project4.di.dataSourceModulee
import com.udacity.project4.di.databaseModule
import com.udacity.project4.di.repositoryyModule
import com.udacity.project4.di.viewModelModule
import com.udacity.project4.ui.addRemainder.AddRemainderViewModel
import com.udacity.project4.ui.map.MapViewModel
import com.udacity.project4.ui.remainderList.RemainderViewModel
import com.udacity.project4.ui.reminderDetail.RemainderDetailViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import org.koin.dsl.module
import timber.log.Timber

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
class RemainderApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        val myModule = module {
            single<RemainderDataSource> { RemaindersLocalDataSource(get()) }
            single<RemaindersRepository> { RemaindersRepositoryImpl(get() as RemainderDataSource) }
            single { DB.createRemainderDatabase(this@RemainderApp) }
            viewModel {
                RemainderViewModel(
                    get() as RemaindersRepository,
                )
            }
            viewModel { MapViewModel() }
            viewModel { RemainderDetailViewModel(get() as RemaindersRepository) }
            single {
                AddRemainderViewModel(
                    get(),
                    get() as RemaindersRepository,
                )
            }
        }

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@RemainderApp)
            modules(listOf(myModule))
        }
    }
}