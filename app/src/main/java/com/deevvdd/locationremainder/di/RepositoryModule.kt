package com.deevvdd.locationremainder.di

import com.deevvdd.locationremainder.data.source.RemaindersRepository
import com.deevvdd.locationremainder.data.source.RemaindersRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindRemainderRepository(
        repositoryImpl: RemaindersRepositoryImpl
    ): RemaindersRepository

}