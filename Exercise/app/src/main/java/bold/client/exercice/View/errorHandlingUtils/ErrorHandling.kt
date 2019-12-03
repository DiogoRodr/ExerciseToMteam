package bold.client.exercice.View.errorHandlingUtils

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import bold.client.exercice.MyApplication
import bold.client.exercice.R

class ErrorHandling {
    companion object {
        fun errorDialog(err: String, activity: Activity) {
            val builder = AlertDialog.Builder(activity)
            builder.setMessage(err)
            builder.setNegativeButton(MyApplication.appContext.getString(R.string.cancel)) { _, _ -> activity.finish()}
            //builder.setPositiveButton(MyApplication.appContext?.getString(R.string.retry)) { _, _ -> activity.recreate() }
            builder.setCancelable(false)
            builder.show()
        }
    }
}