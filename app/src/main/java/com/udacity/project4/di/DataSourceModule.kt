package com.udacity.project4.di

import com.udacity.project4.data.source.RemainderDataSource
import com.udacity.project4.data.source.local.RemaindersLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Singleton
    @Binds
    abstract fun bindRemainderLocalDataSource(
        remaindersLocalDataSource: RemaindersLocalDataSource
    ): RemainderDataSource
}