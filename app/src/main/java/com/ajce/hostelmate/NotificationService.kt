package com.ajce.hostelmate

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.IBinder
import android.preference.PreferenceManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import android.widget.Toast
import com.ajce.hostelmate.login.ReceptionLoginActivity
import com.ajce.hostelmate.reportissue.Issue
import com.ajce.hostelmate.reportissue.reception.ReceptionDashboardActivity
import com.google.firebase.database.*
import java.util.*

class NotificationService : Service() {
    var CHANNEL_ID: String? = "hostelmate_notification"
    lateinit var databaseIssue: DatabaseReference
    lateinit var valueEventListener: ValueEventListener
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    override fun onCreate() {
        super.onCreate()
    }

    override fun onStartCommand(intent: Intent?, flag: Int, startId: Int): Int {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        editor = sharedPreferences.edit()
        Toast.makeText(this, "Notifications activated", Toast.LENGTH_LONG).show()
        databaseIssue = FirebaseDatabase.getInstance().getReference("issues")
        valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                issueList?.clear()
                for (issueSnapshot in dataSnapshot.children) {
                    val issue = issueSnapshot.getValue(Issue::class.java)
                    issueList?.add(issue)
                }
                ReceptionDashboardActivity.Companion.numberOfIssuesOld = sharedPreferences.getInt("NUMBER_OF_ISSUES", 0)
                //Toast.makeText(getApplicationContext(),"num Old : "+numberOfIssuesOld,Toast.LENGTH_LONG).show();
                ReceptionDashboardActivity.Companion.numberOfIssuesNew = issueList!!.size
                //Toast.makeText(getApplicationContext(),"num New : "+numberOfIssuesNew,Toast.LENGTH_LONG).show();
                if (ReceptionDashboardActivity.Companion.numberOfIssuesNew > ReceptionDashboardActivity.Companion.numberOfIssuesOld) {
                    displayNotification()
                }
                editor.putInt("NUMBER_OF_ISSUES", issueList!!.size)
                editor.apply()
                //Toast.makeText(getApplicationContext(),"num New update : "+issueList.size(),Toast.LENGTH_LONG).show();
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        }
        databaseIssue.addValueEventListener(valueEventListener)
        return START_STICKY
    }

    override fun onDestroy() {
        databaseIssue.removeEventListener(valueEventListener)
        Toast.makeText(this, "Notifications deactivated", Toast.LENGTH_LONG).show()
        super.onDestroy()
    }

    override fun onBind(arg0: Intent?): IBinder? {
        return null
    }

    fun displayNotification() {
        createNotificationChannel()
        val builder = NotificationCompat.Builder(this, CHANNEL_ID!!)
        builder.setSmallIcon(R.drawable.ic_notifications_active_white_24dp)
        builder.setContentTitle("Hostel Mate")
        builder.setContentText("New issue reported!")
        builder.priority = NotificationCompat.PRIORITY_HIGH
        builder.setAutoCancel(true)
        val contentIntent = PendingIntent.getActivity(this, 0,
                Intent(this, ReceptionLoginActivity::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(contentIntent)
        val notificationManagerCompat = NotificationManagerCompat.from(this)
        val notificationId = (Date().time / 1000L % Int.MAX_VALUE) as Int
        notificationManagerCompat.notify(notificationId, builder.build())
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name: CharSequence = "Hostelmate notification"
            val description = "Hi"
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val notificationChannel = NotificationChannel(CHANNEL_ID, name, importance)
            notificationChannel.description = description
            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    companion object {
        var issueList: MutableList<Issue?>? = ArrayList()
    }
}