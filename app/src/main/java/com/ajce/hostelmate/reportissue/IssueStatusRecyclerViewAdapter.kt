package com.ajce.hostelmate.reportissue

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.recyclerview.widget.RecyclerView
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ajce.hostelmate.R

/**
 * Created by JithinJude on 15-03-2018.
 */
class IssueStatusRecyclerViewAdapter(var context: Context?, issueList: MutableList<Issue?>?) : RecyclerView.Adapter<IssueStatusRecyclerViewHolder?>() {
    private val issueList: MutableList<Issue?>?
    private val mInflater: LayoutInflater?

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, pos: Int): IssueStatusRecyclerViewHolder {
        val view = mInflater?.inflate(R.layout.recyclerview_row, parent, false)
        return IssueStatusRecyclerViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holderIssueStatus: IssueStatusRecyclerViewHolder, pos: Int) {
        holderIssueStatus.title?.text = issueList?.get(pos)?.issueTitle
        val issueLocation = issueList?.get(pos)?.issueBlock + ", " + issueList?.get(pos)?.issueRoom
        holderIssueStatus.blockAndRoom?.text = issueLocation
        holderIssueStatus.date?.text = issueList?.get(pos)?.issueDate
        holderIssueStatus.issueStatus?.text = issueList?.get(pos)?.issueStatus
        if (issueList?.get(pos)?.issueStatus == "Fixed") {
            holderIssueStatus.issueStatus?.setTextColor(context?.getResources()?.getColor(R.color.green)!!)
        } else {
            holderIssueStatus.issueStatus?.setTextColor(context?.getResources()?.getColor(R.color.red)!!)
        }
        try {
            val imageBitmap = decodeFromFirebaseBase64(issueList?.get(pos)?.imageEncoded)
            holderIssueStatus.imageView?.setImageBitmap(imageBitmap)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        holderIssueStatus.setItemClickListener(object : IssueStatusRecyclerViewClickListener {
            override fun onItemClick(pos: Int) {

            }
        })
    }

    // total number of rows
    override fun getItemCount(): Int {
        return issueList!!.size
        //return mCategoryRecyclerviewData.size();
    }

    companion object {
        fun decodeFromFirebaseBase64(image: String?): Bitmap? {
            val decodedByteArray = Base64.decode(image, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
        }
    }

    // data is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        this.issueList = issueList
    }
}