package com.dazito.android.rideme.webservices.taxistartup.model.request;

import com.dazito.android.rideme.webservices.taxistartup.model.request.Point;

/**
 * Created by Pedro on 10-02-2015.
 */
public class EstimateTrip {

    private final String bundle;
    private final String duration;
    private final String distance;
    private final Point pointA;
    private final Point pointB;

    public EstimateTrip(String bundle, String duration, String distance, Point pointA, Point pointB) {
        this.bundle = bundle;
        this.duration = duration;
        this.distance = distance;
        this.pointA = pointA;
        this.pointB = pointB;
    }

    public String getBundle() {
        return bundle;
    }

    public String getDuration() {
        return duration;
    }

    public String getDistance() {
        return distance;
    }

    public Point getPointA() {
        return pointA;
    }

    public Point getPointB() {
        return pointB;
    }
}
