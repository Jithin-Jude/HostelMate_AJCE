package com.ajce.hostelmate.servicefeedback

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ajce.hostelmate.FirebaseQueryLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by JithinJude on 10,May,2020
 */
class FeedbackViewModel : ViewModel() {
    private val FEEDBACK_REF = FirebaseDatabase.getInstance().getReference("feedback")

    private val liveData = FirebaseQueryLiveData(FEEDBACK_REF)

    fun getDataSnapshotLiveData(): LiveData<DataSnapshot?> {
        return liveData
    }
}