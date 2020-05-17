package com.ajce.hostelmate.fragments

import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ajce.hostelmate.R
import com.ajce.hostelmate.servicefeedback.Feedback
import com.ajce.hostelmate.servicefeedback.FeedbackViewModel
import com.ajce.hostelmate.servicefeedback.reception.ReceptionMonthlyFeedbacksRecyclerViewAdapter
import com.ajce.hostelmate.sickleave.SickLeave
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_reception_dashboard.*
import kotlinx.android.synthetic.main.fragment_feedback_reception.*

/**
 * Created by JithinJude on 01,May,2020
 */
class ReceptionFeedbackFragment : Fragment() {
    
    var monthlyFeedbackList: MutableList<String?>? = ArrayList()
    lateinit var receptionMonthlyFeedbacksRecyclerViewAdapter: ReceptionMonthlyFeedbacksRecyclerViewAdapter

    lateinit var databaseIssue: DatabaseReference
    
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_feedback_reception, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity!!.title = getString(R.string.request_feedback)

        activity!!.pbLoading.visibility = View.VISIBLE

/*        databaseIssue = FirebaseDatabase.getInstance().getReference("feedback")
        val listener: ValueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                monthlyFeedbackList?.clear()
                for (issueSnapshot in dataSnapshot.children) {
//                    val issue = issueSnapshot.getValue(Issue::class.java)
                    monthlyFeedbackList?.add(issueSnapshot.key)
                }

                rvMonthlyFeedback.layoutManager = LinearLayoutManager(context)
                monthlyFeedbackList?.reverse()
                receptionMonthlyFeedbacksRecyclerViewAdapter = ReceptionMonthlyFeedbacksRecyclerViewAdapter(context, monthlyFeedbackList)
                rvMonthlyFeedback.adapter = receptionMonthlyFeedbacksRecyclerViewAdapter
                activity!!.pbLoading.visibility = View.GONE
            }
            override fun onCancelled(databaseError: DatabaseError) {
                Log.d("ERROR","ERROR: " + databaseError)
            }
        }
        databaseIssue.addValueEventListener(listener)*/

        val viewModel: FeedbackViewModel by lazy { ViewModelProviders.of(this).get(FeedbackViewModel::class.java) }

        val liveData: LiveData<DataSnapshot?> = viewModel.getDataSnapshotLiveData()

        liveData.observe(this, androidx.lifecycle.Observer { dataSnapshot ->
            monthlyFeedbackList?.clear()
            for (sickLeaveSnapshot in dataSnapshot?.children!!) {
                monthlyFeedbackList?.add(sickLeaveSnapshot.key)
            }

            rvMonthlyFeedback.layoutManager = LinearLayoutManager(context)
            monthlyFeedbackList?.reverse()
            receptionMonthlyFeedbacksRecyclerViewAdapter = ReceptionMonthlyFeedbacksRecyclerViewAdapter(context, monthlyFeedbackList)
            rvMonthlyFeedback.adapter = receptionMonthlyFeedbacksRecyclerViewAdapter
            activity!!.pbLoading.visibility = View.GONE
        })
    }
}