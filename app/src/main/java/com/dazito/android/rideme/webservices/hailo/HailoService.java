package com.dazito.android.rideme.webservices.hailo;


import com.dazito.android.rideme.webservices.hailo.model.DriversLocation;
import com.dazito.android.rideme.webservices.hailo.model.EstimatedTimesOfArrivals;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Pedro on 17-01-2015.
 */
public interface HailoService {


    // /drivers/near?latitude=51.510761&longitude=-0.1174437
    @GET("/drivers/near")
    void getNearbyDrivers(@Query("latitude") String latitude, @Query("longitude") String longitude, Callback<DriversLocation> cb);

    // /drivers/eta?latitude=51.510761&longitude=0.1174437
    @GET("/drivers/eta")
    void getEstimatedTimeOfArrival(@Query("latitude") String latitude, @Query("longitude") String longitude, Callback<EstimatedTimesOfArrivals> cb);

    @GET("/drivers/eta")
    EstimatedTimesOfArrivals getEstimatedTimeOfArrival(@Query("latitude") String latitude, @Query("longitude") String longitude);

    @GET("/drivers/near")
    DriversLocation getNearbyDrivers(@Query("latitude") String latitude, @Query("longitude") String longitude);
}
