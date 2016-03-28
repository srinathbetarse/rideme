package com.dazito.android.rideme.webservices.uber.model;

/**
 * Created by Pedro on 20-01-2015.
 */
public class HistoryV1 {

    public final String uuid;
    public final int request_time;
    public final String product_id;
    public final String status;
    public final String start_time;

    public final StartLocation start_location;
    public final int end_time;
    public final EndLocation end_location;

    public HistoryV1(String uuid, int request_time, String product_id, String status, String start_time, StartLocation start_location, int end_time, EndLocation end_location) {
        this.uuid = uuid;
        this.request_time = request_time;
        this.product_id = product_id;
        this.status = status;
        this.start_time = start_time;
        this.start_location = start_location;
        this.end_time = end_time;
        this.end_location = end_location;
    }
}
