package com.dazito.android.rideme.network;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.util.Log;

import com.dazito.android.rideme.Constants;
import com.dazito.android.rideme.RideMeEvents;
import com.dazito.android.rideme.gps.CoordinatesPOJO;
import com.dazito.android.rideme.webservices.gmaps.GMaps;
import com.dazito.android.rideme.webservices.gmaps.model.nearbylocation.NearbyLocation;
import com.dazito.android.rideme.webservices.gmaps.model.nearbylocation.Results;
import com.dazito.android.rideme.webservices.gmaps.model.referencedata.PlaceDetails;
import com.dazito.android.rideme.webservices.hailo.Hailo;
import com.dazito.android.rideme.webservices.hailo.model.DriversLocation;
import com.dazito.android.rideme.webservices.hailo.model.EstimatedTimesOfArrivals;
import com.dazito.android.rideme.webservices.taxifarefinder.TaxiFareFinder;
import com.dazito.android.rideme.webservices.taxifarefinder.model.Businesses;
import com.dazito.android.rideme.webservices.taxifarefinder.model.Entity;
import com.dazito.android.rideme.webservices.taxifarefinder.model.Fare;
import com.dazito.android.rideme.webservices.uber.Uber;
import com.dazito.android.rideme.webservices.uber.model.Prices;
import com.dazito.android.rideme.webservices.uber.model.Products;
import com.dazito.android.rideme.webservices.uber.model.TimeEstimates;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by Pedro on 03-02-2015.
 */
public class NetworkIntentService extends IntentService {

    private final static String TAG = NetworkIntentService.class.getSimpleName();


    public NetworkIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final ResultReceiver resultReceiver = intent.getParcelableExtra(Constants.NETWORK_RESULT_RECEIVER);
        final int operationType = intent.getIntExtra(Constants.NETWORK_OPERATION_TYPE, -1);


        if(resultReceiver == null) {
            Log.wtf(TAG, "mResultReceiver == null");
            return;
        }

        CoordinatesPOJO startLocation = intent.getParcelableExtra(Constants.START_LOCATION);
        CoordinatesPOJO endLocation = intent.getParcelableExtra(Constants.END_LOCATION);
        final int radius = startLocation.getRadius();

        // For nearby location we only need the start location

        if(operationType == Constants.GET_PLACE_DETAILS
                || operationType == Constants.UBER_GET_PRODUCTS
                || operationType == Constants.UBER_GET_TIME_ESTIMATE) {
            if(startLocation == null) {
                Log.wtf(TAG, "StartLocation is null");
                return;
            }
        }
        else { // For all the other operations we need both start and end location
            if(startLocation == null || endLocation == null) {
                Log.wtf(TAG, "StartLocation or EndLocation is null.");
                return;
            }
        }

        if(operationType == -1) {
            Log.wtf(TAG, "Unknown operation type.");
            return;
        }

        Entity entity;

