package presentation.ui.fragment

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import presentation.basemvp.BaseFragment
import com.example.webant_gallery.R
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import presentation.presenter.NewPresenter
import presentation.view.NewView
import com.App
import io.realm.Realm


class NewFragment : BaseFragment<NewView, NewPresenter>("new"), NewView {

    @InjectPresenter
    override lateinit var presenter: NewPresenter
    lateinit var realm: Realm

    @ProvidePresenter
    fun providePresenter(): NewPresenter = App.appComponent.provideNewPresenter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_new, container, false)
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