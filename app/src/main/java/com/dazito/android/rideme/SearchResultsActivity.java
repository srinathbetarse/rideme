package com.dazito.android.rideme;

import android.app.ActivityOptions;
import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.dazito.android.rideme.gps.CoordinatesPOJO;
import com.dazito.android.rideme.gui.fragments.RouteFragment;
import com.dazito.android.rideme.gui.fragments.SearchResultsFragment;
import com.dazito.android.rideme.gui.fragments.TaxiStandDialogFragment;
import com.dazito.android.rideme.model.RoutePrice;
import com.dazito.android.rideme.model.TaxiCompany;
import com.dazito.android.rideme.model.TaxiFareFinderRoutePrice;
import com.dazito.android.rideme.model.UberRoutePrice;
import com.dazito.android.rideme.webservices.gmaps.model.nearbylocation.NearbyLocation;
import com.dazito.android.rideme.webservices.gmaps.model.referencedata.PlaceDetails;
import com.dazito.android.rideme.webservices.gmaps.model.referencedata.Result;
import com.dazito.android.rideme.webservices.taxifarefinder.model.Businesses;
import com.dazito.android.rideme.webservices.taxifarefinder.model.Fare;
import com.dazito.android.rideme.webservices.uber.model.Price;
import com.dazito.android.rideme.webservices.uber.model.Prices;
import com.dazito.android.rideme.webservices.uber.model.Product;
import com.dazito.android.rideme.webservices.uber.model.Products;
import com.dazito.android.rideme.webservices.uber.model.TimeEstimate;
import com.dazito.android.rideme.webservices.uber.model.TimeEstimates;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


