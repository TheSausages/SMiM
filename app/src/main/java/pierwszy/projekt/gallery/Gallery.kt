package pierwszy.projekt.gallery

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.Toast
import pierwszy.projekt.MainActivity
import java.lang.RuntimeException

class Gallery(
    private val context: Context,
    private val resolver: ContentResolver,
    private val view: GridView,
    private val startActivity: (intent: Intent) -> Unit,
    private val imageList: ArrayList<GalleryItem> = ArrayList()
) {
    private val adapter: GalleryAdapter = GalleryAdapter(context, imageList)

    init {
        view.adapter = this.adapter

        //When clicking, open the image
        view.onItemClickListener = AdapterView.OnItemClickListener { adapterView, view, i, l -> run {
            val item: GalleryItem = adapterView.getItemAtPosition(i) as GalleryItem

            val intent = Intent(context, GalleryFullscreenActivity::class.java)
            intent.putExtra("imageUri", item.imagePath)
            startActivity(intent)
        }}
    }

    fun updateImagePaths() {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            //Create a cursor to find the photos
            val cursor: Cursor = resolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID),
                null,
                null,
                MediaStore.Images.Media._ID
            ) ?: kotlin.run {
                //When none is found, give a message and exit the app
                Toast.makeText(
                    context,
                    "Permissions denied, Permissions are required to use the app..",
                    Toast.LENGTH_SHORT
                ).show()

                throw RuntimeException("Couldn't create the cursor")
            }

            // loop through all the images
            for (i in 0 until cursor.count) {
                //move the cursor
                cursor.moveToPosition(i)

                //find the image uri
                val dataColumnIndex: Int = cursor.getColumnIndex(MediaStore.Images.Media.DATA)

                //add it to the list
                imageList.add(GalleryItem(cursor.getString(dataColumnIndex)))
            }
            adapter.notifyDataSetChanged()

            //close the cursor at the end
            cursor.close()
        }
    }
}