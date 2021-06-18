package presentation.presenter

import presentation.basemvp.BasePresenter
import moxy.InjectViewState
import presentation.view.PopularView
import com.example.domain.gateway.PhotoGateway
import javax.inject.Inject

@InjectViewState
class PopularPresenter @Inject constructor(photoGateway: PhotoGateway) :
    BasePresenter<PopularView>(null, "true", photoGateway) {
}