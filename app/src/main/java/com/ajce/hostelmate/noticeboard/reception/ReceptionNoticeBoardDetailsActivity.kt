package com.ajce.hostelmate.noticeboard.reception

import android.net.Uri
import android.net.wifi.WifiConfiguration.AuthAlgorithm.strings
import android.os.AsyncTask
import android.os.Bundle
import android.system.Os.link
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ajce.hostelmate.R
import com.ajce.hostelmate.noticeboard.NoticeBoard
import com.github.barteksc.pdfviewer.listener.OnRenderListener
import kotlinx.android.synthetic.main.activity_notice_details_reception.*
import kotlinx.android.synthetic.main.activity_sick_leave_details_inmates.tvDescription
import java.io.BufferedInputStream
import java.io.IOException
import java.io.InputStream
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL


/**
 * Created by JithinJude on 10,May,2020
 */
class ReceptionNoticeBoardDetailsActivity : AppCompatActivity() {

    val SELECTED_NOTICE: String = "selected_notice"
    val SELECTED_POSITION: String = "selected_position"

    lateinit var notice: NoticeBoard
    var selectedPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_details_reception)

        //actionbar
        val actionbar = supportActionBar
        //set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)

        notice = intent.getParcelableExtra(SELECTED_NOTICE)
        selectedPosition = intent.getIntExtra(SELECTED_POSITION, 0)

        title = notice.noticeTitle

        tvDate.text = notice.noticeDate
        tvDescription.text = notice.noticeDescription

        progressBar.visibility = View.GONE
        if(notice.noticePdfUrl != ""){
            RetrievePDFStream().execute(notice.noticePdfUrl)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    inner class RetrievePDFStream : AsyncTask<String?, Void?, InputStream?>() {
        override fun onPreExecute() {
            progressBar.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg pdfUrl: String?): InputStream? {
            var inputStream: InputStream? = null
            Log.d("reached","reached URL string: "+ pdfUrl[0])
            try {
                val urlx = URL(pdfUrl[0])
                Log.d("reached","reached: " + urlx)
                val urlConnection: HttpURLConnection = urlx.openConnection() as HttpURLConnection
                if (urlConnection.responseCode == 200) {
                    inputStream = BufferedInputStream(urlConnection.inputStream)
                }
            } catch (e: Exception) {
                Log.d("error","My Exception: "+e.printStackTrace())
                return null
            }
            return inputStream
        }

        override fun onPostExecute(inputStream: InputStream?) {
            pdfView.fromStream(inputStream).onRender(OnRenderListener { nbPages, pageWidth, pageHeight ->
                progressBar.visibility = View.GONE
            }).load()
        }
    }
}