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
                1 -> { return "01JAN"}
                2 -> { return "02FEB"}
                3 -> { return "03MAR"}
                4 -> { return "04APR"}
                5 -> { return "05MAY"}
                6 -> { return "06JUN"}
                7 -> { return "07JUL"}
                8 -> { return "08AUG"}
                9 -> { return "09SEP"}
                10 -> { return "10OCT"}
                11 -> { return "11NOV"}
                12 -> { return "12DEC"}
                else -> {
                    return "ERROR"
                }
            }
        }

        fun getMonthInFull(month: String): String{
            when (month){
                "JAN" -> { return "January"}
                "FEB" -> { return "February"}
                "MAR" -> { return "March"}
                "APR" -> { return "April"}
                "MAY" -> { return "May"}
                "JUN" -> { return "June"}
                "JUL" -> { return "July"}
                "AUG" -> { return "August"}
                "SEP" -> { return "September"}
                "OCT" -> { return "October"}
                "NOV" -> { return "November"}
                "DEC" -> { return "December"}
                else -> {
                    return "ERROR"
                }
            }
        }
    }
}