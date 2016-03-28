package com.dazito.android.rideme.gps;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.ResultReceiver;
import android.util.Log;

import com.dazito.android.rideme.Constants;

import java.util.ArrayList;

/**
 * Created by Pedro on 29-01-2015.
 */
public class CoordinatesConverter {

    private static final String TAG = CoordinatesConverter.class.getSimpleName();

    private final Context mContext;
    private ResultReceiver mResultReceiver;

    public CoordinatesConverter(Context context) {
        mContext = context;
        mResultReceiver = new LocationResultReceiverIntent(new Handler());
    }

    public void getCoordinatesFromAddress(ArrayList<String> addressList) {
        // Create an intent for passing to the intent service responsible for fetching the coordinates.
        Intent intent = new Intent(mContext, FetchLocationIntentService.class);

        // Pass the result receiver as an extra to the service
        // TODO: Create a receiver to handle this type of intents
        intent.putExtra(Constants.LOCATION_RESULT_RECEIVER_INTENT, mResultReceiver);

        // Pass the addressList as an extra to the service
        intent.putExtra(Constants.ADDRESS_DATA_EXTRA, addressList);
        intent.putExtra(Constants.GEOCODING_OPERATION_TYPE, Constants.ADDRESS_TO_COORDENATES);

        // Start the service. If the service isn't already running, it is instantiated and started
        // (creating a process for it if needed); if it is running then it remains running. The
        // service kills itself automatically once all intents are processed.
        mContext.startService(intent);
        Log.d(TAG, "CoordinatesConverter - IntentService Started.");
    }
}
