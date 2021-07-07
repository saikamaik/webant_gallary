package presentation.presenter

import com.example.domain.gateway.LocalPhotoGateway
import presentation.basemvp.BasePresenter
import moxy.InjectViewState
import presentation.view.NewView
import com.example.domain.gateway.PhotoGateway
import javax.inject.Inject

@InjectViewState
class NewPresenter @Inject constructor(photoGateway: PhotoGateway, localPhotoGateway: LocalPhotoGateway) :
    BasePresenter<NewView>("true", null, photoGateway, localPhotoGateway)
