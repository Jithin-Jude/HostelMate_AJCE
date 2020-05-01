package com.ajce.hostelmate.reportissue

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.ajce.hostelmate.R
import com.bumptech.glide.Glide

/**
 * Created by JithinJude on 15-03-2018.
 */
class IssueStatusRecyclerViewAdapter(var context: Context?, issueList: MutableList<Issue?>?) : RecyclerView.Adapter<IssueStatusRecyclerViewHolder?>() {
    private val issueList: MutableList<Issue?>?
    private val mInflater: LayoutInflater?

    val SELECTED_ISSUE: String = "selected_issue"
    val SELECTED_POSITION: String = "selected_position"

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
            holderIssueStatus.issueStatus?.setTextColor(context?.resources?.getColor(R.color.green)!!)
        } else {
            holderIssueStatus.issueStatus?.setTextColor(context?.resources?.getColor(R.color.red)!!)
        }

        Glide.with(context!!)
                .load(issueList?.get(pos)?.issueImageUrl)
                .into(holderIssueStatus.imageView!!)

        holderIssueStatus.item?.setOnClickListener {
            val intent = Intent(context, ReportedIssuesDetailsForInmatesActivity::class.java)
            intent.putExtra(SELECTED_ISSUE, issueList?.get(pos))
            intent.putExtra(SELECTED_POSITION,pos)
            context!!.startActivity(intent)

        }
    }

    // total number of rows
    override fun getItemCount(): Int {
        return issueList!!.size
        //return mCategoryRecyclerviewData.size();
    }

    // data is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        this.issueList = issueList
    }
}