package com.ajce.hostelmate.servicefeedback.reception

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.ajce.hostelmate.R
import com.ajce.hostelmate.Utils.Companion.getMonthInFull
import com.ajce.hostelmate.servicefeedback.Feedback
import com.ajce.hostelmate.sickleave.SickLeave

/**
 * Created by JithinJude on 02,May,2020
 */
class ReceptionMonthlyFeedbacksRecyclerViewAdapter
internal constructor(var context: Context?,
                     monthlyFeedbackList: MutableList<String?>?) :
        RecyclerView.Adapter<ReceptionMonthlyFeedbacksRecyclerViewAdapter
        .ReceptionsickLeaveStatusRecyclerViewHolder?>() {

    val SELECTED_MONTH_FEEDBACK: String = "selected_month_feedback"
    val SELECTED_POSITION: String = "selected_position"

    private val monthlyFeedbackList: MutableList<String?>?
    private val mInflater: LayoutInflater?

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, p1: Int):
            ReceptionMonthlyFeedbacksRecyclerViewAdapter.ReceptionsickLeaveStatusRecyclerViewHolder {
        val view = mInflater?.inflate(R.layout.recyclerview_item_month_feedback, parent, false)
        return ReceptionsickLeaveStatusRecyclerViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holderStatus:
                                  ReceptionMonthlyFeedbacksRecyclerViewAdapter
                                  .ReceptionsickLeaveStatusRecyclerViewHolder, pos: Int) {
        val yyyy = monthlyFeedbackList?.get(pos)?.substring(0, 4);
        val MMMM = monthlyFeedbackList?.get(pos)?.substring(7, 10);

        val monthName = getMonthInFull(MMMM!!)

        holderStatus.title.text = "$yyyy $monthName"

        holderStatus.item?.setOnClickListener {
/*            val intent = Intent(context, ReceptionSickLeaveDetailsActivity::class.java)
            intent.putExtra(SELECTED_MONTH_FEEDBACK, monthlyFeedbackList?.get(pos))
            intent.putExtra(SELECTED_POSITION, pos)
            context!!.startActivity(intent)*/
        }
    }

    // total number of rows
    override fun getItemCount(): Int {
        return monthlyFeedbackList?.size!!
    }

    // data is passed into the constructor
    init {
        mInflater = LayoutInflater.from(context)
        this.monthlyFeedbackList = monthlyFeedbackList
    }

    inner class ReceptionsickLeaveStatusRecyclerViewHolder(itemView: View?) :
            RecyclerView.ViewHolder(itemView!!) {
        var item: CardView?
        var title: TextView
        var context: Context?

        init {
            context = itemView?.context
            item = itemView?.findViewById(R.id.itemCardView)
            title = itemView?.findViewById(R.id.tvFeedbackMonth)!!
        }
    }
}