package com.dazito.android.rideme.webservices.taxifarefinder;

import retrofit.RestAdapter;

/**
 * Created by Pedro on 28-01-2015.
 */
public class TaxiFareFinderRestClient {

    private static final String BASE_URL = "https://api.taxifarefinder.com";
    private TaxiFareFinderService mTaxiFareFinderService;

    public TaxiFareFinderRestClient()
    {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setEndpoint(BASE_URL)
                .build();

        mTaxiFareFinderService = restAdapter.create(TaxiFareFinderService.class);
    }

    public TaxiFareFinderService getTaxiFareFinderService() {
        return mTaxiFareFinderService;
    }
}
