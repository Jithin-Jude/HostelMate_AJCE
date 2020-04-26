package com.ajce.hostelmate.reportissue

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ajce.hostelmate.R

class ReceptionIssueRecyclerViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!), View.OnClickListener {
    var title: TextView
    var date: TextView
    var blockAndRoom: TextView
    var issueStatus: TextView
    var imageView: ImageView
    private lateinit var itemClickListenerIssueStatus: IssueStatusRecyclerViewClickListener
    var context: Context?
    override fun onClick(view: View?) {
        itemClickListenerIssueStatus.onItemClick(this.layoutPosition)
        val intent = Intent(context, ReportedIssuesDetailsForReceptionActivity::class.java)
        intent.putExtra("POSITION_ID", adapterPosition)
        context?.startActivity(intent)
    }

    fun setItemClickListener(itemClickListenerIssueStatus: IssueStatusRecyclerViewClickListener) {
        this.itemClickListenerIssueStatus = itemClickListenerIssueStatus
    }

    init {
        context = itemView?.context
        title = itemView?.findViewById(R.id.tvTitle)!!
        date = itemView.findViewById(R.id.tvDate)
        blockAndRoom = itemView.findViewById(R.id.tvBlockAndRoom)
        issueStatus = itemView.findViewById(R.id.tvStatus)
        imageView = itemView.findViewById(R.id.ivIssueImage)
        itemView.setOnClickListener(this)
    }
}