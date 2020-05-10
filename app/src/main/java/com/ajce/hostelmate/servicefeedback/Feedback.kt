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
    var feedbackFrom: String? = null
    var foodRating: Int? = null
    var foodReview: String? = null
    var cleaningRating: Int? = null
    var cleaningReview: String? = null

    constructor(parcel: Parcel) : this() {
        feedbackFrom = parcel.readString()
        foodRating = parcel.readInt()
        foodReview = parcel.readString()
        cleaningRating = parcel.readInt()
        cleaningReview = parcel.readString()
    }

    constructor(feedbackFrom: String?, foodRating: Int?, foodReview: String?,
                cleaningRating: Int?, cleaningReview: String?) : this() {
        this.feedbackFrom = feedbackFrom
        this.foodRating = foodRating
        this.foodReview = foodReview
        this.cleaningRating = cleaningRating
        this.cleaningReview = cleaningReview
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(feedbackFrom)
        parcel.writeInt(foodRating!!)
        parcel.writeString(foodReview)
        parcel.writeInt(cleaningRating!!)
        parcel.writeString(cleaningReview)
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