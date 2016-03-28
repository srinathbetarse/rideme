package com.dazito.android.rideme.gui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.dazito.android.rideme.R;

/**
 * Created by Pedro on 06-02-2015.
 */
public class RouteDataViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private static final String TAG = RouteDataViewHolder.class.getSimpleName();

    protected ImageView thumbnail;
    protected TextView serviceType;
    protected TextView price;
    protected TextView duration;
    protected TextView distance;

    public ViewHolderCommunicator mListener;

    public RouteDataViewHolder(View itemView, ViewHolderCommunicator listener) {
        super(itemView);

        thumbnail = (ImageView) itemView.findViewById(R.id.thumbnail);
        serviceType = (TextView) itemView.findViewById(R.id.service_type);
        duration = (TextView) itemView.findViewById(R.id.duration);
        price = (TextView) itemView.findViewById(R.id.price);
        distance = (TextView) itemView.findViewById(R.id.distance);

        mListener = listener;

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int position = getPosition();

        if(mListener != null) {
            mListener.itemPosition(position, price);
        }
    }


    public static interface ViewHolderCommunicator {
        public void itemPosition(int position, View sharedView);
    }
}
