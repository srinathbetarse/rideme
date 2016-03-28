package com.dazito.android.rideme.webservices.hailo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Pedro on 18-01-2015.
 */
public class DriversLocation implements Parcelable {

    public final ArrayList<DriverLocation> drivers;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.drivers);
    }

    public DriversLocation(ArrayList<DriverLocation> drivers) {
        this.drivers = drivers;
    }

    private DriversLocation(Parcel in) {
        this.drivers = (ArrayList<DriverLocation>) in.readSerializable();
    }

    public static final Parcelable.Creator<DriversLocation> CREATOR = new Parcelable.Creator<DriversLocation>() {
        public DriversLocation createFromParcel(Parcel source) {
            return new DriversLocation(source);
        }

        public DriversLocation[] newArray(int size) {
            return new DriversLocation[size];
        }
    };
}
