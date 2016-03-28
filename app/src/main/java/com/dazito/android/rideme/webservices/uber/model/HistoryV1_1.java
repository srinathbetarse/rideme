package com.dazito.android.rideme.webservices.uber.model;

/**
 * Created by Pedro on 20-01-2015.
 */
public class HistoryV1_1 {

    public final String uuid;
    public final int request_time;
    public final String product_id;
    public final String status;
    public final float distance;
    public final int start_time;
    public final int end_time;

    public HistoryV1_1(String uuid, int request_time, String product_id, String status, float distance, int start_time, int end_time) {
        this.uuid = uuid;
        this.request_time = request_time;
        this.product_id = product_id;
        this.status = status;
        this.distance = distance;
        this.start_time = start_time;
        this.end_time = end_time;
    }
}
