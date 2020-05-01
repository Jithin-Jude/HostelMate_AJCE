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

    val SELECTED_ISSUE: String = "selected_issue"
    val SELECTED_POSITION: String = "selected_position"
    
    lateinit var issue: Issue
    var selectedPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reported_issues_details_for_reception)

        //actionbar
        val actionbar = supportActionBar
        //set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)

        issue = intent.getParcelableExtra(SELECTED_ISSUE)
        selectedPosition = intent.getIntExtra(SELECTED_POSITION, 0)

        title = issue.issueTitle
        tvBlock.text = issue.issueBlock
        tvRoom.text = issue.issueRoom
        tvDescription.text = issue.issueDescription
        tvReportedBy.text = issue.issueReportedBy

        Glide.with(this)
                .load(issue.issueImageUrl)
                .into(ivImgIssue)

        if (issue.issueStatus == "Fixed") {
            markAsFixed.visibility = View.INVISIBLE
            tickMark.visibility = View.VISIBLE
        } else {
            markAsFixed.visibility = View.VISIBLE
            tickMark.visibility = View.INVISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun updateIssueStatus(view: View?) {
        val databaseReference = issue.issueId?.let {
            FirebaseDatabase.getInstance().getReference("issues")
                .child(it)
        }
        val status = "Fixed"
        val id: String? = issue.issueId
        val title: String? = issue.issueTitle
        val block: String? = issue.issueBlock
        val room: String? = issue.issueRoom
        val description: String? = issue.issueDescription
        val reportedBy: String? = issue.issueReportedBy
        val date: String? = issue.issueDate
        val imageEncoded: String? = issue.issueImageUrl
        val issue = Issue(id, title, block, room, description, reportedBy, date, status, imageEncoded)
        databaseReference?.setValue(issue)
        markAsFixed.visibility = View.INVISIBLE
        tickMark.visibility = View.VISIBLE
    }
}