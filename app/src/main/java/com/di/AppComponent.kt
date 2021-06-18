package com.di

import dagger.Component
import presentation.presenter.NewPresenter
import presentation.presenter.PopularPresenter
import javax.inject.Singleton

@Singleton
@Component(modules = [GatewayModule::class])
interface AppComponent {

    fun provideNewPresenter(): NewPresenter
    fun providePopularPresenter(): PopularPresenter
}