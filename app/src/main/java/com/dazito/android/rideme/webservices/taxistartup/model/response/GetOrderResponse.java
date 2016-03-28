package com.dazito.android.rideme.webservices.taxistartup.model.response;

import java.util.List;

/**
 * Created by Pedro on 11-02-2015.
 */
public class GetOrderResponse {
    public final CreditCard creditCard;
    public final OrderDetails orderDetails;
    public final TripSummary tripSummary;
    public final Estimation estimation;
    public final String future;
    public final Company company;
    public final Driver driver;
    public final String orderStatus;
    public final String orderStatusTime;

    public GetOrderResponse(CreditCard creditCard, OrderDetails orderDetails, TripSummary tripSummary,
                            Estimation estimation, String future, Company company, Driver driver,
                            String orderStatus, String orderStatusTime) {
        this.creditCard = creditCard;
        this.orderDetails = orderDetails;
        this.tripSummary = tripSummary;
        this.estimation = estimation;
        this.future = future;
        this.company = company;
        this.driver = driver;
        this.orderStatus = orderStatus;
        this.orderStatusTime = orderStatusTime;
    }
}

class CreditCard {
    public final String cardId;
    public final String status;
    public final String isDefault;
    public final String createdAt;
    public final String merchantName;
    public final String formattedName;
    public final String merchantId;
    public final String type;
    public final String displayName;

    public CreditCard(String cardId, String status, String isDefault, String createdAt, String merchantName,
                      String formattedName, String merchantId, String type, String displayName) {
        this.cardId = cardId;
        this.status = status;
        this.isDefault = isDefault;
        this.createdAt = createdAt;
        this.merchantName = merchantName;
        this.formattedName = formattedName;
        this.merchantId = merchantId;
        this.type = type;
        this.displayName = displayName;
    }
}

class OrderDetails {
    public final String pickupTime;
    public final String passengersNumber;
    public final List<String> paymentMethods;
    public final List<Waypoints> waypoints;

    public OrderDetails(String pickupTime, String passengersNumber, List<String> paymentMethods,
                        List<Waypoints> waypoints) {
        this.pickupTime = pickupTime;
        this.passengersNumber = passengersNumber;
        this.paymentMethods = paymentMethods;
        this.waypoints = waypoints;
    }
}

class Waypoints {
    public final Location location;
    public final Address address;
    public final String type;

    public Waypoints(Location location, Address address, String type) {
        this.location = location;
        this.address = address;
        this.type = type;
    }
}

class TripSummary {
    public final String time;
    public final String distance;
    public final TotalCost totalCost;
    public final TripCost tripCost;

    public TripSummary(String time, String distance, TotalCost totalCost, TripCost tripCost) {
        this.time = time;
        this.distance = distance;
        this.totalCost = totalCost;
        this.tripCost = tripCost;
    }
}

class Company {
    public final String region;
    public final String phone;
    public final String name;
    public final String about;
    public final String companyId;

    public Company(String region, String phone, String name, String about, String companyId) {
        this.region = region;
        this.phone = phone;
        this.name = name;
        this.about = about;
        this.companyId = companyId;
    }
}

class Driver {
    public final Car car;
    public final String driverId;
    public final String rating;
    public final User user;

    public Driver(Car car, String driverId, String rating, User user) {
        this.car = car;
        this.driverId = driverId;
        this.rating = rating;
        this.user = user;
    }
}

class Estimation {
    public final String time;
    public final String distance;

    public Estimation(String time, String distance) {
        this.time = time;
        this.distance = distance;
    }
}

class TotalCost {
    public final String amount;
    public final String formattedName;
    public final String currency;

    public TotalCost(String amount, String formattedName, String currency) {
        this.amount = amount;
        this.formattedName = formattedName;
        this.currency = currency;
    }
}

class TripCost {
    public final String amount;
    public final String currency;

    public TripCost(String amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }
}

class Car {
    public final String model;
    public final String numberPlate;
    public final String color;
    public final String maxPassengers;
    public final String year;

    public Car(String model, String numberPlate, String color, String maxPassengers, String year) {
        this.model = model;
        this.numberPlate = numberPlate;
        this.color = color;
        this.maxPassengers = maxPassengers;
        this.year = year;
    }
}