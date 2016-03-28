package com.dazito.android.rideme.webservices.taxistartup.model.request;

/**
 * Created by Pedro on 10-02-2015.
 */
public class Point {

    private final String latitude;
    private final String longitude;

    public Point(String latitude, String longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }
}
