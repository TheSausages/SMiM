package pierwszy.projekt.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pierwszy.projekt.R
import android.widget.ArrayAdapter
import pierwszy.projekt.activities.setImage
import pierwszy.projekt.models.GalleryItemModel

class GalleryAdapter(
    context: Context,
    private val imageList: List<GalleryItemModel>,
    private val layoutResourceId: Int = R.layout.gallery_item_min
): ArrayAdapter<GalleryItemModel>(context, layoutResourceId, imageList) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        //Find the gallery view
        val galleryView = convertView ?: LayoutInflater.from(context).inflate(layoutResourceId, parent, false)

        setImage(galleryView.findViewById(R.id.gallery_item_min_id), getItem(position)!!)

        return galleryView
    }

    override fun getCount(): Int {
        return imageList.size
    }
}