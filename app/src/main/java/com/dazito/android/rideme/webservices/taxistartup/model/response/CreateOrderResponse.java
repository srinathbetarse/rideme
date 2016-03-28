package com.dazito.android.rideme.webservices.taxistartup.model.response;

import com.dazito.android.rideme.webservices.taxistartup.model.request.Point;

import java.util.List;

/**
 * Created by Pedro on 11-02-2015.
 */
public class CreateOrderResponse {

    public final String bundle;
    public final String passengersNumber;
    public final List<String> paymentMethods;

    public final List<Waypoints> waypoints;

    public CreateOrderResponse(String bundle, String passengersNumber, List<String> paymentMethods, List<Waypoints> waypoints) {
        this.bundle = bundle;
        this.passengersNumber = passengersNumber;
        this.paymentMethods = paymentMethods;
        this.waypoints = waypoints;
    }
}

class Address {
    public final String city;
    public final String country;
    public final String houseNumber;
    public final Point point;
    public final String street;

    public Address(String city, String country, String houseNumber, Point point, String street) {
        this.city = city;
        this.country = country;
        this.houseNumber = houseNumber;
        this.point = point;
        this.street = street;
    }
}

class Location {
    public final String accuracy;
    public final String latitude;
    public final String longitude;
    public final String time;

    public Location(String accuracy, String latitude, String longitude, String time) {
        this.accuracy = accuracy;
        this.latitude = latitude;
        this.longitude = longitude;
        this.time = time;
    }
}