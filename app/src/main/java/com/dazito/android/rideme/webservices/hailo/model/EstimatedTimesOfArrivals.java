package com.dazito.android.rideme.webservices.hailo.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Pedro on 18-01-2015.
 */
public class EstimatedTimesOfArrivals implements Parcelable {

    public final ArrayList<EstimatedTimeOfArrival> etas;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.etas);
    }

    public EstimatedTimesOfArrivals(ArrayList<EstimatedTimeOfArrival> etas) {
        this.etas = etas;
    }

    private EstimatedTimesOfArrivals(Parcel in) {
        this.etas = (ArrayList<EstimatedTimeOfArrival>) in.readSerializable();
    }

    public static final Parcelable.Creator<EstimatedTimesOfArrivals> CREATOR = new Parcelable.Creator<EstimatedTimesOfArrivals>() {
        public EstimatedTimesOfArrivals createFromParcel(Parcel source) {
            return new EstimatedTimesOfArrivals(source);
        }

        public EstimatedTimesOfArrivals[] newArray(int size) {
            return new EstimatedTimesOfArrivals[size];
        }
    };
}
