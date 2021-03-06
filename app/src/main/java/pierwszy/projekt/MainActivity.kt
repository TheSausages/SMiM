package pierwszy.projekt

import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import pierwszy.projekt.activities.Gallery

/**
 * Main activity of the application. Is used to check for permissions and start the main Gallery activity.
 *
 * @see Gallery
 */
class MainActivity : AppCompatActivity() {
    private val PERMISSION_REQUEST_CODE = 200
    private lateinit var galleryIntent: Intent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Create a Gallery object that contains all the image paths
        galleryIntent = Intent(this, Gallery::class.java)

        //Add flags to make the gallery activity the base activity
        galleryIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)

        //Check for required permissions
        requestPermissions()
    }

    private fun requestPermissions() {
        if (ContextCompat.checkSelfPermission(applicationContext, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            //If permissions were found and are correct, get the photos
            startActivity(galleryIntent)
        } else {
            //If not, try to get the permissions
            ActivityCompat.requestPermissions(
                this,
                arrayOf(READ_EXTERNAL_STORAGE),
                PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.isNotEmpty()) {
            if (PackageManager.PERMISSION_GRANTED == grantResults[0]) {
                startActivity(galleryIntent)
            } else {
                Toast.makeText(
                    this,
                    "Permissions denied, Permissions are required to use the app..",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}