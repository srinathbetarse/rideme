package com.dazito.android.rideme.webservices.gmaps.model.referencedata;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Pedro on 22-02-2015.
 */
public class Result implements Parcelable {

    public final String icon;
    public final ArrayList<Reviews> reviews;
    public final String website;
    public final String user_ratings_total;
    public final String international_phone_number;
    public final String url;
    public final String reference;
    public final String vicinity;
    public final String name;
    public final String formatted_address;
    public final String formatted_phone_number;
    public final String rating;


    public Result(String icon, ArrayList<Reviews> reviews, String website, String user_ratings_total,
                  String international_phone_number, String url, String reference, String vicinity,
                  String name, String formatted_address, String formatted_phone_number, String rating) {
        this.icon = icon;
        this.reviews = reviews;
        this.website = website;
        this.user_ratings_total = user_ratings_total;
        this.international_phone_number = international_phone_number;
        this.url = url;
        this.reference = reference;
        this.vicinity = vicinity;
        this.name = name;
        this.formatted_address = formatted_address;
        this.formatted_phone_number = formatted_phone_number;
        this.rating = rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.icon);
        dest.writeSerializable(this.reviews);
        dest.writeString(this.website);
        dest.writeString(this.user_ratings_total);
        dest.writeString(this.international_phone_number);
        dest.writeString(this.url);
        dest.writeString(this.reference);
        dest.writeString(this.vicinity);
        dest.writeString(this.name);
        dest.writeString(this.formatted_address);
        dest.writeString(this.formatted_phone_number);
        dest.writeString(this.rating);
    }

    private Result(Parcel in) {
        this.icon = in.readString();
        this.reviews = (ArrayList<Reviews>) in.readSerializable();
        this.website = in.readString();
        this.user_ratings_total = in.readString();
        this.international_phone_number = in.readString();
        this.url = in.readString();
        this.reference = in.readString();
        this.vicinity = in.readString();
        this.name = in.readString();
        this.formatted_address = in.readString();
        this.formatted_phone_number = in.readString();
        this.rating = in.readString();
    }

    public static final Creator<Result> CREATOR = new Creator<Result>() {
        public Result createFromParcel(Parcel source) {
            return new Result(source);
        }

        public Result[] newArray(int size) {
            return new Result[size];
        }
    };
}
