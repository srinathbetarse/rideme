package com.dazito.android.rideme.gui;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.dazito.android.rideme.R;

/**
 * Created by Pedro on 10-03-2015.
 */
public class TaxiStandViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

    private static final String TAG = TaxiStandViewHolder.class.getSimpleName();

    protected TextView mTaxiCompany;
    protected TextView mPhoneNumber;
    protected TextView mRating;

    private TaxiStandViewHolderClick mListener;

    public TaxiStandViewHolder(View itemView, TaxiStandViewHolderClick taxiStandViewHolderClick) {
        super(itemView);

        mTaxiCompany = (TextView) itemView.findViewById(R.id.taxiStandName);
        mPhoneNumber = (TextView) itemView.findViewById(R.id.taxiStandPhone);
        mRating = (TextView) itemView.findViewById(R.id.taxiStandRate);

        mListener = taxiStandViewHolderClick;

        itemView.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int position = getPosition();

        if(mListener != null) {
            mListener.itemPosition(position);
        }
    }

    public static interface TaxiStandViewHolderClick {
        public void itemPosition(int position);
    }
}
