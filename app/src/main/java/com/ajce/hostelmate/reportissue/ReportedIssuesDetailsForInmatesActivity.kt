package com.ajce.hostelmate.reportissue

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import com.ajce.hostelmate.R
import kotlinx.android.synthetic.main.activity_reported_issues_details_for_inmates.*

class ReportedIssuesDetailsForInmatesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reported_issues_details_for_inmates)
        val position = intent.getIntExtra("POSITION_ID", 0)

        title = IssueStatusActivity.issueList?.get(position)?.issueTitle
        tv_block.text = IssueStatusActivity.issueList?.get(position)?.issueBlock
        tv_room.text = IssueStatusActivity.issueList?.get(position)?.issueRoom
        tv_description.text = IssueStatusActivity.issueList?.get(position)?.issueDescription
        tv_status_inmates.text = IssueStatusActivity.issueList?.get(position)?.issueStatus
        try {
            val imageBitmap = decodeFromFirebaseBase64(IssueStatusActivity.issueList?.get(position)?.imageEncoded)
            img_issue_inmates.setImageBitmap(imageBitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    companion object {
        fun decodeFromFirebaseBase64(image: String?): Bitmap? {
            val decodedByteArray = Base64.decode(image, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
        }
    }
}