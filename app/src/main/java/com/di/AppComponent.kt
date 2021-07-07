package com.di

import com.App
import dagger.Component
import presentation.presenter.NewPresenter
import presentation.presenter.PopularPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [GatewayModule::class, RealmModule::class])
interface AppComponent {

    fun inject(target: App)

    fun provideNewPresenter(): NewPresenter
    fun providePopularPresenter(): PopularPresenter
}