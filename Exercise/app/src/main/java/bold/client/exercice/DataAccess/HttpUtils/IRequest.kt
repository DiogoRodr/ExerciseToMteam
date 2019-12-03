package bold.client.exercice.DataAccess.HttpUtils;


import bold.client.exercice.DataTransferObjects.Error
import com.android.volley.toolbox.NetworkImageView


interface IRequest {
    fun get(url: String, tag: Any?, callback: (httpError: String?, flickrError: Error?, resp: String?) -> Unit)

    fun getImage(url: String, image: NetworkImageView, withCache: Boolean)
}
