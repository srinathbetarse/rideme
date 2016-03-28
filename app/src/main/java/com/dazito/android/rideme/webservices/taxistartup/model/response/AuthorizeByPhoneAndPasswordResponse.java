package com.dazito.android.rideme.webservices.taxistartup.model.response;


/**
 * Created by Pedro on 09-02-2015.
 */
public class AuthorizeByPhoneAndPasswordResponse {

    public final String sessionId;
    public final Passenger passenger;
    public final Properties properties;
    public final String authPermit;

    public AuthorizeByPhoneAndPasswordResponse(String sessionId, Passenger passenger, Properties properties, String authPermit) {
        this.sessionId = sessionId;
        this.passenger = passenger;
        this.properties = properties;
        this.authPermit = authPermit;
    }

    public class Passenger {
        public final String passengerId;
        public final User user;

        public Passenger(String passengerId, User user) {
            this.passengerId = passengerId;
            this.user = user;
        }
    }

    public class User {
        public final String phone;

        public User(String phone) {
            this.phone = phone;
        }
    }

    public class Properties {
        public final String defaultCardId;

        public Properties(String defaultCardId) {
            this.defaultCardId = defaultCardId;
        }
    }
}
