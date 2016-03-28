package com.dazito.android.rideme.webservices.hailo;

import retrofit.RestAdapter;

/**
 * Created by Pedro on 17-01-2015.
 */
public class HailoRestClient {

    private static final String BASE_URL = "https://api.hailoapp.com";
    private static final String token = "YOUR_HAILO_TOKEN";
    private HailoService mHailoService;

    public HailoRestClient()
    {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setEndpoint(BASE_URL)
                .setRequestInterceptor(new HailoSessionRequestInterceptor(token))
                .build();

        mHailoService = restAdapter.create(HailoService.class);
    }

    public HailoService getHailoService() {
        return mHailoService;
    }
}
