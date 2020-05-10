package com.ajce.hostelmate.fragments

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RemoteViews
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajce.hostelmate.R
import com.ajce.hostelmate.WidgetForInmates
import com.ajce.hostelmate.reportissue.*
import com.ajce.hostelmate.reportissue.inmates.InmatesIssueStatusRecyclerViewAdapter
import com.ajce.hostelmate.reportissue.inmates.InmatesReportAnIssueActivity
import com.google.firebase.database.DataSnapshot
import kotlinx.android.synthetic.main.content_issue_status.*
import kotlinx.android.synthetic.main.fragment_issue_status.*

/**
 * Created by JithinJude on 01,May,2020
 */
class IssueFragment : Fragment() {
    val USER_EMAIL: String = "user_email"

    var issueList: MutableList<Issue?>? = ArrayList()

    lateinit var adapterInmatesIssueStatus: InmatesIssueStatusRecyclerViewAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_issue_status, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val personEmail = arguments?.getString(USER_EMAIL)

        activity?.pbLoadingInmatesDashboard?.visibility = View.VISIBLE
        fab.setOnClickListener {
            val intent = Intent(context, InmatesReportAnIssueActivity::class.java)
            intent.putExtra(USER_EMAIL, personEmail)
            startActivity(intent)
        }

        val viewModel: IssueViewModel by lazy { ViewModelProviders.of(this)
                .get(IssueViewModel::class.java) }

        val liveData: LiveData<DataSnapshot?> = viewModel.getDataSnapshotLiveData()

        liveData.observe(this, androidx.lifecycle.Observer { dataSnapshot ->
            issueList?.clear()
            for (issueSnapshot in dataSnapshot?.children!!) {
                val issue = issueSnapshot.getValue(Issue::class.java)
                if (issue != null) {
                    if (issue.issueReportedBy == personEmail) {
                        issueList?.add(issue)
                    }
                }
            }
            if (issueList?.size != 0) updateWidget(issueList
                    ?.get(issueList!!.size - 1)?.issueStatus)

            rvIssueStatus.layoutManager = LinearLayoutManager(context)
            issueList?.reverse()
            adapterInmatesIssueStatus = InmatesIssueStatusRecyclerViewAdapter(context, issueList)
            rvIssueStatus.adapter = adapterInmatesIssueStatus
            activity?.pbLoadingInmatesDashboard?.visibility = View.GONE
        })
    }

    fun updateWidget(isFixed: String?) {
        val appWidgetManager = AppWidgetManager.getInstance(context)
        val remoteViews = RemoteViews(context?.packageName, R.layout.widget_for_inmates)
        val thisWidget = ComponentName(context, WidgetForInmates::class.java)
        val widgetText = issueList?.get(issueList!!.size - 1)?.issueTitle
        remoteViews.setTextViewText(R.id.tvWidgetText, widgetText)
        if (isFixed == "Fixed") {
            remoteViews.setImageViewResource(R.id.ivIssueImgWidget, R.drawable.hostel_green)
        } else {
            remoteViews.setImageViewResource(R.id.ivIssueImgWidget, R.drawable.hostel_red)
        }
        appWidgetManager.updateAppWidget(thisWidget, remoteViews)
    }
}