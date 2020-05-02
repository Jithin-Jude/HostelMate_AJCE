package com.ajce.hostelmate.reportissue.reception

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ajce.hostelmate.R
import com.ajce.hostelmate.reportissue.Issue
import com.bumptech.glide.Glide

/**
 * Created by JithinJude on 15-03-2018.
 */
class ReceptionIssueStatusRecyclerViewAdapter
internal constructor(var context: Context?,
                     issueList: MutableList<Issue?>?) : RecyclerView.Adapter<ReceptionIssueStatusRecyclerViewHolder?>() {

    val SELECTED_ISSUE: String = "selected_issue"
    val SELECTED_POSITION: String = "selected_position"

    private val issueList: MutableList<Issue?>?
    private val mInflater: LayoutInflater?

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): ReceptionIssueStatusRecyclerViewHolder {
        val view = mInflater?.inflate(R.layout.recyclerview_row, parent, false)
        return ReceptionIssueStatusRecyclerViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holderStatus: ReceptionIssueStatusRecyclerViewHolder, pos: Int) {
        holderStatus.title.text = issueList?.get(pos)?.issueTitle
        val issueLocation = issueList?.get(pos)?.issueBlock + ", " + issueList?.get(pos)?.issueRoom
        holderStatus.blockAndRoom.text = issueLocation
        holderStatus.date.text = issueList?.get(pos)?.issueDate
        holderStatus.issueStatus.text = issueList?.get(pos)?.issueStatus
        if (issueList?.get(pos)?.issueStatus == "Fixed") {
            context?.resources?.getColor(R.color.green)?.let { holderStatus.issueStatus.setTextColor(it) }
        } else {
            context?.resources?.getColor(R.color.red)?.let { holderStatus.issueStatus.setTextColor(it) }
        }

        Glide.with(context!!)
                .load(issueList?.get(pos)?.issueImageUrl)
                .into(holderStatus.imageView)

        holderStatus.item?.setOnClickListener {
            val intent = Intent(context, ReceptionIssuesDetailsActivity::class.java)
            intent.putExtra(SELECTED_ISSUE, issueList?.get(pos))
            intent.putExtra(SELECTED_POSITION,pos)
            context!!.startActivity(intent)

        }

/*        holder.setItemClickListener(object : IssueStatusRecyclerViewClickListener {
            override fun onItemClick(pos: Int) {

            }
        })*/
    }

    // total number of rows
    override fun getItemCount(): Int {
        return issueList?.size!!
        //return mCategoryRecyclerviewData.size();
    }

/*    companion object {
        fun decodeFromFirebaseBase64(image: String?): Bitmap? {
            val decodedByteArray = Base64.decode(image, Base64.DEFAULT)
            return BitmapFactory.decodeByteArray(decodedByteArray, 0, decodedByteArray.size)
        }
    }*/

    // data is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        this.issueList = issueList
    }
}