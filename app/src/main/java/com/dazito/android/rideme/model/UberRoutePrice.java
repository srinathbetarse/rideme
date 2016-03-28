package com.dazito.android.rideme.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pedro on 22-02-2015.
 */
public class UberRoutePrice extends RoutePrice implements Parcelable {

    private String productId;
    private int lowerEstimate;
    private int mHigerEstimate;
    private String estimate;
    private double surge_multiplier;
    private String productDescription;
    private String displayName;
    private int capacity;
    private int pickupTime;

    // TODO: Use the builder pattern or pass in the Price object to avoid this huge constructor parameter train
    public UberRoutePrice(int id, String companyName, String serviceType, int price, String currencyCode,
                          double distance, int duration, String productId, int lowerEstimate,
                          int higherEstimate, String estimate, float surge_multiplier,
                          String productDescription, String displayName, boolean isMetricSystem,
                          int capacity, int pickupTime) {

        super(id, companyName, serviceType, price, currencyCode, distance, duration, productDescription, isMetricSystem);
        this.productId = productId;
        this.lowerEstimate = lowerEstimate;
        this.mHigerEstimate = higherEstimate;
        this.estimate = estimate;
        this.surge_multiplier = surge_multiplier;
        this.productDescription = productDescription;
        this.displayName = displayName;
        this.capacity = capacity;
        this.pickupTime = pickupTime;
    }

    public String getProductId() {
        return productId;
    }

    public int getLowerEstimate() {
        return lowerEstimate;
    }

    public int getHigherEstimate() {
        return mHigerEstimate;
    }

    public String getEstimate() {
        return estimate;
    }

    public double getSurge_multiplier() {
        return surge_multiplier;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public void setPickupTime(int pickupTime) {
        this.pickupTime = pickupTime;
    }

    public int getCapacity() {
        return capacity;
    }

    public int getPickupTime() {
        return pickupTime;
    }

    public void setProductDescription(String productDescription) {
        this.productDescription = productDescription;
    }


    @Override
    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeInt(lowerEstimate);
        out.writeInt(mHigerEstimate);
        out.writeString(estimate);
        out.writeDouble(surge_multiplier);
        out.writeString(productDescription);
        out.writeString(displayName);
        out.writeInt(capacity);
        out.writeInt(pickupTime);
    }


    private UberRoutePrice(Parcel in) {
        super(in);
        lowerEstimate = in.readInt();
        mHigerEstimate = in.readInt();
        estimate = in.readString();
        surge_multiplier = in.readDouble();
        productDescription = in.readString();
        displayName = in.readString();
        capacity = in.readInt();
        pickupTime = in.readInt();
    }



    public static final Creator<UberRoutePrice> CREATOR = new Creator<UberRoutePrice>() {
        public UberRoutePrice createFromParcel(Parcel source) {
            return new UberRoutePrice(source);
        }

        public UberRoutePrice[] newArray(int size) {
            return new UberRoutePrice[size];
        }
    };
}
