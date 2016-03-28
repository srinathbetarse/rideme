package com.dazito.android.rideme.webservices.taxistartup.model.request;

/**
 * Created by Pedro on 09-02-2015.
 */
public class AuthorizationPassword {

    private final String phoneNumber;

    public AuthorizationPassword(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
