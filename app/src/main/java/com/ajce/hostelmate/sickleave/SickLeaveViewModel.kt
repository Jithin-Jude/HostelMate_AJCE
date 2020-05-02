package com.ajce.hostelmate.sickleave

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ajce.hostelmate.FirebaseQueryLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by JithinJude on 02,May,2020
 */
class SickLeaveViewModel : ViewModel() {
    private val SICK_LEAVE_REF = FirebaseDatabase.getInstance().getReference("sickleave")

    private val liveData = FirebaseQueryLiveData(SICK_LEAVE_REF)

    fun getDataSnapshotLiveData(): LiveData<DataSnapshot?> {
        return liveData
    }
}