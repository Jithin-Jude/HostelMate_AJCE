package com.ajce.hostelmate.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.ajce.hostelmate.R
import com.ajce.hostelmate.sickleave.SickLeave

/**
 * Created by JithinJude on 01,May,2020
 */
class SickLeaveFragment : Fragment() {
    val USER_EMAIL: String = "user_email"

    var issueList: MutableList<SickLeave?>? = ArrayList()

//    lateinit var adapterIssueStatus: IssueStatusRecyclerViewAdapter
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
            inflater.inflate(R.layout.fragment_sick_leave, container, false)
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }
}