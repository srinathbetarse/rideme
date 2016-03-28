package com.dazito.android.rideme.webservices.uber.model;

/**
 * Created by Pedro on 19-01-2015.
 */
public class Product {

    public final String product_id;
    public final String description;
    public final String display_name;
    public final int capacity;
    public final String image;

    public Product(String product_id, String description, String display_name, int capacity, String image) {
        this.product_id = product_id;
        this.description = description;
        this.display_name = display_name;
        this.capacity = capacity;
        this.image = image;
    }
}
