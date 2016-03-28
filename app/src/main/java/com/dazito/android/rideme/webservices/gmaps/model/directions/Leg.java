package com.dazito.android.rideme.webservices.gmaps.model.directions;

import java.util.ArrayList;

/**
 * Created by Pedro on 10-02-2015.
 */
public class Leg {
    public final Distance distance;
    public final Duration duration;
    public final ArrayList<Steps> steps;

    public Leg(Distance distance, Duration duration, ArrayList<Steps> steps) {
        this.distance = distance;
        this.duration = duration;
        this.steps = steps;
    }
}
