package com.dazito.android.rideme.webservices.gmaps;

import com.dazito.android.rideme.webservices.gmaps.model.directions.RouteResponse;
import com.dazito.android.rideme.webservices.gmaps.model.nearbylocation.NearbyLocation;
import com.dazito.android.rideme.webservices.gmaps.model.referencedata.PlaceDetails;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Pedro on 10-02-2015.
 */
public interface GMapsService {

    /*
     * Directions - Defaults to Driving mode
     */

    @GET("/maps/api/directions/json")
    void getDrivingData(@Query("origin") String origin, @Query("destination") String destination, @Query("sensor") boolean sensor, Callback<RouteResponse> cb);

    @GET("/maps/api/directions/json")
    RouteResponse getDrivingData(@Query("origin") String origin, @Query("destination") String destination, @Query("sensor") boolean sensor);

    @GET("/maps/api/directions/json")
    void getDrivingDataBetweenTwoGPSCoordinates(@Query("origin") String originCoordinate, @Query("destination") String destinationCoordinate, @Query("sensor") boolean sensor, Callback<RouteResponse> cb);

    @GET("/maps/api/directions/json")
    RouteResponse getDrivingDistanceBetweenTwoGPSCoordinates(@Query("origin") String originCoordinate, @Query("destination") String destinationCoordinate, @Query("sensor") boolean sensor);

    @GET("/maps/api/place/details/json")
    void getPlaceDetails(@Query("key") String apiKey, @Query("sensor") boolean sensor, @Query("reference") String reference, Callback<PlaceDetails> cb);

    @GET("/maps/api/place/details/json")
    PlaceDetails getPlaceDetails(@Query("key") String apiKey, @Query("sensor") boolean sensor, @Query("reference") String reference);

    @GET("/maps/api/place/nearbysearch/json")
    void getPlaceDetails(@Query("key") String apiKey, @Query("location") String latLongCoordinates, @Query("radius") int radius, @Query("types") String types, @Query("sensor") boolean sensor, Callback<NearbyLocation> cb);

    @GET("/maps/api/place/nearbysearch/json")
    NearbyLocation getNearbySearch(@Query("key") String apiKey, @Query("location") String latLongCoordinates, @Query("radius") int radius, @Query("types") String types, @Query("sensor") boolean sensor);
}
