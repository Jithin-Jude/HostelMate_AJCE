package com.ajce.hostelmate.reportissue

import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.ajce.hostelmate.R

class IssueStatusRecyclerViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!), View.OnClickListener {
    var item: CardView?
    var title: TextView?
    var date: TextView?
    var blockAndRoom: TextView?
    var issueStatus: TextView?
    var imageView: ImageView?
    var itemClickListenerIssueStatus: IssueStatusRecyclerViewClickListener? = null
    var context: Context?

    override fun onClick(view: View?) {
        itemClickListenerIssueStatus?.onItemClick(this.layoutPosition)
    }

    init {
        context = itemView?.context
        item = itemView?.findViewById(R.id.itemCardView)
        title = itemView?.findViewById(R.id.tvTitle)
        date = itemView?.findViewById(R.id.tvDate)
        blockAndRoom = itemView?.findViewById(R.id.tvBlockAndRoom)
        issueStatus = itemView?.findViewById(R.id.tvStatus)
        imageView = itemView?.findViewById(R.id.ivIssueImage)
        itemView?.setOnClickListener(this)
    }
}