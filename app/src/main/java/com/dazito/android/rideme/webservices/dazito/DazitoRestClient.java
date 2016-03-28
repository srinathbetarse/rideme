package com.dazito.android.rideme.webservices.dazito;

import retrofit.RestAdapter;

/**
 * Created by Pedro on 28-03-2015.
 */
public class DazitoRestClient {

    private static final String BASE_URL = "http://rideme.dazito.com";

    private DazitoService mDazitoService;

    public DazitoRestClient() {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .build();

        mDazitoService = restAdapter.create(DazitoService.class);
    }

    public DazitoService getDazitoService() {
        return mDazitoService;
    }
}
