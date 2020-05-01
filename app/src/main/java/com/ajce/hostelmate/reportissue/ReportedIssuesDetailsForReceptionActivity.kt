package com.ajce.hostelmate.reportissue

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Base64
import android.view.View
import com.ajce.hostelmate.R
import com.bumptech.glide.Glide
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_reported_issues_details_for_reception.*

class ReportedIssuesDetailsForReceptionActivity : AppCompatActivity() {

    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reported_issues_details_for_reception)
        position = intent.getIntExtra("POSITION_ID", 0)

        title = ReceptionDashboardActivity.issueList?.get(position)?.issueTitle
        tvBlock.text = ReceptionDashboardActivity.issueList?.get(position)?.issueBlock
        tvRoom.text = ReceptionDashboardActivity.issueList?.get(position)?.issueRoom
        tvDescription.text = ReceptionDashboardActivity.issueList?.get(position)?.issueDescription
        tvReportedBy.text = ReceptionDashboardActivity.issueList?.get(position)?.issueReportedBy

        Glide.with(this)
                .load(ReceptionDashboardActivity.issueList?.get(position)?.issueImageUrl)
                .into(ivImgIssue)

        if (ReceptionDashboardActivity.Companion.issueList?.get(position)?.issueStatus == "Fixed") {
            markAsFixed.visibility = View.INVISIBLE
            tickMark.visibility = View.VISIBLE
        } else {
            markAsFixed.visibility = View.VISIBLE
            tickMark.visibility = View.INVISIBLE
        }
    }

    fun updateIssueStatus(view: View?) {
        val databaseReference = ReceptionDashboardActivity.Companion.issueList?.get(position)?.issueId?.let {
            FirebaseDatabase.getInstance().getReference("issues")
                .child(it)
        }
        val status = "Fixed"
        val id: String? = ReceptionDashboardActivity.issueList?.get(position)?.issueId
        val title: String? = ReceptionDashboardActivity.issueList?.get(position)?.issueTitle
        val block: String? = ReceptionDashboardActivity.issueList?.get(position)?.issueBlock
        val room: String? = ReceptionDashboardActivity.issueList?.get(position)?.issueRoom
        val description: String? = ReceptionDashboardActivity.issueList?.get(position)?.issueDescription
        val reportedBy: String? = ReceptionDashboardActivity.issueList?.get(position)?.issueReportedBy
        val date: String? = ReceptionDashboardActivity.issueList?.get(position)?.issueDate
        val imageEncoded: String? = ReceptionDashboardActivity.issueList?.get(position)?.issueImageUrl
        val issue = Issue(id, title, block, room, description, reportedBy, date, status, imageEncoded)
        databaseReference?.setValue(issue)
        markAsFixed.visibility = View.INVISIBLE
        tickMark.visibility = View.VISIBLE
    }

    companion object {
        fun decodeFromFirebaseBase64(image: String?): Bitmap? {
            val decodedByteArray = Base64.decode(image, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
        }
    }
}