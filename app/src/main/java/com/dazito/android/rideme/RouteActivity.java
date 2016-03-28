package com.dazito.android.rideme;

import android.app.FragmentManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.transition.Slide;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.dazito.android.rideme.gps.CoordinatesPOJO;
import com.dazito.android.rideme.gui.fragments.RouteFragment;
import com.dazito.android.rideme.gui.fragments.TaxiStandDialogFragment;
import com.dazito.android.rideme.model.RoutePrice;
import com.dazito.android.rideme.model.TaxiCompany;
import com.dazito.android.rideme.webservices.gmaps.model.referencedata.PlaceDetails;
import com.dazito.android.rideme.webservices.gmaps.model.referencedata.Result;
import com.dazito.android.rideme.webservices.uber.model.Product;
import com.dazito.android.rideme.webservices.uber.model.Products;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

import de.greenrobot.event.EventBus;


public class RouteActivity extends BaseActivity implements RouteFragment.RouteFragmentCommunicator,
        TaxiStandDialogFragment.TaxiStandDialogFragmentCommunicator ,
        SharedPreferences.OnSharedPreferenceChangeListener {

    private static final String TAG = RouteActivity.class.getSimpleName();

    private RoutePrice mRoutePrice;
    private CoordinatesPOJO mStartLocation;
    private CoordinatesPOJO mEndLocation;
    private RouteFragment mRouteFragment;
    private SharedPreferences mSharedPreferences;
    private boolean mIsMetricSystem = false;

    private String mRadius;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Transition
        if(android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // Faz slide da barra para cima
            Slide slide = new Slide();
            slide.setDuration(500);
            getWindow().setEnterTransition(slide);

            // Quando carrega back explode (A barra explode para baixo... tem que explodir para alguma lado né?)
            getWindow().setReturnTransition(TransitionInflater.from(this).inflateTransition(R.transition.transition_a));
        }

        setContentView(R.layout.activity_route);

        // Create the toolbar
        activateToolbarWithHomeEnabled();

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        mSharedPreferences.registerOnSharedPreferenceChangeListener(this);

        String auxUnitSystem = mSharedPreferences.getString(getString(R.string.pref_unit_system_key), "metric");
        mRadius = mSharedPreferences.getString(getString(R.string.pref_radius_key), "10000");

        if(auxUnitSystem.equalsIgnoreCase("metric")) {
            mIsMetricSystem = true;
        }
        else {
            mIsMetricSystem = false;
        }

        FragmentManager fragmentManager = getFragmentManager();
        mRouteFragment = (RouteFragment) fragmentManager.findFragmentById(R.id.routeDataFragment);

        if(savedInstanceState != null) {
            mRoutePrice = savedInstanceState.getParcelable(Constants.ROUTE_PRICE);
            mStartLocation = savedInstanceState.getParcelable(Constants.START_LOCATION);
            mEndLocation = savedInstanceState.getParcelable(Constants.END_LOCATION);
            SlidingUpPanelLayout.PanelState panelState = (SlidingUpPanelLayout.PanelState) savedInstanceState.getSerializable(Constants.SLIDE_UP_PANEL_STATE);

            if(mRouteFragment != null) {
                mRouteFragment.setSlidingUpPanelLayoutState(SlidingUpPanelLayout.PanelState.COLLAPSED);

                if(panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    mRouteFragment.setHideShowMapText(getString(R.string.hide_map));
                }
                else {
                    mRouteFragment.setHideShowMapText(getString(R.string.show_map));
                }
            }
            else {
                Log.d(TAG, "mRouteFragment is NULL");
            }
        }
        else {
            Intent intent = getIntent();
            mRoutePrice = intent.getParcelableExtra(Constants.ROUTE_PRICE);
            mStartLocation = intent.getParcelableExtra(Constants.START_LOCATION);
            mEndLocation = intent.getParcelableExtra(Constants.END_LOCATION);
        }

        if(mRouteFragment != null) {
            mRouteFragment.changeData(mRoutePrice, mStartLocation, mEndLocation);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        EventBus.getDefault().unregister(this);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_route, menu);
        return true;
    }

    @Override
    protected void onDestroy() {
        mSharedPreferences.unregisterOnSharedPreferenceChangeListener(this);
        super.onDestroy();
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
        // Go back to the previous activity
        if (id == android.R.id.home) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(Constants.ROUTE_PRICE, mRoutePrice);
        outState.putParcelable(Constants.END_LOCATION, mEndLocation);
        outState.putParcelable(Constants.START_LOCATION, mStartLocation);
        outState.putSerializable(Constants.SLIDE_UP_PANEL_STATE, mRouteFragment.getPanelState());
        super.onSaveInstanceState(outState);
    }



    public void addElementsToDialog(ArrayList<TaxiCompany> list) {
        FragmentManager fragmentManager = getFragmentManager();
        TaxiStandDialogFragment taxiStandDialogFragment = (TaxiStandDialogFragment) fragmentManager.findFragmentByTag(TaxiStandDialogFragment.class.getSimpleName());

        if(taxiStandDialogFragment != null) {
            taxiStandDialogFragment.addList(list);
        }
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

    public void onEventMainThread(RideMeEvents.PlaceDetailsError placeDetailsError) {
        Log.e(TAG, "PlaceDetailsError: " + placeDetailsError.getErrorMessage());
        Toast.makeText(this, "PlaceDetailsError: " + placeDetailsError.getErrorMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void bookTaxiDialog(CoordinatesPOJO startLocation) {
        startLocation.setRadius(Integer.parseInt(mRadius));
        showBookingDialog(startLocation);
    }

//    @Override
//    public FragmentManager getFragmentManager() {
//        return getFragmentManager();
//    }

    @Override
    public void makePhoneCall(TaxiCompany taxiCompany) {
        callTaxiCompany(taxiCompany);
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        // If change metric system
         if(key.equalsIgnoreCase(getString(R.string.pref_unit_system_key)))
         {
            if(mRouteFragment != null) {
                final String unit = sharedPreferences.getString(key, "imperial");
                if(unit.equalsIgnoreCase("imperial")) {
                    final double oldDistance = mRoutePrice.getDistance();
                    mRoutePrice.setDistance(Utils.convertKmsToMiles(oldDistance, 1));
                    mRoutePrice.setMetricSystem(false);
                    mRouteFragment.updateDistance(mRoutePrice);
                    Log.d(TAG, "Setting list unit to imperial");
                }
                else if (unit.equalsIgnoreCase("metric")) {
                    final double oldDistance = mRoutePrice.getDistance();
                    mRoutePrice.setDistance(Utils.convertMilesToKms(oldDistance, 1));
                    mRoutePrice.setMetricSystem(true);
                    mRouteFragment.updateDistance(mRoutePrice);
                    Log.d(TAG, "Setting list unit to metric");
                }
            }
        }
        else if(key.equalsIgnoreCase((getString(R.string.pref_radius_key)))) {
            mRadius = sharedPreferences.getString(getString(R.string.pref_radius_key), "10000");
         }
    }


    public void onEventMainThread(RideMeEvents.UberProductsSuccess uberProductsSuccess) {

        Products products = uberProductsSuccess.getProducts();
        ArrayList<Product> productList = products.products;

        for (Product product : productList) {
            Log.d(TAG, "UberProductsSuccess: " + product.display_name + " ProductID: " + product.product_id);
        }
        if (mRouteFragment != null && mRouteFragment.isVisible()) {
//            mRouteFragment.setUberProducts(productList);
        }
    }

    public void onEventMainThread(RideMeEvents.UberProductsError uberProductsError) {
        Log.e(TAG, "UberProductsError: " + uberProductsError.getRetrofitError());
    }


}
