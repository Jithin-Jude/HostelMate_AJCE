package com.ajce.hostelmate.nightstudy.inmates

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.ajce.hostelmate.R
import com.ajce.hostelmate.nightstudy.NightStudy
import com.ajce.hostelmate.sickleave.SickLeave
import kotlinx.android.synthetic.main.activity_sick_leave_details_inmates.*

/**
 * Created by JithinJude on 02,May,2020
 */
class InmatesNightStudyDetailsActivity : AppCompatActivity() {

    val SELECTED_NIGHT_STUDY: String = "selected_night_study"
    val SELECTED_POSITION: String = "selected_position"

    lateinit var nightStudy: NightStudy
    var selectedPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_night_study_details_inmates)

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
        tvStatusInmates.text = nightStudy.nightStudyStatus

        if("" == nightStudy.nightStudyReasonForRejection){
            tvReasonForRejectionLabel.visibility = View.GONE
            tvReasonForRejection.visibility = View.GONE
        } else {
            tvReasonForRejection.text = nightStudy.nightStudyReasonForRejection

            tvReasonForRejectionLabel.visibility = View.VISIBLE
            tvReasonForRejection.visibility = View.VISIBLE
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}