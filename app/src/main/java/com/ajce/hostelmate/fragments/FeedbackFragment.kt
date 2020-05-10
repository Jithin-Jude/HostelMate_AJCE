package com.ajce.hostelmate.fragments

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.ajce.hostelmate.R
import com.ajce.hostelmate.Utils.Companion.getMonthInThreeCapLetter
import com.ajce.hostelmate.servicefeedback.Feedback
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.content_issue_status.*
import kotlinx.android.synthetic.main.fragment_feedback.*
import org.apache.commons.net.ntp.NTPUDPClient
import org.apache.commons.net.ntp.TimeInfo
import java.net.InetAddress
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by JithinJude on 01,May,2020
 */
class FeedbackFragment : Fragment() {

    var databaseReference: DatabaseReference? = null

    val USER_EMAIL: String = "user_email"
    var personEmail: String? = null

    var currentYY: String? = null
    var currentMM: String? = null

    var foodRating: Int? = 0
    var foodReview: String? = ""
    var cleaningRating: Int? = 0
    var cleaningReview: String? = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_feedback, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = getString(R.string.request_feedback)

        personEmail = arguments?.getString(USER_EMAIL)

        ratingBarFood.setOnRatingChangeListener { ratingBar, preCount, currentRating ->
            foodRating = currentRating
        }
        ratingBarCleaning.setOnRatingChangeListener { ratingBar, preCount, currentRating ->
            cleaningRating = currentRating
        }

        btnSubmitFeedback.setOnClickListener {
            foodReview = etFood.text.toString()
            cleaningReview = etCleaning.text.toString()

            val yearMonthKey = "2020_"+getMonthInThreeCapLetter(currentMM?.toInt()!!)
            databaseReference = FirebaseDatabase.getInstance().getReference("feedback/$yearMonthKey")

            submitFeedback()
        }

        GetInternetTime().execute()
    }

    fun submitFeedback() {

        if (foodRating == 0 || cleaningRating == 0 || foodReview == "" || cleaningReview == "") {
            Toast.makeText(context, "Form fields cannot be empty", Toast.LENGTH_LONG).show()
            return
        }

        val monthlyKey = personEmail?.replace(".", "_")

        val feedback = Feedback(personEmail, foodRating, foodReview, cleaningRating, cleaningReview)
        databaseReference?.child(monthlyKey!!)?.setValue(feedback)
        Toast.makeText(context, "Feedback submitted", Toast.LENGTH_LONG).show()
    }

    inner class GetInternetTime : AsyncTask<String?, Void?, String?>() {
        override fun onPreExecute() {
            activity?.pbLoadingInmatesDashboard?.visibility = View.VISIBLE
            btnSubmitFeedback.isEnabled = false
        }

        override fun doInBackground(vararg pdfUrl: String?): String? {

            val TIME_SERVER = "time.nist.gov"
            val timeClient = NTPUDPClient()
            val internetAddress: InetAddress = InetAddress.getByName(TIME_SERVER)
            val timeInfo: TimeInfo = timeClient.getTime(internetAddress)
            val returnTime: Long = timeInfo.message.transmitTimeStamp.time
            val time = Date(returnTime)

            currentYY = SimpleDateFormat("yyyy", Locale.US).format(time)
            currentMM = SimpleDateFormat("MM", Locale.US).format(time)
            return currentMM
        }

        override fun onPostExecute(currentTime: String?) {
            Toast.makeText(activity?.applicationContext, "currentMonth: $currentMM", Toast.LENGTH_LONG).show()
            activity?.pbLoadingInmatesDashboard?.visibility = View.GONE
            btnSubmitFeedback.isEnabled = true
        }
    }
}