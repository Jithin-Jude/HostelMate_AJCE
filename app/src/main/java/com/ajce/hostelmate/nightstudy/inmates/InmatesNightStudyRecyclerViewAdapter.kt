package com.ajce.hostelmate.nightstudy.inmates

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
class InmatesNightStudyRecyclerViewAdapter(var context: Context?,
                                           nightStudyList: MutableList<NightStudy?>?) :
        RecyclerView.Adapter<InmatesNightStudyRecyclerViewAdapter
        .InmatesNightStudyRecyclerViewHolder?>() {
    private val nightStudyList: MutableList<NightStudy?>?
    private val mInflater: LayoutInflater?

    val SELECTED_NIGHT_STUDY: String = "selected_night_study"
    val SELECTED_POSITION: String = "selected_position"

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, pos: Int):
            InmatesNightStudyRecyclerViewAdapter.InmatesNightStudyRecyclerViewHolder {
        val view = mInflater?.inflate(R.layout.recyclerview_item_sickleave, parent, false)
        return InmatesNightStudyRecyclerViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holderInmatesSickLeave: InmatesNightStudyRecyclerViewAdapter
    .InmatesNightStudyRecyclerViewHolder, pos: Int) {
        holderInmatesSickLeave.title?.text = nightStudyList?.get(pos)?.nightStudyTitle
        val issueLocation = nightStudyList?.get(pos)?.nightStudyBlock + ", " + nightStudyList?.get(pos)?.nightStudyRoom
        holderInmatesSickLeave.blockAndRoom?.text = issueLocation
        holderInmatesSickLeave.date?.text = nightStudyList?.get(pos)?.nightStudyDate
        holderInmatesSickLeave.nightStudyStatus?.text = nightStudyList?.get(pos)?.nightStudyStatus
        if (nightStudyList?.get(pos)?.nightStudyStatus == "approved") {
            holderInmatesSickLeave.nightStudyStatus
                    ?.setTextColor(context?.resources?.getColor(R.color.green)!!)
        } else if (nightStudyList?.get(pos)?.nightStudyStatus == "rejected") {
            holderInmatesSickLeave.nightStudyStatus
                    ?.setTextColor(context?.resources?.getColor(R.color.red)!!)
        } else {
            holderInmatesSickLeave.nightStudyStatus
                    ?.setTextColor(context?.resources?.getColor(R.color.dark_grey)!!)
        }

/*        Glide.with(context!!)
                .load(nightStudyList?.get(pos)?.issueImageUrl)
                .into(holderInmatesSickLeave.imageView!!)*/

        holderInmatesSickLeave.item?.setOnClickListener {
            val intent = Intent(context, InmatesNightStudyDetailsActivity::class.java)
            intent.putExtra(SELECTED_NIGHT_STUDY, nightStudyList?.get(pos))
            intent.putExtra(SELECTED_POSITION,pos)
            context!!.startActivity(intent)

        }
    }

    // total number of rows
    override fun getItemCount(): Int {
        return nightStudyList!!.size
        //return mCategoryRecyclerviewData.size();
    }

    // data is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        this.nightStudyList = nightStudyList
    }

    inner class InmatesNightStudyRecyclerViewHolder(itemView: View?) :
            RecyclerView.ViewHolder(itemView!!) {
        var item: CardView?
        var title: TextView?
        var date: TextView?
        var blockAndRoom: TextView?
        var nightStudyStatus: TextView?
//        var imageView: ImageView?
        var context: Context?

        init {
            context = itemView?.context
            item = itemView?.findViewById(R.id.itemCardView)
            title = itemView?.findViewById(R.id.tvTitle)
            date = itemView?.findViewById(R.id.tvDate)
            blockAndRoom = itemView?.findViewById(R.id.tvBlockAndRoom)
            nightStudyStatus = itemView?.findViewById(R.id.tvStatus)
//            imageView = itemView?.findViewById(R.id.ivSickLeaveImage)
        }
    }
}