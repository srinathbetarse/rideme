package com.dazito.android.rideme.webservices.taxistartup;

import com.dazito.android.rideme.webservices.taxistartup.model.request.AuthorizationPassword;
import com.dazito.android.rideme.webservices.taxistartup.model.request.AuthorizationRequest;
import com.dazito.android.rideme.webservices.taxistartup.model.request.AuthorizeByAuthPermitRequest;
import com.dazito.android.rideme.webservices.taxistartup.model.request.CreateOrderRequest;
import com.dazito.android.rideme.webservices.taxistartup.model.request.EstimateTrip;
import com.dazito.android.rideme.webservices.taxistartup.model.request.GetRegionInfoRequest;
import com.dazito.android.rideme.webservices.taxistartup.model.request.UpdatePassengerRequest;
import com.dazito.android.rideme.webservices.taxistartup.model.response.AuthorizeByAuthPermitResponse;
import com.dazito.android.rideme.webservices.taxistartup.model.response.AuthorizeByPhoneAndPasswordResponse;
import com.dazito.android.rideme.webservices.taxistartup.model.response.CreateOrderResponse;
import com.dazito.android.rideme.webservices.taxistartup.model.response.EstimateTripResponse;
import com.dazito.android.rideme.webservices.taxistartup.model.response.GetOrderResponse;
import com.dazito.android.rideme.webservices.taxistartup.model.response.GetOrderStatusResponse;
import com.dazito.android.rideme.webservices.taxistartup.model.response.GetRegionInfoResponse;
import com.dazito.android.rideme.webservices.taxistartup.model.response.GetSMSResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by Pedro on 09-02-2015.
 */
public interface TaxiStartupService {

    /**
     * Sms with password will be sent to the specified phone number.
     * @param authorizationPassword
     * @param cb
     */
    @POST("/api/in/json/RequestAuthorizationPassword")
    void requestSMS(@Body AuthorizationPassword authorizationPassword, Callback<GetSMSResponse> cb);

    /**
     * Sms with password will be sent to the specified phone number.
     * @param authorizationPassword
     */
    @POST("/api/in/json/RequestAuthorizationPassword")
    void requestSMS(@Body AuthorizationPassword authorizationPassword);


    @POST("/api/in/json/authorize")
    void authorizeByPhoneAndPassword(@Body AuthorizationRequest authorizationRequest, Callback<AuthorizeByPhoneAndPasswordResponse> cb);

    @POST("/api/in/json/authorize")
    void authorizeByAuthPermit(@Body AuthorizeByAuthPermitRequest authorizeByAuthPermitRequest, Callback<AuthorizeByAuthPermitResponse> cb );

    /**
     * Received token should be used in every API request when it’s required until token expiration
     * (401 UNAUTHORIZED response status code indicates that using token expired).
     * New token should be requested by Authorize method call with recent received
     * password when it’s necessary.
     * @param authorizationRequest
     * @return
     */
    @POST("api/in/json/authorization")
    AuthorizationRequest postAuthorization(@Body AuthorizationRequest authorizationRequest);

    @POST("/api/in/json/{sessionId}/estimate")
    void estimateTrip(@Path("sessionId") String sessionId, @Body EstimateTrip estimateTrip, Callback<EstimateTripResponse> cb);



    @POST("/api/in/json/UpdateProfile")
    void updatePassenger(@Body UpdatePassengerRequest updatePassengerRequest, Callback<Object> cb);


    @POST("/api/in/json/GetRegionInfo")
    void getRegionInfo(@Body GetRegionInfoRequest getRegionInfoRequest, Callback<GetRegionInfoResponse> cb);

    @POST("/api/in/json/{sessionId}/orders")
    void createOrder(@Path("sessionId") String sessionId, @Body CreateOrderRequest createOrderRequest, Callback<CreateOrderResponse> cb);

    @GET("/api/in/json/{sessionId}/orders/{orderId}")
    void getOrder(@Path("sessionId") String sessionId, @Path("orderId") String orderId, Callback<GetOrderResponse> cb);

    @GET("/api/in/json/{sessionId}/orders/{orderId}/status")
    void getOrderStatus(@Path("sessionId") String sessionId, @Path("orderId") String orderId, Callback<GetOrderStatusResponse> cb);

    @POST("/api/in/json/{sessionId}/estimate")
    EstimateTripResponse estimateTrip(@Path("sessionId") String sessionId, @Body EstimateTrip estimateTrip);
}
