package com.ajce.hostelmate.nightstudy.reception

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ajce.hostelmate.R
import com.ajce.hostelmate.nightstudy.NightStudy
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_night_study_details_reception.*
import kotlinx.android.synthetic.main.activity_sick_leave_details_reception.etComments
import kotlinx.android.synthetic.main.activity_sick_leave_details_reception.ivXXX
import kotlinx.android.synthetic.main.activity_sick_leave_details_reception.tickMark
import kotlinx.android.synthetic.main.activity_sick_leave_details_reception.tvBlock
import kotlinx.android.synthetic.main.activity_sick_leave_details_reception.tvDescription
import kotlinx.android.synthetic.main.activity_sick_leave_details_reception.tvReportedBy
import kotlinx.android.synthetic.main.activity_sick_leave_details_reception.tvRoom

class ReceptionNightStudyDetailsActivity : AppCompatActivity() {

    val SELECTED_NIGHT_STUDY: String = "selected_nightstudy"
    val SELECTED_POSITION: String = "selected_position"

    lateinit var nightStudy: NightStudy
    var selectedPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_night_study_details_reception)

        //actionbar
        val actionbar = supportActionBar
        //set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)

        nightStudy = intent.getParcelableExtra(SELECTED_NIGHT_STUDY)
        selectedPosition = intent.getIntExtra(SELECTED_POSITION, 0)

        title = nightStudy.nightStudyTitle
        tvBlock.text = nightStudy.nightStudyBlock
        tvRoom.text = nightStudy.nightStudyRoom
        tvDescription.text = nightStudy.nightStudyReason
        tvReportedBy.text = nightStudy.nightStudyReportedBy


        if (nightStudy.nightStudyStatus == "pending") {
            btnApproveNightStudy.visibility = View.VISIBLE
            btnRejectNightStudy.visibility = View.VISIBLE
            tickMark.visibility = View.GONE
            ivXXX.visibility = View.GONE
        } else {
            if (nightStudy.nightStudyStatus == "approved"){
                btnApproveNightStudy.visibility = View.GONE
                btnRejectNightStudy.visibility = View.GONE
                ivXXX.visibility = View.GONE
                tickMark.visibility = View.VISIBLE
                etComments.setText(nightStudy.nightStudyReasonForRejection)
                etComments.isFocusable = false
            } else {
                btnApproveNightStudy.visibility = View.GONE
                btnRejectNightStudy.visibility = View.GONE
                tickMark.visibility = View.GONE
                ivXXX.visibility = View.VISIBLE
                etComments.setText(nightStudy.nightStudyReasonForRejection)
                etComments.isFocusable = false
            }
        }

        btnApproveNightStudy.setOnClickListener {
            approvenightStudy()
        }

        btnRejectNightStudy.setOnClickListener {
            rejectNightStudy()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun approvenightStudy() {
        val databaseReference = nightStudy.nightStudyId?.let {
            FirebaseDatabase.getInstance().getReference("nightstudy")
                    .child(it)
        }
        val status = "approved"
        val id: String? = nightStudy.nightStudyId
        val title: String? = nightStudy.nightStudyTitle
        val block: String? = nightStudy.nightStudyBlock
        val room: String? = nightStudy.nightStudyRoom
        val description: String? = nightStudy.nightStudyReason
        val reportedBy: String? = nightStudy.nightStudyReportedBy
        val date: String? = nightStudy.nightStudyDate
        val reasonForRejection: String?
        if("" == etComments.text.toString()){
            reasonForRejection = ""
        } else {
            reasonForRejection = etComments.text.toString()
        }
        val nightStudy = NightStudy(id, title, block, room, description, reportedBy, date, status, reasonForRejection)
        databaseReference?.setValue(nightStudy)
        btnApproveNightStudy.visibility = View.GONE
        btnRejectNightStudy.visibility = View.GONE
        ivXXX.visibility = View.GONE
        tickMark.visibility = View.VISIBLE
    }

    fun rejectNightStudy() {
        val databaseReference = nightStudy.nightStudyId?.let {
            FirebaseDatabase.getInstance().getReference("nightstudy")
                    .child(it)
        }
        val status = "rejected"
        val id: String? = nightStudy.nightStudyId
        val title: String? = nightStudy.nightStudyTitle
        val block: String? = nightStudy.nightStudyBlock
        val room: String? = nightStudy.nightStudyRoom
        val description: String? = nightStudy.nightStudyReason
        val reportedBy: String? = nightStudy.nightStudyReportedBy
        val date: String? = nightStudy.nightStudyDate
        val reasonForRejection: String?
        if("" == etComments.text.toString()){
            reasonForRejection = getString(R.string.report_in_person)
        } else {
            reasonForRejection = etComments.text.toString()
        }
        val nightStudy = NightStudy(id, title, block, room, description, reportedBy, date, status, reasonForRejection)
        databaseReference?.setValue(nightStudy)
        btnApproveNightStudy.visibility = View.GONE
        btnRejectNightStudy.visibility = View.GONE
        tickMark.visibility = View.GONE
        ivXXX.visibility = View.VISIBLE
    }
}