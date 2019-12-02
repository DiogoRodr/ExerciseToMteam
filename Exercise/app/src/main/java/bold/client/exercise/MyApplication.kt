package bold.client.exercise

import android.app.Application
import android.content.Context
import bold.client.exercise.DataAccess.FlickrApiRequestService
import bold.client.exercise.DataAccess.HttpUtils.HttpRequests
import bold.client.exercise.DataAccess.Services.PhotoService
import bold.client.exercise.DataAccess.Services.UserService
import com.android.volley.toolbox.ImageLoader

class MyApplication : Application() {
    private val PREF_FILE_NAME = "bold.exercise.android"

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        imageCache = HttpRequests.TempCache()
        request = HttpRequests(this)
        flickrApi = FlickrApiRequestService(request)
        photoService = PhotoService()
        userService = UserService()

    }

    companion object {
        lateinit var appContext: Context
        lateinit var flickrApi: FlickrApiRequestService
        lateinit var photoService: PhotoService
        lateinit var userService: UserService
        lateinit var request: HttpRequests
        lateinit var imageCache: ImageLoader.ImageCache
    }
}