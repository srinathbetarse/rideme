package com.dazito.android.rideme.gui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;

import com.dazito.android.rideme.Constants;
import com.dazito.android.rideme.R;
import com.dazito.android.rideme.gps.CoordinatesPOJO;
import com.dazito.android.rideme.gui.SimpleDividerItemDecoration;
import com.dazito.android.rideme.gui.TaxiStandRecyclerViewAdapter;
import com.dazito.android.rideme.model.TaxiCompany;
import com.dazito.android.rideme.network.NetworkIntentService;
import com.dazito.android.rideme.network.NetworkResultReceiver;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.util.ArrayList;

/**
 * Created by Pedro on 09-03-2015.
 */
public class TaxiStandDialogFragment extends DialogFragment implements TaxiStandRecyclerViewAdapter.TaxiStandRecyclerViewAdapterCommunicator, DialogInterface.OnClickListener  {

    private static final String TAG = TaxiStandDialogFragment.class.getSimpleName();

    private static final String ARG_START_LOCATION = "ARG_START_LOCATION";

    private NetworkResultReceiver mNetworkResultReceiver;

    private TaxiStandRecyclerViewAdapter mTaxiStandRecyclerViewAdapter;
    private RecyclerView mRecyclerView;
    private TaxiStandDialogFragmentCommunicator mListener;
    private CircleProgressBar mCircleProgressBar;
    private ArrayList<TaxiCompany> mTaxiCompanies;

    private CoordinatesPOJO mStartLocation;

    private RelativeLayout mProgressBarRelativeLayout;

    public static TaxiStandDialogFragment newInstance(CoordinatesPOJO startLocation) {
        TaxiStandDialogFragment fragment = new TaxiStandDialogFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_START_LOCATION, startLocation);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStartLocation = getArguments().getParcelable(ARG_START_LOCATION);
        }
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.fragment_dialog_book_taxi, null);

        mTaxiCompanies = new ArrayList<>();
        mNetworkResultReceiver = new NetworkResultReceiver(new Handler());

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setNegativeButton("Cancel", this);

        mCircleProgressBar = (CircleProgressBar) view.findViewById(R.id.progressBar);
        mCircleProgressBar.setColorSchemeResources(R.color.ridemeColorBackgroundAccent);

        // Initialize the RecyclerView
        mRecyclerView = (RecyclerView) view.findViewById(R.id.book_taxi_recycler_view);

        mProgressBarRelativeLayout = (RelativeLayout) view.findViewById(R.id.loadingTaxiStands);

        // Add the divider between each row
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        // Set the layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Set the adapter
        mTaxiStandRecyclerViewAdapter = new TaxiStandRecyclerViewAdapter(getActivity(), new ArrayList<TaxiCompany>());
        mTaxiStandRecyclerViewAdapter.setTaxiStandViewHolderClick(this);

        mRecyclerView.setAdapter(mTaxiStandRecyclerViewAdapter);

        if(savedInstanceState != null) {
            mTaxiCompanies = savedInstanceState.getParcelableArrayList(Constants.NEARBY_TAXI_STANDS_RESULTS);
            mTaxiStandRecyclerViewAdapter.loadNewData(mTaxiCompanies);
            mProgressBarRelativeLayout.setVisibility(View.GONE);
        }
        else {
            getNearbyPlaces();
        }

        builder.setView(view);
        return builder.create();
    }

    private void getNearbyPlaces() {
        // Retrieve nearby taxi stands
        Intent intent = new Intent(getActivity().getApplicationContext(), NetworkIntentService.class);
        intent.putExtra(Constants.NETWORK_RESULT_RECEIVER, mNetworkResultReceiver);
        intent.putExtra(Constants.NETWORK_OPERATION_TYPE, Constants.GET_PLACE_DETAILS);
        intent.putExtra(Constants.START_LOCATION, mStartLocation);

        // Launch intent service to retrieve nearby taxi stand
        getActivity().startService(intent);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (TaxiStandDialogFragmentCommunicator) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement TaxiStandDialogFragmentCommunicator");
        }
    }

    @Override
    public void onDetach() {
        mListener = null;
        super.onDetach();
    }

    @Override
    public void itemClicked(TaxiCompany taxiCompany) {
        if(mListener != null) {
            mListener.makePhoneCall(taxiCompany);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.NEARBY_TAXI_STANDS_RESULTS, mTaxiCompanies);
        super.onSaveInstanceState(outState);
    }

    public void addList(ArrayList<TaxiCompany> list) {
        if(mTaxiStandRecyclerViewAdapter != null){
            mTaxiCompanies = list;
            mTaxiStandRecyclerViewAdapter.loadNewData(list);
            mProgressBarRelativeLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {

    }

    public static interface TaxiStandDialogFragmentCommunicator{
        public void makePhoneCall(TaxiCompany taxiCompany);
    }
}
