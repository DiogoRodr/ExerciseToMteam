package bold.client.exercise.DataAccess.Services

import bold.client.exercise.DataAccess.FlickrApiRequestService
import bold.client.exercise.DataTransferObjects.PhotoListing
import bold.client.exercise.DataTransferObjects.PhotoSizes.PhotoSizes
import bold.client.exercise.DataTransferObjects.Photos
import bold.client.exercise.MyApplication.Companion.flickrApi
import com.google.gson.Gson
import bold.client.exercise.DataTransferObjects.Error

class PhotoService{

    fun findPublicPhotos(userId: String, page:String, callback: (httpError: String?, flickrError: Error?, userInfo: Photos?) -> Unit) {
        flickrApi.doGet(FlickrApiRequestService.GET_PHOTOS, arrayOf(userId, page), null) { httpError, flickrError, resp ->
            if(httpError != null)
                callback(httpError, null,null)
            else if( flickrError != null){
                callback(null, flickrError, null)
            }
            else{
                val photoListing = Gson().fromJson(resp, PhotoListing::class.java)
                callback(null,null, photoListing.photos)
            }
        }
    }

    fun findPhotoSizes(photoId: String, callback: (httpError: String?, flickrError: Error?, photoSizes: PhotoSizes?) -> Unit) {
        flickrApi.doGet(FlickrApiRequestService.GET_PHOTO_SIZES, arrayOf(photoId), null) { httpError, flickrError, resp ->
            if(httpError != null)
                callback(httpError, null,null)
            else if( flickrError != null){
                callback(null, flickrError, null)
            }
            else{
                val photoSizes = Gson().fromJson(resp, PhotoSizes::class.java)
                callback(null, null, photoSizes)
            }
        }
    }
}