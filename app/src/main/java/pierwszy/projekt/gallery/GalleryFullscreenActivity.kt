package pierwszy.projekt.gallery

import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import pierwszy.projekt.R

class GalleryFullscreenActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_item_fullscreen)

        print( intent.extras)
        val imageUri = intent.extras!!.getString("imageUri")!!
        val imageView: ImageView = findViewById(R.id.gallery_item_fullscreen_id)

        imageView.setImageURI(Uri.parse(imageUri))
    }
}