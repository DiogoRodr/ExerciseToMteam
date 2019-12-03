package bold.client.exercice.DataAccess

import android.util.Base64
import bold.client.exercice.DataAccess.HttpUtils.IRequest
import bold.client.exercice.DataTransferObjects.Error
import com.android.volley.toolbox.NetworkImageView

class FlickrApiRequestService(private val req: IRequest) {

    companion object{
        /*request type*/
        const val FIND_USER = "FIND_USER"
        const val GET_PHOTOS = "GET_PHOTOS"
        const val GET_PHOTO_SIZES = "GET_PHOTO_SIZES"

        /*host request parameters*/
        private const val flickrApiHost: String = "https://www.flickr.com/services/rest/?"
        private const val format: String = "format=json&nojsoncallback=1"
        private var B64SMTH : String = "YXBpX2tleT0xNjUyOGY4OTZkMDk0OWYwNGEzYmE1ZGEyYzk0MDRmZA=="//proguard saved
        private const val method_find_by_username : String = "method=flickr.people.findByUsername"
        private const val method_get_public_photos : String = "method=flickr.people.getPublicPhotos"
        private const val method_get_photo_sizes  : String =  "method=flickr.photos.getSizes"
    }

    private val urlRouter= HashMap<String,( arrL: Array<String>?)-> String> ()

    init{

        B64SMTH = Base64.decode(B64SMTH,0).toString(Charsets.UTF_8)
        urlRouter.put(FIND_USER) { queryValues->
            flickrApiHost + String.format(
                "$method_find_by_username&$B64SMTH&username=%s&$format", queryValues?.get(0)!!.replace(" ","+"))}

        urlRouter.put(GET_PHOTOS) { queryValues->
            flickrApiHost + String.format(                                                      //user_id   +   pageNumber
                "$method_get_public_photos&$B64SMTH&user_id=%s&$format&per_page=10&page=%s", queryValues?.get(0), queryValues?.get(1))}

        urlRouter.put(GET_PHOTO_SIZES) { queryValues->
            flickrApiHost + String.format(
                "$method_get_photo_sizes&$B64SMTH&photo_id=%s&$format", queryValues?.get(0))}
    }

    fun doGet(requestType: String?, queryValues: Array<String>?, tag: Any?, callback: (httpError: String?, flickrError: Error?, resp: String?) -> Unit) {
        req.get(urlRouter[requestType]!!(queryValues), tag, callback)
    }


    fun getPhoto(imageUrl: String, networkImageView: NetworkImageView, withCache: Boolean){
        req.getImage(imageUrl,networkImageView, withCache)
    }

}
