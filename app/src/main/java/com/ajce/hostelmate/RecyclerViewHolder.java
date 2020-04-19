package com.ajce.hostelmate;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ajce.hostelmate.reportissue.ReportedIssuesDetailsForInmatesActivity;

public class RecyclerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView title;
    TextView date;
    TextView blockAndRoom;
    TextView issueStatus;
    ImageView imageView;
    RecyclerViewClickListener itemClickListener;

    Context context;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        this.context = itemView.getContext();

        title = itemView.findViewById(R.id.tv_title);
        date = itemView.findViewById(R.id.date);
        blockAndRoom = itemView.findViewById(R.id.block_and_room);
        issueStatus =  itemView.findViewById(R.id.tv_status);
        imageView = itemView.findViewById(R.id.recycler_view_image);
        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        this.itemClickListener.onItemClick(this.getLayoutPosition());
        Intent intent = new Intent(context, ReportedIssuesDetailsForInmatesActivity.class);
        intent.putExtra("POSITION_ID", getAdapterPosition());
        context.startActivity(intent);
    }

    public void setItemClickListener(RecyclerViewClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}