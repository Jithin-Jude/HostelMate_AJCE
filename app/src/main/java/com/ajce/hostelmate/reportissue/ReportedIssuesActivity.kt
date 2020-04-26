package com.ajce.hostelmate.reportissue

import android.content.ComponentName
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import com.ajce.hostelmate.ControlPanelActivity
import com.ajce.hostelmate.R
import com.ajce.hostelmate.WidgetForInmates
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_reported_issues.*
import java.util.*

class ReportedIssuesActivity : AppCompatActivity() {

    lateinit var adapter: ReceptionIssueRecyclerViewAdapter
    lateinit var databaseIssue: DatabaseReference
    lateinit var sharedPreferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reported_issues)
        title = getString(R.string.reported_issues)

        //Disable widget in Admin mode
        val pacman = applicationContext.packageManager
        pacman.setComponentEnabledSetting(ComponentName(applicationContext, WidgetForInmates::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        editor = sharedPreferences.edit()

        loading_issues.setVisibility(View.VISIBLE)
        databaseIssue = FirebaseDatabase.getInstance().getReference("issues")
        databaseIssue.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                issueList?.clear()
                for (issueSnapshot in dataSnapshot.children) {
                    val issue = issueSnapshot.getValue(Issue::class.java)
                    issueList?.add(issue)
                }
                if (!sharedPreferences.getBoolean("NOTIFICATIONS_ON", false)) {
                    issueList?.size?.let { editor.putInt("NUMBER_OF_ISSUES", it) }
                    editor.apply()
                }
                val recyclerView = findViewById<View?>(R.id.rv_reported_issues) as RecyclerView
                recyclerView.layoutManager = LinearLayoutManager(applicationContext)
                adapter = ReceptionIssueRecyclerViewAdapter(applicationContext, issueList)
                recyclerView.adapter = adapter
                loading_issues.setVisibility(View.GONE)
            }

            override fun onCancelled(databaseError: DatabaseError) {}
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.actionbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        // Handle item selection
        return when (item?.itemId) {
            R.id.control_panel -> {
                val intent = Intent(this, ControlPanelActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    companion object {
        var issueList: MutableList<Issue?>? = ArrayList()
        var numberOfIssuesOld = 0
        var numberOfIssuesNew = 0
    }
}