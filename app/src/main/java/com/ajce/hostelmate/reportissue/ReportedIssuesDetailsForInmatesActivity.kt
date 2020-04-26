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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reported_issues_details_for_inmates)
        val position = intent.getIntExtra("POSITION_ID", 0)

        title = IssueStatusActivity.issueList?.get(position)?.issueTitle
        tvBlock.text = IssueStatusActivity.issueList?.get(position)?.issueBlock
        tvRoom.text = IssueStatusActivity.issueList?.get(position)?.issueRoom
        tvDescription.text = IssueStatusActivity.issueList?.get(position)?.issueDescription
        tvStatusInmates.text = IssueStatusActivity.issueList?.get(position)?.issueStatus

        Glide.with(this)
                .load(IssueStatusActivity.issueList?.get(position)?.issueImageUrl)
                .into(ivImgIssueInmates)
    }

    companion object {
        fun decodeFromFirebaseBase64(image: String?): Bitmap? {
            val decodedByteArray = Base64.decode(image, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
        }
    }
}