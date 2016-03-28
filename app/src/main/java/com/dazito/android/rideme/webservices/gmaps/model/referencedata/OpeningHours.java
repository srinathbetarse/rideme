package com.dazito.android.rideme.webservices.gmaps.model.referencedata;

/**
 * Created by Pedro on 22-02-2015.
 */
public class OpeningHours {
    public final Periods[] periods;
    public final String open_now;
    public final String[] weekday_text;

    public OpeningHours(Periods[] periods, String open_now, String[] weekday_text) {
        this.periods = periods;
        this.open_now = open_now;
        this.weekday_text = weekday_text;
    }
}
