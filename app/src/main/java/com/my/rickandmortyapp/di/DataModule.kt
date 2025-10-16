package com.my.rickandmortyapp.di

import com.my.data.repository.CharacterRepositoryImpl
import com.my.domain.repository.CharacterRepository
import org.koin.dsl.module

val dataModule = module {
    single<CharacterRepository> {
        CharacterRepositoryImpl(context = get())
    }
}