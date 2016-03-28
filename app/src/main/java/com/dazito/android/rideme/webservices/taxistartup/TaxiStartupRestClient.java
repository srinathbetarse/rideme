package com.dazito.android.rideme.webservices.taxistartup;

import retrofit.RestAdapter;

/**
 * Created by Pedro on 09-02-2015.
 */
public class TaxiStartupRestClient {

    private static final String BASE_URL = "http://server.sandbox.taxistartup.com:8079";
    private static final String token = "YOUR_TAXI_STARTUP_TOKEN";
    private TaxiStartupService mTaxiStartupService;

    public TaxiStartupRestClient() {

        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .build();

        mTaxiStartupService = restAdapter.create(TaxiStartupService.class);
    }

    public TaxiStartupService getTaxiStartupService() {
        return mTaxiStartupService;
    }
}
