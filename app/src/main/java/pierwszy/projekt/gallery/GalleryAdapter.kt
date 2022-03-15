package pierwszy.projekt.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import pierwszy.projekt.R
import android.widget.ArrayAdapter


class GalleryAdapter(
    context: Context,
    private val imageList: List<GalleryItem>,
    private val layoutResourceId: Int = R.layout.gallery_item_min
): ArrayAdapter<GalleryItem>(context, layoutResourceId, imageList) {
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