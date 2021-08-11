package com.udacity.project4.di

import com.udacity.project4.data.source.RemaindersRepository
import com.udacity.project4.data.source.RemaindersRepositoryImpl
import org.koin.dsl.module

/**
 * Created by heinhtet deevvdd@gmail.com on 11,August,2021
 */
val repositoryyModule = module {
    single<RemaindersRepository> {
        RemaindersRepositoryImpl(get())
    }
}