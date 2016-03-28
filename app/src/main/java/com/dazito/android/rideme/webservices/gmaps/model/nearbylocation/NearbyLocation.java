package com.dazito.android.rideme.webservices.gmaps.model.nearbylocation;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Pedro on 22-02-2015.
 */
public class NearbyLocation implements Parcelable {
    public final  ArrayList<Results> results;
    public final String status;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.results);
        dest.writeString(this.status);
    }

    public NearbyLocation(ArrayList<Results> results, String status) {
        this.results = results;
        this.status = status;
    }

    private NearbyLocation(Parcel in) {
        this.results = (ArrayList<Results>) in.readSerializable();
        this.status = in.readString();
    }

    public static final Creator<NearbyLocation> CREATOR = new Creator<NearbyLocation>() {
        public NearbyLocation createFromParcel(Parcel source) {
            return new NearbyLocation(source);
        }

        public NearbyLocation[] newArray(int size) {
            return new NearbyLocation[size];
        }
    };
}
