package com.dazito.android.rideme.webservices.uber.model;

/**
 * Created by Pedro on 20-01-2015.
 */
public class Profile {

    public final String first_name;
    public final String last_name;
    public final String email;
    public final String picture;
    public final String promo_code;
    public final String uuid;

    public Profile(String first_name, String last_name, String email, String picture, String promo_code, String uuid) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.email = email;
        this.picture = picture;
        this.promo_code = promo_code;
        this.uuid = uuid;
    }
}
