package com.dazito.android.rideme.gps;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.dazito.android.rideme.Constants;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

/**
 * Created by Pedro on 02-03-2015.
 */
public class GpsTracker implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private static final String TAG = GpsTracker.class.getSimpleName();

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private final Context mContext;
    private LocationResultReceiverIntent mResultReceiver;
    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    // boolean flag for the google play api, present or not present
    private boolean mIsGooglePlayPresent = false;

    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FASTEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 10; // 10 meters

    public GpsTracker(Context context) {
        mContext = context;
        mResultReceiver = new LocationResultReceiverIntent(new Handler());
    }


    public void onStart() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    public void onResume() {
        mIsGooglePlayPresent = checkPlayServices();
        if(mIsGooglePlayPresent == false){
            return;
        }

        // Resuming the periodic location updates
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    public void onStop() {
        if(mIsGooglePlayPresent) {
            if (mGoogleApiClient.isConnected()) {
                mGoogleApiClient.disconnect();
            }
        }
    }

    public void onPause() {
        stopLocationUpdates();
    }

    /**
     * Method to display the location on UI
     * */
    public void displayLocation() {
        if(mIsGooglePlayPresent) {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);

            if (mLastLocation != null) {
                double latitude = mLastLocation.getLatitude();
                double longitude = mLastLocation.getLongitude();
                Log.d(TAG, "Latitude: " + latitude + ", Longitude: " + longitude);

                startFetchAddressFromCoordinatesIntentService(mLastLocation, Constants.COORDINATES_TO_ADDRESS);
            } else {
                Toast.makeText(mContext, "Couldn't get your current location. Make sure location is enabled on the device", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void startFetchAddressFromCoordinatesIntentService(Location location, int geolocationType) {
        // Create an intent for passing to the intent service responsible for fetching the address.
        Intent intent = new Intent(mContext, FetchLocationIntentService.class);

        // Pass the result receiver as an extra to the service
        intent.putExtra(Constants.LOCATION_RESULT_RECEIVER_INTENT, mResultReceiver);

        // Pass the location data as an extra to the service
        intent.putExtra(Constants.LOCATION_DATA_EXTRA, location);

        if (geolocationType == Constants.COORDINATES_TO_ADDRESS) {
            intent.putExtra(Constants.GEOCODING_OPERATION_TYPE, Constants.COORDINATES_TO_ADDRESS);
        }
        else {
            // Wrong value for geolocationType, return and do nothing.
            Log.wtf(TAG, "startFetchAddressIntentService - Wrong value for geolocationType, return and do nothing.");
            return;
        }

        // Start the service. If the service isn't already running, it is instantiated and started
        // (creating a process for it if needed); if it is running then it remains running. The
        // service kills itself automatically once all intents are processed.
        mContext.startService(intent);
        Log.d(TAG, "IntentService Started.");
    }

    /**
     * Method to toggle periodic location updates
     * */
    public void togglePeriodicLocationUpdates() {
        if (!mRequestingLocationUpdates) {

            mRequestingLocationUpdates = true;

            // Starting the location updates
            startLocationUpdates();

            Log.d(TAG, "Periodic location updates started!");

        }
        else {
            mRequestingLocationUpdates = false;

            // Stopping the location updates
            stopLocationUpdates();

            Log.d(TAG, "Periodic location updates stopped!");
        }
    }

    /**
     * Creating google api client object
     * */
    public synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(mContext)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Creating location request object
     * */
    public void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    /**
     * Method to verify google play services on the device
     * */
    public boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(mContext);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, (Activity) mContext, PLAY_SERVICES_RESOLUTION_REQUEST).show();
                Log.d(TAG, "GooglePlayServicesUtil.isUserRecoverableError(resultCode)");
            }
            else {
                Toast.makeText(mContext, "This device is not supported.", Toast.LENGTH_LONG).show();
                return false;
            }
            return false;
        }
        return true;
    }

    /**
     * Starting the location updates
     * */
    protected void startLocationUpdates() {
        if(mGoogleApiClient == null) {
            Log.d(TAG, "startLocationUpdates - mGoogleApiClient is null!");
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        if(mIsGooglePlayPresent) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    /**
     * Google api callback methods
     */
    @Override
    public void onConnectionFailed(ConnectionResult result) {
        Log.i(TAG, "Connection failed: ConnectionResult.getErrorCode() = " + result.getErrorCode());
    }

    @Override
    public void onConnected(Bundle arg0) {

        // Once connected with google api, get the location
//        displayLocation();

        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onConnectionSuspended(int arg0) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onLocationChanged(Location location) {
        // Assign the new location
        mLastLocation = location;

        Toast.makeText(mContext, "Location changed!", Toast.LENGTH_SHORT).show();

        // Displaying the new location on UI
//        displayLocation();
    }

}
