package presentation.ui.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.App
import com.example.webant_gallery.R
import io.realm.Realm
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import presentation.basemvp.BaseFragment
import presentation.presenter.FavoritePresenter
import presentation.presenter.NewPresenter
import presentation.view.FavoriteView

class FavoriteFragment : BaseFragment<FavoriteView, FavoritePresenter>("favorite"), FavoriteView {

    @InjectPresenter
    override lateinit var presenter: FavoritePresenter
    lateinit var realm: Realm

    @ProvidePresenter
    fun providePresenter(): FavoritePresenter = App.appComponent.provideFavoritePresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun initViews() {
        recyclerView = requireView().findViewById(R.id.recyclerViewNew)
        swipeRefreshLayout = requireView().findViewById(R.id.swiperefresh)
        placeholder = requireView().findViewById(R.id.newNoInternetPlaceholder)

        swipeRefreshLayout.setOnRefreshListener {
            presenter.onSwipeRefresh()
        }

        swipeRefreshLayout.setColorScheme(
            R.color.pink,
            R.color.darker_gray,
            R.color.white,
            R.color.black
        )

        progressBar = requireView().findViewById(R.id.progressbar)
    }

    override fun checkInternetConnection() {
        if (isNetworkAvailable()){
            presenter.getPhotos()
        }
        else {
            presenter.checkRealmIsEmpty()
        }
    }

    private fun isNetworkAvailable(): Boolean {
        val connectivityManager = activity?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return  networkInfo!=null && networkInfo.isConnected
    }
}
