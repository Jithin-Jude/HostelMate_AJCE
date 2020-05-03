package com.ajce.hostelmate.sickleave.reception

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ajce.hostelmate.R
import com.ajce.hostelmate.sickleave.SickLeave
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_sick_leave_details_reception.*

class ReceptionSickLeaveDetailsActivity : AppCompatActivity() {

    val SELECTED_SICK_LEAVE: String = "selected_sickleave"
    val SELECTED_POSITION: String = "selected_position"

    lateinit var sickLeave: SickLeave
    var selectedPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sick_leave_details_reception)

        //actionbar
        val actionbar = supportActionBar
        //set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)

        sickLeave = intent.getParcelableExtra(SELECTED_SICK_LEAVE)
        selectedPosition = intent.getIntExtra(SELECTED_POSITION, 0)

        title = sickLeave.sickLeaveTitle
        tvBlock.text = sickLeave.sickLeaveBlock
        tvRoom.text = sickLeave.sickLeaveRoom
        tvDescription.text = sickLeave.sickLeaveReason
        tvReportedBy.text = sickLeave.sickLeaveReportedBy


        if (sickLeave.sickLeaveStatus == "pending") {
            btnApproveSickLeave.visibility = View.VISIBLE
            btnRejectSickLeave.visibility = View.VISIBLE
            tickMark.visibility = View.GONE
            ivXXX.visibility = View.GONE
        } else {
            if (sickLeave.sickLeaveStatus == "approved"){
                btnApproveSickLeave.visibility = View.GONE
                btnRejectSickLeave.visibility = View.GONE
                ivXXX.visibility = View.GONE
                tickMark.visibility = View.VISIBLE
                etComments.setText(sickLeave.sickLeaveReasonForRejection)
                etComments.isFocusable = false
            } else {
                btnApproveSickLeave.visibility = View.GONE
                btnRejectSickLeave.visibility = View.GONE
                tickMark.visibility = View.GONE
                ivXXX.visibility = View.VISIBLE
                etComments.setText(sickLeave.sickLeaveReasonForRejection)
                etComments.isFocusable = false
            }
        }

        btnApproveSickLeave.setOnClickListener {
            approveSickLeave()
        }

        btnRejectSickLeave.setOnClickListener {
            rejectSickLeave()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun approveSickLeave() {
        val databaseReference = sickLeave.sickLeaveId?.let {
            FirebaseDatabase.getInstance().getReference("sickleave")
                    .child(it)
        }
        val status = "approved"
        val id: String? = sickLeave.sickLeaveId
        val title: String? = sickLeave.sickLeaveTitle
        val block: String? = sickLeave.sickLeaveBlock
        val room: String? = sickLeave.sickLeaveRoom
        val description: String? = sickLeave.sickLeaveReason
        val reportedBy: String? = sickLeave.sickLeaveReportedBy
        val date: String? = sickLeave.sickLeaveDate
        val reasonForRejection: String?
        if("" == etComments.text.toString()){
            reasonForRejection = ""
        } else {
            reasonForRejection = etComments.text.toString()
        }
        val sickLeave = SickLeave(id, title, block, room, description, reportedBy, date, status, reasonForRejection)
        databaseReference?.setValue(sickLeave)
        btnApproveSickLeave.visibility = View.GONE
        btnRejectSickLeave.visibility = View.GONE
        ivXXX.visibility = View.GONE
        tickMark.visibility = View.VISIBLE
    }

    fun rejectSickLeave() {
        val databaseReference = sickLeave.sickLeaveId?.let {
            FirebaseDatabase.getInstance().getReference("sickleave")
                    .child(it)
        }
        val status = "rejected"
        val id: String? = sickLeave.sickLeaveId
        val title: String? = sickLeave.sickLeaveTitle
        val block: String? = sickLeave.sickLeaveBlock
        val room: String? = sickLeave.sickLeaveRoom
        val description: String? = sickLeave.sickLeaveReason
        val reportedBy: String? = sickLeave.sickLeaveReportedBy
        val date: String? = sickLeave.sickLeaveDate
        val reasonForRejection: String?
        if("" == etComments.text.toString()){
            reasonForRejection = getString(R.string.report_in_person)
        } else {
            reasonForRejection = etComments.text.toString()
        }
        val sickLeave = SickLeave(id, title, block, room, description, reportedBy, date, status, reasonForRejection)
        databaseReference?.setValue(sickLeave)
        btnApproveSickLeave.visibility = View.GONE
        btnRejectSickLeave.visibility = View.GONE
        tickMark.visibility = View.GONE
        ivXXX.visibility = View.VISIBLE
    }
}