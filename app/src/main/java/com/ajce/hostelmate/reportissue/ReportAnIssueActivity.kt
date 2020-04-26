package com.ajce.hostelmate.reportissue

import android.app.Activity
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import androidx.appcompat.app.AppCompatActivity
import android.util.Base64
import android.view.View
import android.widget.*
import com.ajce.hostelmate.R
import com.ajce.hostelmate.WidgetForInmates
import com.ajce.hostelmate.login.InmatesLoginActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_report_an_issue.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ReportAnIssueActivity : AppCompatActivity() {

    var databaseIssue: DatabaseReference? = null
    var personEmail: String? = null

    var photo: Bitmap? = null
    var imageEncoded: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_an_issue)
        personEmail = intent.extras["PERSON_EMAIL"].toString()
        databaseIssue = FirebaseDatabase.getInstance().getReference("issues")

        val adapterSpinnerBlock = ArrayAdapter.createFromResource(this,
                R.array.block_list, android.R.layout.simple_spinner_item)
        adapterSpinnerBlock.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_block.adapter = adapterSpinnerBlock
        val adapterSpinnerRoom = ArrayAdapter.createFromResource(this,
                R.array.room_list, android.R.layout.simple_spinner_item)
        adapterSpinnerRoom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sp_room.adapter = adapterSpinnerRoom
    }

    fun addIssue(view: View?) {
        if (imageEncoded == null) {
            Toast.makeText(this, "Take photo of Issue", Toast.LENGTH_LONG).show()
            return
        }
        val title = ed_title?.text.toString()
        val block = sp_block.selectedItem.toString()
        val room = sp_room.selectedItem.toString()
        val description = ed_description?.text.toString()
        val reportedBy = personEmail
        val id = databaseIssue?.push()?.key
        val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val status = "Not Fixed"
        val issue = Issue(id, title, block, room, description, reportedBy, date, status, imageEncoded)
        databaseIssue?.child(id!!)?.setValue(issue)
        Toast.makeText(this, "Issue added", Toast.LENGTH_LONG).show()
        updateWidget(title)
    }

    fun takePhoto(view: View?) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photo = data?.extras?.get("data") as Bitmap
            camera_img?.setImageBitmap(photo)
            val baos = ByteArrayOutputStream()
            photo!!.compress(Bitmap.CompressFormat.PNG, 100, baos)
            imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT)
        }
    }

    fun updateWidget(widgetText: String?) {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val remoteViews = RemoteViews(this.packageName, R.layout.widget_for_inmates)
        val intent = Intent(this, InmatesLoginActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        remoteViews.setOnClickPendingIntent(R.id.img_widget, pendingIntent)
        val thisWidget = ComponentName(this, WidgetForInmates::class.java)
        remoteViews.setTextViewText(R.id.appwidget_text, widgetText)
        remoteViews.setImageViewResource(R.id.img_widget, R.drawable.hostel_red)
        appWidgetManager.updateAppWidget(thisWidget, remoteViews)
    }

    companion object {
        private const val CAMERA_REQUEST = 1
    }
}