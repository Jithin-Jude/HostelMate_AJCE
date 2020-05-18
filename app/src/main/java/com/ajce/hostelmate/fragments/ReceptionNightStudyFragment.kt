package com.ajce.hostelmate.fragments

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
import com.ajce.hostelmate.nightstudy.NightStudy
import com.ajce.hostelmate.nightstudy.NightStudyViewModel
import com.ajce.hostelmate.nightstudy.reception.ReceptionNightStudyRecyclerViewAdapter
import com.google.firebase.database.DataSnapshot
import kotlinx.android.synthetic.main.activity_reception_dashboard.*
import kotlinx.android.synthetic.main.fragment_night_study_reception.*

/**
 * Created by JithinJude on 01,May,2020
 */
class ReceptionNightStudyFragment : Fragment() {
    var nightStudyList: MutableList<NightStudy?>? = ArrayList()

    lateinit var adapterStatus: ReceptionNightStudyRecyclerViewAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_night_study_reception, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity!!.title = getString(R.string.night_study)

        activity!!.pbLoading.visibility = View.VISIBLE

        val viewModel: NightStudyViewModel by lazy { ViewModelProviders.of(this).get(NightStudyViewModel::class.java) }

        val liveData: LiveData<DataSnapshot?> = viewModel.getDataSnapshotLiveData()

        liveData.observe(this, androidx.lifecycle.Observer { dataSnapshot ->
            nightStudyList?.clear()
            for (nightStudySnapshot in dataSnapshot?.children!!) {
                val nightStudy = nightStudySnapshot.getValue(NightStudy::class.java)
                nightStudyList?.add(nightStudy)
            }

            rvRequestedNightStudy.layoutManager = LinearLayoutManager(context)
            nightStudyList?.reverse()
            adapterStatus = ReceptionNightStudyRecyclerViewAdapter(context, nightStudyList)
            rvRequestedNightStudy.adapter = adapterStatus
            activity!!.pbLoading.visibility = View.GONE
        })
    }
}