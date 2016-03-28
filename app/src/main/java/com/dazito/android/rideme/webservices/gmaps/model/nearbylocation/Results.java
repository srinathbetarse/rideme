package com.dazito.android.rideme.webservices.gmaps.model.nearbylocation;

/**
 * Created by Pedro on 22-02-2015.
 */
public class Results
{
    public final String id;
    public final String place_id;
    public final String icon;
    public final String vicinity;
    public final String scope;
    public final String name;
    public final String rating;
    public final String[] types;
    public final String reference;

    public Results(String id, String place_id, String icon, String vicinity, String scope, String name, String rating, String[] types, String reference) {
        this.id = id;
        this.place_id = place_id;
        this.icon = icon;
        this.vicinity = vicinity;
        this.scope = scope;
        this.name = name;
        this.rating = rating;
        this.types = types;
        this.reference = reference;
    }
}
