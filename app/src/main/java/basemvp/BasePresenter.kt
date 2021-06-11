package basemvp

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import model.PhotoModel
import moxy.MvpPresenter
import retrofit.APIService


abstract class BasePresenter<V : BaseView>(var type: String) : MvpPresenter<V>() {

    var isLoading = false
    private var photos: ArrayList<PhotoModel> = arrayListOf()
    private var page: Int = 1
    private var isLastPage = false
    private val compositeDisposable = CompositeDisposable()


    override fun onFirstViewAttach() {
        viewState.initViews()
        viewState.initRecyclerView(photos)
        getFirstPhotos(type, false)
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    fun onSwipeRefresh() {
        if (!isLoading) {
            photos.clear()
            page = 1
            viewState.addNewItems()
            getFirstPhotos(type, isNeedSwipeRefresh = true)
        } else {
            viewState.changeSwipeRefreshState(false)
        }
    }

    fun onImageClicked(item: PhotoModel) {
        viewState.navigateToImageDetailFragment(item)
    }

    fun getPhotos(type: String) {
        if (isLastPage || isLoading) {
            return
        }

        APIService.getApiPhotos(type, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                viewState.changeProgressViewState(true)
                isLoading = true
                viewState.changePlaceholderVisibility(true)
            }
            .doOnSuccess {
                page++
            }
            .doFinally {
                viewState.changeProgressViewState(false)
                isLoading = false
            }
            .subscribe({ response ->
                photos.addAll(response.data)
                viewState.addExtraItems(photos.size - response.data.size, response.data.size)
            }, {
                it.printStackTrace()
                photos.clear()
                viewState.changeProgressViewState(false)
                viewState.changePlaceholderVisibility(false)
            })
            .let(compositeDisposable::add)
    }

    private fun getFirstPhotos(type: String, isNeedSwipeRefresh: Boolean) {

        APIService.getApiPhotos(type, page)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                isLoading = true
                if (isNeedSwipeRefresh) {
                    viewState.changeSwipeRefreshState(true)
                }
                viewState.changePlaceholderVisibility(true)
            }
            .doOnSuccess {
                page++
            }
            .doFinally {
                isLoading = false
                if (isNeedSwipeRefresh) {
                    viewState.changeSwipeRefreshState(false)
                }
            }
            .subscribe({
                photos.addAll(it.data as ArrayList<PhotoModel>)
                viewState.addNewItems()
            }, {
                it.printStackTrace()
                photos.clear()
                viewState.changeProgressViewState(false)
                viewState.changePlaceholderVisibility(false)
            })
            .let(compositeDisposable::add)
    }
}