package com.ajce.hostelmate.reportissue

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.ajce.hostelmate.R
import com.google.firebase.database.FirebaseDatabase

class ReportedIssuesDetailsForReceptionActivity : AppCompatActivity() {
    lateinit var blockTextView: TextView
    lateinit var roomTextView: TextView
    lateinit var descriptionTextView: TextView
    lateinit var reportedByTextView: TextView
    lateinit var mButton: Button
    lateinit var mImageView: ImageView
    lateinit var tickButton: ImageView
    var position = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reported_issues_details_for_reception)
        position = intent.getIntExtra("POSITION_ID", 0)
        blockTextView = findViewById(R.id.tv_block)
        roomTextView = findViewById(R.id.tv_room)
        descriptionTextView = findViewById(R.id.tv_description)
        reportedByTextView = findViewById(R.id.tv_reported_by)
        mButton = findViewById(R.id.mark_as_fixed)
        mImageView = findViewById(R.id.img_issue)
        tickButton = findViewById(R.id.tick_mark)
        setTitle(ReportedIssuesActivity.Companion.issueList?.get(position)?.issueTitle)
        blockTextView.setText(ReportedIssuesActivity.Companion.issueList?.get(position)?.issueBlock)
        roomTextView.setText(ReportedIssuesActivity.Companion.issueList?.get(position)?.issueRoom)
        descriptionTextView.setText(ReportedIssuesActivity.Companion.issueList?.get(position)?.issueDescription)
        reportedByTextView.setText(ReportedIssuesActivity.Companion.issueList?.get(position)?.issueReportedBy)
        try {
            val imageBitmap = decodeFromFirebaseBase64(ReportedIssuesActivity.Companion.issueList?.get(position)?.imageEncoded)
            mImageView.setImageBitmap(imageBitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        if (ReportedIssuesActivity.Companion.issueList?.get(position)?.issueStatus == "Fixed") {
            mButton.setVisibility(View.INVISIBLE)
            tickButton.setVisibility(View.VISIBLE)
        } else {
            mButton.setVisibility(View.VISIBLE)
            tickButton.setVisibility(View.INVISIBLE)
        }
    }

    fun updateIssueStatus(view: View?) {
        val databaseReference = ReportedIssuesActivity.Companion.issueList?.get(position)?.issueId?.let {
            FirebaseDatabase.getInstance().getReference("issues")
                .child(it)
        }
        val status = "Fixed"
        val id: String? = ReportedIssuesActivity.Companion.issueList?.get(position)?.issueId
        val title: String? = ReportedIssuesActivity.Companion.issueList?.get(position)?.issueTitle
        val block: String? = ReportedIssuesActivity.Companion.issueList?.get(position)?.issueBlock
        val room: String? = ReportedIssuesActivity.Companion.issueList?.get(position)?.issueRoom
        val description: String? = ReportedIssuesActivity.Companion.issueList?.get(position)?.issueDescription
        val reportedBy: String? = ReportedIssuesActivity.Companion.issueList?.get(position)?.issueReportedBy
        val date: String? = ReportedIssuesActivity.Companion.issueList?.get(position)?.issueDate
        val imageEncoded: String? = ReportedIssuesActivity.Companion.issueList?.get(position)?.imageEncoded
        val issue = Issue(id, title, block, room, description, reportedBy, date, status, imageEncoded)
        databaseReference?.setValue(issue)
        mButton.setVisibility(View.INVISIBLE)
        tickButton.setVisibility(View.VISIBLE)
    }

    companion object {
        fun decodeFromFirebaseBase64(image: String?): Bitmap? {
            val decodedByteArray = Base64.decode(image, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
        }
    }
}