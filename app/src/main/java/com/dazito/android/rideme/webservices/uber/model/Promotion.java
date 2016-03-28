package com.dazito.android.rideme.webservices.uber.model;

/**
 * Created by Pedro on 19-01-2015.
 */
public class Promotion {

    public final String display_text;
    public final String localized_value;
    public final String type;

    public Promotion(String display_text, String localized_value, String type) {
        this.display_text = display_text;
        this.localized_value = localized_value;
        this.type = type;
    }
}
