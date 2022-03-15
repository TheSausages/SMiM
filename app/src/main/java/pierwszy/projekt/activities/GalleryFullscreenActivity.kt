package pierwszy.projekt.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
import android.content.res.Resources
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import pierwszy.projekt.R
import pierwszy.projekt.listeners.SimpleSwipeListener
import pierwszy.projekt.models.GalleryItemModel
import pierwszy.projekt.models.PositionModel
import kotlin.collections.ArrayList


class GalleryFullscreenActivity: AppCompatActivity() {

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.gallery_item_fullscreen)

        val images: ArrayList<GalleryItemModel> =
            intent.extras?.getParcelableArrayList("images")
                ?: throw Resources.NotFoundException("Did not find the images")

        val currentPosition = PositionModel(
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