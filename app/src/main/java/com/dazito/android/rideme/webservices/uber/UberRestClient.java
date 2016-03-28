package com.dazito.android.rideme.webservices.uber;

import retrofit.RestAdapter;

/**
 * Created by Pedro on 19-01-2015.
 */
public class UberRestClient {

    private static final String BASE_URL = "https://api.uber.com";
    private static final String token = "MNEHeNjk2Q7uNN0CXn16WmQ3w0RWo8RYET8ZipFJ";
    private UberService mUberService;

    public UberRestClient()
    {
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setLogLevel(RestAdapter.LogLevel.BASIC)
                .setEndpoint(BASE_URL)
                .setRequestInterceptor(new UberSessionRequestInterceptor(token))
//                .setErrorHandler(new CustomErrorHandler())
                .build();

        mUberService = restAdapter.create(UberService.class);
    }

    public UberService getUberService()
    {
        return mUberService;
    }
}
