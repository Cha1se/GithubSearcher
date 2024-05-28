package com.cha1se.githubsearcher.di

import com.cha1se.githubsearcher.presentation.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val presentationModule = module {
    viewModel<MainViewModel> { MainViewModel(dataRepo = get()) }
}