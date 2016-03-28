package com.dazito.android.rideme.webservices.gmaps.model.referencedata;

import java.util.List;

/**
 * Created by Pedro on 22-02-2015.
 */
public class Reviews  {

    public final String time;
    public final String text;
    public final String author_url;
    public final String author_name;
    public final List<Aspects> aspects;
    public final String rating;
    public final String language;

    public Reviews(String time, String text, String author_url, String author_name, List<Aspects> aspects, String rating, String language) {
        this.time = time;
        this.text = text;
        this.author_url = author_url;
        this.author_name = author_name;
        this.aspects = aspects;
        this.rating = rating;
        this.language = language;
    }
}
