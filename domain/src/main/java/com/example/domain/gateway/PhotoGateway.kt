package com.example.domain.gateway

import com.example.domain.entity.APIResponse
import com.example.domain.entity.PhotoModel
import io.reactivex.Single

interface PhotoGateway {

    fun getPhotos(new: String?, popular: String?, page: Int): Single<APIResponse>
}