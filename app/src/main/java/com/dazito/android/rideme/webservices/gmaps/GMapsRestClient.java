package com.dazito.android.rideme.webservices.gmaps;

import retrofit.RestAdapter;

/**
 * Created by Pedro on 10-02-2015.
 */
public class GMapsRestClient {

    private static final String BASE_URL = "https://maps.googleapis.com";

    private GMapsService mGMapsService;

    public GMapsRestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setEndpoint(BASE_URL)
                .build();

        mGMapsService = restAdapter.create(GMapsService.class);
    }

    public GMapsService getGMapsService() {
        return mGMapsService;
    }
}
