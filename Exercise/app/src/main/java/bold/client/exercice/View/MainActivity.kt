package bold.client.exercice.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import bold.client.exercice.MyApplication.Companion.userService
import bold.client.exercice.R
import bold.client.exercice.View.errorHandlingUtils.ErrorHandling.Companion.errorDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        searchView1.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(p0: String?): Boolean = false

            override fun onQueryTextSubmit(query: String): Boolean {
                userService.findUserByUsername(query) { httpError, flickrError, userInfo ->
                    if (httpError != null)
                        errorDialog(httpError,this@MainActivity)
                    else if(flickrError != null)
                        errorDialog(flickrError.message,this@MainActivity)
                    else {
                        val mIntent = Intent(this@MainActivity, PhotosListActivity::class.java)
                        mIntent.putExtra("userId", userInfo!!.user.id)
                        mIntent.putExtra("username", userInfo.user.username._content)
                        startActivity(mIntent)
                    }
                }
                return true
            }
        })
    }
}
