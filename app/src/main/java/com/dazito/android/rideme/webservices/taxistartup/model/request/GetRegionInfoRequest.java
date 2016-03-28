package com.dazito.android.rideme.webservices.taxistartup.model.request;

/**
 * Created by Pedro on 11-02-2015.
 */
public class GetRegionInfoRequest {

    public final String token;
    public final String bundle;
    public final Point point;

    public GetRegionInfoRequest(String token, String bundle, Point point) {
        this.token = token;
        this.bundle = bundle;
        this.point = point;
    }
}
