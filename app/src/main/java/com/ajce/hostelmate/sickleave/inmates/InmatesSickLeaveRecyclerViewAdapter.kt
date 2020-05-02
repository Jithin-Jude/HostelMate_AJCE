package com.ajce.hostelmate.sickleave.inmates

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
class InmatesSickLeaveRecyclerViewAdapter(var context: Context?,
                                          sickLeaveList: MutableList<SickLeave?>?) :
        RecyclerView.Adapter<InmatesSickLeaveRecyclerViewAdapter
        .InmatesSickLeaveRecyclerViewHolder?>() {
    private val sickLeaveList: MutableList<SickLeave?>?
    private val mInflater: LayoutInflater?

    val SELECTED_SICK_LEAVE: String = "selected_sick_leave"
    val SELECTED_POSITION: String = "selected_position"

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, pos: Int):
            InmatesSickLeaveRecyclerViewAdapter.InmatesSickLeaveRecyclerViewHolder {
        val view = mInflater?.inflate(R.layout.recyclerview_item_sickleave, parent, false)
        return InmatesSickLeaveRecyclerViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holderInmatesSickLeave: InmatesSickLeaveRecyclerViewAdapter
    .InmatesSickLeaveRecyclerViewHolder, pos: Int) {
        holderInmatesSickLeave.title?.text = sickLeaveList?.get(pos)?.sickLeaveTitle
        val issueLocation = sickLeaveList?.get(pos)?.sickLeaveBlock + ", " + sickLeaveList?.get(pos)?.sickLeaveRoom
        holderInmatesSickLeave.blockAndRoom?.text = issueLocation
        holderInmatesSickLeave.date?.text = sickLeaveList?.get(pos)?.sickLeaveDate
        holderInmatesSickLeave.sickLeaveStatus?.text = sickLeaveList?.get(pos)?.sickLeaveStatus
        if (sickLeaveList?.get(pos)?.sickLeaveStatus == "approved") {
            holderInmatesSickLeave.sickLeaveStatus
                    ?.setTextColor(context?.resources?.getColor(R.color.green)!!)
        } else {
            holderInmatesSickLeave.sickLeaveStatus
                    ?.setTextColor(context?.resources?.getColor(R.color.red)!!)
        }

/*        Glide.with(context!!)
                .load(sickLeaveList?.get(pos)?.issueImageUrl)
                .into(holderInmatesSickLeave.imageView!!)*/

        holderInmatesSickLeave.item?.setOnClickListener {
/*            val intent = Intent(context, InmatesSickLeavesDetailsActivity::class.java)
            intent.putExtra(SELECTED_SICK_LEAVE, sickLeaveList?.get(pos))
            intent.putExtra(SELECTED_POSITION,pos)
            context!!.startActivity(intent)*/

        }
    }

    // total number of rows
    override fun getItemCount(): Int {
        return sickLeaveList!!.size
        //return mCategoryRecyclerviewData.size();
    }

    // data is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        this.sickLeaveList = sickLeaveList
    }

    inner class InmatesSickLeaveRecyclerViewHolder(itemView: View?) :
            RecyclerView.ViewHolder(itemView!!) {
        var item: CardView?
        var title: TextView?
        var date: TextView?
        var blockAndRoom: TextView?
        var sickLeaveStatus: TextView?
//        var imageView: ImageView?
        var context: Context?

        init {
            context = itemView?.context
            item = itemView?.findViewById(R.id.itemCardView)
            title = itemView?.findViewById(R.id.tvTitle)
            date = itemView?.findViewById(R.id.tvDate)
            blockAndRoom = itemView?.findViewById(R.id.tvBlockAndRoom)
            sickLeaveStatus = itemView?.findViewById(R.id.tvStatus)
//            imageView = itemView?.findViewById(R.id.ivSickLeaveImage)
        }
    }
}