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
import com.ajce.hostelmate.nightstudy.NightStudy
import com.ajce.hostelmate.nightstudy.NightStudyViewModel
import com.ajce.hostelmate.nightstudy.inmates.InmatesNightStudyRecyclerViewAdapter
import com.ajce.hostelmate.nightstudy.inmates.InmatesRequestNightStudyActivity
import com.google.firebase.database.DataSnapshot
import kotlinx.android.synthetic.main.content_issue_status.*
import kotlinx.android.synthetic.main.fragment_night_study.*
import kotlinx.android.synthetic.main.fragment_sick_leave.*
import kotlinx.android.synthetic.main.fragment_sick_leave.fab

/**
 * Created by JithinJude on 01,May,2020
 */
class NightStudyFragment : Fragment() {
    val USER_EMAIL: String = "user_email"

    var nightStudyList: MutableList<NightStudy?>? = ArrayList()

    lateinit var inmatesNightStudyRecyclerViewAdapter: InmatesNightStudyRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_night_study, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.title = getString(R.string.night_study)

        val personEmail = arguments?.getString(USER_EMAIL)

        activity?.pbLoadingInmatesDashboard?.visibility = View.VISIBLE
        fab.setOnClickListener {
            val intent = Intent(context, InmatesRequestNightStudyActivity::class.java)
            intent.putExtra(USER_EMAIL, personEmail)
            startActivity(intent)
        }

        val viewModel: NightStudyViewModel by lazy { ViewModelProviders.of(this)
                .get(NightStudyViewModel::class.java) }

        val liveData: LiveData<DataSnapshot?> = viewModel.getDataSnapshotLiveData()

        liveData.observe(this, androidx.lifecycle.Observer { dataSnapshot ->
            nightStudyList?.clear()
            for (issueSnapshot in dataSnapshot?.children!!) {
                val issue = issueSnapshot.getValue(NightStudy::class.java)
                if (issue != null) {
                    if (issue.nightStudyReportedBy == personEmail) {
                        nightStudyList?.add(issue)
                    }
                }
            }
/*            if (nightStudyList?.size != 0) updateWidget(issueList
                    ?.get(nightStudyList!!.size - 1)?.issueStatus)*/

            rvNightStudy.layoutManager = LinearLayoutManager(context)
            nightStudyList?.reverse()
            inmatesNightStudyRecyclerViewAdapter = InmatesNightStudyRecyclerViewAdapter(context, nightStudyList)
            rvNightStudy.adapter = inmatesNightStudyRecyclerViewAdapter
            activity?.pbLoadingInmatesDashboard?.visibility = View.GONE
        })
    }
}