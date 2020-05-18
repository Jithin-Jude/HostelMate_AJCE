package com.ajce.hostelmate.nightstudy.reception

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ajce.hostelmate.R
import com.ajce.hostelmate.nightstudy.NightStudy
import com.ajce.hostelmate.sickleave.SickLeave

/**
 * Created by JithinJude on 02,May,2020
 */
class ReceptionNightStudyRecyclerViewAdapter
internal constructor(var context: Context?,
                     nightStudyList: MutableList<NightStudy?>?) :
        RecyclerView.Adapter<ReceptionNightStudyRecyclerViewAdapter
        .ReceptionNightStudyStatusRecyclerViewHolder?>() {

    val SELECTED_NIGHT_STUDY: String = "selected_nightstudy"
    val SELECTED_POSITION: String = "selected_position"

    private val nightStudyList: MutableList<NightStudy?>?
    private val mInflater: LayoutInflater?

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int):
            ReceptionNightStudyRecyclerViewAdapter.ReceptionNightStudyStatusRecyclerViewHolder {
        val view = mInflater?.inflate(R.layout.recyclerview_item_sickleave_inmate, parent, false)
        return ReceptionNightStudyStatusRecyclerViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holderStatus:
                                  ReceptionNightStudyRecyclerViewAdapter
                                  .ReceptionNightStudyStatusRecyclerViewHolder, pos: Int) {
        holderStatus.title.text = nightStudyList?.get(pos)?.nightStudyTitle
        holderStatus.name.text = nightStudyList?.get(pos)?.nightStudyReportedBy
        val issueLocation = nightStudyList?.get(pos)?.nightStudyBlock + ", " + nightStudyList?.get(pos)?.nightStudyRoom
        holderStatus.blockAndRoom.text = issueLocation
        holderStatus.date.text = nightStudyList?.get(pos)?.nightStudyDate
        holderStatus.nightStudyStatus.text = nightStudyList?.get(pos)?.nightStudyStatus
        if (nightStudyList?.get(pos)?.nightStudyStatus == "approved") {
            context?.resources?.getColor(R.color.green)?.let { holderStatus.nightStudyStatus
                    .setTextColor(it) }
        } else if (nightStudyList?.get(pos)?.nightStudyStatus == "rejected"){
            context?.resources?.getColor(R.color.red)?.let { holderStatus.nightStudyStatus
                    .setTextColor(it) }
        } else {
            context?.resources?.getColor(R.color.dark_grey)?.let { holderStatus.nightStudyStatus
                    .setTextColor(it) }
        }

        holderStatus.item?.setOnClickListener {
            val intent = Intent(context, ReceptionNightStudyDetailsActivity::class.java)
            intent.putExtra(SELECTED_NIGHT_STUDY, nightStudyList?.get(pos))
            intent.putExtra(SELECTED_POSITION, pos)
            context!!.startActivity(intent)
        }
    }

    // total number of rows
    override fun getItemCount(): Int {
        return nightStudyList?.size!!
    }

    // data is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        this.nightStudyList = nightStudyList
    }

    inner class ReceptionNightStudyStatusRecyclerViewHolder(itemView: View?) :
            RecyclerView.ViewHolder(itemView!!) {
        var item: CardView?
        var title: TextView
        var name: TextView
        var date: TextView
        var blockAndRoom: TextView
        var nightStudyStatus: TextView
        var context: Context?

        init {
            context = itemView?.context
            item = itemView?.findViewById(R.id.itemCardView)
            title = itemView?.findViewById(R.id.tvTitle)!!
            name = itemView.findViewById(R.id.tvName)
            date = itemView.findViewById(R.id.tvDate)
            blockAndRoom = itemView.findViewById(R.id.tvBlockAndRoom)
            nightStudyStatus = itemView.findViewById(R.id.tvStatus)
        }
    }
}