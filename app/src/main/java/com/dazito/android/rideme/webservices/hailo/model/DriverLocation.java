package com.dazito.android.rideme.webservices.hailo.model;

/**
 * Created by Pedro on 18-01-2015.
 */
public class DriverLocation {

    public final double latitude;
    public final double longitude;
    public final String service_type;

    public DriverLocation(double latitude, double longitude, String service_type) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.service_type = service_type;
    }
}
