package com.ajce.hostelmate.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.ajce.hostelmate.R
import com.ajce.hostelmate.login.AdminLogin
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_reception_login.*
import java.util.*

class ReceptionLoginActivity : AppCompatActivity() {

    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor
    var databaseIssue: DatabaseReference? = null
    var adminLoginList: MutableList<AdminLogin?>? = ArrayList()
    var userNamefromServer: String? = null
    var passwordfromServer: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reception_login)
        supportActionBar?.hide()

        progressBarFetchingPassword.visibility = View.VISIBLE
        btnSignIn.isEnabled = false

        swapUserMode.setOnClickListener {
            val intent = Intent(applicationContext, InmatesLoginActivity::class.java)
            intent.flags =  Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        databaseIssue = FirebaseDatabase.getInstance().getReference("admin_login")
        databaseIssue?.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                adminLoginList?.clear()
                for (issueSnapshot in dataSnapshot.children) {
                    val adminLogin = issueSnapshot.getValue(AdminLogin::class.java)
                    adminLoginList?.add(adminLogin)
                }
                userNamefromServer = adminLoginList?.get(0)?.userName
                passwordfromServer = adminLoginList?.get(0)?.password

                progressBarFetchingPassword.visibility = View.GONE
                btnSignIn.isEnabled = true
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    fun receptionLogin(view: View?) {
        val givenUserName = etUserName?.text.toString()
        val givenPassword = etPassword?.text.toString()

        /*
        String id = databaseIssue.push().getKey();
        AdminLogin adminLogin = new AdminLogin(userName,password);
        databaseIssue.child(id).setValue(adminLogin);
        Toast.makeText(this,"Admin Created!",Toast.LENGTH_LONG).show();
        */if (givenUserName == userNamefromServer && givenPassword == passwordfromServer) {
            val intent = Intent(this, ReceptionDashboardActivity::class.java)
            intent.flags =  Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
            editor = sharedPreferences.edit()
            editor.putString("FIRST_TIME_CHECK", "admin")
            editor.apply()
            finish()
        } else {
            Toast.makeText(this, R.string.reception_login_failed_info, Toast.LENGTH_LONG)
                    .show()
        }
    }
}