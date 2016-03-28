package com.dazito.android.rideme.webservices.taxifarefinder;

import com.dazito.android.rideme.webservices.taxifarefinder.model.Businesses;
import com.dazito.android.rideme.webservices.taxifarefinder.model.Entity;
import com.dazito.android.rideme.webservices.taxifarefinder.model.Fare;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Pedro on 28-01-2015.
 */
public interface TaxiFareFinderService {

    // https://api.taxifarefinder.com/entity?key=TFF_API_KEY&location=42.356261,-71.065334

    /**
     * Returns the nearest TaxiFareFinder entity to the given coordinates.
     * Each TFF supported entity is uniquely identified by a handle string,
     * which is required for some API calls.
     * By default, this call returns the nearest city/town entity supported by TFF.
     * @param apiKey the API access key
     * @param location the current user location (city/town) or latitude,longitude coordinates.
     */
    @GET("/entity")
    void getEntity(@Query("key") String apiKey, @Query("location") String location, Callback<Entity> cb);

    /**
     * Returns information about a particular taxi trip.
     * @param apiKey
     * @param entity_handle
     * @param origin lat,long
     * @param destination lat,long
     * @param cb
     */
    @GET("/fare")
    void getFare(@Query("key") String apiKey, @Query("entity_handle") String entity_handle, @Query("origin") String origin, @Query("destination") String destination, Callback<Fare> cb);

    /**
     * Returns TaxiFareFinder's list of local taxi businesses for an entity.
     * @param apiKey
     * @param entityHandle
     * @param cb
     */
    @GET("/businesses")
    void getBusinesses(@Query("key") String apiKey, @Query("entity_handle") String entityHandle, Callback<Businesses> cb);

    @GET("/entity")
    Entity getEntity(@Query("key") String apiKey, @Query("location") String location);

    @GET("/fare")
    Fare getFare(@Query("key") String apiKey, @Query("entity_handle") String entity_handle, @Query("origin") String origin, @Query("destination") String destination);

    @GET("/businesses")
    Businesses getBusinesses(@Query("key") String apiKey, @Query("entity_handle") String entityHandle);
}
