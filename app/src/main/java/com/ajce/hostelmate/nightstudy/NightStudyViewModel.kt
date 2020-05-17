package com.ajce.hostelmate.nightstudy

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ajce.hostelmate.FirebaseQueryLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by JithinJude on 02,May,2020
 */
class NightStudyViewModel : ViewModel() {
    private val NIGHT_STUDY_REF = FirebaseDatabase.getInstance().getReference("nightstudy")

    private val liveData = FirebaseQueryLiveData(NIGHT_STUDY_REF)

    fun getDataSnapshotLiveData(): LiveData<DataSnapshot?> {
        return liveData
    }
}