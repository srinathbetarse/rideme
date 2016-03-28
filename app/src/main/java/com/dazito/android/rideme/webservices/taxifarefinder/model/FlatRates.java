package com.dazito.android.rideme.webservices.taxifarefinder.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pedro on 23-03-2015.
 */
public class FlatRates implements Parcelable{
    /**
     * Amount of charge.
     */
    public final String charge;

    /**
     * Explanation of charge.
     */
    public final String description;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.charge);
        dest.writeString(this.description);
    }

    public FlatRates(String charge, String description) {
        this.charge = charge;
        this.description = description;
    }

    private FlatRates(Parcel in) {
        this.charge = in.readString();
        this.description = in.readString();
    }

    public static final Creator<FlatRates> CREATOR = new Creator<FlatRates>() {
        public FlatRates createFromParcel(Parcel source) {
            return new FlatRates(source);
        }

        public FlatRates[] newArray(int size) {
            return new FlatRates[size];
        }
    };
}
