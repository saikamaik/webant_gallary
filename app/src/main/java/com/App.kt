package com

import android.app.Application
import com.di.AppComponent
import com.di.DaggerAppComponent

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent
            .builder()
            .build()
    }


    companion object {
        lateinit var appComponent: AppComponent
    }
}