package com.dazito.android.rideme.gps;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pedro on 18-01-2015.
 */
public class CoordinatesPOJO implements Parcelable {

    private final String lat;
    private final String lng;
    private final boolean isStartLocation;
    private int radius;
    private final String address;

    public CoordinatesPOJO(String lat, String lng, int radius, boolean isStartLocation, String address) {
        this.lat = lat;
        this.lng = lng;
        this.isStartLocation = isStartLocation;
        this.radius = radius;
        this.address = address;
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public boolean isStartLocation() {
        return isStartLocation;
    }

    public int getRadius() {
        return radius;
    }

    public String getAddress() {
        return address;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.lat);
        dest.writeString(this.lng);
        dest.writeByte(isStartLocation ? (byte) 1 : (byte) 0);
        dest.writeInt(this.radius);
        dest.writeString(this.address);
    }

    private CoordinatesPOJO(Parcel in) {
        this.lat = in.readString();
        this.lng = in.readString();
        this.isStartLocation = in.readByte() != 0;
        this.radius = in.readInt();
        this.address = in.readString();
    }

    public static final Creator<CoordinatesPOJO> CREATOR = new Creator<CoordinatesPOJO>() {
        public CoordinatesPOJO createFromParcel(Parcel source) {
            return new CoordinatesPOJO(source);
        }

        public CoordinatesPOJO[] newArray(int size) {
            return new CoordinatesPOJO[size];
        }
    };
}
