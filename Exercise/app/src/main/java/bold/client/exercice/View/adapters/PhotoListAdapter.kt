package bold.client.exercice.View.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import bold.client.exercice.DataTransferObjects.Photo
import bold.client.exercice.MyApplication.Companion.flickrApi
import bold.client.exercice.MyApplication.Companion.photoService
import bold.client.exercice.R
import com.android.volley.toolbox.NetworkImageView

class PhotoListAdapter(
    context: Context,
    resource: Int,
    private val photoList: Array<Photo>
) :  ArrayAdapter<Photo>(context, resource, photoList) {

    override fun getCount(): Int {
        super.getCount()
        return photoList.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val holder: ViewHolder
        var row = convertView
        if (row == null) {
            val inflater = LayoutInflater.from(context)
            row = inflater.inflate(R.layout.photos_grid_item, parent, false)

            holder = ViewHolder(
                row.findViewById(R.id.photo_title) as TextView,
                row.findViewById(R.id.photo) as NetworkImageView
            )
            row.tag = holder
        } else
            holder = row.tag as ViewHolder
        //Request Image
        val photoId = photoList!![position].id
        if (photoId != null)
            photoService.findPhotoSizes(photoId) { httpError, flickrError, photoSizes ->
                if (httpError != null || flickrError != null)
                    holder.poster.setImageUrl(null, null)
                else
                    flickrApi.getPhoto(photoSizes!!.sizes.size[1].source, holder.poster, true)
            }
        else
            holder.poster.setImageUrl(null, null)       //Remove image from NetworkImageView

        holder.photoTitle.text = photoList[position].title


        return row!!
    }


    class ViewHolder(val photoTitle: TextView, val poster: NetworkImageView)
}
