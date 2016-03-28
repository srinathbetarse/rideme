package com.dazito.android.rideme.webservices.taxifarefinder;

import android.util.Log;

import com.dazito.android.rideme.RideMeEvents;
import com.dazito.android.rideme.webservices.taxifarefinder.model.Businesses;
import com.dazito.android.rideme.webservices.taxifarefinder.model.Entity;
import com.dazito.android.rideme.webservices.taxifarefinder.model.Fare;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Pedro on 31-01-2015.
 */
public class TaxiFareFinder {

    private static final String TAG = TaxiFareFinder.class.getSimpleName();

    private static final String API_KEY = "YOUR_API_KEY";

    private TaxiFareFinderRestClient mTaxiFareFinderRestClient;

    public TaxiFareFinder() {
        mTaxiFareFinderRestClient = new TaxiFareFinderRestClient();
    }

    public void getEntityAsync(String latitude, String longitude) {
        /**
         * location	lat/lng of location.
         * If city/state/country are not provided, or an entity cannot be found using those data,
         * the nearest entity to this lat/lng will be returned.
         */
        final String location = latitude + "," + longitude;

        mTaxiFareFinderRestClient.getTaxiFareFinderService().getEntity(API_KEY, location, new Callback<Entity>() {
            @Override
            public void success(Entity entity, Response response) {
                if(response.getStatus() == 200) {
                    Log.d(TAG, "Sucesso!");
                    EventBus.getDefault().post(new RideMeEvents.TFFEntitySuccess(entity));
                }
            }
            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "Error: " + error.getMessage());
                EventBus.getDefault().post(new RideMeEvents.TFFEntityError(error.getMessage()));
            }
        });
    }

    public Entity getEntity(String latitude, String longitude) throws Exception { // lança exception
        final String location = latitude + "," + longitude;
        return mTaxiFareFinderRestClient.getTaxiFareFinderService().getEntity(API_KEY, location);
    }

    public void getFareAsync(String entity_handle, String startLatitude, String startLongitude, String endLatitude, String endLongitude) {

        // origin	Origin lat/lng
        final String startLocation =  startLatitude + "," + startLongitude;
        final String endLocation = endLatitude + "," + endLongitude;

        mTaxiFareFinderRestClient.getTaxiFareFinderService().getFare(API_KEY, entity_handle, startLocation, endLocation, new Callback<Fare>() {
            @Override
            public void success(Fare fare, Response response) {
                EventBus.getDefault().post(new RideMeEvents.TFFFareSuccess(fare));
            }

            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().post(new RideMeEvents.TFFFareError(error.getMessage()));
            }
        });
    }

    public Fare getFare(String entity_handle, String startLatitude, String startLongitude, String endLatitude, String endLongitude) throws Exception {
        // origin	Origin lat/lng
        final String startLocation =  startLatitude + "," + startLongitude;
        final String endLocation = endLatitude + "," + endLongitude;

        return mTaxiFareFinderRestClient.getTaxiFareFinderService().getFare(API_KEY, entity_handle, startLocation, endLocation);
    }

    public void getBusinessesAsync(String entityHandle) {
        mTaxiFareFinderRestClient.getTaxiFareFinderService().getBusinesses(API_KEY, entityHandle, new Callback<Businesses>() {
            @Override
            public void success(Businesses businesses, Response response) {
                EventBus.getDefault().post(new RideMeEvents.TFFBusinessesSuccess(businesses));
            }

            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().post(new RideMeEvents.TFFBusinessesError(error.getMessage()));
            }
        });
    }

    public Businesses getBusinesses(String entityHandle) throws Exception {
        return mTaxiFareFinderRestClient.getTaxiFareFinderService().getBusinesses(API_KEY, entityHandle);
    }

    public void getFareWithEntityAsync(String startLatitude, String startLongitude, String endLatitude, String endLongitude) {
        final String startLocation = startLatitude + "," + endLatitude;
        final String endLocation = endLatitude + "," + endLongitude;
        mTaxiFareFinderRestClient.getTaxiFareFinderService().getEntity(API_KEY, startLocation, new Callback<Entity>() {
            @Override
            public void success(Entity entity, Response response) {
                mTaxiFareFinderRestClient.getTaxiFareFinderService().getFare(API_KEY, entity.handle, startLocation, endLocation, new Callback<Fare>() {
                    @Override
                    public void success(Fare fare, Response response) {
                        Log.d(TAG, "YEAHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH!! FARE RECEIVED!!!");
                        EventBus.getDefault().post(new RideMeEvents.TFFFareSuccess(fare));
                    }
                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(TAG, "Shit. Erro na fare: " + error.getMessage());
                        EventBus.getDefault().post(new RideMeEvents.TFFFareError(error.getMessage()));
                    }
                });
            }
            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "Shit erro na entity: " + error.getMessage());
                EventBus.getDefault().post(new RideMeEvents.TFFEntityError(error.getMessage()));
            }
        });
    }
}
