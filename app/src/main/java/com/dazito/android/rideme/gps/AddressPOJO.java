package com.dazito.android.rideme.gps;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pedro on 28-01-2015.
 */
public class AddressPOJO implements Parcelable{

    private final String street;
    private final String adminArea;
    private final String subAdminArea;
    private final String country;

    public AddressPOJO(String street, String adminArea, String subAdminArea, String country) {
        this.street = street;
        this.adminArea = adminArea;
        this.subAdminArea = subAdminArea;
        this.country = country;
    }

    public String getStreet() {
        if(street != null) {
            return street;
        }
        return "";
    }

    public String getAdminArea() {
        if(adminArea != null) {
            return adminArea;
        }
        return "";
    }

    public String getCountry() {
        if(country != null) {
            return country;
        }
        return "";
    }

    public String getSubAdminArea() {
        return subAdminArea;
    }


    @Override
    public String toString() {
        String aux = "";

        if(street != "" && street != null) {
            aux += street + ", ";
        }

        if (subAdminArea != "" && subAdminArea != null) {
            aux += subAdminArea + ", ";
        }

        if(adminArea != "" && adminArea != null) {
            aux += adminArea + ", ";
        }

        if (country != "" && country != null) {
            aux += country;
        }
        return aux;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.street);
        dest.writeString(this.adminArea);
        dest.writeString(this.subAdminArea);
        dest.writeString(this.country);
    }

    private AddressPOJO(Parcel in) {
        this.street = in.readString();
        this.adminArea = in.readString();
        this.subAdminArea = in.readString();
        this.country = in.readString();
    }

    public static final Creator<AddressPOJO> CREATOR = new Creator<AddressPOJO>() {
        public AddressPOJO createFromParcel(Parcel source) {
            return new AddressPOJO(source);
        }

        public AddressPOJO[] newArray(int size) {
            return new AddressPOJO[size];
        }
    };
}
