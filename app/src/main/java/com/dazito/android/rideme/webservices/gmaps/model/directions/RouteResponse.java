package com.dazito.android.rideme.webservices.gmaps.model.directions;

import java.util.ArrayList;

/**
 * Created by Pedro on 10-02-2015.
 */
public class RouteResponse {
    public final ArrayList<Route> routes;
    public final String status;

    public RouteResponse(ArrayList<Route> routes, String status) {
        this.routes = routes;
        this.status = status;
    }
}




