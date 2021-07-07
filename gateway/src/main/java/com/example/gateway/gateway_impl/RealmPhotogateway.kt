package com.example.gateway.gateway_impl

import com.example.domain.entity.ListPhotoModel
import com.example.domain.entity.PaginationModel
import com.example.domain.entity.PhotoModel
import com.example.domain.gateway.LocalPhotoGateway
import com.example.gateway.models.RealmPhotoModel
import io.reactivex.Completable
import io.reactivex.Scheduler
import io.reactivex.Single
import io.realm.Realm
import io.realm.com_example_gateway_models_RealmImageModelRealmProxy.insertOrUpdate
import io.realm.com_example_gateway_models_RealmPhotoModelRealmProxy.insertOrUpdate

class RealmPhotoGateway(private val realm: Realm, private val scheduler: Scheduler) :
    LocalPhotoGateway {
    override fun getLocalPhotos(new: String?, popular: String?, page: Int) = Single.defer {
        realm.beginTransaction()
        val totalItems: Int
        val photos = realm.where(RealmPhotoModel::class.java)
            .findAll()
            .also { states -> totalItems =  states.size }
            .drop((page - 1) * 10)
            .take(10)
            .map(RealmPhotoModel::toDomain)
        realm.commitTransaction()

        val paginationModel = PaginationModel(
            totalItems = totalItems,
            itemsPerPage = 10,
            countOfPages = totalItems / 10,
            model = ListPhotoModel(items = photos as ArrayList<PhotoModel>))

        Single.just(paginationModel)
    }.subscribeOn(scheduler)

    override fun addPhotos(photo: List<PhotoModel>) = Completable.fromAction {
            realm.executeTransaction {
                photo.forEach{
                    realm.insertOrUpdate(RealmPhotoModel.fromDomain(it))
                }
            }
            Completable.complete()
        }.subscribeOn(scheduler)
}