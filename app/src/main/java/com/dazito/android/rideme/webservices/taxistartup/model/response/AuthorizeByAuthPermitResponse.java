package com.dazito.android.rideme.webservices.taxistartup.model.response;

/**
 * Created by Pedro on 11-02-2015.
 */
public class AuthorizeByAuthPermitResponse {

    public final String authPermit;
    public final String sessionId;

    public final Passenger passenger;

    public final Config config;

    public AuthorizeByAuthPermitResponse(String authPermit, String sessionId, Passenger passenger, Config config) {
        this.authPermit = authPermit;
        this.sessionId = sessionId;
        this.passenger = passenger;
        this.config = config;
    }
}

class Passenger {
    public final User user;
    public final String passengerId;
    public final String phone;
    public final boolean phoneVerified;
    public final boolean emailVerified;

    public Passenger(User user, String passengerId, String phone, boolean phoneVerified, boolean emailVerified) {
        this.user = user;
        this.passengerId = passengerId;
        this.phone = phone;
        this.phoneVerified = phoneVerified;
        this.emailVerified = emailVerified;
    }
}

class User {
    public final String phone;
    public final boolean phoneVerified;
    public final boolean emailVerified;

    public User(String phone, boolean phoneVerified, boolean emailVerified) {
        this.phone = phone;
        this.phoneVerified = phoneVerified;
        this.emailVerified = emailVerified;
    }
}

class Config{
    public final String creditCardSignUp;
    public final String defaultMerchantId;

    public Config(String creditCardSignUp, String defaultMerchantId) {
        this.creditCardSignUp = creditCardSignUp;
        this.defaultMerchantId = defaultMerchantId;
    }
}

