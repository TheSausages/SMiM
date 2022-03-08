package pierwszy.projekt.gallery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
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

        galleryView
            //Find the view for a given item
            .findViewById<ImageView>(R.id.gallery_item_min_id)
            //Set the Image Uri for a given item
            .setImageURI(getItem(position)!!.asUri())

        return galleryView
    }

    override fun getCount(): Int {
        return imageList.size
    }
}