public class SearchResultsActivity extends BaseActivity implements SearchResultsFragment.RouteResultsFragmentCommunicator, SharedPreferences.OnSharedPreferenceChangeListener,
        RouteFragment.RouteFragmentCommunicator, TaxiStandDialogFragment.TaxiStandDialogFragmentCommunicator {

    private static final String TAG = SearchResultsActivity.class.getSimpleName();

    private FragmentManager mFragmentManager;
    private SearchResultsFragment mSearchResultsFragment;
    private RouteFragment mRouteFragment;
    private boolean mIsMetricSystem;
    private String mRadius;
    private boolean mOrderByPrice;
    private SharedPreferences mSharedPreferences;
    private int id;
    private boolean mIsScreenRotation = false;

    /**
     * Value to add to the progress bar every time a new task is completed.
     * Divide 100 by the (total number of tasks to execute - 1) => To give the user the opportunity
     * to see the 100%.
     */
    private static final int PROGRESS_BAR_VALUE = 33;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate");

        setContentView(R.layout.activity_search_results);

        // Set the toolbar
        activateToolbarWithHomeEnabled();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);

        mOrderByPrice = mSharedPreferences.getBoolean(getString(R.string.pref_order_list_key), false);
        String auxUnitSystem = mSharedPreferences.getString(getString(R.string.pref_unit_system_key), "metric");
        mRadius = mSharedPreferences.getString(getString(R.string.pref_radius_key), "10000");

        if(auxUnitSystem.equalsIgnoreCase("metric")) {
            mIsMetricSystem = true;
        }
        else {
            mIsMetricSystem = false;
        }

        // Initialize the FragmentManager
        mFragmentManager = getFragmentManager();

        mSearchResultsFragment = (SearchResultsFragment) mFragmentManager.findFragmentById(R.id.searchRouteResultsFragment);

        id = 0;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart");
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume");
    }

    @Override
    protected void onStop() {
        Log.d(TAG, "onStop");
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // If it is radius value
        if (key.equalsIgnoreCase(getResources().getString(R.string.pref_radius_key))) {
        }
        // If it is the list order
        else if (key.equalsIgnoreCase(getString(R.string.pref_order_list_key))) {
            if(mSearchResultsFragment != null) {
                mOrderByPrice = sharedPreferences.getBoolean(key, false);
                mSearchResultsFragment.updateRecyclerView(mOrderByPrice);
                Log.d(TAG, "Ordering list by price? " + mOrderByPrice);
            }
        }
        // If change metric system
        else if(key.equalsIgnoreCase(getString(R.string.pref_unit_system_key))) {
            if(mSearchResultsFragment != null) {
                final String unit = sharedPreferences.getString(key, "imperial");
                if(unit.equalsIgnoreCase("imperial")) {
                    mSearchResultsFragment.convertListFromKmsToMiles();
                    mSearchResultsFragment.updateRecyclerView(mOrderByPrice);
                    Log.d(TAG, "Setting list unit to imperial");
                }
                else if (unit.equalsIgnoreCase("metric")) {
                    mSearchResultsFragment.convertListFromMilesToMeters();
                    mSearchResultsFragment.updateRecyclerView(mOrderByPrice);
                    Log.d(TAG, "Setting list unit to metric");
                }
            }
        }
        else if(key.equalsIgnoreCase(getString(R.string.pref_radius_key))) {
            mRadius = sharedPreferences.getString(key, "10000");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_results, menu);
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
    public void onFragmentCommunication(RoutePrice routePrice, CoordinatesPOJO startLocation, CoordinatesPOJO endLocation, View sharedView) {
        mRouteFragment = (RouteFragment) mFragmentManager.findFragmentById(R.id.routeDataFragment);

        // if landscape orientation?
        if (mRouteFragment != null && mRouteFragment.isVisible()) {
            mRouteFragment.changeData(routePrice, startLocation, endLocation);
        }
        // Portrait orientation
        else {
            Intent intentRouteActivity = new Intent(this, RouteActivity.class);
            intentRouteActivity.putExtra(Constants.ROUTE_PRICE, routePrice);
            intentRouteActivity.putExtra(Constants.START_LOCATION, startLocation);
            intentRouteActivity.putExtra(Constants.END_LOCATION, endLocation);

            // Check if API Version supports Lollipop Transitions
            if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                String transitionName = getString(R.string.priceTransition);
                ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(SearchResultsActivity.this, sharedView, transitionName);
                startActivity(intentRouteActivity, transitionActivityOptions.toBundle());
            }
            else { // Else, start the activity normally
                startActivity(intentRouteActivity);
            }
        }
    }



    /**
     * EventBus Handlers
     */
    public void onEventMainThread(RideMeEvents.GetRoutePricesFinished getRoutePricesFinished) {
        mSearchResultsFragment.updateRecyclerView(mOrderByPrice);
    }

    public void onEventMainThread(RideMeEvents.UberPricesSuccess uberPricesSuccess) {
        setProgressBarStatus(PROGRESS_BAR_VALUE);

        Prices prices = uberPricesSuccess.getPrices();
        ArrayList<Price> pricesList = prices.prices;
        double distanceInUserSystem;

        for (Price price : pricesList) {
            // Convert to the user's defined unit system
            if(mIsMetricSystem) {
                distanceInUserSystem = Utils.convertMilesToKms(price.distance, 1);
            }
            else { // In Miles - Uber already uses miles as their unit. No need to convert
                distanceInUserSystem = price.distance;
            }

            UberRoutePrice uberRoutePrice;

            if(price.low_estimate == 0 || price.high_estimate == 0 || price.currency_code == null) {
                // TODO: Use the builder pattern or pass in the Price object to avoid this huge constructor parameter train
                uberRoutePrice = new UberRoutePrice(id++, Constants.UBER, price.display_name,
                        price.low_estimate, "", distanceInUserSystem, price.duration, price.product_id,
                        price.low_estimate, price.high_estimate, price.estimate, price.surge_multiplier,
                        "Product Description", price.display_name, mIsMetricSystem, -1, -1);
            }
            else {
                // TODO: Use the builder pattern or pass in the Price object to avoid this huge constructor parameter train
                uberRoutePrice = new UberRoutePrice(id++, Constants.UBER, price.display_name,
                        price.low_estimate, price.currency_code, distanceInUserSystem, price.duration, price.product_id,
                        price.low_estimate, price.high_estimate, price.estimate, price.surge_multiplier,
                        "Product Description", price.display_name, mIsMetricSystem, -1, -1);
            }
            mSearchResultsFragment.addToList(uberRoutePrice);
        }
    }


    public void onEventMainThread(RideMeEvents.UberPricesError uberPricesError) {
        Log.e(TAG, "UberPricesError: " + uberPricesError.getRetrofitError());
        Toast.makeText(this, uberPricesError.getRetrofitError(), Toast.LENGTH_SHORT).show();
    }


    public void onEventMainThread(RideMeEvents.UberProductsSuccess uberProductsSuccess) {
        setProgressBarStatus(PROGRESS_BAR_VALUE);

        Products products = uberProductsSuccess.getProducts();
        ArrayList<Product> productList = products.products;

        for(Product product : productList) {
            Log.d(TAG, "Product ID: " + product.product_id + " || DisplayName: " + product.display_name);
        }

        mSearchResultsFragment.setProductToUberRoutePrice(products.products);
    }

    public void onEventMainThread(RideMeEvents.UberProductsError uberProductsError) {
        Log.e(TAG, "UberProductsError: " + uberProductsError.getRetrofitError());
    }


    public void onEventMainThread(RideMeEvents.UberTimeEstimatesSuccess uberTimeEstimatesSuccess) {
        setProgressBarStatus(PROGRESS_BAR_VALUE);

        final TimeEstimates timeEstimates = uberTimeEstimatesSuccess.getTimeEstimates();
        final ArrayList<TimeEstimate> timeEstimateArrayList = timeEstimates.times;

        for(TimeEstimate timeEstimate : timeEstimateArrayList) {
            Log.d(TAG, "TimeEstimate: " + timeEstimate.display_name + " | " + timeEstimate.estimate + " sec || " + timeEstimate.product_id);
        }

        mSearchResultsFragment.setEstimatesToUberRoutePrice(timeEstimateArrayList);
    }

    public void onEventMainThread(RideMeEvents.UberTimeEstimatesError uberTimeEstimatesError) {
        Log.d(TAG, "UberTimeEstimatesError: " + uberTimeEstimatesError.getRetrofitError());
    }

    public void onEventMainThread(RideMeEvents.TFFFareSuccess tffFareSuccess)
    {
        setProgressBarStatus(PROGRESS_BAR_VALUE);

        final Fare fare = tffFareSuccess.getFare();
        final String currencyString = fare.currency.int_symbol;
        final double distanceInKiloUnit = Utils.convertKiloUnitToUnit(fare.distance, 1);
        double distanceInUserSystem;

        // Distance converter
        if(mIsMetricSystem) {
            // TFF already uses metric system, no need to perform conversion
            distanceInUserSystem = distanceInKiloUnit;
        }
        else { // Convert from meters to miles
            distanceInUserSystem = Utils.convertKmsToMiles(distanceInKiloUnit, 2);
        }

        // TODO: Use the builder pattern or pass in the Fare object to avoid this huge constructor parameter train
        TaxiFareFinderRoutePrice tffRoutePrice = new TaxiFareFinderRoutePrice(id++, Constants.TAXI_FARE_FINDER,
                Constants.TAXI_FARE_FINDER_SERVICE_TYPE, "Taxi Service Description", fare.total_fare, currencyString,
                distanceInUserSystem, fare.duration, fare.initial_fare, fare.metered_fare, fare.tip_amount, fare.tip_percentage,
                Constants.TAXI_FARE_FINDER_CAPACITY, fare.locale, fare.rate_area, fare.extra_charges, mIsMetricSystem);

        mSearchResultsFragment.addToList(tffRoutePrice);
    }

    public void onEventMainThread(RideMeEvents.TFFFareError tffFareError) {
        Log.d(TAG, "TFFFareError: " + tffFareError.getRetrofitError());
    }

    public void onEventMainThread(RideMeEvents.TFFEntitySuccess tffEntitySuccess) {
        Log.d(TAG, "TFFEntitySuccess: " + tffEntitySuccess.getEntity());
    }

    public void onEventMainThread(RideMeEvents.TFFEntityError tffEntityError) {
        Log.e(TAG, "TFFEntityError: " + tffEntityError.getRetrofitError());
        Toast.makeText(this, "TFFEntityError: " + tffEntityError.getRetrofitError(), Toast.LENGTH_SHORT).show();
    }

    public void onEventMainThread(RideMeEvents.TFFBusinessesSuccess tffBusinessesSuccess) {
        // Update the progress bar

        Businesses businesses = tffBusinessesSuccess.getBusinesses();
        ArrayList<Businesses.Business> businessArrayList = businesses.businesses;

        for (Businesses.Business business : businessArrayList) {
            Log.d(TAG, "Business: " + business.name + " | " + business.type);
        }
    }

    public void onEventMainThread(RideMeEvents.TFFBusinessesError tffBusinessesError) {
        Log.e(TAG, "TFFBusinessesError: " + tffBusinessesError.getRetrofitError());
    }

    public void onEventMainThread(RideMeEvents.NearbyLocationSuccess nearbyLocationSuccess) {
        NearbyLocation nearbyLocation = nearbyLocationSuccess.getNearbyLocation();
        Log.d(TAG, "NearbyLocationSuccess - status: " + nearbyLocation.status + " total:" + nearbyLocation.results.size());
    }

    public void onEventMainThread(RideMeEvents.NearbyLocationError nearbyLocationError) {
        Log.d(TAG, "NearbyLocationSuccess - Error: " + nearbyLocationError.getErrorMessage());
    }


    public void onEventMainThread(RideMeEvents.PlaceDetailsError placeDetailsError) {
        Log.d(TAG, "NearbyLocationSuccess - Error: " + placeDetailsError.getErrorMessage());
    }

    public void onEventMainThread(RideMeEvents.PlaceDetailsSuccess placeDetailsSuccess) {
        ArrayList<PlaceDetails> placeDetailsArrayList =placeDetailsSuccess.getPlaceDetails();
        ArrayList<TaxiCompany> taxiCompanies = new ArrayList<>();

        for(PlaceDetails placeDetails : placeDetailsArrayList) {
            if(placeDetails.status.equalsIgnoreCase("ok")) {
                Result result = placeDetails.result;

                if(result.formatted_phone_number != null) {
                    String phoneNumber = result.formatted_phone_number;
                    String name = result.name;
                    String rating = result.rating;

                    taxiCompanies.add(new TaxiCompany(name, phoneNumber, rating));
                }
            }
        }
        addElementsToDialog(taxiCompanies);
    }

    public void addElementsToDialog(ArrayList<TaxiCompany> list) {
        FragmentManager fragmentManager = getFragmentManager();
        TaxiStandDialogFragment taxiStandDialogFragment = (TaxiStandDialogFragment) fragmentManager.findFragmentByTag(TaxiStandDialogFragment.class.getSimpleName());

        if(taxiStandDialogFragment != null) {
            taxiStandDialogFragment.addList(list);
        }
    }

    @Override
    public void bookTaxiDialog(CoordinatesPOJO startLocation) {
        startLocation.setRadius(Integer.parseInt(mRadius));
        showBookingDialog(startLocation);
    }

    @Override
    public void makePhoneCall(TaxiCompany taxiCompany) {
        callTaxiCompany(taxiCompany);
    }

    private void callTaxiCompany(TaxiCompany taxiCompany) {
        Intent intent = new Intent(Intent.ACTION_CALL);

        intent.setData(Uri.parse("tel:" + taxiCompany.getPhoneNumber()));
        startActivity(intent);
    }

    public void showBookingDialog(CoordinatesPOJO startLocation) {
        FragmentManager fragmentManager = getFragmentManager();
        TaxiStandDialogFragment taxiStandDialogFragment = TaxiStandDialogFragment.newInstance(startLocation);
        taxiStandDialogFragment.show(fragmentManager, TaxiStandDialogFragment.class.getSimpleName());
    }

    private void setProgressBarStatus(int value) {
        int currentStatus = mSearchResultsFragment.getProgressBarStatus();
        mSearchResultsFragment.setProgressBarStatus(currentStatus + value);
    }
}
