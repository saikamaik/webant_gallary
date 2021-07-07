package com.example.domain.gateway

import com.example.domain.entity.ListPhotoModel
import com.example.domain.entity.PaginationModel
import com.example.domain.entity.PhotoModel
import io.reactivex.Completable
import io.reactivex.Single

interface LocalPhotoGateway {

    fun getLocalPhotos(new: String?, popular: String?, page: Int): Single<PaginationModel<ListPhotoModel>>
    fun addPhotos(photo: List<PhotoModel>): Completable
}