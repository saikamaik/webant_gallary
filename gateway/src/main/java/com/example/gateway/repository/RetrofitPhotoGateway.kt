package com.example.gateway.repository

import com.example.domain.entity.APIResponse
import com.example.domain.gateway.PhotoGateway
import com.example.gateway.remoteDataSource.GalleryApi
import io.reactivex.Single

class RetrofitPhotoGateway(private val api: GalleryApi): PhotoGateway {

    override fun getPhotos(new: String?, popular: String?, page: Int): Single<APIResponse> {
        return api.getPhotos(new, popular, page)
    }
}