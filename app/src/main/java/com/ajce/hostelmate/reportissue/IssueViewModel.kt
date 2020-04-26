package com.ajce.hostelmate.reportissue

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase


/**
 * Created by JithinJude on 26,April,2020
 */
class IssueViewModel : ViewModel() {
    private val ISSUES_REF = FirebaseDatabase.getInstance().getReference("issues")

    private val liveData = FirebaseQueryLiveData(ISSUES_REF)

    fun getDataSnapshotLiveData(): LiveData<DataSnapshot?> {
        return liveData
    }
}