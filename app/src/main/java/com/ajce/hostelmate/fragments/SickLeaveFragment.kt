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
import com.ajce.hostelmate.sickleave.SickLeave
import com.ajce.hostelmate.sickleave.SickLeaveViewModel
import com.ajce.hostelmate.sickleave.inmates.InmatesRequestSickLeaveActivity
import com.ajce.hostelmate.sickleave.inmates.InmatesSickLeaveRecyclerViewAdapter
import com.google.firebase.database.DataSnapshot
import kotlinx.android.synthetic.main.content_issue_status.*
import kotlinx.android.synthetic.main.fragment_sick_leave.*

/**
 * Created by JithinJude on 01,May,2020
 */
class SickLeaveFragment : Fragment() {
    val USER_EMAIL: String = "user_email"

    var sickLeaveList: MutableList<SickLeave?>? = ArrayList()

    lateinit var inmatesSickLeaveRecyclerViewAdapter: InmatesSickLeaveRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_sick_leave, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = getString(R.string.sick_leave)

        val personEmail = arguments?.getString(USER_EMAIL)

        activity?.pbLoadingInmatesDashboard?.visibility = View.VISIBLE
        fab.setOnClickListener {
            val intent = Intent(context, InmatesRequestSickLeaveActivity::class.java)
            intent.putExtra(USER_EMAIL, personEmail)
            startActivity(intent)
        }

        val viewModel: SickLeaveViewModel by lazy { ViewModelProviders.of(this)
                .get(SickLeaveViewModel::class.java) }

        val liveData: LiveData<DataSnapshot?> = viewModel.getDataSnapshotLiveData()

        liveData.observe(this, androidx.lifecycle.Observer { dataSnapshot ->
            sickLeaveList?.clear()
            for (issueSnapshot in dataSnapshot?.children!!) {
                val issue = issueSnapshot.getValue(SickLeave::class.java)
                if (issue != null) {
                    if (issue.sickLeaveReportedBy == personEmail) {
                        sickLeaveList?.add(issue)
                    }
                }
            }
/*            if (sickLeaveList?.size != 0) updateWidget(issueList
                    ?.get(sickLeaveList!!.size - 1)?.issueStatus)*/

            rvSickLeave.layoutManager = LinearLayoutManager(context)
            sickLeaveList?.reverse()
            inmatesSickLeaveRecyclerViewAdapter = InmatesSickLeaveRecyclerViewAdapter(context, sickLeaveList)
            rvSickLeave.adapter = inmatesSickLeaveRecyclerViewAdapter
            activity?.pbLoadingInmatesDashboard?.visibility = View.GONE
        })
    }
}