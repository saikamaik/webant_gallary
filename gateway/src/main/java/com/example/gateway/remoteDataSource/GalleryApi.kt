package com.example.gateway.remoteDataSource

import com.example.domain.entity.APIResponse
import com.example.domain.entity.ListPhotoModel
import com.example.domain.entity.PaginationModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GalleryApi {

    @GET("photos?limit=10")
    fun getPhotos(
        @Query("new") new: String?, @Query("popular") popular: String?, @Query("page") page: Int): Single<APIResponse>

    @GET("photos?limit=10")
    fun getLocalPhotos(
        @Query("new") new: String?, @Query("popular") popular: String?, @Query("page") page: Int):Single<PaginationModel<ListPhotoModel>>
}