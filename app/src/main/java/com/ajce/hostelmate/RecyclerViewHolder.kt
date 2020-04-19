package com.ajce.hostelmate

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.ajce.hostelmate.reportissue.ReportedIssuesDetailsForInmatesActivity

class RecyclerViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!), View.OnClickListener {
    var title: TextView?
    var date: TextView?
    var blockAndRoom: TextView?
    var issueStatus: TextView?
    var imageView: ImageView?
    private var itemClickListener: RecyclerViewClickListener? = null
    var context: Context?

    override fun onClick(view: View?) {
        itemClickListener?.onItemClick(this.layoutPosition)
        val intent = Intent(context, ReportedIssuesDetailsForInmatesActivity::class.java)
        intent.putExtra("POSITION_ID", adapterPosition)
        context?.startActivity(intent)
    }

    fun setItemClickListener(itemClickListener: RecyclerViewClickListener?) {
        this.itemClickListener = itemClickListener
    }

    init {
        context = itemView?.getContext()
        title = itemView?.findViewById(R.id.tv_title)
        date = itemView?.findViewById(R.id.date)
        blockAndRoom = itemView?.findViewById(R.id.block_and_room)
        issueStatus = itemView?.findViewById(R.id.tv_status)
        imageView = itemView?.findViewById(R.id.recycler_view_image)
        itemView?.setOnClickListener(this)
    }
}