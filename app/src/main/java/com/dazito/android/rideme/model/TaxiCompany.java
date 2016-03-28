package com.dazito.android.rideme.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pedro on 10-03-2015.
 */
public class TaxiCompany implements Parcelable {

    private final String name;
    private final String phoneNumber;
    private final String rating;

    public TaxiCompany(String name, String phoneNumber, String rating) {
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.rating = rating;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getRating() {
        return rating;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.name);
        dest.writeString(this.phoneNumber);
        dest.writeString(this.rating);
    }

    private TaxiCompany(Parcel in) {
        this.name = in.readString();
        this.phoneNumber = in.readString();
        this.rating = in.readString();
    }

    public static final Parcelable.Creator<TaxiCompany> CREATOR = new Parcelable.Creator<TaxiCompany>() {
        public TaxiCompany createFromParcel(Parcel source) {
            return new TaxiCompany(source);
        }

        public TaxiCompany[] newArray(int size) {
            return new TaxiCompany[size];
        }
    };
}
