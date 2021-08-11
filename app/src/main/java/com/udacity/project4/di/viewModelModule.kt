package com.udacity.project4.di

import com.udacity.project4.ui.addRemainder.AddRemainderViewModel
import com.udacity.project4.ui.map.MapViewModel
import com.udacity.project4.ui.remainderList.RemainderViewModel
import com.udacity.project4.ui.reminderDetail.RemainderDetailViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
 * Created by heinhtet deevvdd@gmail.com on 11,August,2021
 */
val viewModelModule = module {
    viewModel {
        RemainderViewModel(get())
    }

    viewModel {
        RemainderDetailViewModel(get())
    }

    viewModel {
        AddRemainderViewModel(get())
    }

    viewModel {
        MapViewModel()
    }
}