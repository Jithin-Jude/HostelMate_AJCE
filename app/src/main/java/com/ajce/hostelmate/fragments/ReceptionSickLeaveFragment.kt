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
import com.ajce.hostelmate.R
import com.ajce.hostelmate.sickleave.SickLeave
import com.ajce.hostelmate.sickleave.SickLeaveViewModel
import com.ajce.hostelmate.sickleave.reception.ReceptionSickLeaveRecyclerViewAdapter
import com.google.firebase.database.DataSnapshot
import kotlinx.android.synthetic.main.activity_reception_dashboard.*
import kotlinx.android.synthetic.main.fragment_sick_leave_reception.*

/**
 * Created by JithinJude on 01,May,2020
 */
class ReceptionSickLeaveFragment : Fragment() {

    var sickLeaveList: MutableList<SickLeave?>? = ArrayList()

    lateinit var adapterStatus: ReceptionSickLeaveRecyclerViewAdapter
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_sick_leave_reception, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity!!.title = getString(R.string.sick_leave)

        activity!!.pbLoading.visibility = View.VISIBLE
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
        editor = sharedPreferences.edit()

        val viewModel: SickLeaveViewModel by lazy { ViewModelProviders.of(this).get(SickLeaveViewModel::class.java) }

        val liveData: LiveData<DataSnapshot?> = viewModel.getDataSnapshotLiveData()

        liveData.observe(this, androidx.lifecycle.Observer { dataSnapshot ->
            sickLeaveList?.clear()
            for (sickLeaveSnapshot in dataSnapshot?.children!!) {
                val sickLeave = sickLeaveSnapshot.getValue(SickLeave::class.java)
                sickLeaveList?.add(sickLeave)
            }
/*            if (!sharedPreferences.getBoolean("NOTIFICATIONS_ON", false)) {
                sickLeaveList?.size?.let { editor.putInt("NUMBER_OF_ISSUES", it) }
                editor.apply()
            }*/

            rvRequestedSickLeaves.layoutManager = LinearLayoutManager(context)
            sickLeaveList?.reverse()
            adapterStatus = ReceptionSickLeaveRecyclerViewAdapter(context, sickLeaveList)
            rvRequestedSickLeaves.adapter = adapterStatus
            activity!!.pbLoading.visibility = View.GONE
        })
    }
}