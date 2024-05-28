package com.cha1se.githubsearcher

import android.app.Application
import com.cha1se.githubsearcher.di.dataModule
import com.cha1se.githubsearcher.di.domainModule
import com.cha1se.githubsearcher.di.presentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        GlobalContext.startKoin {
            androidLogger()
            androidContext(this@App)
            modules(dataModule, presentationModule, domainModule)
        }
    }

}