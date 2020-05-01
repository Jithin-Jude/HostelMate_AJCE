package com.ajce.hostelmate.reportissue

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.ajce.hostelmate.R
import com.ajce.hostelmate.fragments.*
import com.ajce.hostelmate.login.InmatesLoginActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_issue_status.*
import kotlinx.android.synthetic.main.content_issue_status.*

class IssueStatusActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    var USER_NAME: String? = "user_name"
    var USER_EMAIL: String? = "user_email"
    var PROFILE_PIC: String? = "profile_pic"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_status)
        val toolbar = findViewById<View?>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

//        loadingIssuesForInmates.visibility = View.VISIBLE

        val personName = intent.extras[USER_NAME].toString()
        val personEmail = intent.extras[USER_EMAIL].toString()
        val profilePicUri = intent.extras[PROFILE_PIC].toString()

        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        val navigationView = findViewById<View?>(R.id.navView) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        val header = navigationView.getHeaderView(0)
        val personNameTV = header.findViewById<TextView?>(R.id.tvPersonName)
        val personEmailTV = header.findViewById<TextView?>(R.id.tvPersonEmail)
        val profilePic = header.findViewById<ImageView?>(R.id.ivProfilePic)
        Glide.with(this)
                .load(profilePicUri)
                .apply(RequestOptions()
                        .circleCrop())
                .into(profilePic!!)
        personNameTV?.text = personName
        personEmailTV?.text = personEmail

        val fragment = IssueFragment()
        val bundle = Bundle().apply {
            putString(USER_EMAIL, personEmail)
        }
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().replace(R.id.dashboardContainer, fragment, fragment.javaClass.simpleName)
                .commit()

        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bNavIssue -> {
                    val fragment = IssueFragment()
                    val bundle = Bundle().apply {
                        putString(USER_EMAIL, personEmail)
                    }
                    fragment.arguments = bundle
                    supportFragmentManager.beginTransaction().replace(R.id.dashboardContainer, fragment, fragment.javaClass.simpleName)
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bNavSickLeave -> {
                    val fragment = SickLeaveFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.dashboardContainer, fragment, fragment.javaClass.simpleName)
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bNavNightStudy -> {
                    val fragment = NightStudyFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.dashboardContainer, fragment, fragment.javaClass.simpleName)
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bNavNoticeBoard -> {
                    val fragment = NoticeBoardFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.dashboardContainer, fragment, fragment.javaClass.getSimpleName())
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bNavFeedback -> {
                    val fragment = FeedbackFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.dashboardContainer, fragment, fragment.javaClass.getSimpleName())
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

        bottomNavView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onBackPressed() {
        val drawer = findViewById<View?>(R.id.drawer_layout) as DrawerLayout
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        val id = item.itemId
        if (id == R.id.nav_logout) {
            signOut()
        }
        val drawer = findViewById<View?>(R.id.drawer_layout) as DrawerLayout
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    private fun signOut() {
        FirebaseAuth.getInstance().signOut()
        InmatesLoginActivity.Companion.mGoogleSignInClient?.signOut()?.addOnCompleteListener(this,
                OnCompleteListener<Void?> {
                    finish()
                    val intent = Intent(applicationContext, InmatesLoginActivity::class.java)
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                    startActivity(intent)
                })
        Toast.makeText(this, "Logged out ", Toast.LENGTH_LONG).show()
    }
}