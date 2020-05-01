package com.ajce.hostelmate.reportissue

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Base64
import com.ajce.hostelmate.R
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_reported_issues_details_for_inmates.*

class ReportedIssuesDetailsForInmatesActivity : AppCompatActivity() {

    val SELECTED_ISSUE: String = "selected_issue"
    val SELECTED_POSITION: String = "selected_position"

    lateinit var issue: Issue
    var selectedPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reported_issues_details_for_inmates)
        issue = intent.getParcelableExtra(SELECTED_ISSUE)
        selectedPosition = intent.getIntExtra(SELECTED_POSITION, 0)

        title = issue.issueTitle
        tvBlock.text = issue.issueBlock
        tvRoom.text = issue.issueRoom
        tvDescription.text = issue.issueDescription
        tvStatusInmates.text = issue.issueStatus

        Glide.with(this)
                .load(issue.issueImageUrl)
                .into(ivImgIssueInmates)
    }
}