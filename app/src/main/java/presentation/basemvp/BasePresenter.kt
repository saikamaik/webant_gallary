package presentation.basemvp

import android.annotation.SuppressLint
import com.example.domain.entity.PhotoModel
import com.example.domain.gateway.LocalPhotoGateway
import com.example.domain.gateway.PhotoGateway
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.realm.Realm
import moxy.MvpPresenter


abstract class BasePresenter<V : BaseView>(
    var new: String?,
    var popular: String?,
    private val photoGateway: PhotoGateway,
    private val localPhotoGateway: LocalPhotoGateway
) : MvpPresenter<V>() {

    var realm: Realm = Realm.getDefaultInstance()
    var isLoading = false
    private var photos: ArrayList<PhotoModel> = arrayListOf()
    private var page: Int = 1
    private var isLastPage = false
    private val compositeDisposable = CompositeDisposable()


    override fun onFirstViewAttach() {
        viewState.initViews()
        viewState.initRecyclerView()
        viewState.checkInternetConnection()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }

    fun onSwipeRefresh() {
        if (!isLoading) {
            photos.clear()
            viewState.addNewItems(photos)
            page = 1
            viewState.checkInternetConnection()
        } else {
            viewState.changeSwipeRefreshState(false)
        }
    }

    fun onImageClicked(item: PhotoModel) {
        viewState.navigateToImageDetailFragment(item)
    }

    fun getPhotos() {
        if (isLastPage || isLoading) {
            return
        }

        photoGateway.getPhotos(new, popular, page)
            .map {
                it.data
            }
            .flatMap {
                localPhotoGateway.addPhotos(it)
                    .andThen(Single.just(it))
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe {
                viewState.changeRecyclerViewVisibility(true)
                viewState.changePlaceholderVisibility(true)
                viewState.changeProgressViewState(true)
                isLoading = true
            }
            .doOnSuccess {
                page++
            }
            .doFinally {
                viewState.changeRecyclerViewVisibility(true)
                viewState.changeProgressViewState(false)
                isLoading = false
                viewState.changeSwipeRefreshState(false)
            }
            .subscribe({
                if (page == 1) {
                photos.addAll(it)
                viewState.addNewItems(photos)
            } else {
                photos.addAll(it)
                viewState.addExtraItems(it, photos.size - it.size, it.size)
            }}, {
                it.printStackTrace()
                photos.clear()
                viewState.addNewItems(photos)
                viewState.changePlaceholderVisibility(false )
                viewState.changeProgressViewState(false)
            })
            .let(compositeDisposable::add)
    }

    private fun getLocalPhotos() {
        localPhotoGateway.getLocalPhotos(new, popular, page)
            .map {
                it.model
            }
            .flatMap {
                localPhotoGateway.addPhotos(it.items)
                    .andThen(Single.just(it))
            }
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                viewState.changeRecyclerViewVisibility(true)
                viewState.changePlaceholderVisibility(true)
                isLoading = true
                viewState.changeSwipeRefreshState(true)
                }
            .doOnSuccess { page++ }
            .observeOn(AndroidSchedulers.mainThread())
            .doFinally {
                viewState.changeRecyclerViewVisibility(true)
                viewState.changePlaceholderVisibility(true)
                isLoading = false
                viewState.changeSwipeRefreshState(false)
                }
            .subscribe({
                if (page == 1) {
                    photos.addAll(it.items)
                    viewState.addNewItems(photos)
                } else {
                    photos.addAll(it.items)
                    viewState.addExtraItems(it.items, photos.size - it.items.size, it.items.size)
                }
            }, {
                it.printStackTrace()
                photos.clear()
                viewState.addNewItems(photos)
                viewState.changePlaceholderVisibility(false)
                viewState.changeProgressViewState(false)
                }).let(compositeDisposable::add)
            }

    fun checkRealmIsEmpty() {
        if (realm.isEmpty){
            viewState.changePlaceholderVisibility(false)
        }
        else {
            viewState.changeRecyclerViewVisibility(true)
            getLocalPhotos()
        }
    }
    }
