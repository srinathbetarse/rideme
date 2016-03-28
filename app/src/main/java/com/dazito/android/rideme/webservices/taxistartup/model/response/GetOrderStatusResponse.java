package com.dazito.android.rideme.webservices.taxistartup.model.response;


/**
 * Created by Pedro on 12-02-2015.
 */
public class GetOrderStatusResponse {
    public final DriverLocation driverLocation;
    public final String orderStatus;
    public final String orderStatusTime;

    public GetOrderStatusResponse(DriverLocation driverLocation, String orderStatus, String orderStatusTime) {
        this.driverLocation = driverLocation;
        this.orderStatus = orderStatus;
        this.orderStatusTime = orderStatusTime;
    }
}

class DriverLocation {
    public final String longitude;
    public final String latitude;

    public DriverLocation(String longitude, String latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }
}