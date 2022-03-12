package pierwszy.projekt.gallery

import android.annotation.SuppressLint
import android.net.Uri
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import pierwszy.projekt.R
import pierwszy.projekt.SimpleGalleryListener

class GalleryFullscreenActivity: AppCompatActivity() {
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_item_fullscreen)

        val images = intent.extras!!.getStringArrayList("images")!!
        val currentPosition = Pos(intent.extras!!.getInt("currentPosition"), images.size)
        val imageView: ImageView = findViewById(R.id.gallery_item_fullscreen_id)

        imageView.setImageURI(Uri.parse(images[currentPosition.position]))

        imageView.setOnTouchListener(SimpleGalleryListener(
            imageView.context,
            { imageView.setImageURI(Uri.parse(images[currentPosition.add()])) },
            { imageView.setImageURI(Uri.parse(images[currentPosition.minus()])) }
        ))
    }
}

class Pos(var position: Int, val max: Int) {
    fun add(): Int {
        if (position < max - 1) position++
        return position
    }

    fun minus(): Int {
        if (position > 0) position--
        return position
    }
}