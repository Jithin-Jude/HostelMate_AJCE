package com.ajce.hostelmate.sickleave.inmates

import android.appwidget.AppWidgetManager
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ajce.hostelmate.R
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
class InmatesRequestSickLeaveActivity : AppCompatActivity() {

    val USER_EMAIL: String = "user_email"

    var databaseReference: DatabaseReference? = null

    var personEmail: String? = null
    var sickLeaveReasonForRejection: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_sick_leave)

        title = getString(R.string.request_sickleave)

        //actionbar
        val actionbar = supportActionBar
        //set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)

        personEmail = intent.extras[USER_EMAIL].toString()
        databaseReference = FirebaseDatabase.getInstance().getReference("sickleave")

        val adapterSpinnerBlock = ArrayAdapter.createFromResource(this,
                R.array.block_list, android.R.layout.simple_spinner_item)
        adapterSpinnerBlock.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spBlock.adapter = adapterSpinnerBlock

        val adapterSpinnerRoom = ArrayAdapter.createFromResource(this,
                R.array.room_list, android.R.layout.simple_spinner_item)
        adapterSpinnerRoom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spRoom.adapter = adapterSpinnerRoom
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun addSickLeave(view: View?) {
/*        if (sickLeaveReasonForRejection == null) {
            Toast.makeText(this, "Take photo of Issue", Toast.LENGTH_LONG).show()
            return
        }*/
        val title = etTitle?.text.toString()
        val block = spBlock.selectedItem.toString()
        val room = spRoom.selectedItem.toString()
        val description = etDescription?.text.toString()
        val reportedBy = personEmail
        val id = databaseReference?.push()?.key
        val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val status = "pending"
        sickLeaveReasonForRejection = ""
        val issue = SickLeave(id, title, block, room, description, reportedBy, date, status, sickLeaveReasonForRejection)
        databaseReference?.child(id!!)?.setValue(issue)
        Toast.makeText(this, "Sick leave requested", Toast.LENGTH_LONG).show()
//        updateWidget(title)
        finish()
    }

/*    fun updateWidget(widgetText: String?) {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val remoteViews = RemoteViews(this.packageName, R.layout.widget_for_inmates)
        val intent = Intent(this, InmatesLoginActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        remoteViews.setOnClickPendingIntent(R.id.ivIssueImgWidget, pendingIntent)
        val thisWidget = ComponentName(this, WidgetForInmates::class.java)
        remoteViews.setTextViewText(R.id.tvWidgetText, widgetText)
        remoteViews.setImageViewResource(R.id.ivIssueImgWidget, R.drawable.hostel_red)
        appWidgetManager.updateAppWidget(thisWidget, remoteViews)
    }*/
}