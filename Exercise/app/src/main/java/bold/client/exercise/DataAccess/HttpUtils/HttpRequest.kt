package bold.client.exercise.DataAccess.HttpUtils;

import bold.client.exercise.MyApplication.Companion.imageCache
import android.content.Context
import android.graphics.Bitmap
import bold.client.exercise.MyApplication
import bold.client.exercise.R
import bold.client.exercise.DataTransferObjects.Error
import com.android.volley.*
import com.android.volley.toolbox.ImageLoader
import com.android.volley.toolbox.NetworkImageView
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class HttpRequests(ctx: Context) : IRequest {
    private val requestQueue: RequestQueue = Volley.newRequestQueue(ctx)
    private val mImageLoaderWithCache = ImageLoader(requestQueue, imageCache)
    private val mImageLoaderWithoutCache = ImageLoader(
        requestQueue,
        TempCache()
    )

    override fun get(url: String, tag: Any?, callback: (err: String?, resp: String?) -> Unit) {
        requestQueue.add(StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> { response -> callback(null, response) },
            Response.ErrorListener { error ->
                if (error is TimeoutError || error is NoConnectionError)
                    callback(
                        MyApplication.appContext.getString(R.string.error),
                        null
                    )
                else {
                    val dto = Gson().fromJson(
                        String(error.networkResponse.data),
                        Error::class.java
                    )
                    callback(
                        "Error Code: " + dto.code + "\n\t" + dto.message,
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
            System.out.println("Getting cached image")
            return cache[url]
        }

        override fun putBitmap(url: String?, bitmap: Bitmap?) {
            cache.put(url!!, bitmap!!)
        }
    }
}