package com.dazito.android.rideme.webservices.uber.model;

/**
 * Created by Pedro on 19-01-2015.
 */
public class Price {

    public final String product_id;
    public final String currency_code;
    public final String display_name;
    public final String estimate;
    public final int low_estimate;
    public final int high_estimate;
    public final float surge_multiplier;
    public final int duration;
    public final double distance;

    public Price(String product_id, String currency_code, String display_name, String estimate,
                 int low_estimate, int high_estimate, float surge_multiplier, int duration, double distance) {
        this.product_id = product_id;
        this.currency_code = currency_code;
        this.display_name = display_name;
        this.estimate = estimate;
        this.low_estimate = low_estimate;
        this.high_estimate = high_estimate;
        this.surge_multiplier = surge_multiplier;
        this.duration = duration;
        this.distance = distance;
    }
}
