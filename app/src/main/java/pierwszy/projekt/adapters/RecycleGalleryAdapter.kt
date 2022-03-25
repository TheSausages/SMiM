package pierwszy.projekt.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import pierwszy.projekt.R
import pierwszy.projekt.activities.GalleryFullscreenActivity
import pierwszy.projekt.activities.setImage
import pierwszy.projekt.models.GalleryItemModel

/**
 * IAdapter for RecycleView used in the main Gallery Layout.
 */
class RecycleGalleryAdapter(
    val context: Context,
    private val imageList: List<GalleryItemModel>,
): RecyclerView.Adapter<RecycleGalleryAdapter.RecyclerViewHolder>() {
    /** View Holder Class to handle Recycler View. */
    class RecyclerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        // creating variables for our views.
        val imageIV: ImageView = itemView.findViewById(R.id.gallery_item_min_id)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerViewHolder {
        // Inflate Layout in this method which we have created.
        val view: View = LayoutInflater.from(context).inflate(
                    R.layout.gallery_item_min,
                    parent,
                    false
                )

        return RecyclerViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerViewHolder, position: Int) {
        //Set each image
        setImage(context, holder.imageIV, imageList[position])

        //Add listener, so when clicked takes to fullscreen
        holder.itemView.setOnClickListener {
            val intent = Intent(context, GalleryFullscreenActivity::class.java)
            intent.putParcelableArrayListExtra("images", ArrayList(imageList))
            intent.putExtra("currentPosition", position)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return imageList.size
    }
}