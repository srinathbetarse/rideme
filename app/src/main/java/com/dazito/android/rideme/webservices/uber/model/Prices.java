package com.dazito.android.rideme.webservices.uber.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Pedro on 19-01-2015.
 */
public class Prices implements Parcelable{

    public final ArrayList<Price> prices;

    public Prices(ArrayList<Price> prices) {
        this.prices = prices;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.prices);
    }

    private Prices(Parcel in) {
        this.prices = (ArrayList<Price>) in.readSerializable();
    }

    public static final Creator<Prices> CREATOR = new Creator<Prices>() {
        public Prices createFromParcel(Parcel source) {
            return new Prices(source);
        }

        public Prices[] newArray(int size) {
            return new Prices[size];
        }
    };
}
