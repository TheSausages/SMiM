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

/**
 * Activity used for showing an image on the full screen.
 */
class GalleryFullscreenActivity: AppCompatActivity() {

    /**
     * The suppression is because we don't use a custom view (that would overwrite performClick).
     */
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Use the fullscrean layout
        setContentView(R.layout.gallery_item_fullscreen)
        val imageView: ImageView = findViewById(R.id.gallery_item_fullscreen_id)

        //Pull list of images and the current position
        val images: ArrayList<GalleryItemModel> =
            intent.extras?.getParcelableArrayList("images")
                ?: throw Resources.NotFoundException("Did not find the images")

        val currentPosition = PositionModel(
            intent.extras?.getInt("currentPosition")
                ?: throw Resources.NotFoundException("Did not find the current position"),
            images.size
        )

        //Set the correct image
        setImage(this, imageView, images[currentPosition.position])

        //Add the swipe listener
        imageView.setOnTouchListener(SimpleSwipeListener(
            imageView.context,
            {  changePhotoWith(!currentPosition.isEnd, currentPosition.forward(), images, R.anim.slide_in_right, R.anim.slide_out_left) },
            {  changePhotoWith(!currentPosition.isBeginning, currentPosition.back(), images, R.anim.slide_in_left, R.anim.slide_out_right) }
        ))
    }

    /**
     * Method used to change photos when fullscreen. It creates a new [GalleryFullscreenActivity] with an updated position.
     * It also adds animations to the transition.
     */
    private fun changePhotoWith(condition: Boolean, pos: PositionModel, images: List<GalleryItemModel>, enterAnimation: Int, endAnimation: Int) {
        if (condition) {
            kotlin.run {
                val intent = Intent(baseContext, GalleryFullscreenActivity::class.java).setFlags(FLAG_ACTIVITY_CLEAR_TOP)
                intent.putParcelableArrayListExtra("images", ArrayList(images))
                intent.putExtra("currentPosition", pos.position)
                startActivity(intent)
                overridePendingTransition(enterAnimation, endAnimation)
            }
        }
    }
}