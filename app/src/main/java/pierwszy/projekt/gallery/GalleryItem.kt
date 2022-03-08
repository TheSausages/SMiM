package pierwszy.projekt.gallery

import android.net.Uri
import java.io.File

class GalleryItem(val imagePath: String) {
    fun asFile(): File {
        return File(imagePath)
    }

    fun asUri(): Uri {
        return Uri.fromFile(asFile())
    }
}