package presentation.presenter

import com.example.domain.gateway.LocalPhotoGateway
import com.example.domain.gateway.PhotoGateway
import moxy.InjectViewState
import presentation.basemvp.BasePresenter
import presentation.view.FavoriteView
import javax.inject.Inject

@InjectViewState
class FavoritePresenter @Inject constructor(photoGateway: PhotoGateway, localPhotoGateway: LocalPhotoGateway) :
    BasePresenter<FavoriteView>("false", null, photoGateway, localPhotoGateway)