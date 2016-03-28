package com.dazito.android.rideme.webservices.taxifarefinder.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Pedro on 27-01-2015.
 */
public class Fare implements Parcelable{

    /**
     * status code
     */
    public final String status;

    /**
     * Distance in meters of the route used to calculate this fare.
     */
    public final double distance;

    /**
     * Duration in seconds of the route used to calculate this fare.
     */
    public final int duration;

    /**
     * Total fare, including initial fare, metered fare, and tip.
     */
    public final double total_fare;

    /**
     * Flag drop charge, or charge for first portion of trip.
     */
    public final double initial_fare;

    /**
     * Additional metered fare based upon the length and duration of the trip.
     */
    public final double metered_fare;

    /**
     * Array of objects, each describing a flat rate that is applicable to this trip.
     */
    public final ArrayList<FlatRates> flat_rates;

    /**
     * Array of objects, each describing an extra charges that is applicable to this trip.
     */
    public final ArrayList<FlatRates> extra_charges;

    /**
     * Tip to add in addition to initial_fare + metered_fare.
     */
    public final double tip_amount;

    /**
     * Tip percentage that was used, customary to this region.
     */
    public final double tip_percentage;

    /**
     * Locale for entity used in fare calculation.
     */
    public final String locale;

    /**
     * Information about the local currency for this entity
     */
    public final Currency currency;

    /**
     * Name of municipality used for the rates. May differ from the entity passed in to this call,
     * for instance if the entity is part of a larger metropolitan area which governs taxi rates.
     */
    public final String rate_area;



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeDouble(this.distance);
        dest.writeInt(this.duration);
        dest.writeDouble(this.total_fare);
        dest.writeDouble(this.initial_fare);
        dest.writeDouble(this.metered_fare);
        dest.writeSerializable(this.flat_rates);
        dest.writeSerializable(this.extra_charges);
        dest.writeDouble(this.tip_amount);
        dest.writeDouble(this.tip_percentage);
        dest.writeString(this.locale);
        dest.writeParcelable(this.currency, 0);
        dest.writeString(this.rate_area);
    }

    public Fare(String status, double distance, int duration, double total_fare, double initial_fare,
                double metered_fare, ArrayList<FlatRates> flat_rates, ArrayList<FlatRates> extra_charges,
                double tip_amount, double tip_percentage, String locale, Currency currency, String rate_area) {
        this.status = status;
        this.distance = distance;
        this.duration = duration;
        this.total_fare = total_fare;
        this.initial_fare = initial_fare;
        this.metered_fare = metered_fare;
        this.flat_rates = flat_rates;
        this.extra_charges = extra_charges;
        this.tip_amount = tip_amount;
        this.tip_percentage = tip_percentage;
        this.locale = locale;
        this.currency = currency;
        this.rate_area = rate_area;
    }

    private Fare(Parcel in) {
        this.status = in.readString();
        this.distance = in.readDouble();
        this.duration = in.readInt();
        this.total_fare = in.readDouble();
        this.initial_fare = in.readDouble();
        this.metered_fare = in.readDouble();
        this.flat_rates = (ArrayList<FlatRates>) in.readSerializable();
        this.extra_charges = (ArrayList<FlatRates>) in.readSerializable();
        this.tip_amount = in.readDouble();
        this.tip_percentage = in.readDouble();
        this.locale = in.readString();
        this.currency = in.readParcelable(Currency.class.getClassLoader());
        this.rate_area = in.readString();
    }

    public static final Creator<Fare> CREATOR = new Creator<Fare>() {
        public Fare createFromParcel(Parcel source) {
            return new Fare(source);
        }

        public Fare[] newArray(int size) {
            return new Fare[size];
        }
    };
}
