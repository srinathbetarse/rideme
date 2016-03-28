package com.dazito.android.rideme.webservices.uber;

import com.dazito.android.rideme.webservices.uber.model.Prices;
import com.dazito.android.rideme.webservices.uber.model.Products;
import com.dazito.android.rideme.webservices.uber.model.Profile;
import com.dazito.android.rideme.webservices.uber.model.Promotion;
import com.dazito.android.rideme.webservices.uber.model.TimeEstimates;
import com.dazito.android.rideme.webservices.uber.model.UserActivityV1;
import com.dazito.android.rideme.webservices.uber.model.UserActivityV1_1;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by Pedro on 19-01-2015.
 */
public interface UberService {

    @GET("/v1/products")
    void getProducts(@Query("latitude") String latitude, @Query("longitude") String longitude, Callback<Products> cb);

    @GET("/v1/estimates/price")
    void getPriceEstimates(@Query("start_latitude") String startLatitude, @Query("start_longitude") String startLongitude,
                           @Query("end_latitude") String endLatitude, @Query("end_longitude") String endLongitude, Callback<Prices> cb);

    /**
     *
     * @param startLatitude
     * @param startLongitude
     * @param customerUuid Optional parameter
     * @param productId Optional parameter
     * @param cb
     */
    @GET("/v1/estimates/time")
    void getTimeEstimates(@Query("start_latitude") String startLatitude, @Query("start_longitude") String startLongitude,
                          @Query("customer_uuid") String customerUuid, @Query("product_id") String productId, Callback<TimeEstimates> cb);

    @GET("/v1/promotions")
    void getPromotions(@Query("start_latitude") String startLatitude, @Query("start_longitude") String startLongitude,
                       @Query("end_latitude") String endLatitude, @Query("end_longitude") String endLongitude, Callback<Promotion> cb);

    @GET("/v1.1/history")
    void getUserActivityV1_1(@Query("offset") int offset, @Query("limit") int limite, Callback<UserActivityV1_1> cb);

    @GET("/v1/history")
    void getUserActivityV1(@Query("offset") int offset, @Query("limit") int limite, Callback<UserActivityV1> cb);

    /**
     * Get the user profile information.
     * @param cb
     */
    @GET("/v1/me")
    void getUserProfile(Callback<Profile> cb);

    @GET("/v1/products")
    Products getProducts(@Query("latitude") String latitude, @Query("longitude") String longitude);

    @GET("/v1/estimates/price")
    Prices getPriceEstimates(@Query("start_latitude") String startLatitude, @Query("start_longitude") String startLongitude,
                           @Query("end_latitude") String endLatitude, @Query("end_longitude") String endLongitude);

    /**
     *
     * @param startLatitude
     * @param startLongitude
     * @param customerUuid Optional parameter
     * @param productId Optional parameter
     */
    @GET("/v1/estimates/time")
    TimeEstimates getTimeEstimates(@Query("start_latitude") String startLatitude, @Query("start_longitude") String startLongitude,
                          @Query("customer_uuid") String customerUuid, @Query("product_id") String productId);

    @GET("/v1/estimates/time")
    TimeEstimates getTimeEstimates(@Query("start_latitude") String startLatitude, @Query("start_longitude") String startLongitude,
                                   @Query("product_id") String productId);

    @GET("/v1/estimates/time")
    TimeEstimates getTimeEstimates(@Query("start_latitude") String startLatitude, @Query("start_longitude") String startLongitude);

    @GET("/v1/promotions")
    Promotion getPromotions(@Query("start_latitude") String startLatitude, @Query("start_longitude") String startLongitude,
                       @Query("end_latitude") String endLatitude, @Query("end_longitude") String endLongitude);

    @GET("/v1.1/history")
    UserActivityV1_1 getUserActivityV1_1(@Query("offset") int offset, @Query("limit") int limite);

    @GET("/v1/history")
    UserActivityV1 getUserActivityV1(@Query("offset") int offset, @Query("limit") int limite);

    /**
     * Get the user profile information.
     */
    @GET("/v1/me")
    Profile getUserProfile();
}
