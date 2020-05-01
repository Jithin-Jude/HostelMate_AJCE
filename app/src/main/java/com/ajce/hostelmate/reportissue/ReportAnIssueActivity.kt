package com.ajce.hostelmate.reportissue

import android.app.Activity
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.RemoteViews
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ajce.hostelmate.R
import com.ajce.hostelmate.WidgetForInmates
import com.ajce.hostelmate.login.InmatesLoginActivity
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_report_an_issue.*
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*

class ReportAnIssueActivity : AppCompatActivity() {

    val USER_EMAIL: String = "user_email"

    var databaseIssue: DatabaseReference? = null
    var firebaseStore: FirebaseStorage? = null
    var storageReference: StorageReference? = null

    var personEmail: String? = null
    var photo: Bitmap? = null
    var imageUrl: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report_an_issue)
        personEmail = intent.extras[USER_EMAIL].toString()
        databaseIssue = FirebaseDatabase.getInstance().getReference("issues")

        val adapterSpinnerBlock = ArrayAdapter.createFromResource(this,
                R.array.block_list, android.R.layout.simple_spinner_item)
        adapterSpinnerBlock.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spBlock.adapter = adapterSpinnerBlock

        val adapterSpinnerRoom = ArrayAdapter.createFromResource(this,
                R.array.room_list, android.R.layout.simple_spinner_item)
        adapterSpinnerRoom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spRoom.adapter = adapterSpinnerRoom
    }

    fun addIssue(view: View?) {
        if (imageUrl == null) {
            Toast.makeText(this, "Take photo of Issue", Toast.LENGTH_LONG).show()
            return
        }
        val title = etTitle?.text.toString()
        val block = spBlock.selectedItem.toString()
        val room = spRoom.selectedItem.toString()
        val description = etDescription?.text.toString()
        val reportedBy = personEmail
        val id = databaseIssue?.push()?.key
        val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
        val status = "Not Fixed"
        val issue = Issue(id, title, block, room, description, reportedBy, date, status, imageUrl)
        databaseIssue?.child(id!!)?.setValue(issue)
        Toast.makeText(this, "Issue added", Toast.LENGTH_LONG).show()
        updateWidget(title)
    }

    fun takePhoto(view: View?) {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(cameraIntent, CAMERA_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            photo = data?.extras?.get("data") as Bitmap
            ivCameraImg?.setImageBitmap(photo)
            val baos = ByteArrayOutputStream()
            photo!!.compress(Bitmap.CompressFormat.PNG, 100, baos)
            val imgData = baos.toByteArray()

            uploadImage(getTimeStamp(), imgData)
        }
    }

    fun uploadImage(timeStamp: String, imgData: ByteArray){
        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        var downloadUri = ""

        if(imgData.isNotEmpty()){
            val ref = storageReference?.child("issueImages/issue_" + timeStamp)
            val uploadTask = ref?.putBytes(imgData)

            val urlTask = uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation ref.downloadUrl
            })?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    downloadUri = task.result.toString()
                    imageUrl = downloadUri
                    Log.d("ISSUE_IMAGE_URL","ISSUE_IMAGE_UR:" + imageUrl)
                    //loadDatabase(downloadUri, timeStamp)
                } else {
                    //showSnackBar("Upload failed")
                }
            }?.addOnFailureListener{

            }
        }else{
            //showSnackBar("Please select an Image")
        }
    }

    fun getTimeStamp(): String{
        val s = SimpleDateFormat("ddMMyyyyhhmmss", Locale.US)
        val timeStamp = s.format(Date())
        return timeStamp
    }

    fun updateWidget(widgetText: String?) {
        val appWidgetManager = AppWidgetManager.getInstance(this)
        val remoteViews = RemoteViews(this.packageName, R.layout.widget_for_inmates)
        val intent = Intent(this, InmatesLoginActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        remoteViews.setOnClickPendingIntent(R.id.ivIssueImgWidget, pendingIntent)
        val thisWidget = ComponentName(this, WidgetForInmates::class.java)
        remoteViews.setTextViewText(R.id.tvWidgetText, widgetText)
        remoteViews.setImageViewResource(R.id.ivIssueImgWidget, R.drawable.hostel_red)
        appWidgetManager.updateAppWidget(thisWidget, remoteViews)
    }

    companion object {
        private const val CAMERA_REQUEST = 1
    }
}