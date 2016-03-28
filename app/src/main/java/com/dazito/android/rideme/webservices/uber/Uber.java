package com.dazito.android.rideme.webservices.uber;

import com.dazito.android.rideme.RideMeEvents;
import com.dazito.android.rideme.webservices.uber.model.Prices;
import com.dazito.android.rideme.webservices.uber.model.Products;
import com.dazito.android.rideme.webservices.uber.model.TimeEstimates;

import de.greenrobot.event.EventBus;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Pedro on 31-01-2015.
 */
public class Uber {

    private static final String TAG = Uber.class.getSimpleName();

    private UberRestClient mUberRestClient;

    public Uber() {
        mUberRestClient = new UberRestClient();
    }

    public void getProductsAsync(String latitude, String longitude) {
        mUberRestClient.getUberService().getProducts(latitude, longitude, new Callback<Products>() {
            @Override
            public void success(Products products, Response response) {
                if(response.getStatus() == 200) {
                    EventBus.getDefault().post(new RideMeEvents.UberProductsSuccess(products));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().post(new RideMeEvents.UberProductsError(error.getMessage()));
            }
        });
    }

    public void getPriceEstimatesAsync(String startLatitude, String startLongitude, String endLatitude, String endLongitude) {
        mUberRestClient.getUberService().getPriceEstimates(startLatitude, startLongitude, endLatitude, endLongitude, new Callback<Prices>() {
            @Override
            public void success(Prices prices, Response response) {
                if(response.getStatus() == 200) {
                    EventBus.getDefault().post(new RideMeEvents.UberPricesSuccess(prices));
                }
            }

            @Override
            public void failure(RetrofitError error) {
                EventBus.getDefault().post(new RideMeEvents.UberPricesError(error.getMessage()));
            }
        });
    }

    // TODO getTimeEstimates, getPromotions, getUserActivityV1_1, getUserActivityV1, getUserProfile

    /**
     *  SYNC
     */

    public Products getProducts(String latitude, String longitude) throws Exception {
        return mUberRestClient.getUberService().getProducts(latitude, longitude);
    }

    public Prices getPriceEstimates(String startLatitude, String startLongitude, String endLatitude, String endLongitude) throws Exception {
        return mUberRestClient.getUberService().getPriceEstimates(startLatitude, startLongitude, endLatitude, endLongitude);
    }

    public TimeEstimates getTimeEstimate(String latitude, String longitude) throws Exception {
        return mUberRestClient.getUberService().getTimeEstimates(latitude, longitude);
    }
}
