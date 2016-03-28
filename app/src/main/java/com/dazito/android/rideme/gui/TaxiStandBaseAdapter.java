package com.dazito.android.rideme.gui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.dazito.android.rideme.R;
import com.dazito.android.rideme.gui.fragments.TaxiStandDialogFragment;
import com.dazito.android.rideme.model.TaxiCompany;

import java.util.ArrayList;

/**
 * Created by Pedro on 10-03-2015.
 */
public class TaxiStandBaseAdapter extends BaseAdapter {

    private static final String TAG = TaxiStandDialogFragment.class.getSimpleName();

    private TextView mTaxiCompany;
    private TextView mPhoneNumber;
    private TextView mRating;

    private ArrayList<TaxiCompany> mTaxiCompanies;
    private Context mContext;

    public TaxiStandBaseAdapter(Context context, ArrayList<TaxiCompany> taxiCompanies) {
        mContext = context;
        mTaxiCompanies = taxiCompanies;
    }

    @Override
    public int getCount() {
        if(mTaxiCompanies == null) { return 0; }
        else { return mTaxiCompanies.size(); }
    }

    @Override
    public Object getItem(int position) {
        if(mTaxiCompanies == null) {return null; }
        else {return mTaxiCompanies.get(position); }
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the root view
        View view = LayoutInflater.from(mContext).inflate(R.layout.taxi_stand, parent, false);

        mTaxiCompany = (TextView) view.findViewById(R.id.taxiStandName);
        mPhoneNumber = (TextView) view.findViewById(R.id.taxiStandPhone);
        mRating = (TextView) view.findViewById(R.id.taxiStandRate);

        TaxiCompany taxiCompany = mTaxiCompanies.get(position);

        mTaxiCompany.setText(taxiCompany.getName());
        mPhoneNumber.setText(taxiCompany.getPhoneNumber());
        mRating.setText(taxiCompany.getRating());

        return view;
    }
}
