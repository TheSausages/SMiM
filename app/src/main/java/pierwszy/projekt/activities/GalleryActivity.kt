package pierwszy.projekt.activities

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.*
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import pierwszy.projekt.R
import pierwszy.projekt.adapters.RecycleGalleryAdapter
import pierwszy.projekt.configuration.GlideApp
import pierwszy.projekt.models.GalleryItemModel
import java.lang.RuntimeException

/**
 * Helper method for setting an image into a view using Glide.
 */
fun setImage(context: Context, view: ImageView, image: GalleryItemModel) =
    GlideApp
        .with(context)
        .load(image.asUri())
        .into(view)

/**
 * Main activity of the Gallery app. This activity shows the images as Grid using RecycleView.
 */
class Gallery: AppCompatActivity() {
    /** Image adapter for the gallery. */
    private lateinit var adapter: RecycleGalleryAdapter

    /** List of paths to the images, both internal and external. */
    private var images: ArrayList<GalleryItemModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Use the RecycleView layout
        setContentView(R.layout.gallery_collection)
        val view: RecyclerView = findViewById(R.id.gallery_view_Id)

        //Update the image paths
        this.getAllImagePaths()

        //Create the adapter and pass the images to it
        adapter = RecycleGalleryAdapter(this, images)

        //Create the layout manager
        //Update for better 3 span (mniejsze odstępy ale większe obrazki)
        val manager = GridLayoutManager(this, 3)

        view.layoutManager = manager
        view.adapter = adapter
    }

    private fun getAllImagePaths() {
        getImagePathsFromUri(MediaStore.Images.Media.INTERNAL_CONTENT_URI)

        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            getImagePathsFromUri(MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        }
    }

    private fun getImagePathsFromUri(uri: Uri) {
        //Create a cursor to find the photos
        val cursor: Cursor = contentResolver.query(
            uri,
            arrayOf(MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID),
            null,
            null,
            MediaStore.Images.Media._ID
        ) ?: kotlin.run {
            //When none is found, give a message and exit the app
            Toast.makeText(
                this,
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
            images.add(GalleryItemModel(cursor.getString(dataColumnIndex)))
        }

        //close the cursor at the end
        cursor.close()
    }
}