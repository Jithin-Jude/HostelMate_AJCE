package com.ajce.hostelmate.noticeboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.ajce.hostelmate.FirebaseQueryLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

/**
 * Created by JithinJude on 10,May,2020
 */
class NoticeBoardViewModel : ViewModel() {
    private val NOTICE_BOARD_REF = FirebaseDatabase.getInstance().getReference("noticeboard")

    private val liveData = FirebaseQueryLiveData(NOTICE_BOARD_REF)

    fun getDataSnapshotLiveData(): LiveData<DataSnapshot?> {
        return liveData
    }
}