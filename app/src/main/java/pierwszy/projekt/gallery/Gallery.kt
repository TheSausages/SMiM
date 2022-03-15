package pierwszy.projekt.gallery

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.os.Environment
import android.os.Parcel
import android.os.Parcelable
import android.provider.MediaStore
import android.view.View
import android.widget.AdapterView
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import pierwszy.projekt.MainActivity
import java.io.File
import java.lang.RuntimeException

class GalleryItem(private val imagePath: String): Parcelable {
    //Parcelable elements
    constructor(parcel: Parcel) : this(parcel.readString() ?: "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imagePath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GalleryItem> {
        override fun createFromParcel(parcel: Parcel): GalleryItem {
            return GalleryItem(parcel)
        }

        override fun newArray(size: Int): Array<GalleryItem?> {
            return arrayOfNulls(size)
        }
    }

    //Other elements
    fun asFile(): File {
        return File(imagePath)
    }

    fun asUri(): Uri {
        return Uri.fromFile(asFile())
    }

    override fun toString(): String {
        return imagePath
    }
}

fun setImage(view: ImageView, image: GalleryItem) = view.setImageURI(image.asUri())

class Gallery(
    private val context: Context,
    private val resolver: ContentResolver,
    view: GridView,
    private val startActivity: (intent: Intent) -> Unit,
    private val imageList: ArrayList<GalleryItem> = ArrayList()
) {
    private val adapter: GalleryAdapter = GalleryAdapter(context, imageList)

    init {
        view.adapter = this.adapter

        //When clicking, open the image
        view.onItemClickListener = AdapterView.OnItemClickListener { _, _, i, _ -> run {
            val intent = Intent(context, GalleryFullscreenActivity::class.java)
            intent.putParcelableArrayListExtra("images", imageList)
            intent.putExtra("currentPosition", i)
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