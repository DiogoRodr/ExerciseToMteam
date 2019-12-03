package bold.client.exercice.View

import android.os.Bundle
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import bold.client.exercice.DataTransferObjects.Photos
import bold.client.exercice.MyApplication.Companion.photoService
import bold.client.exercice.R
import bold.client.exercice.View.adapters.PhotoListAdapter
import bold.client.exercice.View.errorHandlingUtils.ErrorHandling.Companion.errorDialog
import kotlinx.android.synthetic.main.photos_grid_list.*

class PhotosListActivity : AppCompatActivity() {
    private lateinit var adapter : PhotoListAdapter
    private var currentPage: Int = 1
    private var totalNumberOfPages = 0
    private var queriedUserId: String? = null
    private var queriedUserName: String? = null
    private var currentPhotoList: Photos? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.photos_grid_list)

        val bundleFromParent: Bundle? = intent.extras
        queriedUserId = bundleFromParent!!.getString("userId")
        queriedUserName = bundleFromParent.getString("username")

        //button
        nextPageButton.setOnClickListener {
            currentPage++
            getNewPublicPhotosList()
        }

        previousPageButton.setOnClickListener {
            currentPage--
            getNewPublicPhotosList()
        }
    }

    override fun onStart() {
        super.onStart()
        if(currentPhotoList == null)
            getNewPublicPhotosList()
        else
            populateActivity(currentPhotoList!!)
    }

    override fun onPause() {
        super.onPause()
        val view : View = findViewById(android.R.id.content)
        view.visibility = INVISIBLE
    }

    override fun onResume() {
        super.onResume()
        val view : View = findViewById(android.R.id.content)
        view.rootView.visibility = VISIBLE
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putInt("currentPage", currentPage)
        savedInstanceState.putString("queriedUserId", queriedUserId)
        savedInstanceState.putString("queriedUserName", queriedUserName)
        savedInstanceState.putInt("currentPages", totalNumberOfPages)
    }

    override fun onRestoreInstanceState(restoreInstanceState: Bundle) {
        super.onRestoreInstanceState(restoreInstanceState)
        currentPage = restoreInstanceState.getInt("currentPage")
        totalNumberOfPages = restoreInstanceState.getInt("totalPages")
        queriedUserId = restoreInstanceState.getString("queriedUserId")
        queriedUserName = restoreInstanceState.getString("queriedUserName")
    }

    private fun populateActivity( photoList: Photos) {
        textView.setText(String.format("User: %s photos| page:%s of %s",queriedUserName,currentPage, totalNumberOfPages))
        adapter = PhotoListAdapter(applicationContext, R.layout.photos_grid_item, photoList.photo)
        list_gridView.adapter = adapter

        if(currentPage == 1)
            previousPageButton.visibility = INVISIBLE
        else
            previousPageButton.visibility = VISIBLE

        if(currentPage == totalNumberOfPages)
            nextPageButton.visibility = INVISIBLE
        else
            nextPageButton.visibility = VISIBLE
    }

    private fun getNewPublicPhotosList(){
        photoService.findPublicPhotos(queriedUserId!!, currentPage.toString()) { httpError, flickrError, photoList ->
            if (httpError != null)
                errorDialog(httpError,this)
            else if(flickrError != null)
                errorDialog(flickrError.message,this)
            else {
                currentPhotoList = photoList
                totalNumberOfPages = photoList!!.pages
                populateActivity(photoList)
            }
        }
    }
}
