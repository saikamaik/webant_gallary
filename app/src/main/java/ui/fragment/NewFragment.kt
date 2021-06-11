package ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.Placeholder
import basemvp.BaseFragment
import com.example.webant_gallery.R
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import presentation.presenter.NewPresenter
import presentation.view.NewView


class NewFragment : BaseFragment<NewView, NewPresenter>("new"), NewView {

    @InjectPresenter
    override lateinit var presenter: NewPresenter

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
        placeholder = requireView().findViewById(R.id.newPlaceholder)

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
}