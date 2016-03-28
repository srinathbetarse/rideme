package com.dazito.android.rideme.gui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dazito.android.rideme.R;
import com.dazito.android.rideme.model.TaxiCompany;

import java.util.List;

/**
 * Created by Pedro on 10-03-2015.
 */
public class TaxiStandRecyclerViewAdapter extends RecyclerView.Adapter<TaxiStandViewHolder> implements TaxiStandViewHolder.TaxiStandViewHolderClick, View.OnClickListener {

    private static final String TAG = TaxiStandRecyclerViewAdapter.class.getSimpleName();

    private List<TaxiCompany> mTaxiCompanies;
    private final Context mContext;
    private TaxiStandRecyclerViewAdapterCommunicator mListener;

    public TaxiStandRecyclerViewAdapter(Context context, List<TaxiCompany> taxiCompanies) {
        mTaxiCompanies = taxiCompanies;
        mContext = context;
    }

    @Override
    public TaxiStandViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Need to inflate out layout
        View view = LayoutInflater.from(mContext).inflate(R.layout.taxi_stand, null);

        view.setOnClickListener(this);

        TaxiStandViewHolder taxiStandViewHolder = new TaxiStandViewHolder(view, this);

        return taxiStandViewHolder;
    }

    @Override
    public void onBindViewHolder(TaxiStandViewHolder holder, int position) {

        final TaxiCompany taxiCompany = mTaxiCompanies.get(position);
        final String name = taxiCompany.getName();
        final String phoneNumber = taxiCompany.getPhoneNumber();
        final String rating = taxiCompany.getRating();

        holder.mTaxiCompany.setText(name);
        holder.mPhoneNumber.setText("Phone: " + phoneNumber);

        if(rating == null) {
            holder.mRating.setText(" Not Available");
        }
        else {
            holder.mRating.setText(" " + rating + " stars.");
        }

    }

    @Override
    public int getItemCount() {
        if(mTaxiCompanies == null) {
            return 0;
        }
        else {
            return mTaxiCompanies.size();
        }
    }

    @Override
    public void itemPosition(int position) {
        final TaxiCompany taxiCompany = mTaxiCompanies.get(position);
        if(mListener != null) {
            mListener.itemClicked(taxiCompany);
        }
    }

    public void setTaxiStandViewHolderClick(TaxiStandRecyclerViewAdapterCommunicator listener) {
        mListener = listener;
    }

    public void loadNewData(List<TaxiCompany> taxiCompanies) {
        mTaxiCompanies = taxiCompanies;
        notifyDataSetChanged();
    }


    public TaxiCompany getTaxiCompany(int position) {
        if(mTaxiCompanies != null) {
            return mTaxiCompanies.get(position);
        }
        return null;
    }

    @Override
    public void onClick(View v) {
        Log.d(TAG, "Clicked view");
    }

    public static interface TaxiStandRecyclerViewAdapterCommunicator {
        public void itemClicked(TaxiCompany taxiCompany);
    }
}
