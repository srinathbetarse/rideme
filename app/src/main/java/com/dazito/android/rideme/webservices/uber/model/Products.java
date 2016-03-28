package com.dazito.android.rideme.webservices.uber.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Pedro on 19-01-2015.
 */
public class Products implements Parcelable {

    public final ArrayList<Product> products;

    public Products(ArrayList<Product> products) {
        this.products = products;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeSerializable(this.products);
    }

    private Products(Parcel in) {
        this.products = (ArrayList<Product>) in.readSerializable();
    }

    public static final Parcelable.Creator<Products> CREATOR = new Parcelable.Creator<Products>() {
        public Products createFromParcel(Parcel source) {
            return new Products(source);
        }

        public Products[] newArray(int size) {
            return new Products[size];
        }
    };
}
