package com.ajce.hostelmate.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ajce.hostelmate.R
import com.ajce.hostelmate.reportissue.Issue
import com.ajce.hostelmate.reportissue.IssueViewModel
import com.ajce.hostelmate.reportissue.ReceptionDashboardActivity
import com.ajce.hostelmate.reportissue.ReceptionIssueRecyclerViewAdapter
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseReference
import kotlinx.android.synthetic.main.activity_reception_dashboard.*
import kotlinx.android.synthetic.main.fragment_issue_reception.*

/**
 * Created by JithinJude on 01,May,2020
 */
class ReceptionIssueFragment : Fragment() {
    
    var issueList: MutableList<Issue?>? = ArrayList()

    lateinit var adapter: ReceptionIssueRecyclerViewAdapter
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_issue_reception, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity!!.title = getString(R.string.reported_issues)

        activity!!.pbLoadingIssues.visibility = View.VISIBLE
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        editor = sharedPreferences.edit()

        val viewModel: IssueViewModel by lazy { ViewModelProviders.of(this).get(IssueViewModel::class.java) }

        val liveData: LiveData<DataSnapshot?> = viewModel.getDataSnapshotLiveData()

        liveData.observe(this, androidx.lifecycle.Observer { dataSnapshot ->
            issueList?.clear()
            for (issueSnapshot in dataSnapshot?.children!!) {
                val issue = issueSnapshot.getValue(Issue::class.java)
                issueList?.add(issue)
            }
            if (!sharedPreferences.getBoolean("NOTIFICATIONS_ON", false)) {
                issueList?.size?.let { editor.putInt("NUMBER_OF_ISSUES", it) }
                editor.apply()
            }

            rvReportedIssues.layoutManager = LinearLayoutManager(context)
            issueList?.reverse()
            adapter = ReceptionIssueRecyclerViewAdapter(context, issueList)
            rvReportedIssues.adapter = adapter
            activity!!.pbLoadingIssues.visibility = View.GONE
        })
    }
}