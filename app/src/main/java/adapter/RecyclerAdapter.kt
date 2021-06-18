package adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.webant_gallery.R
import com.squareup.picasso.Picasso
import com.example.domain.entity.PhotoModel

class RecyclerAdapter(
    private val photos: List<PhotoModel>,
    private val callback: MyViewHolder.Callback
) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>() {

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
            firstImg.setOnClickListener {
                callback.onImageClicked(photo)
            }

            Picasso.get()
                .load("http://gallery.dev.webant.ru/media/${photo.image.name}")
                .resize(1000, 1000)
                .centerInside()
                .into(firstImg)
        }
    }
}
