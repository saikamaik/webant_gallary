package presentation.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import presentation.basemvp.BaseFragment
import com.example.webant_gallery.R
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import presentation.presenter.PopularPresenter
import presentation.view.PopularView
import com.App

class PopularFragment : PopularView, BaseFragment<PopularView, PopularPresenter>("popular") {

    @InjectPresenter
    override lateinit var presenter: PopularPresenter


    @ProvidePresenter
    fun providePresenter(): PopularPresenter = App.appComponent.providePopularPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_popular, container, false)
    }

    override fun initViews() {
        recyclerView = requireView().findViewById(R.id.recyclerViewPopular)
        swipeRefreshLayout = requireView().findViewById(R.id.swiperefresh)
        placeholder = requireView().findViewById(R.id.popularPlaceholder)

        swipeRefreshLayout.setOnRefreshListener {
            presenter.onSwipeRefresh()
            swipeRefreshLayout.isRefreshing = false
        }

        swipeRefreshLayout.setColorScheme(
            R.color.pink,
            R.color.darker_gray,
            R.color.white,
            R.color.black
        )

        progressBar = requireView().findViewById(R.id.progressbar)
    }

}