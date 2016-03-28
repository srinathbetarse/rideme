package com.dazito.android.rideme.network;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import com.dazito.android.rideme.Constants;
import com.dazito.android.rideme.RideMeEvents;
import com.dazito.android.rideme.webservices.gmaps.model.referencedata.PlaceDetails;
import com.dazito.android.rideme.webservices.taxifarefinder.model.Businesses;
import com.dazito.android.rideme.webservices.taxifarefinder.model.Entity;
import com.dazito.android.rideme.webservices.taxifarefinder.model.Fare;
import com.dazito.android.rideme.webservices.uber.model.Price;
import com.dazito.android.rideme.webservices.uber.model.Prices;
import com.dazito.android.rideme.webservices.uber.model.Products;
import com.dazito.android.rideme.webservices.uber.model.TimeEstimates;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by Pedro on 03-02-2015.
 */
public class NetworkResultReceiver extends ResultReceiver {

    private final static String TAG = NetworkResultReceiver.class.getSimpleName();


    /**
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     *
     * @param handler
     */
    public NetworkResultReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        Log.d(TAG, "onReceiveResult");

        switch (resultCode) {
            case Constants.UBER_GET_PRICE_EESTIMATES:
                Log.d(TAG, "UBER_GET_PRICE_EESTIMATES");
                receivedUberPriceEstimate(resultData);
                break;

            case Constants.UBER_GET_PRODUCTS:
                Log.d(TAG, "UBER_GET_PRODUCTS");
                receivedUberProducts(resultData);
                break;

            case Constants.UBER_GET_TIME_ESTIMATE:
                Log.d(TAG, "UBER_GET_TIME_ESTIMATE");
                receivedUberTimeEstimates(resultData);

            case Constants.HAILO_GET_NEARBY_DRIVERS:
                Log.d(TAG, "HAILO_GET_NEARBY_DRIVERS"); // TODO
                break;

            case Constants.HAILO_GET_ESTIMATED_TIME_OF_ARRIVAL:
                Log.d(TAG, "HAILO_GET_ESTIMATED_TIME_OF_ARRIVAL"); // TODO
                break;

            case Constants.TFF_GET_FARES:
                Log.d(TAG, "TFF_GET_FARES");
                receivedTffFares(resultData);
                break;

            case Constants.TFF_GET_BUSINESS:
                Log.d(TAG, "TFF_GET_BUSINESS");
                receivedTffBusinesses(resultData);
                break;

            case Constants.TFF_GET_ENTITY:
                Log.d(TAG, "TFF_GET_ENTITY");
                receivedTffEntity(resultData);
                break;
            
            case Constants.GET_PLACE_DETAILS:
                Log.d(TAG, "GET_PLACE_DETAILS");
                receivedNearbyLocation(resultData);
                break;

            default:
                return;
        }
    }



    /**
     * Uber
     */
    private void receivedUberPriceEstimate(Bundle resultData) {
        Prices prices = resultData.getParcelable(Constants.NETWORK_OPERATION_DATA);

        if(prices != null) {
            ArrayList<Price> priceList = prices.prices;

            for(Price price : priceList) {
                Log.d(TAG, "Uber Estimated prices - " + price.display_name + ": <" + price.low_estimate + "," + price.high_estimate + "> Estimated: " + price.estimate + " " + price.currency_code);
            }

            EventBus.getDefault().post(new RideMeEvents.UberPricesSuccess(prices));
        }
        else {
            EventBus.getDefault().post(new RideMeEvents.UberPricesError("Couldn't access Uber prices."));
        }
    }



    private void receivedUberProducts(Bundle resultData){
        Products products = resultData.getParcelable(Constants.NETWORK_OPERATION_DATA);

        if(products != null) {
            EventBus.getDefault().post(new RideMeEvents.UberProductsSuccess(products));
        }
        else {
            EventBus.getDefault().post(new RideMeEvents.UberProductsError("Couldn't retrieve Uber's products"));
        }
    }

    private void receivedUberTimeEstimates(Bundle resultData) {
        TimeEstimates timeEstimates = resultData.getParcelable(Constants.NETWORK_OPERATION_DATA);

        if(timeEstimates != null) {
            EventBus.getDefault().post(new RideMeEvents.UberTimeEstimatesSuccess(timeEstimates));
        }
        else {
            EventBus.getDefault().post(new RideMeEvents.UberTimeEstimatesError("Couldn't retrieve Uber's time estimates"));
        }
    }


    /**
     * Taxi Fare Finder
     */

    private void receivedTffFares(Bundle resultData) {
        Fare fare = resultData.getParcelable(Constants.NETWORK_OPERATION_DATA);

        if(fare != null) {
            Log.d(TAG, "TFF Fare - Fare total: " + fare.total_fare + " " + fare.currency.int_symbol);
            EventBus.getDefault().post(new RideMeEvents.TFFFareSuccess(fare));
        }
        else {
            EventBus.getDefault().post(new RideMeEvents.TFFFareError("Couldn't retrieve TaxiFareFinder's fare."));
        }
    }

    private void receivedTffEntity(Bundle resultData) {
        Entity entity = resultData.getParcelable(Constants.NETWORK_OPERATION_DATA);

        if(entity != null) {
            EventBus.getDefault().post(new RideMeEvents.TFFEntitySuccess(entity));
        }
        else {
            EventBus.getDefault().post(new RideMeEvents.TFFEntityError("Couldn't retrieve TaxiFareFinder's entity."));
        }
    }

    private void receivedTffBusinesses(Bundle resultData) {
        Businesses business = resultData.getParcelable(Constants.NETWORK_OPERATION_DATA);

        if(business != null) {
            EventBus.getDefault().post(new RideMeEvents.TFFBusinessesSuccess(business));
        }
        else {
            EventBus.getDefault().post(new RideMeEvents.TFFBusinessesError("Couldn't retrieve TaxiFareFinder's business list"));
        }
    }

    /**
     * Nearby Location
     */

    private void receivedNearbyLocation(Bundle resultData) {
        ArrayList<PlaceDetails> placeDetailsList = resultData.getParcelableArrayList(Constants.NETWORK_OPERATION_DATA);

        if(placeDetailsList.size() != 0) {
            EventBus.getDefault().post(new RideMeEvents.PlaceDetailsSuccess(placeDetailsList));
        }
        else {
            EventBus.getDefault().post(new RideMeEvents.PlaceDetailsError("Couldn't find any taxi stand nearby"));
        }
    }

}
