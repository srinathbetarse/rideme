package com.dazito.android.rideme.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.dazito.android.rideme.webservices.taxifarefinder.model.FlatRates;

import java.util.ArrayList;

/**
 * Created by Pedro on 22-02-2015.
 */
public class TaxiFareFinderRoutePrice extends RoutePrice implements Parcelable {

    private double initialFare;
    private double meteredFare;
    private double tipAmount;
    private double tipPercentage;
    private int capacity;
    private String locale;
    private String rateArea;
    private ArrayList<FlatRates> extraCharges;
    private String displayName = "TaxiFareFinder";
    private String productDescription = "Taxi Service";

    // TODO: Use the builder pattern or pass in the Fare object to avoid this huge constructor parameter train
    public TaxiFareFinderRoutePrice(int id, String companyName, String serviceType, String serviceDescription, double price,
                                    String currencyCode, double distance, int duration,
                                    double initialFare, double meteredFare, double tipAmount, double tipPercentage,
                                    int capacity, String locale,
                                    String rateArea, ArrayList<FlatRates> extraCharges, boolean isMetricSystem) {
        super(id, companyName, serviceType, price, currencyCode, distance, duration, serviceDescription, isMetricSystem);
        this.initialFare = initialFare;
        this.meteredFare = meteredFare;
        this.tipAmount = tipAmount;
        this.capacity = capacity;
        this.locale = locale;
        this.rateArea = rateArea;
        this.extraCharges = extraCharges;
        this.tipPercentage = tipPercentage;
    }

    public double getInitialFare() {
        return initialFare;
    }

    public double getMeteredFare() {
        return meteredFare;
    }

    public double getTipAmount() {
        return tipAmount;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getLocale() {
        return locale;
    }

    public String getRateArea() {
        return rateArea;
    }

    public double getTipPercentage() {
        return tipPercentage;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getProductDescription() {
        return productDescription;
    }

    public double getDistanceInKms() {
        return super.getDistance()/1000;
    }

    public ArrayList<FlatRates> getExtraCharges() {
        return extraCharges;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeDouble(this.initialFare);
        dest.writeDouble(this.meteredFare);
        dest.writeDouble(this.tipAmount);
        dest.writeDouble(this.tipPercentage);
        dest.writeInt(this.capacity);
        dest.writeString(this.locale);
        dest.writeString(this.rateArea);
        dest.writeList(this.extraCharges);
        dest.writeString(this.displayName);
        dest.writeString(this.productDescription);
    }

    private TaxiFareFinderRoutePrice(Parcel in) {
        super(in);
        this.initialFare = in.readDouble();
        this.meteredFare = in.readDouble();
        this.tipAmount = in.readDouble();
        this.tipPercentage = in.readDouble();
        this.capacity = in.readInt();
        this.locale = in.readString();
        this.rateArea = in.readString();
        this.extraCharges = (ArrayList<FlatRates>) in.readArrayList(FlatRates.class.getClassLoader());
        this.displayName = in.readString();
        this.productDescription = in.readString();
    }

    public static final Creator<TaxiFareFinderRoutePrice> CREATOR = new Creator<TaxiFareFinderRoutePrice>() {
        public TaxiFareFinderRoutePrice createFromParcel(Parcel source) {
            return new TaxiFareFinderRoutePrice(source);
        }

        public TaxiFareFinderRoutePrice[] newArray(int size) {
            return new TaxiFareFinderRoutePrice[size];
        }
    };
}
