package com.example.mdotmnativesample;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class FeedListRowHolder extends RecyclerView.ViewHolder {
    protected ImageView profileIcon;
    protected TextView nameView;
    protected TextView descriptionView;
    protected TextView adFlagView;
    protected LinearLayout linearLayout;
    

    public FeedListRowHolder(View view) {
        super(view);
        this.profileIcon = (ImageView) view.findViewById(R.id.profileIcon);
        this.nameView = (TextView) view.findViewById(R.id.name);
        this.descriptionView = (TextView) view.findViewById(R.id.description);
        this.adFlagView=(TextView) view.findViewById(R.id.ad_flag);
        this.linearLayout=(LinearLayout) view.findViewById(R.id.liniear);
    }

}
