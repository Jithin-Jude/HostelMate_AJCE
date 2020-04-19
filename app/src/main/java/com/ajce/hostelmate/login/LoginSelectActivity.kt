package com.ajce.hostelmate.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.ajce.hostelmate.R

class LoginSelectActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_select)
        supportActionBar?.hide()
    }

    fun receptionLoginSelected(view: View?) {
        val intent = Intent(this, ReceptionLoginActivity::class.java)
        startActivity(intent)
    }

    fun inmatesLoginSelected(view: View?) {
        val intent = Intent(this, InmatesLoginActivity::class.java)
        startActivity(intent)
    }
}