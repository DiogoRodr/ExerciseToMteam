package bold.client.exercice.View

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import bold.client.exercice.MyApplication
import bold.client.exercice.MyApplication.Companion.flickrApi
import bold.client.exercice.R
import bold.client.exercice.View.errorHandlingUtils.ErrorHandling
import com.android.volley.toolbox.NetworkImageView
import kotlinx.android.synthetic.main.photo_details.*

class PhotoDetailsActivity : AppCompatActivity(){
    lateinit var photo_id:String
    lateinit var photo_title :String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photo_details)

        val bundleFromParent: Bundle? = intent.extras
        photo_id = bundleFromParent!!.getString("photo_id")
        photo_title = bundleFromParent!!.getString("photo_title")

    }

    override fun onStart() {
        super.onStart()
        populateActivity()
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState!!.putString("photo_id", photo_id)
        outState!!.putString("totalPages", photo_title)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        photo_title = savedInstanceState!!.getString("photo_title")
        photo_id = savedInstanceState!!.getString("photo_ids")
    }

    fun populateActivity(){
        MyApplication.photoService.findPhotoSizes(photo_id) { httpError, flickrError, photoSizes ->
            var image: NetworkImageView = findViewById(R.id.photo_detail)
            if (httpError != null)
                ErrorHandling.errorDialog(
                    httpError,
                    this
                )
            else if(flickrError != null)
                ErrorHandling.errorDialog(
                    flickrError.message,
                    this
                )
            else
                flickrApi.getPhoto(photoSizes!!.sizes.size[5].source, image, true)

            photo_detail_title.setText(photo_title)
        }
    }
}