package presentation.basemvp

import adapter.PaginationScrollListener
import adapter.RecyclerAdapter
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.webant_gallery.R
import com.example.domain.entity.PhotoModel
import moxy.MvpAppCompatFragment
import presentation.ui.fragment.ImageDetailFragment


abstract class BaseFragment<V : BaseView, P: BasePresenter<V>>(var type: String): BaseView,
    MvpAppCompatFragment() {

    protected abstract val presenter: P
    lateinit var recyclerView: RecyclerView
    lateinit var swipeRefreshLayout: SwipeRefreshLayout
    lateinit var progressBar: ProgressBar
    lateinit var placeholder: View
    private lateinit var adapter: RecyclerAdapter


    override fun initRecyclerView() {
        adapter = RecyclerAdapter(object : RecyclerAdapter.MyViewHolder.Callback {
            override fun onImageClicked(item: PhotoModel) {
                presenter.onImageClicked(item)
            }
        })
        var linearLayoutManager = GridLayoutManager(this.context, 2)
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            linearLayoutManager = GridLayoutManager(this.context, 4)
        }
        recyclerView.layoutManager = linearLayoutManager
        recyclerView.adapter = adapter
        adapter.stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        recyclerView.setHasFixedSize(true)

        recyclerView.addOnScrollListener(object : PaginationScrollListener(linearLayoutManager) {
            override fun loadMoreItems() {
                presenter.getPhotos()
            }
        })
    }

    override fun addExtraItems(photos: List<PhotoModel>, position: Int, quantity: Int) {
        adapter.photos.addAll(photos)
        adapter.notifyItemRangeInserted(position, quantity)
    }

    override fun addNewItems(photos: ArrayList<PhotoModel>) {
        adapter.photos = photos
        adapter.notifyDataSetChanged()
    }

    override fun changeSwipeRefreshState(isRefreshing: Boolean) {
        swipeRefreshLayout.isRefreshing = isRefreshing
    }

    override fun changeProgressViewState(isLoading: Boolean) {
        if (isLoading) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun changePlaceholderVisibility(IsNetworkAvailable: Boolean) {
        if (IsNetworkAvailable) {
            recyclerView.visibility = View.VISIBLE
            placeholder.visibility = View.GONE
        } else {
            adapter.notifyDataSetChanged()
            recyclerView.visibility = View.INVISIBLE
            placeholder.visibility = View.VISIBLE
        }
    }

    override fun changeRecyclerViewVisibility(visibility: Boolean) {
        if (visibility) {
            recyclerView.visibility = View.VISIBLE
        } else {
            recyclerView.visibility = View.INVISIBLE
        }
    }

    override fun navigateToImageDetailFragment(photoModel: PhotoModel) {
        val imageDetailFragment = ImageDetailFragment()
        val args = Bundle()
        args.putString("imageName", photoModel.name)
        args.putString("imageDescription", photoModel.description)
        args.putString("imageLink", photoModel.image?.name)
        imageDetailFragment.arguments = args
        parentFragmentManager
            .beginTransaction()
            .add(R.id.fl_container, imageDetailFragment)
            .addToBackStack(null)
            .commit()
    }


}