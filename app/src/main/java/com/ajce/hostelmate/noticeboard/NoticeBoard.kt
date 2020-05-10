package com.ajce.hostelmate.noticeboard

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties

/**
 * Created by JithinJude on 10,May,2020
 */

@IgnoreExtraProperties
@Keep
class NoticeBoard() : Parcelable {
    var noticeId: String? = null
    var noticeTitle: String? = null
    var noticeDescription: String? = null
    var noticeDate: String? = null
    var noticePdfUrl: String? = null

    constructor(parcel: Parcel) : this() {
        noticeId = parcel.readString()
        noticeTitle = parcel.readString()
        noticeDescription = parcel.readString()
        noticeDate = parcel.readString()
        noticePdfUrl = parcel.readString()
    }

    constructor(noticeId: String?, noticeTitle: String?, noticeDescription: String?,
                noticeDate: String?, noticePdfUrl: String?) : this() {
        this.noticeId = noticeId
        this.noticeTitle = noticeTitle
        this.noticeDescription = noticeDescription
        this.noticeDate = noticeDate
        this.noticePdfUrl = noticePdfUrl
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(noticeId)
        parcel.writeString(noticeTitle)
        parcel.writeString(noticeDescription)
        parcel.writeString(noticeDate)
        parcel.writeString(noticePdfUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NoticeBoard> {
        override fun createFromParcel(parcel: Parcel): NoticeBoard {
            return NoticeBoard(parcel)
        }

        override fun newArray(size: Int): Array<NoticeBoard?> {
            return arrayOfNulls(size)
        }
    }
}