package com.my.rickandmortyapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import com.my.rickandmortyapp.di.appModule
import com.my.rickandmortyapp.di.dataModule
import com.my.rickandmortyapp.di.domainModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, dataModule, domainModule))
        }
    }
}