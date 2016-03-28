package com.dazito.android.rideme.webservices.gmaps.model.referencedata;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pedro on 22-02-2015.
 */
public class PlaceDetails implements Parcelable {
    public final Result result;
    public final String status;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.result, flags);
        dest.writeString(this.status);
    }

    public PlaceDetails(Result result, String status) {
        this.result = result;
        this.status = status;
    }

    private PlaceDetails(Parcel in) {
        this.result = in.readParcelable(Result.class.getClassLoader());
        this.status = in.readString();
    }

    public static final Creator<PlaceDetails> CREATOR = new Creator<PlaceDetails>() {
        public PlaceDetails createFromParcel(Parcel source) {
            return new PlaceDetails(source);
        }

        public PlaceDetails[] newArray(int size) {
            return new PlaceDetails[size];
        }
    };
}
