package bold.client.exercice

import android.app.Application
import android.content.Context
import bold.client.exercice.DataAccess.FlickrApiRequestService
import bold.client.exercice.DataAccess.HttpUtils.HttpRequests
import bold.client.exercice.DataAccess.Services.PhotoService
import bold.client.exercice.DataAccess.Services.UserService
import com.android.volley.toolbox.ImageLoader

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
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
    }
}