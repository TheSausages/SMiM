package pierwszy.projekt

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import kotlin.math.abs

class SimpleGalleryListener(
    context: Context,
    val swipeRight: () -> Unit,
    val swipeLeft: () -> Unit
): View.OnTouchListener {
    private val detector = GestureDetector(context, SwipeGalleryDetector())

    override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
        p0?.performClick()
        return detector.onTouchEvent(p1)
    }

    private inner class SwipeGalleryDetector: GestureDetector.SimpleOnGestureListener() {
        private val swipeThreshold = 100
        private val swipeVelocityThreshold = 100

        override fun onDown(e: MotionEvent?): Boolean {
            return true
        }

        override fun onShowPress(e: MotionEvent?) {
            return
        }

        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return false
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent?,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            return false
        }

        override fun onLongPress(e: MotionEvent?) {
            return
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            try {
                val diffY = e2!!.y - e1!!.y
                val diffX = e2.x - e1.x
                if (abs(diffX) > abs(diffY)) {
                    if (abs(diffX) > swipeThreshold && abs(velocityX) > swipeVelocityThreshold) {
                        if (diffX > 0) {
                            swipeLeft()
                        }
                        else {
                            swipeRight()
                        }
                    }
                }
            }
            catch (exception: Exception) {
                exception.printStackTrace()
            }
            return true
        }
    }
}