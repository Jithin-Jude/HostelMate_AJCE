package com.ajce.hostelmate.nightstudy

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by JithinJude on 02,May,2020
 */

@IgnoreExtraProperties
@Keep
class NightStudy() : Parcelable {
    var nightStudyId: String? = null
    var nightStudyTitle: String? = null
    var nightStudyBlock: String? = null
    var nightStudyRoom: String? = null
    var nightStudyReason: String? = null
    var nightStudyReportedBy: String? = null
    var nightStudyDate: String? = null
    var nightStudyStatus: String? = null
    var nightStudyReasonForRejection: String? = null

    constructor(parcel: Parcel) : this() {
        nightStudyId = parcel.readString()
        nightStudyTitle = parcel.readString()
        nightStudyBlock = parcel.readString()
        nightStudyRoom = parcel.readString()
        nightStudyReason = parcel.readString()
        nightStudyReportedBy = parcel.readString()
        nightStudyDate = parcel.readString()
        nightStudyStatus = parcel.readString()
        nightStudyReasonForRejection = parcel.readString()
    }

    constructor(nightStudyId: String?, nightStudyTitle: String?, nightStudyBlock: String?,
                nightStudyRoom: String?, nightStudyReason: String?, nightStudyReportedBy: String?,
                nightStudyDate: String?, nightStudyStatus: String?, nightStudyReasonForRejection: String?) : this() {
        this.nightStudyId = nightStudyId
        this.nightStudyTitle = nightStudyTitle
        this.nightStudyBlock = nightStudyBlock
        this.nightStudyRoom = nightStudyRoom
        this.nightStudyReason = nightStudyReason
        this.nightStudyReportedBy = nightStudyReportedBy
        this.nightStudyDate = nightStudyDate
        this.nightStudyStatus = nightStudyStatus
        this.nightStudyReasonForRejection = nightStudyReasonForRejection
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(nightStudyId)
        parcel.writeString(nightStudyTitle)
        parcel.writeString(nightStudyBlock)
        parcel.writeString(nightStudyRoom)
        parcel.writeString(nightStudyReason)
        parcel.writeString(nightStudyReportedBy)
        parcel.writeString(nightStudyDate)
        parcel.writeString(nightStudyStatus)
        parcel.writeString(nightStudyReasonForRejection)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NightStudy> {
        override fun createFromParcel(parcel: Parcel): NightStudy {
            return NightStudy(parcel)
        }

        override fun newArray(size: Int): Array<NightStudy?> {
            return arrayOfNulls(size)
        }
    }
}