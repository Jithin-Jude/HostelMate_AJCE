package com.ajce.hostelmate.login

import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.Keep
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
@Keep
class AdminLogin() : Parcelable {
    constructor(userName: String?, password: String?) : this() {
        this.userName = userName
        this.password = password
    }

    var userName: String? = null
    var password: String? = null

    constructor(parcel: Parcel) : this() {
        userName = parcel.readString()
        password = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(userName)
        parcel.writeString(password)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AdminLogin> {
        override fun createFromParcel(parcel: Parcel): AdminLogin {
            return AdminLogin(parcel)
        }

        override fun newArray(size: Int): Array<AdminLogin?> {
            return arrayOfNulls(size)
        }
    }
}