package com.dazito.android.rideme;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dazito.android.rideme.gps.AddressPOJO;
import com.dazito.android.rideme.gps.CoordinatesConverter;
import com.dazito.android.rideme.gps.CoordinatesPOJO;
import com.dazito.android.rideme.gps.GpsTracker;
import com.dazito.android.rideme.network.NetworkResultReceiver;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


public class MainActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    private NetworkResultReceiver mNetworkResultReceiver;

    private EditText mStartJourney;
    private EditText mEndJourney;
    private Button mGo;
    private ImageView mGpsImageView;
    private GpsTracker mMyGpsTracker;
    private TextView mUberLocations;
    private TextView mTaxiFareFinderLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // Create the toolbar
        activateToolbar();

        mUberLocations = (TextView) findViewById(R.id.uberLocations);
        mTaxiFareFinderLocations = (TextView) findViewById(R.id.taxifarestandLocations);
        mUberLocations.setOnClickListener(this);
        mTaxiFareFinderLocations.setOnClickListener(this);

        mNetworkResultReceiver = new NetworkResultReceiver(new Handler());

        mMyGpsTracker = new GpsTracker(this);
        mMyGpsTracker.togglePeriodicLocationUpdates();

        mStartJourney = (EditText) findViewById(R.id.startLocation);
        mEndJourney = (EditText) findViewById(R.id.endLocation);

        mGo = (Button) findViewById(R.id.goButton);

        mGpsImageView = (ImageView) findViewById(R.id.gpsIcon);

        // First we need to check availability of play services
        if (mMyGpsTracker.checkPlayServices()) {

            // Building the GoogleApi client
            mMyGpsTracker.buildGoogleApiClient();

            mMyGpsTracker.createLocationRequest();
        }

        /**
         * Image view Click Listener
         */
        mGpsImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMyGpsTracker.displayLocation();
            }
        });

        /**
         * Go Button Click Listener
         */
        mGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(isNetworkAvailable()) {
                    final String addressOrigin = mStartJourney.getText().toString();
                    final String addressDestination = mEndJourney.getText().toString();

                    hideKeyboard();

                    if(addressDestination.isEmpty() && addressOrigin.isEmpty()) {
                        Toast.makeText(MainActivity.this, "You need to fill in the start and end location for your journey.", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else if(addressOrigin.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please fill in your starting location.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    else if(addressDestination.isEmpty()) {
                        Toast.makeText(MainActivity.this, "Please fill in your end location.", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    final ArrayList addressList = new ArrayList();
                    addressList.add(addressOrigin + Constants.START_LOCATION_APPEND_TO_ADDRESS_FLAG);
                    addressList.add(addressDestination);

                    CoordinatesConverter coordinatesConverter = new CoordinatesConverter(getApplicationContext());
                    coordinatesConverter.getCoordinatesFromAddress(addressList);

//                TaxiStartup taxiStartup = new TaxiStartup();
//                AuthorizationPassword authorizationPassword = new AuthorizationPassword("+351925100721");
//                AuthorizationRequest authorizationRequest = new AuthorizationRequest("+351925100721", "850");

//                taxiStartup.requestSMSAsync(authorizationPassword);
//                taxiStartup.authorizeByPhoneAndPasswordAsyn(authorizationRequest);

//                GMaps gMaps = new GMaps();
//                gMaps.getDrivingDataAsync("Oeiras", "Lisboa", false);
                }
                else {
                    Toast.makeText(getApplicationContext(), "You are not connected to the internet.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            setActivityEnterUpDownAnimation();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }




    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        mMyGpsTracker.onStart();
    }

    @Override
    protected void onStop() {
        mMyGpsTracker.onStop();
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    protected void onPause() {
        mMyGpsTracker.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mMyGpsTracker.onResume();
    }

    private void startResultsActivity(CoordinatesPOJO startLocation, CoordinatesPOJO endLocation) {

        if(startLocation != null && endLocation != null) {
            Intent resultsActivityIntent = new Intent(MainActivity.this, SearchResultsActivity.class);

            resultsActivityIntent.putExtra(Constants.COORDINATES_START_LOCATION, startLocation);
            resultsActivityIntent.putExtra(Constants.COORDINATES_END_LOCATION, endLocation);
            startActivity(resultsActivityIntent);

            // Set the Activity animation
            overridePendingTransition(R.animator.activity_open_translate, R.animator.activity_close_scale);
        }
        else {
            Toast.makeText(this, "Your starting and/or ending location are not correct.", Toast.LENGTH_SHORT).show();
        }
    }

    public void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) this.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = this.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if(view == null) {
            view = new View(this);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     *
     *          Eventbus Handlers
     *
     */


    public void onEventMainThread(RideMeEvents.GeolocationAddressFetchedError geolocationAddressFetchedError) {
        Toast.makeText(this, geolocationAddressFetchedError.getErrorDescription(), Toast.LENGTH_SHORT).show();
    }

    public void onEventMainThread(RideMeEvents.GeolocationAddressFetchedSuccess geolocationAddressFetchedSuccess) {
        ArrayList<AddressPOJO> addressList = geolocationAddressFetchedSuccess.getAddress();
        int numberOfAddresses = addressList.size();

        final String[] items = new String[numberOfAddresses];

        for(int i = 0; i<addressList.size(); i++) {
            AddressPOJO addressPOJO = addressList.get(i);
            items[i] = addressPOJO.toString();
        }

        showDialogBoxWithAddressFromGPS(items);
    }

    /**
     * Text Address converted into coordinates.
     * @param gcfs
     */
    public void onEventMainThread(RideMeEvents.GeolocationCoordinatesFetchedSuccess gcfs) {
        ArrayList<CoordinatesPOJO> resultCoordinates = gcfs.getResultCoordinates();

        CoordinatesPOJO startLocation = null;
        CoordinatesPOJO endLocation = null;

        for(CoordinatesPOJO coordinatesPOJO : resultCoordinates) {
            if(coordinatesPOJO.isStartLocation()) {
                startLocation = coordinatesPOJO;
            }
            else {
                endLocation = coordinatesPOJO;
            }
        }
        startResultsActivity(startLocation, endLocation);
    }

    public void onEventMainThread(RideMeEvents.GeolocationCoordinatesFetchedError gcfe) {
        Toast.makeText(this, gcfe.getErrorDescription(), Toast.LENGTH_SHORT).show();
    }



    /**
     *
     *          Private Methods
     *
     */

    private void showDialogBoxWithAddressFromGPS(final String[] items) {
        if(items.length > 0) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            // Add the buttons
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if(id < 0) {
                        mStartJourney.setText(items[0]);
                    }
                    else {
                        mStartJourney.setText(items[id]);
                    }
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });

            builder.setTitle(getString(R.string.current_location));
            builder.setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    mStartJourney.setText(items[which]);
                }
            });

            AlertDialog dialog = builder.create();

            dialog.show();
        }
        else {
            // TODO display an error, no location found.
            Toast.makeText(this, "Couldn't determine your current location.", Toast.LENGTH_SHORT).show();
        }
    }

    private void launchBrowser(String urlToLoad) {
        Uri webpage = Uri.parse(urlToLoad);
        Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.uberLocations) {
            final String uberLocationsUrl = getString(R.string.uber_url_available_locations);
            launchBrowser(uberLocationsUrl);
        }
        else if(v.getId() == R.id.taxifarestandLocations) {
            final String taxiFareFinderLocationsUrl = getString(R.string.taxifarefinder_url_availabled_locations);
            launchBrowser(taxiFareFinderLocationsUrl);
        }
    }
}
