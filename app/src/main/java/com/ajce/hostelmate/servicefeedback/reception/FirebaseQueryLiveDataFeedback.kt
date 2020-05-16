package com.ajce.hostelmate.servicefeedback.reception

import android.util.Log
import androidx.lifecycle.LiveData
import com.google.firebase.database.*


/**
 * Created by JithinJude on 26,April,2020
 */

class FirebaseQueryLiveDataFeedback : LiveData<String?> {
    private val query: Query
    private val listener = MyValueEventListener()

    constructor(query: Query) {
        this.query = query
    }

    constructor(ref: DatabaseReference) {
        query = ref
    }

    override fun onActive() {
        Log.d(LOG_TAG, "onActive")
        query.addValueEventListener(listener)
    }

    override fun onInactive() {
        Log.d(LOG_TAG, "onInactive")
        query.removeEventListener(listener)
    }

    private inner class MyValueEventListener : ValueEventListener {
        override fun onDataChange(dataSnapshot: DataSnapshot) {
            for (child in dataSnapshot.children) {
                value = child.value.toString()
            }
        }

        override fun onCancelled(databaseError: DatabaseError) {
            Log.e(LOG_TAG, "Can't listen to query $query", databaseError.toException())
        }
    }

    companion object {
        private const val LOG_TAG = "FirebaseQueryLiveData"
    }
}