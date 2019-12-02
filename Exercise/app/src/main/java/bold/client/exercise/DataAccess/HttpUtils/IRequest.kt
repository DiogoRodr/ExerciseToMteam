package bold.client.exercise.DataAccess.HttpUtils;


import com.android.volley.toolbox.NetworkImageView


interface IRequest {
    fun get(url: String, tag: Any?, callback: (err: String?, resp: String?) -> Unit)

    fun getImage(url: String, image: NetworkImageView, withCache: Boolean)
}
