package com.ajce.hostelmate.fragments

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ajce.hostelmate.R
import kotlinx.android.synthetic.main.content_issue_status.*
import kotlinx.android.synthetic.main.fragment_feedback.*
import org.apache.commons.net.ntp.NTPUDPClient
import org.apache.commons.net.ntp.TimeInfo
import java.lang.Exception
import java.net.InetAddress
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by JithinJude on 01,May,2020
 */
class FeedbackFragment : Fragment() {

    var currentTime: String? = null

    var foodRating: Int? = 0
    var foodReview: String? = ""
    var cleaningRating: Int? = 0
    var cleaningReview: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_feedback, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = getString(R.string.request_feedback)

        ratingBarFood.setOnRatingChangeListener { ratingBar, preCount, currentRating ->
            foodRating = currentRating
        }
        ratingBarCleaning.setOnRatingChangeListener { ratingBar, preCount, currentRating ->
            cleaningRating = currentRating
        }

        btnSubmitFeedback.setOnClickListener {
            foodReview = etFood.text.toString()
            cleaningReview = etCleaning.text.toString()

            Toast.makeText(context, "rating: $foodRating\n\n$foodReview", Toast.LENGTH_LONG).show()
        }

        GetInternetTime().execute()
    }

    inner class GetInternetTime : AsyncTask<String?, Void?, String?>() {
        override fun onPreExecute() {
            activity?.pbLoadingInmatesDashboard?.visibility = View.VISIBLE
        }

        override fun doInBackground(vararg pdfUrl: String?): String? {

            val TIME_SERVER = "time.nist.gov"
            val timeClient = NTPUDPClient()
            val internetAddress: InetAddress = InetAddress.getByName(TIME_SERVER)
            val timeInfo: TimeInfo = timeClient.getTime(internetAddress)
            val returnTime: Long = timeInfo.message.transmitTimeStamp.time
            val time = Date(returnTime)

            val s = SimpleDateFormat("yyyyMM", Locale.US)
            currentTime = s.format(time)
            return currentTime
        }

        override fun onPostExecute(currentTime: String?) {
            Toast.makeText(activity?.applicationContext, "time: $currentTime", Toast.LENGTH_LONG).show()
            activity?.pbLoadingInmatesDashboard?.visibility = View.GONE
        }
    }
}