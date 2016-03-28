package com.dazito.android.rideme.webservices.gmaps;

import android.util.Log;

import com.dazito.android.rideme.webservices.gmaps.model.directions.Leg;
import com.dazito.android.rideme.webservices.gmaps.model.directions.Route;
import com.dazito.android.rideme.webservices.gmaps.model.directions.RouteResponse;
import com.dazito.android.rideme.webservices.gmaps.model.nearbylocation.NearbyLocation;
import com.dazito.android.rideme.webservices.gmaps.model.referencedata.PlaceDetails;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

//import com.dazito.android.rideme.webservices.gmaps.model.directions.RouteResponse.Route;

/**
 * Created by Pedro on 10-02-2015.
 */
public class GMaps {

    private static final String TAG = GMaps.class.getSimpleName();

    private static final String API_KEY = "YOUR_API_KEY_HERE";

    private final GMapsRestClient mGMapsRestClient;

    public GMaps() {
        mGMapsRestClient = new GMapsRestClient();
    }

    public GMapsRestClient getGMapsRestClient() {
        return mGMapsRestClient;
    }

    public void getDrivingDataAsync(String origin, String destination, boolean sensor) {
        mGMapsRestClient.getGMapsService().getDrivingData(origin, destination, sensor, new Callback<RouteResponse>() {
            @Override
            public void success(RouteResponse routeResponse, Response response) {
                List<Route> routes = routeResponse.routes;

                for (Route route : routes) {
                    List<Leg> legs = route.legs;

                    for (Leg leg : legs) {
                        Log.d(TAG, "Distance: " + leg.distance.text + " Duration: " + leg.duration.text);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "getDrivingDataAsync - Failure: " + error.getMessage());
            }
        });
    }

    public RouteResponse getDrivingData(String origin, String destination, boolean sensor) throws Exception {
        return mGMapsRestClient.getGMapsService().getDrivingData(origin, destination, sensor);
    }

    public void getDrivingDataBetweenTwoGPSCoordinatesAsync(String originLat, String originLong, String destinationLat, String destinationLong, boolean sensor) throws Exception {
        final String origin = originLat + "," + originLong;
        final String destination = destinationLat + "," + destinationLong;

        mGMapsRestClient.getGMapsService().getDrivingDataBetweenTwoGPSCoordinates(origin, destination, false, new Callback<RouteResponse>() {
            @Override
            public void success(RouteResponse routeResponse, Response response) {
                List<Route> routes = routeResponse.routes;

                for (Route route : routes) {
                    List<Leg> legs = route.legs;

                    for (Leg leg : legs) {
                        Log.d(TAG, "Distance: " + leg.distance.text + " Duration: " + leg.duration.text);
                    }
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "getDrivingDataBetweenTwoGPSCoordinates - Failure: " + error.getMessage());
            }
        });
    }

    public RouteResponse getDrivingDataBetweenTwoGPSCoordinates(String originLat, String originLong, String destinationLat, String destinationLong, boolean sensor) throws Exception {
        final String origin = originLat + "," + originLong;
        final String destination = destinationLat + "," + destinationLong;
        return mGMapsRestClient.getGMapsService().getDrivingDistanceBetweenTwoGPSCoordinates(origin, destination, sensor);
    }

    public void getReferencePhoneAsync(boolean sensor, String reference){
        mGMapsRestClient.getGMapsService().getPlaceDetails(API_KEY, sensor, reference, new Callback<PlaceDetails>() {
            @Override
            public void success(PlaceDetails placeDetails, Response response) {
                Log.d(TAG, "getPlaceDetailsAsync - Reference phone: " + placeDetails.result.formatted_phone_number);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Error: getPlaceDetails - " + error.getMessage());
            }
        });
    }

    public PlaceDetails getPlaceDetails(boolean sensor, String reference) throws Exception {
        return mGMapsRestClient.getGMapsService().getPlaceDetails(API_KEY, sensor, reference);
    }

    public void getPlaceDetailsAsync(String latitude, String longitude, int radius, String types, boolean sensor) {
        final String latLongCoordinates = latitude + "," + longitude;
        mGMapsRestClient.getGMapsService().getPlaceDetails(API_KEY, latLongCoordinates, radius, types, sensor, new Callback<NearbyLocation>() {
            @Override
            public void success(NearbyLocation nearbyLocation, Response response) {
                Log.d(TAG, "getPlaceDetailsAsync - NearbyLocations: " + nearbyLocation.results.size() + " Status: " + nearbyLocation.status);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Error: getPlaceDetailsAsync - " + error.getMessage());
            }
        });
    }

    public NearbyLocation getNearbySearch(String latitude, String longitude, int radius, String types, boolean sensor) throws Exception {
        final String latLongCoordinates = latitude + "," + longitude;
        return mGMapsRestClient.getGMapsService().getNearbySearch(API_KEY, latLongCoordinates, radius, types, sensor);
    }
}
