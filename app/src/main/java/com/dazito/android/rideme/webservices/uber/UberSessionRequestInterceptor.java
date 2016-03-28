package com.dazito.android.rideme.webservices.uber;

import retrofit.RequestInterceptor;

/**
 * Created by Pedro on 20-01-2015.
 */
public class UberSessionRequestInterceptor implements RequestInterceptor {

    private final String prefixToken = "Token ";
    private final String token;

    public UberSessionRequestInterceptor(String token) {
        this.token = prefixToken + token;
    }

    @Override
    public void intercept(RequestFacade request) {
        request.addHeader("Authorization", token);
    }
}
