package com.ajce.hostelmate

import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.text.InputFilter
import android.view.View
import android.widget.CompoundButton
import android.widget.EditText
import android.widget.Toast
import com.ajce.hostelmate.login.AdminLogin
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_control_panel.*
import java.util.*

class ControlPanelActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var adminLoginList: MutableList<AdminLogin?>? = ArrayList()
    var NOTIFICATIONS_ON: String? = "NOTIFICATIONS_ON"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_control_panel)
        title = getString(R.string.control_panel)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        editor = sharedPreferences.edit()

        notificationSwitch.isChecked = sharedPreferences.getBoolean(NOTIFICATIONS_ON, false)
        notificationSwitch.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked -> // do something, the isChecked will be
            // true if the switch is in the On position
            if (isChecked) {
                turnOnNotifications()
                editor.putBoolean(NOTIFICATIONS_ON, true)
            } else {
                turnOffNotifications()
                editor.putBoolean(NOTIFICATIONS_ON, false)
            }
            editor.apply()
        })
    }

    private fun turnOnNotifications() {
        val intent = Intent(this, NotificationService::class.java)
        startService(intent)
    }

    private fun turnOffNotifications() {
        val intent = Intent(this, NotificationService::class.java)
        stopService(intent)
    }

    fun changePassword(view: View?) {
        val builder: AlertDialog.Builder
        builder = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            AlertDialog.Builder(this, R.style.AppCompatAlertDialogStyle)
        } else {
            AlertDialog.Builder(this)
        }
        val edittextNewPassword = EditText(this)
        edittextNewPassword.setHint(R.string.new_password_please)
        edittextNewPassword.setHintTextColor(resources.getColor(R.color.grey))
        edittextNewPassword.setTextColor(resources.getColor(R.color.colorAccent))
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.O) edittextNewPassword.importantForAutofill = View.IMPORTANT_FOR_AUTOFILL_NO
        val filter = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (Character.isWhitespace(source[i])) {
                    return@InputFilter ""
                }
            }
            null
        }
        edittextNewPassword.filters = arrayOf<InputFilter?>(filter)
        builder.setTitle(R.string.change_password)
                .setPositiveButton(android.R.string.yes) { dialog, which ->
                    val userName = getString(R.string.user_name_value)
                    val newPassword = edittextNewPassword.text.toString()
                    val databaseReference = FirebaseDatabase.getInstance().getReference("admin_login")
                            .child("-LLz9xijklx_GLw4DRSh")
                    val adminLogin = AdminLogin(userName, newPassword)
                    databaseReference.setValue(adminLogin)
                    Toast.makeText(applicationContext, R.string.password_updated, Toast.LENGTH_LONG).show()
                }
                .setNegativeButton(android.R.string.no) { dialog, which ->
                    // do nothing
                }
                .setIcon(R.drawable.ic_password)
                .setView(edittextNewPassword)
                .show()
    }
}