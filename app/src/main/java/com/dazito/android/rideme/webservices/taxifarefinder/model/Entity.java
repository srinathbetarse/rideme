package com.dazito.android.rideme.webservices.taxifarefinder.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Pedro on 27-01-2015.
 */
public class Entity implements Parcelable {

    /**
     * Status code
     * http://www.taxifarefinder.com/api.php#statuscodes
     */
    public final String status;

    /**
     * Short name for entity
     */
    public final String name;

    /**
     * Long name for entity
     */
    public final String full_name;

    /**
     * TFF handle for this entity
     */
    public final String handle;

    /**
     * Entity's locale
     */
    public final String locale;

    /**
     * Meters from given location to entity
     */
    public final int distance;

    /**
     * "geocode" or "nearest", depending on what method TFF was able to use to find the entity.
     */
    public String how_found;

    public Entity(String status, String name, String handle, String locale, int distance, String full_name, String how_found) {
        this.status = status;
        this.name = name;
        this.handle = handle;
        this.locale = locale;
        this.distance = distance;
        this.full_name = full_name;
        this.how_found = how_found;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.status);
        dest.writeString(this.name);
        dest.writeString(this.full_name);
        dest.writeString(this.handle);
        dest.writeString(this.locale);
        dest.writeInt(this.distance);
        dest.writeString(this.how_found);
    }

    private Entity(Parcel in) {
        this.status = in.readString();
        this.name = in.readString();
        this.full_name = in.readString();
        this.handle = in.readString();
        this.locale = in.readString();
        this.distance = in.readInt();
        this.how_found = in.readString();
    }

    public static final Creator<Entity> CREATOR = new Creator<Entity>() {
        public Entity createFromParcel(Parcel source) {
            return new Entity(source);
        }

        public Entity[] newArray(int size) {
            return new Entity[size];
        }
    };
}
