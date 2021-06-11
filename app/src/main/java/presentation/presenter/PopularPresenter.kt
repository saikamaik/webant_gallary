package presentation.presenter

import adapter.RecyclerAdapter
import android.annotation.SuppressLint
import basemvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import model.PhotoModel
import moxy.InjectViewState
import moxy.presenter.ProvidePresenter
import presentation.view.NewView
import presentation.view.PopularView
import retrofit.APIService

@InjectViewState
class PopularPresenter: BasePresenter<PopularView>("popular")