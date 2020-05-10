package com.ajce.hostelmate

import android.app.Application
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by JithinJude on 01,May,2020
 */
class Utils : Application() {

    companion object {
        fun getTimeStamp(): String{
            val s = SimpleDateFormat("ddMMyyyyhhmmss", Locale.US)
            val timeStamp = s.format(Date())
            return timeStamp
        }
    }
}