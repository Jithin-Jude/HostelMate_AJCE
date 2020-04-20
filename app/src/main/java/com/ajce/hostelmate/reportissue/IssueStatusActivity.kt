package com.ajce.hostelmate.reportissue

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import android.view.MenuItem
import android.view.View
import android.widget.*
import com.ajce.hostelmate.R
import com.ajce.hostelmate.RecyclerViewAdapter
import com.ajce.hostelmate.WidgetForInmates
import com.ajce.hostelmate.login.InmatesLoginActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.nav_header_issue_status.*
import java.util.*

class IssueStatusActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var adapter: RecyclerViewAdapter
    lateinit var databaseIssue: DatabaseReference
    lateinit var progressBarLodingIssuesForInmates: ProgressBar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_status)
        val toolbar = findViewById<View?>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)
        progressBarLodingIssuesForInmates = findViewById(R.id.loading_issues_for_inmates)
        progressBarLodingIssuesForInmates.setVisibility(View.VISIBLE)
        val personName = intent.extras["PERSON_NAME"].toString()
        val personEmail = intent.extras["PERSON_EMAIL"].toString()
        val profilePicUri = intent.extras["PROFILE_PIC"].toString()
        val fab = findViewById<View?>(R.id.fab) as FloatingActionButton
        fab.setOnClickListener {
            val intent = Intent(applicationContext, ReportAnIssueActivity::class.java)
            intent.putExtra("PERSON_EMAIL", personEmail)
            startActivity(intent)
        }
        val drawer = findViewById<View?>(R.id.drawer_layout) as DrawerLayout
        val toggle = ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        toggle.syncState()
        val navigationView = findViewById<View?>(R.id.nav_view) as NavigationView
        navigationView.setNavigationItemSelectedListener(this)
        val header = navigationView.getHeaderView(0)
        val personNameTV = header.findViewById<TextView?>(R.id.person_name)
        val personEmailTV = header.findViewById<TextView?>(R.id.person_email)
        val profilePic = header.findViewById<ImageView?>(R.id.profile_pic)
        Glide.with(this)
                .load(profilePicUri)
                .apply(RequestOptions()
                        .circleCrop())
                .into(profilePic!!)
        personNameTV?.text = personName
        personEmailTV?.text = personEmail
        databaseIssue = FirebaseDatabase.getInstance().getReference("issues")
        databaseIssue.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                issueList?.clear()
                for (issueSnapshot in dataSnapshot.children) {
                    val issue = issueSnapshot.getValue(Issue::class.java)
                    if (issue != null) {
                        if (issue.issueReportedBy == personEmail) {
                            issueList?.add(issue)
                        }
                    }
                }
                if (issueList?.size != 0) updateWidget(issueList?.get(issueList!!.size - 1)?.issueStatus)
                val recyclerView = findViewById<View?>(R.id.rv_issue_status) as RecyclerView
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                adapter = RecyclerViewAdapter(applicationContext, issueList)
                recyclerView.adapter = adapter
                progressBarLodingIssuesForInmates.setVisibility(View.GONE)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
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

    fun updateWidget(isFixed: String?) {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val remoteViews = RemoteViews(this.packageName, R.layout.widget_for_inmates)
        val thisWidget = ComponentName(this, WidgetForInmates::class.java)
        val widgetText = issueList?.get(issueList!!.size - 1)?.issueTitle
        remoteViews.setTextViewText(R.id.appwidget_text, widgetText)
        if (isFixed == "Fixed") {
            remoteViews.setImageViewResource(R.id.img_widget, R.drawable.hostel_green)
        } else {
            remoteViews.setImageViewResource(R.id.img_widget, R.drawable.hostel_red)
        }
        appWidgetManager.updateAppWidget(thisWidget, remoteViews)
    }

    companion object {
        var issueList: MutableList<Issue?>? = ArrayList()
    }
}