package bold.client.exercise.DataAccess

import bold.client.exercise.DataAccess.HttpUtils.IRequest
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
        private val api_key : String = System.getenv("flickr_api_key")
        private const val method_find_by_username : String = "method=flickr.people.findByUsername"
        private const val method_get_public_photos : String = "method=flickr.people.getPublicPhotos"
        private const val method_get_photo_sizes  : String =  "method=flickr.photos.getSizes"
    }

    private val urlRouter= HashMap<String,( arrL: Array<String>?)-> String> ()

    init{
        urlRouter.put(FIND_USER) { queryValues->
            flickrApiHost + String.format(
                "$method_find_by_username&$api_key&username=%s&$format", queryValues?.get(0)!!.replace(" ","+"))}

        urlRouter.put(GET_PHOTOS) { queryValues->
            flickrApiHost + String.format(                                                      //user_id   +   pageNumber
                "$method_get_public_photos&$api_key&user_id=%s&$format&per_page=10&page=%s", queryValues?.get(0), queryValues?.get(1))}

        urlRouter.put(GET_PHOTO_SIZES) { queryValues->
            flickrApiHost + String.format(
                "$method_get_photo_sizes&$api_key&photo_id=%s&$format", queryValues?.get(0))}
    }

    fun doGet(requestType: String?, queryValues: Array<String>?, tag: Any?, callback: (err: String?, resp: String?) -> Unit) {
        req.get(urlRouter[requestType]!!(queryValues), tag, callback)
    }


    fun getPhoto(imageUrl: String, networkImageView: NetworkImageView, withCache: Boolean){
        req.getImage(imageUrl,networkImageView, withCache)
    }

}
