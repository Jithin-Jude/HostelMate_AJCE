package com.ajce.hostelmate.reportissue

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.RemoteViews
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ajce.hostelmate.R
import com.ajce.hostelmate.WidgetForInmates
import com.ajce.hostelmate.login.InmatesLoginActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_issue_status.*
import kotlinx.android.synthetic.main.content_issue_status.*
import java.util.*

class IssueStatusActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    lateinit var adapterIssueStatus: IssueStatusRecyclerViewAdapter
    lateinit var databaseIssue: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_issue_status)
        val toolbar = findViewById<View?>(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        loadingIssuesForInmates.visibility = View.VISIBLE

        val personName = intent.extras["PERSON_NAME"].toString()
        val personEmail = intent.extras["PERSON_EMAIL"].toString()
        val profilePicUri = intent.extras["PROFILE_PIC"].toString()
        val fab = findViewById<View?>(R.id.fab) as FloatingActionButton

        fab.setOnClickListener {
            val intent = Intent(applicationContext, ReportAnIssueActivity::class.java)
            intent.putExtra("PERSON_EMAIL", personEmail)
            startActivity(intent)
        }

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

        val viewModel: IssueViewModel by lazy { ViewModelProviders.of(this)
                .get(IssueViewModel::class.java) }

        val liveData: LiveData<DataSnapshot?> = viewModel.getDataSnapshotLiveData()

        liveData.observe(this, androidx.lifecycle.Observer { dataSnapshot ->
            issueList?.clear()
            for (issueSnapshot in dataSnapshot?.children!!) {
                val issue = issueSnapshot.getValue(Issue::class.java)
                if (issue != null) {
                    if (issue.issueReportedBy == personEmail) {
                        issueList?.add(issue)
                    }
                }
            }
            if (issueList?.size != 0) updateWidget(issueList
                    ?.get(issueList!!.size - 1)?.issueStatus)
            val recyclerView = findViewById<View?>(R.id.rvIssueStatus) as RecyclerView
            recyclerView.layoutManager = LinearLayoutManager(applicationContext)
            adapterIssueStatus = IssueStatusRecyclerViewAdapter(applicationContext, issueList)
            recyclerView.adapter = adapterIssueStatus
            loadingIssuesForInmates.visibility = View.GONE
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
        remoteViews.setTextViewText(R.id.tvWidgetText, widgetText)
        if (isFixed == "Fixed") {
            remoteViews.setImageViewResource(R.id.ivIssueImgWidget, R.drawable.hostel_green)
        } else {
            remoteViews.setImageViewResource(R.id.ivIssueImgWidget, R.drawable.hostel_red)
        }
        appWidgetManager.updateAppWidget(thisWidget, remoteViews)
    }

    companion object {
        var issueList: MutableList<Issue?>? = ArrayList()
    }
}