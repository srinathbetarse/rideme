package com.dazito.android.rideme.webservices.gmaps.model.directions;

import java.util.ArrayList;

/**
 * Created by Pedro on 10-02-2015.
 */
public class Route {
    public final String copyrights;
    public final ArrayList<Leg> legs;
    public final OverviewPolyline overview_polyline;

    public Route(String copyrights, ArrayList<Leg> legs, OverviewPolyline overview_polyline) {
        this.copyrights = copyrights;
        this.legs = legs;
        this.overview_polyline = overview_polyline;
    }
}