package com.dazito.android.rideme.webservices.dazito.request;

/**
 * Created by Pedro on 28-03-2015.
 */
public class Feedback {

    private final String id;
    private final String name;
    private final String email;
    private final String message;

    public Feedback(String id, String name, String email, String message) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getMessage() {
        return message;
    }
}
