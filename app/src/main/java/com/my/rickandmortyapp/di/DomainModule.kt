package com.my.rickandmortyapp.di

import com.my.domain.usecases.GetNextPageUseCase
import org.koin.dsl.module

val domainModule = module {
    factory<GetNextPageUseCase> {
        GetNextPageUseCase(characterRepository = get())
    }
}