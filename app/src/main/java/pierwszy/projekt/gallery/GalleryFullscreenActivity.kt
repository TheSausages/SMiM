package pierwszy.projekt.gallery

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.content.res.Resources
import android.icu.text.Transliterator
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import pierwszy.projekt.R
import pierwszy.projekt.SimpleSwipeListener
import java.util.*
import kotlin.collections.ArrayList


class GalleryFullscreenActivity: AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_item_fullscreen)

        val images: ArrayList<GalleryItem> =
            intent.extras?.getParcelableArrayList("images")
                ?: throw Resources.NotFoundException("Did not find the images")

        val currentPosition = Position(
            intent.extras?.getInt("currentPosition")
                ?: throw Resources.NotFoundException("Did not find the current position"),
            images.size
        )

        val imageView: ImageView = findViewById(R.id.gallery_item_fullscreen_id)

        setImage(imageView, (images[currentPosition.position]))

        imageView.setOnTouchListener(SimpleSwipeListener(
            imageView.context,
            {
                kotlin.run {
                    if (!currentPosition.isEnd) {
                        val intent = Intent(baseContext, GalleryFullscreenActivity::class.java).setFlags(FLAG_ACTIVITY_CLEAR_TOP)
                        intent.putParcelableArrayListExtra("images", images)
                        intent.putExtra("currentPosition", currentPosition.forward())
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left)
                    }
                }
            },
            {
                kotlin.run {
                    if (!currentPosition.isBeginning) {
                        val intent = Intent(baseContext, GalleryFullscreenActivity::class.java).setFlags(FLAG_ACTIVITY_CLEAR_TOP)
                        intent.putParcelableArrayListExtra("images", images)
                        intent.putExtra("currentPosition", currentPosition.back())
                        startActivity(intent)
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right)
                    }
                }
            }
        ))
    }
}

class Position(var position: Int, max: Int) {
    var upperLimit = max - 1

    fun forward(): Int {
        if (position < upperLimit) position++
        return position
    }

    fun back(): Int {
        if (position > 0) position--
        return position
    }

    val isBeginning: Boolean
        get() = position == 0

    val isEnd: Boolean
        get() = position == upperLimit
}