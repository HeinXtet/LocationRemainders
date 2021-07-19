package com.deevvdd.locationremainder.di

import com.deevvdd.locationremainder.data.source.RemaindersRepository
import com.deevvdd.locationremainder.data.source.RemaindersRepositoryImpl
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by heinhtet deevvdd@gmail.com on 19,July,2021
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    abstract fun bindRemainderRepository(
        repositoryImpl: RemaindersRepositoryImpl
    ): RemaindersRepository

}