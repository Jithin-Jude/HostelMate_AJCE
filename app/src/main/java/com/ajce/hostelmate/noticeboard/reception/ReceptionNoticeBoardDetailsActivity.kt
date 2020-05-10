package com.ajce.hostelmate.noticeboard.reception

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ajce.hostelmate.R
import com.ajce.hostelmate.noticeboard.NoticeBoard
import kotlinx.android.synthetic.main.activity_sick_leave_details_inmates.*

/**
 * Created by JithinJude on 10,May,2020
 */
class ReceptionNoticeBoardDetailsActivity : AppCompatActivity() {

    val SELECTED_NOTICE: String = "selected_notice"
    val SELECTED_POSITION: String = "selected_position"

    lateinit var notice: NoticeBoard
    var selectedPosition: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notice_details_reception)

        //actionbar
        val actionbar = supportActionBar
        //set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)

        notice = intent.getParcelableExtra(SELECTED_NOTICE)
        selectedPosition = intent.getIntExtra(SELECTED_POSITION, 0)

        title = notice.noticeTitle
        tvDescription.text = notice.noticeDescription
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}