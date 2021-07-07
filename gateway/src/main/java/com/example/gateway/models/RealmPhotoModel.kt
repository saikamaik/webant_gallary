package com.example.gateway.models

import com.example.domain.entity.Image
import com.example.domain.entity.PhotoModel
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmImageModel: RealmObject(){
    var id: Int? = 0
    var name: String? = ""

    public fun convertToImage(): Image {
        return Image(id, name)
    }
}

open class RealmPhotoModel : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    var name: String? = ""
    var description: String? = ""
    var new: Boolean = false
    var popular: Boolean = false
    var image: RealmImageModel? = null
    var user: String? = null

    fun toDomain(): PhotoModel {
        return PhotoModel(
            id = this.id,
            name = this.name,
            description = this.description,
            new = this.new,
            popular = this.popular,
            image = this.image?.convertToImage(),
            user = this.user
        )
    }


    companion object {

        fun fromDomain(domain: PhotoModel): RealmPhotoModel {
            return RealmPhotoModel().apply {
                id = domain.id
                name = domain.name
                description = domain.description
                new = domain.new
                popular = domain.popular
            }
        }
    }
}