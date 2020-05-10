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

        fun getMonthInThreeCapLetter(month: Int): String{
            when (month){
                1 -> { return "JAN"}
                2 -> { return "FEB"}
                3 -> { return "MAR"}
                4 -> { return "APR"}
                5 -> { return "MAY"}
                6 -> { return "JUN"}
                7 -> { return "JUL"}
                8 -> { return "AUG"}
                9 -> { return "SEP"}
                10 -> { return "OCT"}
                11 -> { return "NOV"}
                12 -> { return "DEC"}
                else -> {
                    return "ERROR"
                }
            }
        }
    }
}