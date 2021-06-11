package presentation.presenter

import basemvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import model.PhotoModel
import moxy.InjectViewState
import presentation.view.NewView
import retrofit.APIService

@InjectViewState
class NewPresenter : BasePresenter<NewView>("new")
