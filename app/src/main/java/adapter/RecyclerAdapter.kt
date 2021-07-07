package adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.content.res.AppCompatResources.getDrawable
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.webant_gallery.R
import com.squareup.picasso.Picasso
import com.example.domain.entity.PhotoModel
import com.google.android.material.internal.ContextUtils.getActivity

class RecyclerAdapter(
    private val callback: MyViewHolder.Callback
) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

    var photos: ArrayList<PhotoModel> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_image, parent, false)

        return MyViewHolder(itemView, callback)
    }

    override fun getItemCount() = photos.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(photos[position])
    }

    class MyViewHolder(listItemView: View, private val callback: Callback) :
        RecyclerView.ViewHolder(listItemView) {

        interface Callback {
            fun onImageClicked(item: PhotoModel)
        }

        private val firstImg: ImageView = itemView.findViewById(R.id.img)

        fun bind(photo: PhotoModel) {

            firstImg.setImageResource(R.drawable.nointernet)

            firstImg.setOnClickListener {
                callback.onImageClicked(photo)
            }
            if (photo.image?.name != null) {
                Picasso.get()
                    .load("http://gallery.dev.webant.ru/media/${photo.image?.name}")
                    .resize(1000, 1000)
                    .centerInside()
                    .into(firstImg)
            }
        }
    }
}
