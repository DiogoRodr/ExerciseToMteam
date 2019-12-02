package bold.client.exercise.DataAccess.Services

import bold.client.exercise.DataAccess.FlickrApiRequestService
import bold.client.exercise.DataTransferObjects.PhotoListing
import bold.client.exercise.DataTransferObjects.PhotoSizes.PhotoSizes
import bold.client.exercise.DataTransferObjects.Photos
import bold.client.exercise.MyApplication.Companion.flickrApi
import com.google.gson.Gson

class PhotoService{

    fun findPublicPhotos(userId: String, page:String, callback: (err: String?, userInfo: Photos?) -> Unit) {
        flickrApi.doGet(FlickrApiRequestService.GET_PHOTOS, arrayOf(userId, page), null) { error, resp ->
            if(error != null) callback(error, null)
            else{
                val photoListing = Gson().fromJson(resp, PhotoListing::class.java)
                callback(null, photoListing.photos)
            }
        }
    }

    fun findPhotoSizes(photoId: String, callback: (err: String?, photoSizes: PhotoSizes?) -> Unit) {
        flickrApi.doGet(FlickrApiRequestService.GET_PHOTO_SIZES, arrayOf(photoId), null) { error, resp ->
            if(error != null) callback(error, null)
            else{
                val photoSizes = Gson().fromJson(resp, PhotoSizes::class.java)
                callback(null, photoSizes)
            }
        }
    }
}