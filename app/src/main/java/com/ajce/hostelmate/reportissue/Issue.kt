package com.ajce.hostelmate.reportissue

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
@Keep
class Issue() : Parcelable {
    var issueId: String? = null
    var issueTitle: String? = null
    var issueBlock: String? = null
    var issueRoom: String? = null
    var issueDescription: String? = null
    var issueReportedBy: String? = null
    var issueDate: String? = null
    var issueStatus: String? = null
    var imageEncoded: String? = null

    constructor(parcel: Parcel) : this() {
        issueId = parcel.readString()
        issueTitle = parcel.readString()
        issueBlock = parcel.readString()
        issueRoom = parcel.readString()
        issueDescription = parcel.readString()
        issueReportedBy = parcel.readString()
        issueDate = parcel.readString()
        issueStatus = parcel.readString()
        imageEncoded = parcel.readString()
    }

    constructor(issueId: String?, issueTitle: String?, issueBlock: String?,
                issueRoom: String?, issueDescription: String?, issueReportedBy: String?,
                issueDate: String?, issueStatus: String?, imageEncoded: String?) : this() {
        this.issueId = issueId
        this.issueTitle = issueTitle
        this.issueBlock = issueBlock
        this.issueRoom = issueRoom
        this.issueDescription = issueDescription
        this.issueReportedBy = issueReportedBy
        this.issueDate = issueDate
        this.issueStatus = issueStatus
        this.imageEncoded = imageEncoded
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(issueId)
        parcel.writeString(issueTitle)
        parcel.writeString(issueBlock)
        parcel.writeString(issueRoom)
        parcel.writeString(issueDescription)
        parcel.writeString(issueReportedBy)
        parcel.writeString(issueDate)
        parcel.writeString(issueStatus)
        parcel.writeString(imageEncoded)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Issue> {
        override fun createFromParcel(parcel: Parcel): Issue {
            return Issue(parcel)
        }

        override fun newArray(size: Int): Array<Issue?> {
            return arrayOfNulls(size)
        }
    }
}