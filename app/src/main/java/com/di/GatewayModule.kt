package com.di

import dagger.Module
import dagger.Provides
import com.example.gateway.remoteDataSource.GalleryApi
import com.example.domain.gateway.PhotoGateway
import com.example.gateway.repository.RetrofitPhotoGateway
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
class GatewayModule {

    @Provides
    @Singleton
    fun providePhotoGateway(api: GalleryApi): PhotoGateway {
        return RetrofitPhotoGateway(api)
    }
}