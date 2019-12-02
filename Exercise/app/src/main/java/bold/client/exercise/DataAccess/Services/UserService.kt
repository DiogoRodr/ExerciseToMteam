package bold.client.exercise.DataAccess.Services

import bold.client.exercise.DataAccess.FlickrApiRequestService
import bold.client.exercise.DataTransferObjects.UserInfo
import bold.client.exercise.MyApplication
import com.google.gson.Gson
import bold.client.exercise.DataTransferObjects.Error

class UserService {
    private val flickrApi: FlickrApiRequestService = MyApplication.flickrApi

    fun findUserByUsername(username: String, callback: (httpError: String?, flickrError: Error?, userInfo: UserInfo?) -> Unit) {
        flickrApi.doGet(FlickrApiRequestService.FIND_USER, arrayOf(username), null) { httpError,flickrError, resp ->
            if(httpError != null)
                callback(httpError, null,null)
            else if( flickrError != null){
                callback(null, flickrError, null)
            }
            else{
                val userInfo = Gson().fromJson(resp, UserInfo::class.java)
                callback(null, null, userInfo)
            }
        }
    }
}