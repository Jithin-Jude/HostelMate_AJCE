package com.ajce.hostelmate

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.os.AsyncTask
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import com.ajce.hostelmate.login.InmatesLoginActivity
import com.ajce.hostelmate.login.LoginSelectActivity
import com.ajce.hostelmate.login.ReceptionLoginActivity

class SplashScreenActivity : AppCompatActivity() {
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var firstTimeCheck: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AsyncCaller().execute()
    }

    override fun onPause() {
        super.onPause()
        finish()
    }

    override fun onBackPressed() {}

    //=========================================AsyncTask================================================
    private inner class AsyncCaller : AsyncTask<Void?, Void?, Void?>() {
        override fun onPreExecute() {
            super.onPreExecute()
            //this method will be running on UI thread
        }

        override fun doInBackground(vararg params: Void?): Void? {

            //this method will be running on background thread so don't update UI frome here
            //do your long running http tasks here,you dont want to pass argument and u can access the parent class' variable url over here
            try {
                Thread.sleep(1000)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(result: Void?) {
            super.onPostExecute(result)

            //this method will be running on UI thread
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
            editor = sharedPreferences.edit()
            firstTimeCheck = sharedPreferences.getString("FIRST_TIME_CHECK", null)
            editor.apply()
            if (isNetwork()) {
                if (firstTimeCheck == null) {
                    val intent = Intent(this@SplashScreenActivity, LoginSelectActivity::class.java)
                    startActivity(intent)
                } else if (firstTimeCheck == "admin") {
                    val intent = Intent(this@SplashScreenActivity, ReceptionLoginActivity::class.java)
                    startActivity(intent)
                } else if (firstTimeCheck == "inmate") {
                    val intent = Intent(this@SplashScreenActivity, InmatesLoginActivity::class.java)
                    startActivity(intent)
                }
            } else {
                finish()
                val intent = Intent(this@SplashScreenActivity, NoNetwork::class.java)
                startActivity(intent)
            }
        }
    }

    //=========================================AsyncTask================================================
    fun isNetwork(): Boolean {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }
}