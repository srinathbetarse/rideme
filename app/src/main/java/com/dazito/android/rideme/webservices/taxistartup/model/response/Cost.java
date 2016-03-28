package com.dazito.android.rideme.webservices.taxistartup.model.response;

/**
 * Created by Pedro on 10-02-2015.
 */
public class Cost {

    public final float amount;
    public final String currency;
    public final String formattedName;

    public Cost(float amount, String currency, String formattedName) {
        this.amount = amount;
        this.currency = currency;
        this.formattedName = formattedName;
    }
}
