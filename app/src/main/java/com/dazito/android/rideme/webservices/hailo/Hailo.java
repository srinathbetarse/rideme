package com.dazito.android.rideme.webservices.hailo;

import com.dazito.android.rideme.RideMeEvents;
import com.dazito.android.rideme.webservices.hailo.model.DriversLocation;
import com.dazito.android.rideme.webservices.hailo.model.EstimatedTimesOfArrivals;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Pedro on 28-01-2015.
 */
public class Hailo {

    private HailoRestClient mHailoRestClient;

    public Hailo() {
        mHailoRestClient = new HailoRestClient();
    }

    public void getNearbyDriversAsync(String latitude, String longitude)
    {
        mHailoRestClient.getHailoService().getNearbyDrivers(latitude, longitude, new Callback<DriversLocation>() {
            @Override
            public void success(DriversLocation driversLocation, Response response) {
                if(response.getStatus() == 200) {
                    EventBus.getDefault().post(new RideMeEvents.HailoDriversLocationSuccess(driversLocation));
                }

                // TODO else do something...
            }

            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().post(new RideMeEvents.HailoDriversLocationError(error.getMessage()));
            }
        });
    }



    public void getEstimatedTimeOfArrivalAsync(String latitude, String longitude) {
        mHailoRestClient.getHailoService().getEstimatedTimeOfArrival(latitude, longitude, new Callback<EstimatedTimesOfArrivals>() {
            @Override
            public void success(EstimatedTimesOfArrivals estimatedTimesOfArrivals, Response response) {
                if (response.getStatus() == 200) {
                    EventBus.getDefault().post(new RideMeEvents.HailoDriverETASuccess(estimatedTimesOfArrivals));
                }

                // TODO else do something
            }

            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().post(new RideMeEvents.HailoDriverETAError(error.getMessage()));
            }
        });
    }

    public EstimatedTimesOfArrivals getEstimatedTimeOfArrival(String latitude, String longitude) throws Exception {
        return mHailoRestClient.getHailoService().getEstimatedTimeOfArrival(latitude, longitude);
    }

    public DriversLocation getNearbyDrivers(String latitude, String longitude) throws Exception {
        return mHailoRestClient.getHailoService().getNearbyDrivers(latitude, longitude);
    }
}
