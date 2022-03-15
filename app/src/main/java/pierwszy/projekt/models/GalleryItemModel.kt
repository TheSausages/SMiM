package pierwszy.projekt.models

import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import java.io.File

class GalleryItemModel(private val imagePath: String): Parcelable {
    //Parcelable elements
    constructor(parcel: Parcel) : this(parcel.readString() ?: "")

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(imagePath)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<GalleryItemModel> {
        override fun createFromParcel(parcel: Parcel): GalleryItemModel {
            return GalleryItemModel(parcel)
        }

        override fun newArray(size: Int): Array<GalleryItemModel?> {
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