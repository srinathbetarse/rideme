package com.dazito.android.rideme.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.Comparator;

/**
 * Created by Pedro on 22-02-2015.
 */
public class RoutePrice implements Parcelable, Comparator<RoutePrice>{

    private String companyName;
    private String serviceType;
    private double price;
    private String currency;
    private double distance;
    private int duration;
    private String serviceDescription; // Descrição do serviço, tipo descrição do uberXL
    private boolean isMetricSystem;
    private int id;

    // TODO: Use the builder pattern to avoid this huge constructor parameter train
    public RoutePrice(int id, String companyName, String serviceType, double price, String currency, double distance, int duration, String serviceDescription, boolean isMetricSystem) {
        this.companyName = companyName;
        this.serviceType = serviceType;
        this.price = price;
        this.currency = currency;
        this.distance = distance;
        this.duration = duration;
        this.serviceDescription = serviceDescription;
        this.isMetricSystem = isMetricSystem;
        this.id = id;
    }
    public String getCompanyName() {
        return companyName;
    }

    public String getServiceType() {
        return serviceType;
    }

    public double getPrice() {
        return price;
    }

    public String getCurrency() {
        return currency;
    }

    public double getDistance() {
        return distance;
    }

    public int getDuration() {
        return duration;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public boolean isMetricSystem() {
        return isMetricSystem;
    }

    public int getId() {
        return id;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public void setMetricSystem(boolean isMetricSystem) {
        this.isMetricSystem = isMetricSystem;
    }

    public static final Creator<RoutePrice> CREATOR = new Creator<RoutePrice>() {
        @Override
        public RoutePrice createFromParcel(Parcel in) {
            return new RoutePrice(in);
        }

        @Override
        public RoutePrice[] newArray(int size) {
            return new RoutePrice[size];
        }
    };

    @Override
    public int compare(RoutePrice rp1, RoutePrice rp2) {
        if (rp1.getPrice() < rp2.getPrice()) return -1;
        if (rp1.getPrice() > rp2.getPrice()) return 1;
        return 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.companyName);
        dest.writeString(this.serviceType);
        dest.writeDouble(this.price);
        dest.writeString(this.currency);
        dest.writeDouble(this.distance);
        dest.writeInt(this.duration);
        dest.writeString(this.serviceDescription);
        dest.writeByte(isMetricSystem ? (byte) 1 : (byte) 0);
        dest.writeInt(this.id);
    }

    protected RoutePrice(Parcel in) {
        readFromParcel(in);
    }

    public void readFromParcel(Parcel in) {
        this.companyName = in.readString();
        this.serviceType = in.readString();
        this.price = in.readDouble();
        this.currency = in.readString();
        this.distance = in.readDouble();
        this.duration = in.readInt();
        this.serviceDescription = in.readString();
        this.isMetricSystem = in.readByte() != 0;
        this.id = in.readInt();
    }

}