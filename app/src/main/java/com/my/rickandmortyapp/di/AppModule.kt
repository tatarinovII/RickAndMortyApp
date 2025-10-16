package com.my.rickandmortyapp.di
import com.my.rickandmortyapp.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    viewModel<MainViewModel> {
        MainViewModel(
            getNextPageUseCase = get()
        )
    }
}