package com.dazito.android.rideme.gui.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dazito.android.rideme.R;
import com.dazito.android.rideme.Utils;
import com.dazito.android.rideme.gps.CoordinatesPOJO;
import com.dazito.android.rideme.model.RoutePrice;
import com.dazito.android.rideme.model.TaxiFareFinderRoutePrice;
import com.dazito.android.rideme.model.UberRoutePrice;
import com.dazito.android.rideme.network.NetworkResultReceiver;
import com.dazito.android.rideme.webservices.gmaps.GMaps;
import com.dazito.android.rideme.webservices.gmaps.model.directions.Route;
import com.dazito.android.rideme.webservices.gmaps.model.directions.RouteResponse;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


/**
 * to handle interaction events.
 * Use the {@link RouteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RouteFragment extends Fragment implements View.OnClickListener, OnMapReadyCallback {

    private static final String TAG = RouteFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private NetworkResultReceiver mNetworkResultReceiver;
    private CoordinatesPOJO mStartLocation;
    private CoordinatesPOJO mEndLocation;
    private UberRoutePrice mUberRoutePrice;
    private TaxiFareFinderRoutePrice mTaxiFareFinderRoutePrice;

    private TextView mPrice;
    private TextView mStartLocationAddress;
    private TextView mEndLocationAddress;
    private TextView mDisplayName;
    private TextView mDescription;
    private TextView mPickUpIn;
    private TextView mCapacity;
    private TextView mDistance;
    private TextView mDuration;
    private TextView mSurgeMultiplier;
    private TextView mRateArea;
    private TextView mInitialFare;
    private TextView mMeteredFare;
    private TextView mTipAmount;
    private Button mBook;
    private Button mShowMap;

    private TextView mRateAreaTxt;
    private TextView mInitialFareTxt;
    private TextView mMeteredFareTxt;
    private TextView mTipAmountTxt;

    private SlidingUpPanelLayout mSlidingUpPanelLayout;

    private MapView mMapView;
//    private GoogleMap mGoogleMap;

    private RouteFragmentCommunicator mRouteFragmentCommunicator;

//    private FragmentManager mFragmentManager;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RouteDataFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RouteFragment newInstance(String param1, String param2) {
        RouteFragment fragment = new RouteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RouteFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mRouteFragmentCommunicator = (RouteFragmentCommunicator) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement RouteFragmentCommunicator");
        }
    }

    @Override
    public void onDetach() {
        mRouteFragmentCommunicator = null;
        super.onDetach();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_route, container, false);

        Log.d(TAG, "onCreateView");

        mNetworkResultReceiver = new NetworkResultReceiver(new Handler());

        mPrice = (TextView) view.findViewById(R.id.price);
        mStartLocationAddress = (TextView) view.findViewById(R.id.startLocationAddress);
        mEndLocationAddress = (TextView) view.findViewById(R.id.endLocationAddress);
        mDisplayName = (TextView) view.findViewById(R.id.displayName);
        mDescription = (TextView) view.findViewById(R.id.description);
        mCapacity = (TextView) view.findViewById(R.id.capacityValue);
        mDistance = (TextView) view.findViewById(R.id.distanceValue);
        mDuration = (TextView) view.findViewById(R.id.durationValue);
        mPickUpIn = (TextView) view.findViewById(R.id.pickupInValue);
        mSurgeMultiplier = (TextView) view.findViewById(R.id.surgeMultiplierValue);
        mRateArea = (TextView) view.findViewById(R.id.rateAreaValue);
        mInitialFare = (TextView) view.findViewById(R.id.initialFareValue);
        mMeteredFare = (TextView) view.findViewById(R.id.meteredFareValue);
        mTipAmount = (TextView) view.findViewById(R.id.tipAmountValue);
        mBook = (Button) view.findViewById(R.id.bookMapBar);
        mShowMap = (Button) view.findViewById(R.id.showMap);

        mRateAreaTxt = (TextView) view.findViewById(R.id.rateAreaTxt);
        mInitialFareTxt = (TextView) view.findViewById(R.id.initialFareTxt);
        mMeteredFareTxt = (TextView) view.findViewById(R.id.meteredFareTxt);
        mTipAmountTxt = (TextView) view.findViewById(R.id.tipAmountTxt);

        mSlidingUpPanelLayout = (SlidingUpPanelLayout) view.findViewById(R.id.sliding_layout);

        if(mSlidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED) {
            Log.d(TAG, "onCreateView - Setting ShowMap");
            mShowMap.setText(getString(R.string.show_map));
        }
        if(mSlidingUpPanelLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
            Log.d(TAG, "onCreateView - Setting HideMap");
            mShowMap.setText(getString(R.string.hide_map));
        }

        // Gets the MapView from the XML layout and create it
        mMapView = (MapView) view.findViewById(R.id.googleMap);

        // Pass savedInstanceState as null, avoids exception but restarts the map in every
        // configuration change... it's a trade off.
        mMapView.onCreate(null);
        mMapView.onResume();

        // Needs to call MapsInitializer before doing any CameraUpdateFactory calls
        try {
            MapsInitializer.initialize(getActivity().getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Gets to GoogleMap from the MapView and does initialization stuff
        mMapView.getMapAsync(this);

        mBook.setOnClickListener(this);
        mPickUpIn.setOnClickListener(this);
        mShowMap.setOnClickListener(this);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        googleMap.getUiSettings().setMyLocationButtonEnabled(false);
        googleMap.setMyLocationEnabled(true);

        final GMaps gMaps = new GMaps();
        final ArrayList polygonPoints = new ArrayList();

        if(mStartLocation == null || mEndLocation == null) {
            Log.d(TAG, "Start Location or End Location is null");
            return;
        }
        else {
            final String originCoordinates = mStartLocation.getLat() + "," + mStartLocation.getLng();
            final String destinationCoordinates = mEndLocation.getLat() + "," + mEndLocation.getLng();

            gMaps.getGMapsRestClient().getGMapsService().getDrivingDataBetweenTwoGPSCoordinates(originCoordinates, destinationCoordinates, false, new Callback<RouteResponse>() {
                @Override
                public void success(RouteResponse routeResponse, Response response) {
                    final String responseCode = routeResponse.status;
                    if(responseCode.equalsIgnoreCase("ok")) {
                        Route route = routeResponse.routes.get(0); // Use the first root
                        final String points = route.overview_polyline.points;

                        final ArrayList<LatLng> latLngs = decodePoly(points);

                        PolylineOptions polylineOptions = new PolylineOptions();
                        polylineOptions.addAll(latLngs);
                        polylineOptions.width(5);
                        polylineOptions.color(Color.BLUE).geodesic(false);

                        MarkerOptions markerOptionsStartLocation = new MarkerOptions()
                                .position(latLngs.get(0))
                                .title(mStartLocation.getAddress());

                        MarkerOptions markerOptionsEndLocation = new MarkerOptions()
                                .position(latLngs.get(latLngs.size()-1))
                                .title(mEndLocation.getAddress());

                        googleMap.addMarker(markerOptionsEndLocation);
                        googleMap.addMarker(markerOptionsStartLocation);

                        googleMap.addPolyline(polylineOptions);

                        LatLngBounds.Builder builder = new LatLngBounds.Builder();
                        builder.include(markerOptionsEndLocation.getPosition());
                        builder.include(markerOptionsStartLocation.getPosition());
//                        for (MarkerOptions marker : markers) {
//                            builder.include(marker.getPosition());
//                        }
                        LatLngBounds bounds = builder.build();

                        int padding = 150; // offset from edges of the map in pixels
                        CameraUpdate cu = CameraUpdateFactory.newLatLngBounds(bounds, padding);

                        // Finally move the map:
//                        googleMap.moveCamera(cu);

                        //Or if you want an animation:
                        googleMap.animateCamera(cu);
                    }
                    else if (responseCode.equalsIgnoreCase("NOT_FOUND") || responseCode.equalsIgnoreCase("ZERO_RESULTS")){
                        Toast.makeText(getActivity(), "No routes could be found.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getActivity(), "Couldn't get the required data to draw the path.", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d(TAG, "ERROR: Points NOT received.");
                }
            });
        }
    }


    // Source: http://jeffreysambells.com/2010/05/27/decoding-polylines-from-google-maps-direction-api-with-java
    private ArrayList<LatLng> decodePoly(String encoded) {

        ArrayList<LatLng> poly = new ArrayList<>();
        int index = 0, len = encoded.length();
        int lat = 0, lng = 0;

        while (index < len)
        {
            int b, shift = 0, result = 0;

            do{
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dlat = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lat += dlat;

            shift = 0;
            result = 0;

            do {
                b = encoded.charAt(index++) - 63;
                result |= (b & 0x1f) << shift;
                shift += 5;
            } while (b >= 0x20);

            int dlng = ((result & 1) != 0 ? ~(result >> 1) : (result >> 1));
            lng += dlng;

            LatLng latLng = new LatLng(((double) lat / 1E5), ((double) lng / 1E5));
            poly.add(latLng);
        }

        return poly;
    }


    public SlidingUpPanelLayout.PanelState getPanelState(){
        return mSlidingUpPanelLayout.getPanelState();
    }

    public void setSlidingUpPanelLayoutState(SlidingUpPanelLayout.PanelState state) {
        mSlidingUpPanelLayout.setPanelState(state);
    }

    public void setHideShowMapText(String text){
        mShowMap.setText(text);
    }

    public void changeData(RoutePrice routePrice, CoordinatesPOJO startLocation, CoordinatesPOJO endLocation) {
        mStartLocation = startLocation;
        mEndLocation = endLocation;

        populateFields(routePrice, startLocation, endLocation);
        if(mMapView != null) {
            // Gets to GoogleMap from the MapView and does initialization stuff
            mMapView.getMapAsync(this);
        }
        else {
            Log.d(TAG, "mMapView is NULL");
        }
    }

    private void populateFields(RoutePrice routePrice, CoordinatesPOJO startLocation, CoordinatesPOJO endLocation) {

        if(startLocation == null) {
            Log.d(TAG, "populateFields - startLocation null");
            return;
        }

        if(endLocation == null) {
            Log.d(TAG, "populateFields - endLocation null");
            return;
        }

        String unit = " mi";
        if(routePrice.isMetricSystem()) {
            unit = " km";
        }

        int durationInMinutes = Utils.convertSecondsToMinutesRoundUp(routePrice.getDuration());
        String duration = "";

        if(durationInMinutes < 2 ) {
            duration = durationInMinutes + getString(R.string.minute);
        }
        else {
            duration = durationInMinutes + getString(R.string.minutes);
        }

        if(routePrice instanceof UberRoutePrice)
        {
            Log.d(TAG, "routePrice instanceof of UberRoutePrice");
            mUberRoutePrice = (UberRoutePrice) routePrice;
            String pickUpTime = "";
            String capacityTxt = "";


            int pickupTimeInMinutes = Utils.convertSecondsToMinutesRoundUp(mUberRoutePrice.getPickupTime());
            int capacity = mUberRoutePrice.getCapacity();

            if(pickupTimeInMinutes < 2) {
                pickUpTime = pickupTimeInMinutes + getString(R.string.minute);
            }
            else if( pickupTimeInMinutes > 1) {
                pickUpTime = pickupTimeInMinutes + getString(R.string.minutes);
            }

            if( capacity > 1 ) {
                capacityTxt = capacity + getString(R.string.passengers);
            }
            else {
                capacityTxt = capacity + getString(R.string.passenger);
            }

            mPrice.setText(mUberRoutePrice.getEstimate());
            mStartLocationAddress.setText(" " + startLocation.getAddress());
            mEndLocationAddress.setText(" " + endLocation.getAddress());
            mDisplayName.setText(mUberRoutePrice.getDisplayName());
            mDescription.setText(mUberRoutePrice.getProductDescription());
            mDistance.setText(String.valueOf(mUberRoutePrice.getDistance()) + unit);
            mDuration.setText(duration);
            mSurgeMultiplier.setText(String.valueOf(mUberRoutePrice.getSurge_multiplier()));

            if(mUberRoutePrice.getPickupTime() != -1) { mPickUpIn.setText(pickUpTime); }
            else { mPickUpIn.setText("All nearby cars are full but one should free up soon. Please try again in a few minutes.") ;}

            if(mUberRoutePrice.getCapacity() != -1) { mCapacity.setText(capacityTxt); }
            else { mCapacity.setText("Unknown"); }

            mRateAreaTxt.setVisibility(View.GONE);
            mRateArea.setVisibility(View.GONE);
            mInitialFareTxt.setVisibility(View.GONE);
            mInitialFare.setVisibility(View.GONE);
            mMeteredFareTxt.setVisibility(View.GONE);
            mMeteredFare.setVisibility(View.GONE);
            mTipAmount.setVisibility(View.GONE);
            mTipAmountTxt.setVisibility(View.GONE);

        }
        else if(routePrice instanceof TaxiFareFinderRoutePrice)
        {
            Log.d(TAG, "routePrice instanceof TaxiFareFinderRoutePrice");
            mTaxiFareFinderRoutePrice = (TaxiFareFinderRoutePrice) routePrice;

            String currency;

            if(mTaxiFareFinderRoutePrice.getCurrency().equalsIgnoreCase("usd ")) {
                currency = "$";
            }
            else if(mTaxiFareFinderRoutePrice.getCurrency().equalsIgnoreCase("eur ")) {
                currency = "€";
            }
            else if(mTaxiFareFinderRoutePrice.getCurrency().equalsIgnoreCase("gbp ")) {
                currency = "£";
            }
            else {
                currency = mTaxiFareFinderRoutePrice.getCurrency() + " ";
            }

            mPrice.setText(String.valueOf(currency + mTaxiFareFinderRoutePrice.getPrice()));
            mStartLocationAddress.setText(" " + startLocation.getAddress());
            mEndLocationAddress.setText(" " + endLocation.getAddress());
            mDisplayName.setText(mTaxiFareFinderRoutePrice.getDisplayName());
            mDescription.setText(mTaxiFareFinderRoutePrice.getProductDescription());
            mCapacity.setText(getString(R.string.unknown));
            mDistance.setText(String.valueOf(mTaxiFareFinderRoutePrice.getDistance()) + unit);
            mDuration.setText(duration);
            mSurgeMultiplier.setText(getString(R.string.unknown));
            mPickUpIn.setText(getString(R.string.unknown));

            mRateArea.setText(mTaxiFareFinderRoutePrice.getRateArea());
            mInitialFare.setText(currency + " " + String.valueOf(mTaxiFareFinderRoutePrice.getInitialFare()));
            mMeteredFare.setText(currency + " " + String.valueOf(mTaxiFareFinderRoutePrice.getMeteredFare()));
            mTipAmount.setText(currency + " " + String.valueOf(mTaxiFareFinderRoutePrice.getTipAmount())
                    + " (" + mTaxiFareFinderRoutePrice.getTipPercentage() + "%)");
        }
    }

    public void updateDistance(RoutePrice routePrice) {
        String unit = " mi";
        if(routePrice.isMetricSystem()) {
            unit = " km";
        }
        if(routePrice instanceof TaxiFareFinderRoutePrice) {
            TaxiFareFinderRoutePrice route = (TaxiFareFinderRoutePrice) routePrice;
            mDistance.setText(String.valueOf(route.getDistance() + unit));
        }
        else if(routePrice instanceof UberRoutePrice) {
            UberRoutePrice route = (UberRoutePrice) routePrice;
            mDistance.setText(String.valueOf(route.getDistance()) + unit);
        }
    }


    @Override
    public void onClick(View v) {

        if(v.getId() == R.id.pickupInValue) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage(mPickUpIn.getText())
                    .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                        }
                    });
            builder.create().show();
        }
        else if(v.getId() == R.id.bookMapBar) {
            // Are we in a Uber Route?
            if (mUberRoutePrice != null) {
                launchUberApp();
            }
            // Are we in a TFF Route?
            if (mTaxiFareFinderRoutePrice != null && mStartLocation != null) {
                mRouteFragmentCommunicator.bookTaxiDialog(mStartLocation);
            }
        }
        else if(v.getId() == R.id.showMap) {
            SlidingUpPanelLayout.PanelState panelState = mSlidingUpPanelLayout.getPanelState();

            if(panelState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                mShowMap.setText(getString(R.string.hide_map));
            }
            else if(panelState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                mSlidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
                mShowMap.setText(getString(R.string.show_map));
            }
        }
    }


    private void launchUberApp() {
        PackageManager pm = getActivity().getPackageManager();
        try {
            pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
            // Do something awesome - the app is installed! Launch App.
        }
        catch (PackageManager.NameNotFoundException e) {
            Uri webpage = Uri.parse("https://m.uber.com/sign-up?client_id=");
            Intent intent = new Intent(Intent.ACTION_VIEW, webpage);

            if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(intent);
            }
        }
    }



    public static interface RouteFragmentCommunicator{
        public void bookTaxiDialog(CoordinatesPOJO coordinatesPOJO);
//        public FragmentManager getFragmentManager();
    }


    //    private void getUberProducts(CoordinatesPOJO startLocation) {
//        startLocation = new CoordinatesPOJO("33.669497", "-117.978745", 0, true, "rua sobe e desce");
//
//        Intent intent = new Intent(getActivity(), NetworkIntentService.class);
//        intent.putExtra(Constants.NETWORK_OPERATION_TYPE, Constants.UBER_GET_PRODUCTS);
//        intent.putExtra(Constants.START_LOCATION, startLocation);
//        intent.putExtra(Constants.NETWORK_RESULT_RECEIVER, mNetworkResultReceiver);
//
//        getActivity().getApplicationContext().startService(intent);
//    }

    //    public void setUberProducts(ArrayList<Product> productList) {
//        if(mUberRoutePrice != null) {
//            final String productId = mUberRoutePrice.getProductId();
//            for(Product product : productList) {
//                if(product.product_id.equalsIgnoreCase(productId)) {
////                    mProductDescription.setText(product.description);
////                    mDistanceValue.setVisibility(View.GONE);
//                    return;
//                }
//            }
//            Log.e(TAG, "ProductID didn't match any Route.");
//        }
//        Log.e(TAG, "Couldn't set the product description...");
//    }
}
