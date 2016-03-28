package com.dazito.android.rideme.webservices.dazito;

import com.dazito.android.rideme.webservices.dazito.request.Feedback;

import retrofit.Callback;

/**
 * Created by Pedro on 28-03-2015.
 */
public class Dazito {

    private DazitoService mDazitoService;

    public Dazito() {
        mDazitoService = new DazitoRestClient().getDazitoService();
    }

    public void sendFeedback(Feedback feedback) {
        mDazitoService.sendFeedback(feedback);
    }

    public void sendFeedback(Feedback feedback, Callback callback) {
        mDazitoService.sendFeedback(feedback, callback);
    }


}
