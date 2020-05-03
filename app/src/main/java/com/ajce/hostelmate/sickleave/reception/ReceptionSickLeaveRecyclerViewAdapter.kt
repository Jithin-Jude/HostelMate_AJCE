package com.ajce.hostelmate.sickleave.reception

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ajce.hostelmate.R
import com.ajce.hostelmate.sickleave.SickLeave

/**
 * Created by JithinJude on 02,May,2020
 */
class ReceptionSickLeaveRecyclerViewAdapter
internal constructor(var context: Context?,
                     sickLeaveList: MutableList<SickLeave?>?) :
        RecyclerView.Adapter<ReceptionSickLeaveRecyclerViewAdapter
        .ReceptionsickLeaveStatusRecyclerViewHolder?>() {

    val SELECTED_SICK_LEAVE: String = "selected_sickleave"
    val SELECTED_POSITION: String = "selected_position"

    private val sickLeaveList: MutableList<SickLeave?>?
    private val mInflater: LayoutInflater?

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int):
            ReceptionSickLeaveRecyclerViewAdapter.ReceptionsickLeaveStatusRecyclerViewHolder {
        val view = mInflater?.inflate(R.layout.recyclerview_item_sickleave_inmate, parent, false)
        return ReceptionsickLeaveStatusRecyclerViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holderStatus:
                                  ReceptionSickLeaveRecyclerViewAdapter
                                  .ReceptionsickLeaveStatusRecyclerViewHolder, pos: Int) {
        holderStatus.title.text = sickLeaveList?.get(pos)?.sickLeaveTitle
        holderStatus.name.text = sickLeaveList?.get(pos)?.sickLeaveReportedBy
        val issueLocation = sickLeaveList?.get(pos)?.sickLeaveBlock + ", " + sickLeaveList?.get(pos)?.sickLeaveRoom
        holderStatus.blockAndRoom.text = issueLocation
        holderStatus.date.text = sickLeaveList?.get(pos)?.sickLeaveDate
        holderStatus.sickLeaveStatus.text = sickLeaveList?.get(pos)?.sickLeaveStatus
        if (sickLeaveList?.get(pos)?.sickLeaveStatus == "approved") {
            context?.resources?.getColor(R.color.green)?.let { holderStatus.sickLeaveStatus
                    .setTextColor(it) }
        } else if (sickLeaveList?.get(pos)?.sickLeaveStatus == "rejected"){
            context?.resources?.getColor(R.color.red)?.let { holderStatus.sickLeaveStatus
                    .setTextColor(it) }
        } else {
            context?.resources?.getColor(R.color.dark_grey)?.let { holderStatus.sickLeaveStatus
                    .setTextColor(it) }
        }

        holderStatus.item?.setOnClickListener {
            val intent = Intent(context, ReceptionSickLeaveDetailsActivity::class.java)
            intent.putExtra(SELECTED_SICK_LEAVE, sickLeaveList?.get(pos))
            intent.putExtra(SELECTED_POSITION, pos)
            context!!.startActivity(intent)
        }
    }

    // total number of rows
    override fun getItemCount(): Int {
        return sickLeaveList?.size!!
    }

    // data is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        this.sickLeaveList = sickLeaveList
    }

    inner class ReceptionsickLeaveStatusRecyclerViewHolder(itemView: View?) :
            RecyclerView.ViewHolder(itemView!!) {
        var item: CardView?
        var title: TextView
        var name: TextView
        var date: TextView
        var blockAndRoom: TextView
        var sickLeaveStatus: TextView
        var context: Context?

        init {
            context = itemView?.context
            item = itemView?.findViewById(R.id.itemCardView)
            title = itemView?.findViewById(R.id.tvTitle)!!
            name = itemView.findViewById(R.id.tvName)
            date = itemView.findViewById(R.id.tvDate)
            blockAndRoom = itemView.findViewById(R.id.tvBlockAndRoom)
            sickLeaveStatus = itemView.findViewById(R.id.tvStatus)
        }
    }
}