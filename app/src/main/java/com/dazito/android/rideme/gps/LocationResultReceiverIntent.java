package com.dazito.android.rideme.gps;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import com.dazito.android.rideme.Constants;
import com.dazito.android.rideme.RideMeEvents;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;

/**
 * Created by Pedro on 22-01-2015.
 */
public class LocationResultReceiverIntent extends ResultReceiver{

    private static final String TAG = LocationResultReceiverIntent.class.getSimpleName();

    private static final String ADDRESS_ERROR_MESSAGE = "Failed to retrieve the user location.";

    private static final String COORDINATES_ERROR_MESSAGE = "Failed to retrieve the GPS coordinates for the given address";

    private static final String ONLY_ONE_COORDINATE_PROCESSED = "ONLY_ONE_COORDINATE_PROCESSED";
    private static final String START_LOCATION_UNKNOWN = "Couldn't find the inserted origin location.";
    private static final String END_LOCATION_UNKNOWN = "Couldn't find the inserted destination location.";

    /**
     * Receiver for data sent from FetchAddressIntentService
     *
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     *
     * @param handler
     */
    public LocationResultReceiverIntent(Handler handler) {
        super(handler);
    }

    /**
     * Receives data sent from AddressResultReceiverIntent
     * @param resultCode
     * @param resultData
     */
    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        Log.d(TAG, "ResultReceiver Thread: " + Thread.currentThread().getName() + " ID: " + Thread.currentThread().getId());

        if(resultCode == Constants.COORDINATES_TO_ADDRESS_SUCCESS_RESULT)
        {
            ArrayList<AddressPOJO> address = resultData.getParcelableArrayList(Constants.ADDRESS_RESULT_DATA_KEY);
            EventBus.getDefault().post(new RideMeEvents.GeolocationAddressFetchedSuccess(address));
        }
        else if(resultCode == Constants.COORDINATES_TO_ADDRESS_FAILURE_RESULT)
        {
            EventBus.getDefault().post(new RideMeEvents.GeolocationAddressFetchedError(resultCode, ADDRESS_ERROR_MESSAGE));
        }
        else if(resultCode == Constants.ADDRESS_TO_COORDINATES_SUCCESS_RESULT)
        {
            final ArrayList<CoordinatesPOJO> resultCoordinates = resultData.getParcelableArrayList(Constants.COORDINATES_RESULT_DATA_KEY);

            if(resultCoordinates.size() == 2)
            {
                for(CoordinatesPOJO coordinatesPOJO : resultCoordinates) {
                    final String latitude = coordinatesPOJO.getLat();
                    final String longitude = coordinatesPOJO.getLng();

                    if(latitude == null && longitude == null) {
                        boolean isStartLocation = coordinatesPOJO.isStartLocation();

                        if(isStartLocation) {
                            EventBus.getDefault().post(new RideMeEvents.GeolocationCoordinatesFetchedError(resultCode, START_LOCATION_UNKNOWN));
                            Log.d(TAG, "NULL - Start Location");
                            return;
                        }
                        else {
                            EventBus.getDefault().post(new RideMeEvents.GeolocationCoordinatesFetchedError(resultCode, END_LOCATION_UNKNOWN));
                            Log.d(TAG, "NULL - End Location");
                            return;
                        }
                    }

                    Log.d(TAG, "Coordinates received from intent - Lat: " + latitude + " Long: " + longitude);
                }

                EventBus.getDefault().post(new RideMeEvents.GeolocationCoordinatesFetchedSuccess(resultCoordinates));
            }
            else
            {
                if(resultCoordinates.size() == 1) {
                    Log.d(TAG, "Only one address parsed. Is it startLocation? " + resultCoordinates.get(0).isStartLocation());
                }
                EventBus.getDefault().post(new RideMeEvents.GeolocationCoordinatesFetchedError(resultCode, ONLY_ONE_COORDINATE_PROCESSED));
            }

        }
        else if(resultCode == Constants.ADDRESS_TO_COORDINATES_FAILURE_RESULT)
        {
            EventBus.getDefault().post(new RideMeEvents.GeolocationCoordinatesFetchedError(resultCode, COORDINATES_ERROR_MESSAGE));
        }
    }
}
