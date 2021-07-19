package com.deevvdd.locationremainder.di

import com.deevvdd.locationremainder.data.source.RemainderDataSource
import com.deevvdd.locationremainder.data.source.local.RemaindersLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindRemainderLocalDataSource(
        remaindersLocalDataSource: RemaindersLocalDataSource
    ): RemainderDataSource
}