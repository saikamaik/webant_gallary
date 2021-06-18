package com.di

import dagger.Module
import dagger.Provides
import com.example.gateway.remoteDataSource.GalleryApi
import retrofit2.Retrofit
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class])
class ApiModule {

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): GalleryApi {
        return retrofit.create(GalleryApi::class.java)
    }
}