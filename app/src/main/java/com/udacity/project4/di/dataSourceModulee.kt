package com.udacity.project4.di

import com.udacity.project4.data.source.RemainderDataSource
import com.udacity.project4.data.source.local.RemaindersLocalDataSource
import org.koin.dsl.module

/**
 * Created by heinhtet deevvdd@gmail.com on 11,August,2021
 */
val dataSourceModulee = module {
    single<RemainderDataSource> {
        RemaindersLocalDataSource(get())
    }
}