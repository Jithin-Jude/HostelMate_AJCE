package com.ajce.hostelmate.sickleave

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by JithinJude on 02,May,2020
 */

@IgnoreExtraProperties
@Keep
class SickLeave() : Parcelable {
    var sickLeaveId: String? = null
    var sickLeaveTitle: String? = null
    var sickLeaveBlock: String? = null
    var sickLeaveRoom: String? = null
    var sickLeaveReason: String? = null
    var sickLeaveReportedBy: String? = null
    var sickLeaveDate: String? = null
    var sickLeaveStatus: String? = null
    var sickLeaveReasonForRejection: String? = null

    constructor(parcel: Parcel) : this() {
        sickLeaveId = parcel.readString()
        sickLeaveTitle = parcel.readString()
        sickLeaveBlock = parcel.readString()
        sickLeaveRoom = parcel.readString()
        sickLeaveReason = parcel.readString()
        sickLeaveReportedBy = parcel.readString()
        sickLeaveDate = parcel.readString()
        sickLeaveStatus = parcel.readString()
        sickLeaveReasonForRejection = parcel.readString()
    }

    constructor(sickLeaveId: String?, sickLeaveTitle: String?, sickLeaveBlock: String?,
                sickLeaveRoom: String?, sickLeaveReason: String?, sickLeaveReportedBy: String?,
                sickLeaveDate: String?, sickLeaveStatus: String?, sickLeaveReasonForRejection: String?) : this() {
        this.sickLeaveId = sickLeaveId
        this.sickLeaveTitle = sickLeaveTitle
        this.sickLeaveBlock = sickLeaveBlock
        this.sickLeaveRoom = sickLeaveRoom
        this.sickLeaveReason = sickLeaveReason
        this.sickLeaveReportedBy = sickLeaveReportedBy
        this.sickLeaveDate = sickLeaveDate
        this.sickLeaveStatus = sickLeaveStatus
        this.sickLeaveReasonForRejection = sickLeaveReasonForRejection
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(sickLeaveId)
        parcel.writeString(sickLeaveTitle)
        parcel.writeString(sickLeaveBlock)
        parcel.writeString(sickLeaveRoom)
        parcel.writeString(sickLeaveReason)
        parcel.writeString(sickLeaveReportedBy)
        parcel.writeString(sickLeaveDate)
        parcel.writeString(sickLeaveStatus)
        parcel.writeString(sickLeaveReasonForRejection)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<SickLeave> {
        override fun createFromParcel(parcel: Parcel): SickLeave {
            return SickLeave(parcel)
        }

        override fun newArray(size: Int): Array<SickLeave?> {
            return arrayOfNulls(size)
        }
    }
}