package com.ajce.hostelmate.sickleave.inmates

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ajce.hostelmate.R
import com.ajce.hostelmate.sickleave.SickLeave
import kotlinx.android.synthetic.main.activity_sick_leave_details_inmates.*

/**
 * Created by JithinJude on 02,May,2020
 */
class InmatesSickLeavesDetailsActivity : AppCompatActivity() {

    val SELECTED_SICK_LEAVE: String = "selected_sick_leave"
    val SELECTED_POSITION: String = "selected_position"

    lateinit var sickLeave: SickLeave
    var selectedPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sick_leave_details_inmates)

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
        tvStatusInmates.text = sickLeave.sickLeaveStatus

        if("" == sickLeave.sickLeaveReasonForRejection){
            tvReasonForRejectionLabel.visibility = View.GONE
            tvReasonForRejection.visibility = View.GONE
        } else {
            tvReasonForRejection.text = sickLeave.sickLeaveReasonForRejection

            tvReasonForRejectionLabel.visibility = View.VISIBLE
            tvReasonForRejection.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}