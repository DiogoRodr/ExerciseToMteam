package bold.client.exercice.DataAccess.HttpUtils;

import android.content.Context
import android.graphics.Bitmap
import bold.client.exercice.MyApplication
import bold.client.exercice.R
import bold.client.exercice.DataTransferObjects.Error
import com.android.volley.*
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject
import java.util.concurrent.ConcurrentHashMap

class HttpRequests(ctx: Context) : IRequest {
    private val requestQueue: RequestQueue = Volley.newRequestQueue(ctx)
    private val mImageLoaderWithCache = ImageLoader(requestQueue, TempCache())
    private val mImageLoaderWithoutCache = ImageLoader(
        requestQueue,
        null
    )

    override fun get(url: String, tag: Any?, callback: (httpError: String?, flickrError: Error?,  resp: String?) -> Unit) {
        requestQueue.add(StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> { response ->
                //since flickr is not restfull, we must check for errors in a ugly way :(
                val jsonResponse:JSONObject = JSONObject(response)
                if(jsonResponse.has("stat") && jsonResponse.has("code") && jsonResponse.has("message")) {
                    val flickrError: Error = Gson().fromJson(jsonResponse.toString(), Error::class.java)
                    callback(null, flickrError, null)
                }
                else callback(null, null, response)
             },
            Response.ErrorListener { error ->
                if (error is TimeoutError || error is NoConnectionError)
                    callback(
                        MyApplication.appContext.getString(R.string.error),
                        null,
                        null
                    )
                else {
                    callback(
                        "HTTP Error Code: " + error.networkResponse.statusCode ,
                         null,
                        null
                    )
                }
            }
        ).setTag(tag)
        )
    }

    override fun getImage(url: String, image: NetworkImageView, withCache: Boolean) {
        image.setImageUrl(
            url,
            if (withCache) mImageLoaderWithCache else mImageLoaderWithoutCache
        )
    }

    fun cancelRequests(tag: Any) {
        requestQueue.cancelAll(tag)
    }

    class TempCache : ImageLoader.ImageCache {
        private val cache: HashMap<String, Bitmap> = HashMap()

        override fun getBitmap(url: String?): Bitmap? {
            return cache[url]
        }

        override fun putBitmap(url: String?, bitmap: Bitmap?) {
            cache[url!!] = bitmap!!
        }
    }
}