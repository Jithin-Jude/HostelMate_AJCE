package com.ajce.hostelmate.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajce.hostelmate.R
import com.ajce.hostelmate.noticeboard.NoticeBoard
import com.ajce.hostelmate.noticeboard.NoticeBoardViewModel
import com.ajce.hostelmate.noticeboard.reception.ReceptionNoticeBoardRecyclerViewAdapter
import com.ajce.hostelmate.noticeboard.reception.ReceptionPublishNoticeActivity
import com.google.firebase.database.DataSnapshot
import kotlinx.android.synthetic.main.content_issue_status.*
import kotlinx.android.synthetic.main.fragment_notice_board_reception.*

/**
 * Created by JithinJude on 01,May,2020
 */
class ReceptionNoticeBoardFragment : Fragment() {

    var noticeList: MutableList<NoticeBoard?>? = ArrayList()

    lateinit var receptionNoticeBoardRecyclerViewAdapter: ReceptionNoticeBoardRecyclerViewAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_notice_board_reception, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = getString(R.string.notice_board)

        activity?.loadingIssuesForInmates?.visibility = View.VISIBLE
        fab.setOnClickListener {
            val intent = Intent(context, ReceptionPublishNoticeActivity::class.java)
            startActivity(intent)
        }

        val viewModel: NoticeBoardViewModel by lazy { ViewModelProviders.of(this)
                .get(NoticeBoardViewModel::class.java) }

        val liveData: LiveData<DataSnapshot?> = viewModel.getDataSnapshotLiveData()

        liveData.observe(this, androidx.lifecycle.Observer { dataSnapshot ->
            noticeList?.clear()
            for (issueSnapshot in dataSnapshot?.children!!) {
                val notice = issueSnapshot.getValue(NoticeBoard::class.java)
                if (notice != null) {
                    noticeList?.add(notice)
                }
            }

            rvNotice.layoutManager = LinearLayoutManager(context)
            noticeList?.reverse()
            receptionNoticeBoardRecyclerViewAdapter = ReceptionNoticeBoardRecyclerViewAdapter(context, noticeList)
            rvNotice.adapter = receptionNoticeBoardRecyclerViewAdapter
            activity?.loadingIssuesForInmates?.visibility = View.GONE
        })
    }
}