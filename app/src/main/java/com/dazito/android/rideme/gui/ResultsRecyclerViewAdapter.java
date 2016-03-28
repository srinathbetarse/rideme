package com.dazito.android.rideme.gui;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dazito.android.rideme.R;
import com.dazito.android.rideme.Utils;
import com.dazito.android.rideme.model.RoutePrice;
import com.dazito.android.rideme.model.TaxiFareFinderRoutePrice;
import com.dazito.android.rideme.model.UberRoutePrice;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by Pedro on 05-02-2015.
 */
public class ResultsRecyclerViewAdapter extends RecyclerView.Adapter<RouteDataViewHolder> implements RouteDataViewHolder.ViewHolderCommunicator {

    private static final String TAG = ResultsRecyclerViewAdapter.class.getSimpleName();

    private List<RoutePrice> mRouteDataResults;
    private Context mContext;

    private ResultsRecyclerViewAdapterCommunicator mResultsRecyclerViewAdapterCommunicator;

    public ResultsRecyclerViewAdapter(Context context, List<RoutePrice> routeDataResults) {
        mRouteDataResults = routeDataResults;
        mContext = context;
    }

    @Override
    public RouteDataViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {

        // Need to inflate out layout
        View view = LayoutInflater.from(mContext).inflate(R.layout.ride, null); //espetar aqui o view.setOnClickListener

        RouteDataViewHolder routeDataViewHolder = new RouteDataViewHolder(view, this);

        return routeDataViewHolder;
    }

    @Override
    public void onBindViewHolder(RouteDataViewHolder routeData, int position) {
        // Any time there is an object/view on screen that needs to be updated.
        // So here we download the images that are on the screen

        // Position is the index of the element in the view that is being drawn in the screen
        // at that time
        RoutePrice routePrice = mRouteDataResults.get(position);

        final String companyName = routePrice.getCompanyName();
        final int durationInMinutes = Utils.convertSecondsToMinutes(routePrice.getDuration());

        // Set the fields
        routeData.duration.setText("Trip duration: " + durationInMinutes + " minutes");

        // Is it a Uber Price?
        if(routePrice instanceof UberRoutePrice) {
            UberRoutePrice uberRoutePrice = (UberRoutePrice) routePrice;

            if(uberRoutePrice.isMetricSystem()) {
                routeData.distance.setText("Distance: " + uberRoutePrice.getDistance() + " km");
            }
            else {
                routeData.distance.setText("Distance: " + uberRoutePrice.getDistance() + " mi");
            }

            final String currency = uberRoutePrice.getCurrency();
            routeData.serviceType.setText(uberRoutePrice.getDisplayName());

            routeData.price.setText(currency + " " + uberRoutePrice.getLowerEstimate() + "-" + uberRoutePrice.getHigherEstimate());
            routeData.thumbnail.setImageResource(R.drawable.uber_logo_black_background);
        }
        // Is it a Taxi Fare Finder Price?
        else if(routePrice instanceof TaxiFareFinderRoutePrice) {
            TaxiFareFinderRoutePrice taxiFareFinderRoutePrice = (TaxiFareFinderRoutePrice) routePrice;

            if(taxiFareFinderRoutePrice.isMetricSystem()) {
                routeData.distance.setText("Distance: " + taxiFareFinderRoutePrice.getDistance() + " km");
            }
            else {
                routeData.distance.setText("Distance: " + taxiFareFinderRoutePrice.getDistance() + " mi");
            }

            final String currency = taxiFareFinderRoutePrice.getCurrency();
            routeData.serviceType.setText(taxiFareFinderRoutePrice.getCompanyName());

            routeData.price.setText(currency + taxiFareFinderRoutePrice.getPrice());
            routeData.thumbnail.setImageResource(R.drawable.taxi_fare_finder_logo);
        }
        // Something is wrong if we end up here!
        else {
            routeData.thumbnail.setImageResource(R.drawable.placeholder);
        }
    }

    @Override
    public int getItemCount() {
        if(mRouteDataResults == null) {
            return 0;
        }
        else {
            return mRouteDataResults.size();
        }
    }

    @Override
    public void itemPosition(int position, View sharedView) {
        final RoutePrice routeDataResult = mRouteDataResults.get(position);
        if(mResultsRecyclerViewAdapterCommunicator != null) {
            mResultsRecyclerViewAdapterCommunicator.itemClicked(routeDataResult, sharedView);
        }
    }

    public void setResultsRecyclerViewAdapterCommunicator(ResultsRecyclerViewAdapterCommunicator resultsRecyclerViewAdapterCommunicator) {
        mResultsRecyclerViewAdapterCommunicator = resultsRecyclerViewAdapterCommunicator;
    }

    public void orderListByPrice(){
        Collections.sort(mRouteDataResults, new Comparator<RoutePrice>() {
            @Override
            public int compare(RoutePrice rp1, RoutePrice rp2) {
                if (rp1.getPrice() < rp2.getPrice()) return -1;
                if (rp1.getPrice() > rp2.getPrice()) return 1;
                return 0;
            }
        });
    }

    public void orderListById() {
        Collections.sort(mRouteDataResults, new Comparator<RoutePrice>() {
            @Override
            public int compare(RoutePrice rp1, RoutePrice rp2) {
                if(rp1.getId() < rp2.getId()) return -1;
                if(rp1.getId() > rp2.getId()) return 1;
                return 0;
            }
        });
    }

    public void loadNewData(List<RoutePrice> routeDataResults) {
        mRouteDataResults = routeDataResults;
        notifyDataSetChanged();
    }

    public void addItem(RoutePrice routePrice) {
        mRouteDataResults.add(routePrice);
    }

    public RoutePrice getRouteDataResult(int position) {
        if(mRouteDataResults != null) {
            return mRouteDataResults.get(position);
        }
        return null;
    }

    public List<RoutePrice> getRoutePrices() {
        return mRouteDataResults;
    }

    public static interface ResultsRecyclerViewAdapterCommunicator {
        public void itemClicked(RoutePrice routeDataResult, View sharedView);
    }
}
