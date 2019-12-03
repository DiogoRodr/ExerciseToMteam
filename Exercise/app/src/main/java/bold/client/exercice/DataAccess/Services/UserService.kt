package bold.client.exercice.DataAccess.Services

import bold.client.exercice.DataAccess.FlickrApiRequestService
import bold.client.exercice.DataTransferObjects.UserInfo
import bold.client.exercice.MyApplication
import com.google.gson.Gson
import bold.client.exercice.DataTransferObjects.Error

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