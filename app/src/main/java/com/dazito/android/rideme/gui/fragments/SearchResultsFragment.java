package com.dazito.android.rideme.gui.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dazito.android.rideme.Constants;
import com.dazito.android.rideme.R;
import com.dazito.android.rideme.Utils;
import com.dazito.android.rideme.gps.CoordinatesPOJO;
import com.dazito.android.rideme.gui.ResultsRecyclerViewAdapter;
import com.dazito.android.rideme.gui.SimpleDividerItemDecoration;
import com.dazito.android.rideme.model.RoutePrice;
import com.dazito.android.rideme.model.TaxiFareFinderRoutePrice;
import com.dazito.android.rideme.model.UberRoutePrice;
import com.dazito.android.rideme.network.NetworkIntentService;
import com.dazito.android.rideme.network.NetworkResultReceiver;
import com.dazito.android.rideme.webservices.uber.model.Product;
import com.dazito.android.rideme.webservices.uber.model.TimeEstimate;
import com.lsjwzh.widget.materialloadingprogressbar.CircleProgressBar;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchResultsFragment.RouteResultsFragmentCommunicator} interface
 * to handle interaction events.
 * Use the {@link SearchResultsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchResultsFragment extends Fragment implements ResultsRecyclerViewAdapter.ResultsRecyclerViewAdapterCommunicator {

    private static final String TAG = SearchResultsFragment.class.getSimpleName();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";




    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private RouteResultsFragmentCommunicator communicator;

    private NetworkResultReceiver mNetworkResultReceiver;
    private RecyclerView mRecyclerView;
    private TextView mNoteTextView;
    private ResultsRecyclerViewAdapter mResultsRecyclerViewAdapter;
    private CircleProgressBar mCircleProgressBar;

    private CoordinatesPOJO mStartLocation;
    private CoordinatesPOJO mEndLocation;

    private ArrayList<RoutePrice> mRouteDataResults;

    private boolean mIsMetricSystem = false;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RouteResultsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchResultsFragment newInstance(String param1, String param2) {
        SearchResultsFragment fragment = new SearchResultsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public SearchResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate");
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView");
        View view = inflater.inflate(R.layout.fragment_search_results, container, false);

        // Note
//        mNoteTextView = (TextView) view.findViewById(R.id.note);
//        mNoteTextView.setText("* Approximated value, tip included");

        mCircleProgressBar = (CircleProgressBar) view.findViewById(R.id.progressBar);
        mCircleProgressBar.setShowProgressText(true);
        mCircleProgressBar.setColorSchemeResources(R.color.ridemeColorBackgroundAccent);

        // Initialize the RecyclerView
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);

        // Add the divider between each row
        mRecyclerView.addItemDecoration(new SimpleDividerItemDecoration(getActivity()));

        // Set the layout manager
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Set the adapter
        mResultsRecyclerViewAdapter = new ResultsRecyclerViewAdapter(getActivity(), new ArrayList<RoutePrice>());
        mResultsRecyclerViewAdapter.setResultsRecyclerViewAdapterCommunicator(this);

        mRecyclerView.setAdapter(mResultsRecyclerViewAdapter);

        mNetworkResultReceiver = new NetworkResultReceiver(new Handler());

        if(savedInstanceState != null) {
            mRouteDataResults = savedInstanceState.getParcelableArrayList(Constants.ROUTE_DATA_RESULTS);
            mStartLocation = savedInstanceState.getParcelable(Constants.START_LOCATION);
            mEndLocation = savedInstanceState.getParcelable(Constants.END_LOCATION);
            mResultsRecyclerViewAdapter.loadNewData(mRouteDataResults);
            view.findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        }
        else {
            mRouteDataResults = new ArrayList<>();

            final Intent receivedIntent = getActivity().getIntent();

            final Bundle bundle = receivedIntent.getExtras();
            mStartLocation = bundle.getParcelable(Constants.COORDINATES_START_LOCATION);
            mEndLocation = bundle.getParcelable(Constants.COORDINATES_END_LOCATION);

            // Request the data again
            startUberGetPriceEstimatesIntent(mStartLocation, mEndLocation); //
//            startTffGetEntity(mStartLocation, mEndLocation);
            startTffGetFares(mStartLocation, mEndLocation); //
//            startTffGetBusiness(mStartLocation, mEndLocation);
            startUberGetProducts(mStartLocation, mEndLocation); //
            startUberGetTimeEstimates(mStartLocation);
        }


        /**
         * TODO: Finish the integration
         startHailoGetEstimatedTimeOfArrival();
         startHailoGetNearbyDrivers();
         */
        return view;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Log.d(TAG, "onAttach");
        try {
            communicator = (RouteResultsFragmentCommunicator) activity;
        }
        catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement RouteResultsFragmentCommunicator");
        }
    }



    @Override
    public void onDetach() {
        super.onDetach();
        communicator = null;
        Log.d(TAG, "onDetach");
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(Constants.ROUTE_DATA_RESULTS, mRouteDataResults);
        outState.putParcelable(Constants.START_LOCATION, mStartLocation);
        outState.putParcelable(Constants.END_LOCATION, mEndLocation);
        super.onSaveInstanceState(outState);
    }

    public void updateRecyclerView(boolean orderByPrice) {
        if(orderByPrice) {
            mResultsRecyclerViewAdapter.orderListByPrice();
        }
        else {
            mResultsRecyclerViewAdapter.orderListById();
        }

        getActivity().findViewById(R.id.loadingPanel).setVisibility(View.GONE);
        mResultsRecyclerViewAdapter.notifyDataSetChanged();
    }

    public void addToList(TaxiFareFinderRoutePrice routePrice) {
        mResultsRecyclerViewAdapter.addItem(routePrice);
        mRouteDataResults.add(routePrice);
    }

    public void addToList(UberRoutePrice uberRoutePrice) {
        mResultsRecyclerViewAdapter.addItem(uberRoutePrice);
        mRouteDataResults.add(uberRoutePrice);
    }

    public void convertListFromKmsToMiles() {
        final List<RoutePrice> list = mResultsRecyclerViewAdapter.getRoutePrices();

        for(RoutePrice routePrice : list) {
            final double oldDistance = routePrice.getDistance();
            routePrice.setDistance(Utils.convertKmsToMiles(oldDistance, 1));
            routePrice.setMetricSystem(false);
        }
    }

    public void convertListFromMilesToMeters() {
        final List<RoutePrice> list = mResultsRecyclerViewAdapter.getRoutePrices();

        for(RoutePrice routePrice : list) {
            final double oldDistance = routePrice.getDistance();
            routePrice.setDistance(Utils.convertMilesToKms(oldDistance, 1));
            routePrice.setMetricSystem(true);
        }
    }

    public void setProgressBarStatus(int progress) {
        if(mCircleProgressBar != null) {
            mCircleProgressBar.setProgress(progress);
        }
    }

    public int getProgressBarStatus() {
        if (mCircleProgressBar != null) {
            return mCircleProgressBar.getProgress();
        }
        return 0;
    }

    public void setProductToUberRoutePrice(ArrayList<Product> uberProductsList) {

        for (int i = 0; i<mRouteDataResults.size(); i++) {
            if(mRouteDataResults.get(i) instanceof UberRoutePrice) {
                UberRoutePrice uberRoutePrice = (UberRoutePrice) mRouteDataResults.get(i);
                final String productId = uberRoutePrice.getProductId();

                for(Product product : uberProductsList) {
                    if(product.product_id.equalsIgnoreCase(productId)) {
                        uberRoutePrice.setCapacity(product.capacity);
                        uberRoutePrice.setProductDescription(product.description);
                    }
                }
            }
        }
    }

    public void setEstimatesToUberRoutePrice(ArrayList<TimeEstimate> timeEstimates) {

        for(int i = 0; i<mRouteDataResults.size(); i++) {
            if(mRouteDataResults.get(i) instanceof UberRoutePrice) {
                final UberRoutePrice uberRoutePrice = (UberRoutePrice) mRouteDataResults.get(i);
                final String productId = uberRoutePrice.getProductId();

                for(TimeEstimate timeEstimate : timeEstimates) {
                    if(timeEstimate.product_id.equalsIgnoreCase(productId)) {
                        uberRoutePrice.setPickupTime(timeEstimate.estimate);
                    }
                }
            }
        }
    }

    private void startUberGetPriceEstimatesIntent(CoordinatesPOJO startLocation, CoordinatesPOJO endLocation) {
        final Intent intent = new Intent(getActivity(), NetworkIntentService.class);
        intent.putExtra(Constants.NETWORK_OPERATION_TYPE, Constants.UBER_GET_PRICE_EESTIMATES);
        intent.putExtra(Constants.START_LOCATION, startLocation);
        intent.putExtra(Constants.END_LOCATION, endLocation);
        intent.putExtra(Constants.NETWORK_RESULT_RECEIVER, mNetworkResultReceiver);

        getActivity().getApplicationContext().startService(intent);
    }

    private void startUberGetProducts(CoordinatesPOJO startLocation, CoordinatesPOJO endLocation) {
        final Intent intent = new Intent(getActivity(), NetworkIntentService.class);
        intent.putExtra(Constants.NETWORK_OPERATION_TYPE, Constants.UBER_GET_PRODUCTS);
        intent.putExtra(Constants.START_LOCATION, startLocation);
        intent.putExtra(Constants.END_LOCATION, endLocation);
        intent.putExtra(Constants.NETWORK_RESULT_RECEIVER, mNetworkResultReceiver);

        getActivity().getApplicationContext().startService(intent);
    }

    private void startUberGetTimeEstimates(CoordinatesPOJO startLocation) {
        final Intent intent = new Intent(getActivity(), NetworkIntentService.class);
        intent.putExtra(Constants.NETWORK_OPERATION_TYPE, Constants.UBER_GET_TIME_ESTIMATE);
        intent.putExtra(Constants.START_LOCATION, startLocation);
        intent.putExtra(Constants.NETWORK_RESULT_RECEIVER, mNetworkResultReceiver);

        getActivity().getApplicationContext().startService(intent);
    }

    private void startHailoGetNearbyDrivers(CoordinatesPOJO startLocation, CoordinatesPOJO endLocation) {
//        startLocation = new CoordinatesPOJO("33.669497", "-117.978745", 0, true, "8562 Kelso Drive, Huntington Beach, CA 92646, USA");
//        endLocation = new CoordinatesPOJO("33.707205", "-118.007584", 0, false, "6911 Via Angelina Drive, Huntington Beach, CA 92647, USA");

        final Intent intent = new Intent(getActivity(), NetworkIntentService.class);
        intent.putExtra(Constants.NETWORK_OPERATION_TYPE, Constants.HAILO_GET_NEARBY_DRIVERS);
        intent.putExtra(Constants.START_LOCATION, startLocation);
        intent.putExtra(Constants.END_LOCATION, endLocation);
        intent.putExtra(Constants.NETWORK_RESULT_RECEIVER, mNetworkResultReceiver);

        getActivity().getApplicationContext().startService(intent);
    }

    private void startHailoGetEstimatedTimeOfArrival(CoordinatesPOJO startLocation, CoordinatesPOJO endLocation) {
        final Intent intent = new Intent(getActivity(), NetworkIntentService.class);
        intent.putExtra(Constants.NETWORK_OPERATION_TYPE, Constants.HAILO_GET_ESTIMATED_TIME_OF_ARRIVAL);
        intent.putExtra(Constants.START_LOCATION, startLocation);
        intent.putExtra(Constants.END_LOCATION, endLocation);
        intent.putExtra(Constants.NETWORK_RESULT_RECEIVER, mNetworkResultReceiver);

        getActivity().getApplicationContext().startService(intent);
    }

    private void startTffGetFares(CoordinatesPOJO startLocation, CoordinatesPOJO endLocation) {
        final Intent intent = new Intent(getActivity(), NetworkIntentService.class);
        intent.putExtra(Constants.NETWORK_OPERATION_TYPE, Constants.TFF_GET_FARES);
        intent.putExtra(Constants.START_LOCATION, startLocation);
        intent.putExtra(Constants.END_LOCATION, endLocation);
        intent.putExtra(Constants.NETWORK_RESULT_RECEIVER, mNetworkResultReceiver);

        getActivity().getApplicationContext().startService(intent);
    }

    private void startTffGetBusiness(CoordinatesPOJO startLocation, CoordinatesPOJO endLocation) {
//        startLocation = new CoordinatesPOJO("33.669497", "-117.978745", 0, true, "8562 Kelso Drive, Huntington Beach, CA 92646, USA");
//        endLocation = new CoordinatesPOJO("33.707205", "-118.007584", 0, false, "6911 Via Angelina Drive, Huntington Beach, CA 92647, USA");

//        TaxiFareFinder taxiFareFinder = new TaxiFareFinder();
//        taxiFareFinder.

        final Intent intent = new Intent(getActivity(), NetworkIntentService.class);
        intent.putExtra(Constants.NETWORK_OPERATION_TYPE, Constants.TFF_GET_BUSINESS);
        intent.putExtra(Constants.START_LOCATION, startLocation);
        intent.putExtra(Constants.END_LOCATION, endLocation);
        intent.putExtra(Constants.NETWORK_RESULT_RECEIVER, mNetworkResultReceiver);

        getActivity().getApplicationContext().startService(intent);
    }

    private void startTffGetEntity(CoordinatesPOJO startLocation, CoordinatesPOJO endLocation) {
//        startLocation = new CoordinatesPOJO("33.669497", "-117.978745", 0, true, "8562 Kelso Drive, Huntington Beach, CA 92646, USA");
//        endLocation = new CoordinatesPOJO("33.707205", "-118.007584", 0, false, "6911 Via Angelina Drive, Huntington Beach, CA 92647, USA");

//        TaxiFareFinder taxiFareFinder = new TaxiFareFinder();
//        taxiFareFinder.getEntityAsync(mStartLocation.getLat(), mStartLocation.getLng());

        final Intent intent = new Intent(getActivity(), NetworkIntentService.class);
        intent.putExtra(Constants.NETWORK_OPERATION_TYPE, Constants.TFF_GET_ENTITY);
        intent.putExtra(Constants.START_LOCATION, startLocation);
        intent.putExtra(Constants.END_LOCATION, endLocation);
        intent.putExtra(Constants.NETWORK_RESULT_RECEIVER, mNetworkResultReceiver);

        getActivity().getApplicationContext().startService(intent);
    }

    private void nearbyLocation(CoordinatesPOJO startLocation) {
//        startLocation = new CoordinatesPOJO("33.669497", "-117.978745", 15000, true, "8562 Kelso Drive, Huntington Beach, CA 92646, USA");

        final Intent intent = new Intent(getActivity(), NetworkIntentService.class);
        intent.putExtra(Constants.NETWORK_OPERATION_TYPE, Constants.GET_PLACE_DETAILS);
        intent.putExtra(Constants.START_LOCATION, startLocation);
        intent.putExtra(Constants.NETWORK_RESULT_RECEIVER, mNetworkResultReceiver);

        getActivity().getApplicationContext().startService(intent);
    }

    @Override
    public void itemClicked(RoutePrice routePrice, View sharedView) {
        communicator.onFragmentCommunication(routePrice, mStartLocation, mEndLocation, sharedView);
    }

    /**
     * This interface must be implemented by the activities that contain this
     * fragment in order to allow Fragment <-> Activity <-> Fragment communication
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface RouteResultsFragmentCommunicator {
        public void onFragmentCommunication(RoutePrice routePrice, CoordinatesPOJO startLocation, CoordinatesPOJO endLocation, View sharedView);
    }

}
