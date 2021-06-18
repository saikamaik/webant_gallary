package presentation.basemvp

import com.example.domain.entity.PhotoModel
import moxy.MvpView
import moxy.viewstate.strategy.AddToEndSingleStrategy
import moxy.viewstate.strategy.OneExecutionStateStrategy
import moxy.viewstate.strategy.StateStrategyType

interface BaseView: MvpView{

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun addExtraItems(position: Int, quantity: Int)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun addNewItems()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun initRecyclerView(photos: ArrayList<PhotoModel>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun initViews()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeSwipeRefreshState(isRefreshing: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changeProgressViewState(isLoading: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun changePlaceholderVisibility(IsNetworkAvailable: Boolean)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun navigateToImageDetailFragment(photoModel: PhotoModel)
}