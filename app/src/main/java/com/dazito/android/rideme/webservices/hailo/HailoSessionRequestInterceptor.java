package com.dazito.android.rideme.webservices.hailo;

import retrofit.RequestInterceptor;

/**
 * Created by Pedro on 20-01-2015.
 */
public class HailoSessionRequestInterceptor implements RequestInterceptor {

    private final String prefixToken = "token  ";
    private final String token;

    public HailoSessionRequestInterceptor(String token) {
        this.token = prefixToken + token;
    }

    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("Authorization", token);
    }
}
