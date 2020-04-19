package com.ajce.hostelmate.reportissue

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.widget.ImageView
import android.widget.TextView
import com.ajce.hostelmate.R

class ReportedIssuesDetailsForInmatesActivity : AppCompatActivity() {
    lateinit var blockTextView: TextView
    lateinit var roomTextView: TextView
    lateinit var descriptionTextView: TextView
    lateinit var statusTextView: TextView
    lateinit var mImageView: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reported_issues_details_for_inmates)
        val position = intent.getIntExtra("POSITION_ID", 0)
        blockTextView = findViewById(R.id.tv_block)
        roomTextView = findViewById(R.id.tv_room)
        descriptionTextView = findViewById(R.id.tv_description)
        statusTextView = findViewById(R.id.tv_status_inmates)
        mImageView = findViewById(R.id.img_issue_inmates)
        setTitle(IssueStatusActivity.Companion.issueList?.get(position)?.issueTitle)
        blockTextView.setText(IssueStatusActivity.Companion.issueList?.get(position)?.issueBlock)
        roomTextView.setText(IssueStatusActivity.Companion.issueList?.get(position)?.issueRoom)
        descriptionTextView.setText(IssueStatusActivity.Companion.issueList?.get(position)?.issueDescription)
        statusTextView.setText(IssueStatusActivity.Companion.issueList?.get(position)?.issueStatus)
        try {
            val imageBitmap = decodeFromFirebaseBase64(IssueStatusActivity.Companion.issueList?.get(position)?.imageEncoded)
            mImageView.setImageBitmap(imageBitmap)
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