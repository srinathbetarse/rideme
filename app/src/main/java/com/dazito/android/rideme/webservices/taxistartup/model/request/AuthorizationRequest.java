package com.dazito.android.rideme.webservices.taxistartup.model.request;

/**
 * Created by Pedro on 09-02-2015.
 */
public class AuthorizationRequest {

    private final String phoneNumber;
    private final String password;

    public AuthorizationRequest(String phoneNumber, String password) {
        this.phoneNumber = phoneNumber;
        this.password = password;
    }


    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getPassword() {
        return password;
    }
}
