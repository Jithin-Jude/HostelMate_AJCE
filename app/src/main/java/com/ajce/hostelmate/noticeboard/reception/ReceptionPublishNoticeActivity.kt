package com.ajce.hostelmate.noticeboard.reception

import android.appwidget.AppWidgetManager
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ajce.hostelmate.R
import com.ajce.hostelmate.noticeboard.NoticeBoard
import com.ajce.hostelmate.reportissue.Issue
import com.ajce.hostelmate.sickleave.SickLeave
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_request_sick_leave.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by JithinJude on 02,May,2020
 */
class ReceptionPublishNoticeActivity : AppCompatActivity() {

    var databaseReference: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish_notice)

        title = getString(R.string.publish_notice)

        //actionbar
        val actionbar = supportActionBar
        //set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)

        databaseReference = FirebaseDatabase.getInstance().getReference("noticeboard")

        btnConfirm.setOnClickListener {
            publishNotice()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun publishNotice() {
/*        if (sickLeaveReasonForRejection == null) {
            Toast.makeText(this, "Take photo of Issue", Toast.LENGTH_LONG).show()
            return
        }*/
        val title = etTitle?.text.toString()
        val description = etDescription?.text.toString()
        val id = databaseReference?.push()?.key
        val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

        if ("" == title || "" == description) {
            Toast.makeText(this, "Form fields cannot be empty", Toast.LENGTH_LONG).show()
            return
        }

        val issue = NoticeBoard(id, title, description, date)
        databaseReference?.child(id!!)?.setValue(issue)
        Toast.makeText(this, "Notice published", Toast.LENGTH_LONG).show()
        finish()
    }
}