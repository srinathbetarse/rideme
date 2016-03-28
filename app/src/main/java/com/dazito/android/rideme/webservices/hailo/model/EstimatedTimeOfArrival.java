package com.dazito.android.rideme.webservices.hailo.model;

/**
 * Created by Pedro on 18-01-2015.
 */
public class EstimatedTimeOfArrival {

    public final int eta;
    public final int count;
    public final String service_type;

    public EstimatedTimeOfArrival(int eta, int count, String service_type) {
        this.eta = eta;
        this.count = count;
        this.service_type = service_type;
    }
}
