package com.ajce.hostelmate.reportissue

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Base64
import android.view.View
import com.ajce.hostelmate.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_reported_issues_details_for_reception.*

class ReportedIssuesDetailsForReceptionActivity : AppCompatActivity() {

    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reported_issues_details_for_reception)
        position = intent.getIntExtra("POSITION_ID", 0)

        title = ReportedIssuesActivity.issueList?.get(position)?.issueTitle
        tv_block.text = ReportedIssuesActivity.issueList?.get(position)?.issueBlock
        tv_room.text = ReportedIssuesActivity.issueList?.get(position)?.issueRoom
        tv_description.text = ReportedIssuesActivity.issueList?.get(position)?.issueDescription
        tv_reported_by.text = ReportedIssuesActivity.issueList?.get(position)?.issueReportedBy
        try {
            val imageBitmap = decodeFromFirebaseBase64(ReportedIssuesActivity.Companion.issueList?.get(position)?.imageEncoded)
            img_issue.setImageBitmap(imageBitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (ReportedIssuesActivity.Companion.issueList?.get(position)?.issueStatus == "Fixed") {
            mark_as_fixed.setVisibility(View.INVISIBLE)
            tick_mark.setVisibility(View.VISIBLE)
        } else {
            mark_as_fixed.setVisibility(View.VISIBLE)
            tick_mark.setVisibility(View.INVISIBLE)
        }
    }

    fun updateIssueStatus(view: View?) {
        val databaseReference = ReportedIssuesActivity.Companion.issueList?.get(position)?.issueId?.let {
            FirebaseDatabase.getInstance().getReference("issues")
                .child(it)
        }
        val status = "Fixed"
        val id: String? = ReportedIssuesActivity.issueList?.get(position)?.issueId
        val title: String? = ReportedIssuesActivity.issueList?.get(position)?.issueTitle
        val block: String? = ReportedIssuesActivity.issueList?.get(position)?.issueBlock
        val room: String? = ReportedIssuesActivity.issueList?.get(position)?.issueRoom
        val description: String? = ReportedIssuesActivity.issueList?.get(position)?.issueDescription
        val reportedBy: String? = ReportedIssuesActivity.issueList?.get(position)?.issueReportedBy
        val date: String? = ReportedIssuesActivity.issueList?.get(position)?.issueDate
        val imageEncoded: String? = ReportedIssuesActivity.issueList?.get(position)?.imageEncoded
        val issue = Issue(id, title, block, room, description, reportedBy, date, status, imageEncoded)
        databaseReference?.setValue(issue)
        mark_as_fixed.visibility = View.INVISIBLE
        tick_mark.visibility = View.VISIBLE
    }

    companion object {
        fun decodeFromFirebaseBase64(image: String?): Bitmap? {
            val decodedByteArray = Base64.decode(image, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
        }
    }
}