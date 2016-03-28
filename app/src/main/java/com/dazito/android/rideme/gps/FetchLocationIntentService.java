package com.dazito.android.rideme.gps;

import android.app.IntentService;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import com.dazito.android.rideme.Constants;
import com.dazito.android.rideme.R;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by Pedro on 22-01-2015.
 *
 * Fetches the address for a given set of coordinates.
 */
public class FetchLocationIntentService extends IntentService{

    private static final String TAG = FetchLocationIntentService.class.getSimpleName();

    private static final int MAX_ADDRESSES_TO_LOOK_FOR = 1;

    /**
     * The receiver where results are forwarded from this service.
     */

    public FetchLocationIntentService() {
        super(TAG);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        ResultReceiver mResultReceiver = intent.getParcelableExtra(Constants.LOCATION_RESULT_RECEIVER_INTENT);

        if(mResultReceiver == null) {
            Log.d(TAG, "mResultReceiver == null");
            return;
        }
        final int geocodingOperationType = intent.getIntExtra(Constants.GEOCODING_OPERATION_TYPE, -1);

        if(geocodingOperationType == -1) {
            Log.wtf(TAG, "geocodingOperationType == -1");
            // bad extra passed in to the received intent
            return;
        }

        if(geocodingOperationType == Constants.COORDINATES_TO_ADDRESS) {
            parseCoordinatesToAddress(intent, mResultReceiver);
        }
        else if(geocodingOperationType == Constants.ADDRESS_TO_COORDENATES) {
            parseAddressToCoordinates(intent, mResultReceiver);
        }

    }




