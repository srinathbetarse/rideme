package com.dazito.android.rideme.webservices.gmaps.model.referencedata;

/**
 * Created by Pedro on 22-02-2015.
 */
public class AddressComponents {
    public final String long_name;
    public final String[] types;
    public final String short_name;

    public AddressComponents(String long_name, String[] types, String short_name) {
        this.long_name = long_name;
        this.types = types;
        this.short_name = short_name;
    }
}