        switch (operationType) {
            case Constants.UBER_GET_PRICE_EESTIMATES:
                Log.d(TAG, "UBER_GET_PRICE_EESTIMATES");
                Prices prices = uberGetPriceEstimates(startLocation, endLocation);
                deliverResultsToReceiver(prices, resultReceiver, Constants.UBER_GET_PRICE_EESTIMATES);
                break;

            case Constants.UBER_GET_PRODUCTS:
                Log.d(TAG, "UBER_GET_PRODUCTS");
                Products products = uberGetProducts(startLocation);
                deliverResultsToReceiver(products, resultReceiver, Constants.UBER_GET_PRODUCTS);
                break;

            case Constants.UBER_GET_TIME_ESTIMATE:
                Log.d(TAG, "UBER_GET_TIME_ESTIMATE");
                TimeEstimates timeEstimates = uberGetTimeEstimates(startLocation);
                deliverResultsToReceiver(timeEstimates, resultReceiver, Constants.UBER_GET_TIME_ESTIMATE);
                break;

            case Constants.HAILO_GET_NEARBY_DRIVERS:
                Log.d(TAG, "HAILO_GET_NEARBY_DRIVERS");
                DriversLocation driversLocation = hailoGetNearbyDrivers(startLocation);
                deliverResultsToReceiver(driversLocation, resultReceiver, Constants.HAILO_GET_NEARBY_DRIVERS);
                break;

            case Constants.HAILO_GET_ESTIMATED_TIME_OF_ARRIVAL:
                Log.d(TAG, "HAILO_GET_ESTIMATED_TIME_OF_ARRIVAL");
                EstimatedTimesOfArrivals estimatedTimesOfArrivals = hailoGetEstimatedTimeOfArrivel(startLocation);
                deliverResultsToReceiver(estimatedTimesOfArrivals, resultReceiver, Constants.HAILO_GET_ESTIMATED_TIME_OF_ARRIVAL);
                break;

            case Constants.TFF_GET_FARES:
                entity = tffGetEntity(startLocation);
                if(entity != null) {
                    Fare fare = tffGetFare(entity.handle, startLocation, endLocation);
                    deliverResultsToReceiver(fare, resultReceiver, Constants.TFF_GET_FARES);
                }
                break;

            case Constants.TFF_GET_BUSINESS:
                Log.d(TAG, "TFF_GET_BUSINESS");
                entity = tffGetEntity(startLocation);
                if(entity != null) {
                    Businesses business = tffGetBusiness(entity.handle);
                    deliverResultsToReceiver(business, resultReceiver, Constants.TFF_GET_BUSINESS);
                }
                break;

            case Constants.TFF_GET_ENTITY:
                Log.d(TAG, "TFF_GET_ENTITY");
                entity = tffGetEntity(startLocation); // 2º entra aqui exception
                deliverResultsToReceiver(entity, resultReceiver, Constants.TFF_GET_ENTITY);
                break;

            case Constants.GET_PLACE_DETAILS:// acabar de implementar os throws exceptions...
                Log.d(TAG, "GET_PLACE_DETAILS");
                NearbyLocation nearbyLocation = nearbyLocation(startLocation, radius, Constants.TAXI_STAND_TYPE, false);
                ArrayList<PlaceDetails> placeDetailsList = getPlaceDetails(nearbyLocation);
                deliverResultsToReceiver(placeDetailsList, resultReceiver, Constants.GET_PLACE_DETAILS);

            default:
                Log.d(TAG, "default");
                return;
        }
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        EventBus.getDefault().post(new RideMeEvents.GetRoutePricesFinished());
        super.onDestroy();
    }

    /**
     * Uber
     */

    private Products uberGetProducts(CoordinatesPOJO location) {
        final Uber uber = new Uber();

        Products products = null;

        try {
            products = uber.getProducts(location.getLat(), location.getLng());
        } catch (Exception e) {

        }
        return products;
    }

    private Prices uberGetPriceEstimates(CoordinatesPOJO startLocation, CoordinatesPOJO endLocation) {
        Log.d(TAG, "uberGetPriceEstimates");
        final Uber uber = new Uber();

        final String startLatitude = startLocation.getLat();
        final String startLongitude = startLocation.getLng();

        final String endLatitude = endLocation.getLat();
        final String endLongitude = endLocation.getLng();

        Prices prices = null;
        try {
            prices = uber.getPriceEstimates(startLatitude, startLongitude, endLatitude, endLongitude);
        } catch (Exception e) {
//            EventBus.getDefault().post(new RideMeEvents.UberPricesError(e.getMessage()));
            // It will return null and will be handled on the ResultReceiver
        }
        return prices;
    }

    private TimeEstimates uberGetTimeEstimates(CoordinatesPOJO startLocation) {
        final Uber uber = new Uber();

        final String startLatitude = startLocation.getLat();
        final String startLongitude = startLocation.getLng();

        TimeEstimates timeEstimates = null;

        try {
            timeEstimates = uber.getTimeEstimate(startLatitude, startLongitude);
        } catch (Exception e) { }

        return timeEstimates;
    }

    /**
     * Hailo
     */
    private DriversLocation hailoGetNearbyDrivers(CoordinatesPOJO location) {
        final Hailo hailo = new Hailo();

        DriversLocation driversLocation = null;

        try {
            driversLocation = hailo.getNearbyDrivers(location.getLat(), location.getLng());
        } catch (Exception e) {
            // It will return null and will be handled on the ResultReceiver
        }
        return driversLocation;
    }

    private EstimatedTimesOfArrivals hailoGetEstimatedTimeOfArrivel(CoordinatesPOJO location) {
        final Hailo hailo = new Hailo();

        EstimatedTimesOfArrivals estimatedTimesOfArrivals = null;

        try {
            estimatedTimesOfArrivals = hailo.getEstimatedTimeOfArrival(location.getLat(), location.getLng());
        } catch (Exception e) {
            // It will return null and will be handled on the ResultReceiver
        }
        return estimatedTimesOfArrivals;
    }

    /**
     * Taxi Fare Finder
     */
    private Fare tffGetFare(String entityHandle, CoordinatesPOJO startLocation, CoordinatesPOJO endLocation) {
        final TaxiFareFinder taxiFareFinder = new TaxiFareFinder();

        final String startLatitude = startLocation.getLat();
        final String startLongitude = startLocation.getLng();
        final String endLatitude = endLocation.getLat();
        final String endLongitude = endLocation.getLng();

        Fare fare = null;

        try {
            fare = taxiFareFinder.getFare(entityHandle, startLatitude, startLongitude, endLatitude, endLongitude);
        } catch (Exception e) {
            // It will return null and will be handled on the ResultReceiver
        }
        return fare;
    }

    private Businesses tffGetBusiness(String entityHandle) {
        final TaxiFareFinder taxiFareFinder = new TaxiFareFinder();
        Businesses businesses = null;

        try {
            businesses = taxiFareFinder.getBusinesses(entityHandle);
        } catch (Exception e) {
            // It will return null and will be handled on the ResultReceiver
        }
        return businesses;
    }

    private Entity tffGetEntity(CoordinatesPOJO location)  {
        TaxiFareFinder taxiFareFinder = new TaxiFareFinder();
        Entity entity = null;

        try {
            entity = taxiFareFinder.getEntity(location.getLat(), location.getLng()); // 1º entra aqui a exception
        } catch (Exception e) {
            // It will return null and will be handled on the ResultReceiver
        }
        return entity;
    }

    /**
     * Nearby Location
     */
    private NearbyLocation nearbyLocation(CoordinatesPOJO location, int radius, String types, boolean sensor) {
        final GMaps gMaps = new GMaps();
        final String latitude = location.getLat();
        final String longitude = location.getLng();

        NearbyLocation nearbyLocation = null;

        try {
            nearbyLocation = gMaps.getNearbySearch(latitude, longitude, radius, types, sensor);
        } catch (Exception e) {
            // It will return null and will be handled on the ResultReceiver
        }
        return nearbyLocation;
    }

    /**
     * Get Place Details
     */

    private ArrayList<PlaceDetails> getPlaceDetails(NearbyLocation nearbyLocation) {
        final ArrayList<Results> results = nearbyLocation.results;
        ArrayList<PlaceDetails> placeDetailsList = new ArrayList<>();
        final GMaps gMaps = new GMaps();
        try {
            for (Results result : results) {
                final String reference = result.reference;
                PlaceDetails placeDetails = null;

                placeDetails = gMaps.getPlaceDetails(false, reference);

                placeDetailsList.add(placeDetails);
            }
        } catch (Exception e) {
            // It will return null and will be handled on the ResultReceiver
        }
        return placeDetailsList;
    }

    private void deliverResultsToReceiver(Parcelable parcelable, ResultReceiver mResultReceiver, int flagResultType) {
        Log.d(TAG, "deliverResultToReceiver");
        Bundle bundle = new Bundle();
        bundle.putParcelable(Constants.NETWORK_OPERATION_DATA, parcelable);
        mResultReceiver.send(flagResultType, bundle);
    }

    private void deliverResultsToReceiver(ArrayList parcelable, ResultReceiver mResultReceiver, int flagResultType) {
        Log.d(TAG, "deliverResultToReceiver");
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.NETWORK_OPERATION_DATA, parcelable);
        mResultReceiver.send(flagResultType, bundle);
    }


}
