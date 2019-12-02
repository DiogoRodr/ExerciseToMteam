package bold.client.exercise.DataAccess.Services

import bold.client.exercise.DataAccess.FlickrApiRequestService
import bold.client.exercise.DataTransferObjects.UserInfo
import bold.client.exercise.MyApplication
import com.google.gson.Gson
import java.lang.Error

class UserService {
    private val flickrApi: FlickrApiRequestService = MyApplication.flickrApi

    fun findUserByUsername(username: String, callback: (err: String?, userInfo: UserInfo?) -> Unit) {
        flickrApi.doGet(FlickrApiRequestService.FIND_USER, arrayOf(username), null) { error, resp ->
            if(error != null) callback(error, null)
            else{
                val userInfo = Gson().fromJson(resp, UserInfo::class.java)
                callback(null, userInfo)
            }
        }
    }
}