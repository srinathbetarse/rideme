package com.dazito.android.rideme.webservices.taxistartup.model.request;

/**
 * Created by Pedro on 11-02-2015.
 */
public class UpdatePassengerRequest {

    public final String token;
    public final Passenger passenger;
    public final Properties properties;

    public UpdatePassengerRequest(String token, Passenger passenger, Properties properties) {
        this.token = token;
        this.passenger = passenger;
        this.properties = properties;
    }
}

class Passenger {
    public final String passengerId;
    public final User user;

    public Passenger(String passengerId, User user) {
        this.passengerId = passengerId;
        this.user = user;
    }
}

class User {
    public final String name;
    public final String phone;

    public User(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }
}

class Properties{
    public final String defaultCardId;

    public Properties(String defaultCardId) {
        this.defaultCardId = defaultCardId;
    }
}