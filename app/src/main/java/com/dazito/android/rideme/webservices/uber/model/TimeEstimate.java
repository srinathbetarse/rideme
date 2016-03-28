package com.dazito.android.rideme.webservices.uber.model;

/**
 * Created by Pedro on 19-01-2015.
 */
public class TimeEstimate {

    public final String product_id;
    public final String display_name;
    public final int estimate;

    public TimeEstimate(String product_id, String display_name, int estimate) {
        this.product_id = product_id;
        this.display_name = display_name;
        this.estimate = estimate;
    }
}
