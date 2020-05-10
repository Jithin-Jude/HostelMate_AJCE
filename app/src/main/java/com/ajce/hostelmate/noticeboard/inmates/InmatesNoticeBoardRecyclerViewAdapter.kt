package com.ajce.hostelmate.noticeboard.inmates

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ajce.hostelmate.R
import com.ajce.hostelmate.noticeboard.NoticeBoard

/**
 * Created by JithinJude on 10,May,2020
 */
class InmatesNoticeBoardRecyclerViewAdapter(var context: Context?,
                                            noticeList: MutableList<NoticeBoard?>?) :
        RecyclerView.Adapter<InmatesNoticeBoardRecyclerViewAdapter
        .InmatesSickLeaveRecyclerViewHolder?>() {
    private val noticeList: MutableList<NoticeBoard?>?
    private val mInflater: LayoutInflater?

    val SELECTED_NOTICE: String = "selected_notice"
    val SELECTED_POSITION: String = "selected_position"

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, pos: Int):
            InmatesNoticeBoardRecyclerViewAdapter.InmatesSickLeaveRecyclerViewHolder {
        val view = mInflater?.inflate(R.layout.recyclerview_item_notice, parent, false)
        return InmatesSickLeaveRecyclerViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holderInmatesSickLeave: InmatesNoticeBoardRecyclerViewAdapter
    .InmatesSickLeaveRecyclerViewHolder, pos: Int) {
        holderInmatesSickLeave.title?.text = noticeList?.get(pos)?.noticeTitle
        holderInmatesSickLeave.date?.text = noticeList?.get(pos)?.noticeDate

        holderInmatesSickLeave.item?.setOnClickListener {
            val intent = Intent(context, InmatesNoticeBoardDetailsActivity::class.java)
            intent.putExtra(SELECTED_NOTICE, noticeList?.get(pos))
            intent.putExtra(SELECTED_POSITION,pos)
            context!!.startActivity(intent)

        }
    }

    // total number of rows
    override fun getItemCount(): Int {
        return noticeList!!.size
    }

    // data is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        this.noticeList = noticeList
    }

    inner class InmatesSickLeaveRecyclerViewHolder(itemView: View?) :
            RecyclerView.ViewHolder(itemView!!) {
        var item: CardView?
        var title: TextView?
        var date: TextView?
        var context: Context?

        init {
            context = itemView?.context
            item = itemView?.findViewById(R.id.itemCardView)
            title = itemView?.findViewById(R.id.tvTitle)
            date = itemView?.findViewById(R.id.tvDate)
        }
    }
}