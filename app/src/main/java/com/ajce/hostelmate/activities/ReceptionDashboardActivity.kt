package com.ajce.hostelmate.activities

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.ajce.hostelmate.R
import com.ajce.hostelmate.WidgetForInmates
import com.ajce.hostelmate.fragments.*
import com.ajce.hostelmate.reportissue.Issue
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_reception_dashboard.*
import java.util.*

class ReceptionDashboardActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reception_dashboard)

        //Disable widget in Admin mode
        val pacman = applicationContext.packageManager
        pacman.setComponentEnabledSetting(ComponentName(applicationContext, WidgetForInmates::class.java),
                PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP)

        val fragment = ReceptionIssueFragment()
        supportFragmentManager.beginTransaction().replace(R.id.dashboardContainer, fragment, fragment.javaClass.simpleName)
                .commit()

        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.bNavIssue -> {
                    val fragment = ReceptionIssueFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.dashboardContainer, fragment, fragment.javaClass.simpleName)
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bNavSickLeave -> {
                    val fragment = ReceptionSickLeaveFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.dashboardContainer, fragment, fragment.javaClass.simpleName)
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bNavNightStudy -> {
                    val fragment = ReceptionNightStudyFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.dashboardContainer, fragment, fragment.javaClass.simpleName)
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }
                R.id.bNavNoticeBoard -> {
                    val fragment = ReceptionNoticeBoardFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.dashboardContainer, fragment, fragment.javaClass.getSimpleName())
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }
/*                R.id.bNavFeedback -> {
                    val fragment = ReceptionFeedbackFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.dashboardContainer, fragment, fragment.javaClass.getSimpleName())
                            .commit()
                    return@OnNavigationItemSelectedListener true
                }*/
            }
            false
        }

        bottomNavView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
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