package pierwszy.projekt.activities

import android.content.ContentResolver
import android.content.Intent
import android.database.Cursor
import android.os.*
import android.provider.MediaStore
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import pierwszy.projekt.R
import pierwszy.projekt.adapters.GalleryAdapter
import pierwszy.projekt.models.GalleryItemModel
import java.lang.RuntimeException

fun setImage(view: ImageView, image: GalleryItemModel) = view.setImageURI(image.asUri())

class Gallery: AppCompatActivity() {
    private lateinit var adapter: GalleryAdapter
    private var images: ArrayList<GalleryItemModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_collection)
        val view: GridView = findViewById(R.id.gallery_collection_id)

        this.updateImagePaths(contentResolver)
        adapter = GalleryAdapter(this, images)

        view.adapter = adapter

        //When clicking, open the image
        view.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ -> run {
            val intent = Intent(this, GalleryFullscreenActivity::class.java)
            intent.putParcelableArrayListExtra("images", images)
            intent.putExtra("currentPosition", i)
            startActivity(intent)
        }}
    }

    private fun updateImagePaths(contentResolver: ContentResolver) {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
            //Create a cursor to find the photos
            val cursor: Cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
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
}