    // Parse Coordinates to an Address
    private void parseCoordinatesToAddress(Intent intent, ResultReceiver mResultReceiver) {
        String errorMessage = "";

        // Get the location passed to this service through an extra.
        Location location = intent.getParcelableExtra(Constants.LOCATION_DATA_EXTRA);

        // Make sure that the location data was really sent over through an extra. If it wasn't,
        // send an error error message and return.
        if (location == null) {
            errorMessage = getString(R.string.locationWasNull);
            Log.d(TAG, errorMessage);
            deliverAddressToReceiver(Constants.COORDINATES_TO_ADDRESS_FAILURE_RESULT, null, mResultReceiver);
            return;
        }

        // Errors could still arise from using the Geocoder (for example, if there is no
        // connectivity, or if the Geocoder is given illegal location data). Or, the Geocoder may
        // simply not have an address for a location. In all these cases, we communicate with the
        // receiver using a resultCode indicating failure. If an address is found, we use a
        // resultCode indicating success.

        // The Geocoder used in this sample. The Geocoder's responses are localized for the given
        // Locale, which represents a specific geographical or linguistic region. Locales are used
        // to alter the presentation of information such as numbers or dates to suit the conventions
        // in the region they describe.
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        List<Address> addresses = null;

        try {
            addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), MAX_ADDRESSES_TO_LOOK_FOR); // Just getting 3 address/sample
        }
        catch (IOException ex) {
            errorMessage = getString(R.string.service_is_not_available);
            Log.e(TAG, "Error IOException: " + ex.getMessage());
        }
        catch (IllegalArgumentException ex) {
            errorMessage = getString(R.string.invalid_latitude_and_or_longitude);
            Log.e(TAG, errorMessage + ". " +
                    "Latitude = " + location.getLatitude() +
                    ", Longitude = " + location.getLongitude(), ex);
        }


        // Handle case where no address was found.
        if (addresses == null || addresses.size()  == 0)
        {
            if(errorMessage.isEmpty()) {
                errorMessage = getString(R.string.no_address_found);
                Log.d(TAG, errorMessage);
            }
            deliverAddressToReceiver(Constants.COORDINATES_TO_ADDRESS_FAILURE_RESULT, new ArrayList<AddressPOJO>(), mResultReceiver);
        }
        else {

            ArrayList<AddressPOJO> addressPOJOs = new ArrayList<>();
            for(Address address : addresses) {
                Log.d(TAG, address.toString());


                final String street = address.getAddressLine(0);

                if(street != null) {
                    final String adminArea = address.getAdminArea();
                    final String subAdminArea = address.getSubAdminArea();
                    final String country = address.getCountryName();

                    AddressPOJO addressPOJO = new AddressPOJO(street, adminArea, subAdminArea, country);
                    addressPOJOs.add(addressPOJO);
                }
            }
            deliverAddressToReceiver(Constants.COORDINATES_TO_ADDRESS_SUCCESS_RESULT, addressPOJOs, mResultReceiver);
        }
    }



    // TODO este ResultReceiver mResultReceiver não deveria ser a minha classe? E falta depois ver onde recebem o resultado deste metodo e fazer null check!!
    private void parseAddressToCoordinates(Intent intent, ResultReceiver mResultReceiver) {
        String errorMessage = "";
        final ArrayList<CoordinatesPOJO> addressInCoordinates = new ArrayList<>();

        final ArrayList<String> addressToConvertList = intent.getStringArrayListExtra(Constants.ADDRESS_DATA_EXTRA);

        CoordinatesPOJO coordinatesPOJO = null;

        CoordinatesPOJO startLocationCoordinatesPOJO;
        CoordinatesPOJO endLocationCoordinatesPOJO;

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        try {
            for(String address : addressToConvertList)
            {
                Log.d(TAG, "Endereço recebido: " + address);
                // If it is startLocation
                if(address.contains(Constants.START_LOCATION_APPEND_TO_ADDRESS_FLAG))
                {
                    address = address.replace(Constants.START_LOCATION_APPEND_TO_ADDRESS_FLAG, "");
                    Log.d(TAG, "START LOCATION REPLACED STRING: " + address);
                    List<Address> addressesFound = geocoder.getFromLocationName(address, 1);

                    // If it has results
                    if(addressesFound.size() > 0) {
                        final String latitude = String.valueOf(addressesFound.get(0).getLatitude());
                        final String longitude = String.valueOf(addressesFound.get(0).getLongitude());

                        startLocationCoordinatesPOJO = new CoordinatesPOJO(latitude, longitude, 0, true, address);

                        addressInCoordinates.add(startLocationCoordinatesPOJO);
                    }
                    else{
                        addressInCoordinates.add(new CoordinatesPOJO(null, null, 0, true, address));
                    }
                }
                else
                {
                    List<Address> addressesFound = geocoder.getFromLocationName(address, 1);

                    if(addressesFound.size() > 0) {
                        final String latitude = String.valueOf(addressesFound.get(0).getLatitude());
                        final String longitude = String.valueOf(addressesFound.get(0).getLongitude());

                        endLocationCoordinatesPOJO = new CoordinatesPOJO(latitude, longitude, 0, false, address);

                        addressInCoordinates.add(endLocationCoordinatesPOJO);
                    }
                    else {
                        addressInCoordinates.add(new CoordinatesPOJO(null, null, 0, false, address));
                    }
                }
            }
        } catch (IOException ex) {
            errorMessage = getString(R.string.service_is_not_available);
            Log.e(TAG, "Error IOException: " + ex.getMessage());
        }

        deliverCoordinatesToReceiver(Constants.ADDRESS_TO_COORDINATES_SUCCESS_RESULT, addressInCoordinates, mResultReceiver);
    }

    private void deliverCoordinatesToReceiver(int resultCode, ArrayList<CoordinatesPOJO> addressInCoordinates, ResultReceiver mResultReceiver) {
        Log.d(TAG, "deliverResultToReceiver");
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.COORDINATES_RESULT_DATA_KEY, addressInCoordinates);
        mResultReceiver.send(resultCode, bundle);
    }

    private void deliverAddressToReceiver(int resultCode, ArrayList<AddressPOJO> addresses, ResultReceiver mResultReceiver) {
        Log.d(TAG, "deliverResultToReceiver");
        Bundle bundle = new Bundle();
        bundle.putParcelableArrayList(Constants.ADDRESS_RESULT_DATA_KEY, addresses);
        mResultReceiver.send(resultCode, bundle);
    }
}
