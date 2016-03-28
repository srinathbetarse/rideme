package com.dazito.android.rideme.webservices.taxifarefinder.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pedro on 04-02-2015.
 */
public class Currency implements Parcelable{
    public final String int_symbol;

    public Currency(String int_symbol) {
        this.int_symbol = int_symbol;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.int_symbol);
    }

    private Currency(Parcel in) {
        this.int_symbol = in.readString();
    }

    public static final Creator<Currency> CREATOR = new Creator<Currency>() {
        public Currency createFromParcel(Parcel source) {
            return new Currency(source);
        }

        public Currency[] newArray(int size) {
            return new Currency[size];
        }
    };
}