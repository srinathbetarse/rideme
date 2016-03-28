package com.dazito.android.rideme.webservices.taxifarefinder.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Pedro on 28-01-2015.
 */
public class Businesses implements Parcelable{

    /**
     * status code
     */
    private String status;

    /**
     * Array of objects, each describing a taxi company local to this entity.
     * Each object contains the following properties:
     */
    public final ArrayList<Business> businesses;

    public Businesses(ArrayList<Business> businesses) {
        this.businesses = businesses;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeSerializable(this.businesses);
    }

    private Businesses(Parcel in) {
        this.status = in.readString();
        this.businesses = (ArrayList<Business>) in.readSerializable();
    }

    public static final Creator<Businesses> CREATOR = new Creator<Businesses>() {
        public Businesses createFromParcel(Parcel source) {
            return new Businesses(source);
        }

        public Businesses[] newArray(int size) {
            return new Businesses[size];
        }
    };

    public class Business {

        /**
         * Name of business
         */
        public String name;

        /**
         * Dispatch phone number of business
         */
        public String phone;

        /**
         * Business type: "taxi" or "limo"
         */
        public String type;
    }
}
