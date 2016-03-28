package com.dazito.android.rideme.webservices.uber.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Pedro on 19-01-2015.
 */
public class TimeEstimates implements Parcelable{

    public final ArrayList<TimeEstimate> times;

    public TimeEstimates(ArrayList<TimeEstimate> times) {
        this.times = times;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.times);
    }

    private TimeEstimates(Parcel in) {
        this.times = (ArrayList<TimeEstimate>) in.readSerializable();
    }

    public static final Creator<TimeEstimates> CREATOR = new Creator<TimeEstimates>() {
        public TimeEstimates createFromParcel(Parcel source) {
            return new TimeEstimates(source);
        }

        public TimeEstimates[] newArray(int size) {
            return new TimeEstimates[size];
        }
    };
}
