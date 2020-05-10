package com.ajce.hostelmate.noticeboard.reception

import android.content.ContentResolver
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.MimeTypeMap
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.ajce.hostelmate.R
import com.ajce.hostelmate.Utils.Companion.getTimeStamp
import com.ajce.hostelmate.noticeboard.NoticeBoard
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import kotlinx.android.synthetic.main.activity_publish_notice.*
import kotlinx.android.synthetic.main.activity_request_sick_leave.btnConfirm
import kotlinx.android.synthetic.main.activity_request_sick_leave.etDescription
import kotlinx.android.synthetic.main.activity_request_sick_leave.etTitle
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by JithinJude on 02,May,2020
 */
class ReceptionPublishNoticeActivity : AppCompatActivity() {

    val NOTICE_PDF = 111;

    var databaseReference: DatabaseReference? = null
    var firebaseStore: FirebaseStorage? = null
    var storageReference: StorageReference? = null

    lateinit var selectedFile: Uri
    var noticePdfUrl: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_publish_notice)

        title = getString(R.string.publish_notice)

        //actionbar
        val actionbar = supportActionBar
        //set back button
        actionbar?.setDisplayHomeAsUpEnabled(true)

        databaseReference = FirebaseDatabase.getInstance().getReference("noticeboard")

        pbFileUpload.visibility = View.GONE

        btnUploadNotice.setOnClickListener {
            val intent = Intent()
                    .setType("application/pdf")
                    .setAction(Intent.ACTION_GET_CONTENT)
            intent.addCategory(Intent.CATEGORY_OPENABLE);

            startActivityForResult(Intent.createChooser(intent, "Select a notice PDF"), NOTICE_PDF)
        }

        btnConfirm.setOnClickListener {
            publishNotice()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == NOTICE_PDF && resultCode == RESULT_OK) {
            selectedFile = data?.data!! //The uri with the location of the file

            val cR: ContentResolver = this.contentResolver
            val mime = MimeTypeMap.getSingleton()
            val type = mime.getExtensionFromMimeType(cR.getType(selectedFile!!))

            if(type == "pdf"){
                uploadImage(getTimeStamp(), selectedFile)
                Toast.makeText(applicationContext, "Notice PDF added", Toast.LENGTH_LONG).show()
            }else{
                Toast.makeText(applicationContext, "Select only PDF files", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    fun uploadImage(timeStamp: String, file: Uri){

        pbFileUpload.visibility = View.VISIBLE
        btnConfirm.isEnabled = false

        firebaseStore = FirebaseStorage.getInstance()
        storageReference = FirebaseStorage.getInstance().reference

        var downloadUri = ""

            val ref = storageReference?.child("notice/notice_" + timeStamp)
            val uploadTask = ref?.putFile(file)

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
                    noticePdfUrl = downloadUri
                    Log.d("ISSUE_IMAGE_URL","ISSUE_IMAGE_UR:" + noticePdfUrl)
                    Toast.makeText(applicationContext, "File upload success", Toast.LENGTH_SHORT).show()
                    pbFileUpload.visibility = View.GONE
                    btnConfirm.isEnabled = true
                } else {
                    Log.d("failed","Notice PDF failed to add")
                    Toast.makeText(applicationContext, "File upload failed", Toast.LENGTH_SHORT).show()
                    pbFileUpload.visibility = View.GONE
                    btnConfirm.isEnabled = true
                }
            }?.addOnFailureListener{

            }
    }

    fun publishNotice() {
/*        if (sickLeaveReasonForRejection == null) {
            Toast.makeText(this, "Take photo of Issue", Toast.LENGTH_LONG).show()
            return
        }*/
        val title = etTitle?.text.toString()
        val description = etDescription?.text.toString()
        val id = databaseReference?.push()?.key
        val date = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())

        if ("" == title || "" == description) {
            Toast.makeText(this, "Form fields cannot be empty", Toast.LENGTH_LONG).show()
            return
        }

        val issue = NoticeBoard(id, title, description, date, noticePdfUrl)
        databaseReference?.child(id!!)?.setValue(issue)
        Toast.makeText(this, "Notice published", Toast.LENGTH_LONG).show()
        finish()
    }
}