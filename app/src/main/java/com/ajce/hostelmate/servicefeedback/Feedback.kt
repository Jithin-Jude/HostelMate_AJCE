package com.ajce.hostelmate.servicefeedback

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by JithinJude on 10,May,2020
 */

@IgnoreExtraProperties
@Keep
class Feedback() : Parcelable {
    var feedbackId: String? = null
    var feedbackRating: Int? = null
    var feedbackDescription: String? = null
    var feedbackFrom: String? = null

    constructor(parcel: Parcel) : this() {
        feedbackId = parcel.readString()
        feedbackDescription = parcel.readString()
        feedbackFrom = parcel.readString()
    }

    constructor(feedbackId: String?, feedbackRating: Int?, feedbackDescription: String?,
                feedbackFrom: String?) : this() {
        this.feedbackId = feedbackId
        this.feedbackRating = feedbackRating
        this.feedbackDescription = feedbackDescription
        this.feedbackFrom = feedbackFrom
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(feedbackId)
        parcel.writeInt(feedbackRating!!)
        parcel.writeString(feedbackDescription)
        parcel.writeString(feedbackFrom)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Feedback> {
        override fun createFromParcel(parcel: Parcel): Feedback {
            return Feedback(parcel)
        }

        override fun newArray(size: Int): Array<Feedback?> {
            return arrayOfNulls(size)
        }
    }
}