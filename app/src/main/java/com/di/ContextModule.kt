package com.di

import android.content.Context
import com.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [AppModule::class])
class ContextModule {

    @Provides
    @Singleton
    fun provideContext(app: App): Context = app.applicationContext
}