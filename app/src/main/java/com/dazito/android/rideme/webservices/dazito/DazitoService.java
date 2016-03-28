package com.dazito.android.rideme.webservices.dazito;

import com.dazito.android.rideme.webservices.dazito.request.Feedback;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.POST;

/**
 * Created by Pedro on 28-03-2015.
 */
public interface DazitoService {

    @POST("/feedback/")
    void sendFeedback(@Body Feedback feedback);

    @POST("/feedback/")
    void sendFeedback(@Body Feedback feedback, Callback<Feedback> callback);
}
