package com.dazito.android.rideme.webservices.taxistartup.model.request;

/**
 * Created by Pedro on 11-02-2015.
 */
public class AuthorizeByAuthPermitRequest {

    public final String authPermit;
    public final String bundle;
    public final String version;

    public AuthorizeByAuthPermitRequest(String authPermit, String bundle, String version) {
        this.authPermit = authPermit;
        this.bundle = bundle;
        this.version = version;
    }
}
