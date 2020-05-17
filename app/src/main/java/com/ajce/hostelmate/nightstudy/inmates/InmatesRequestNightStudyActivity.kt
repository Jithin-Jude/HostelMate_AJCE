package com.ajce.hostelmate.nightstudy.inmates

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
class InmatesRequestNightStudyActivity : AppCompatActivity() {

    val USER_EMAIL: String = "user_email"

    var databaseReference: DatabaseReference? = null

    var personEmail: String? = null
    var nightStudyReasonForRejection: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_request_night_study)

        title = getString(R.string.request_nightstudy)

        //actionbar
        val actionbar = supportActionBar
        //set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)

        personEmail = intent.extras[USER_EMAIL].toString()
        databaseReference = FirebaseDatabase.getInstance().getReference("nightstudy")

        val adapterSpinnerBlock = ArrayAdapter.createFromResource(this,
                R.array.block_list, android.R.layout.simple_spinner_item)
        adapterSpinnerBlock.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spBlock.adapter = adapterSpinnerBlock

        val adapterSpinnerRoom = ArrayAdapter.createFromResource(this,
                R.array.room_list, android.R.layout.simple_spinner_item)
        adapterSpinnerRoom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spRoom.adapter = adapterSpinnerRoom

        btnConfirm.setOnClickListener {
            addNightStudy()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun addNightStudy() {
/*        if (nightStudyReasonForRejection == null) {
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
        nightStudyReasonForRejection = ""

        if ("" == title || "" == description) {
            Toast.makeText(this, "Form fields cannot be empty", Toast.LENGTH_LONG).show()
            return
        }

        val issue = SickLeave(id, title, block, room, description, reportedBy, date, status, nightStudyReasonForRejection)
        databaseReference?.child(id!!)?.setValue(issue)
        Toast.makeText(this, "Night Study requested", Toast.LENGTH_LONG).show()
        finish()
    }

}