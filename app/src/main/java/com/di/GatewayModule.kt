package com.di

import com.example.domain.gateway.LocalPhotoGateway
import dagger.Module
import dagger.Provides
import com.example.gateway.remoteDataSource.GalleryApi
import com.example.domain.gateway.PhotoGateway
import com.example.gateway.gateway_impl.RealmPhotoGateway
import com.example.gateway.repository.RetrofitPhotoGateway
import io.reactivex.Scheduler
import io.realm.Realm
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
class GatewayModule {

    @Provides
    @Singleton
    fun providePhotoGateway(api: GalleryApi): PhotoGateway {
        return RetrofitPhotoGateway(api)
    }

    @Provides
    @Singleton
    fun provideLocalPhotoGateway(realm: Realm,
                                 scheduler: Scheduler): LocalPhotoGateway {
        return RealmPhotoGateway(realm, scheduler)
    }

}