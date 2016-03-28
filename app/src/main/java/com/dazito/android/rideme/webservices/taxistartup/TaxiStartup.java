package com.dazito.android.rideme.webservices.taxistartup;

import android.util.Log;

import com.dazito.android.rideme.webservices.taxistartup.model.request.AuthorizationPassword;
import com.dazito.android.rideme.webservices.taxistartup.model.request.AuthorizationRequest;
import com.dazito.android.rideme.webservices.taxistartup.model.request.EstimateTrip;
import com.dazito.android.rideme.webservices.taxistartup.model.response.AuthorizeByPhoneAndPasswordResponse;
import com.dazito.android.rideme.webservices.taxistartup.model.response.EstimateTripResponse;
import com.dazito.android.rideme.webservices.taxistartup.model.response.GetSMSResponse;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Pedro on 09-02-2015.
 */
public class TaxiStartup {

    private static final String TAG = TaxiStartup.class.getSimpleName();

    private final TaxiStartupRestClient mTaxiStartupRestClient;

    public TaxiStartup() {
        mTaxiStartupRestClient = new TaxiStartupRestClient();
    }

    /**
     * Sms with password will be sent to the specified phone number.
     * @param authorizationPassword
     */
    public void requestSMSAsync(AuthorizationPassword authorizationPassword){
        mTaxiStartupRestClient.getTaxiStartupService().requestSMS(authorizationPassword, new Callback<GetSMSResponse>() {
            @Override
            public void success(GetSMSResponse getSMSResponse, Response response) {
                Log.d(TAG, "requestSMSAsync - Request SMS Success! Response: " + response.getStatus() + " RecoveryCode: " + getSMSResponse.recoveryCode);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "requestSMSAsync failed: " + error.getMessage());
            }
        });
    }

    public void authorizeByPhoneAndPasswordAsyn(AuthorizationRequest authorizationRequest) {
        mTaxiStartupRestClient.getTaxiStartupService().authorizeByPhoneAndPassword(authorizationRequest, new Callback<AuthorizeByPhoneAndPasswordResponse>() {
            @Override
            public void success(AuthorizeByPhoneAndPasswordResponse authorizeByPhoneAndPasswordResponse, Response response) {
                Log.d(TAG, "authorizeByPhoneAndPasswordAsyn success!");
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "authorizeByPhoneAndPasswordAsyn failure: " + error.getMessage());
            }
        });
    }

    /**
     * Sms with password will be sent to the specified phone number.
     * @param authorizationPassword
     */
    public void requestSMS(AuthorizationPassword authorizationPassword) throws Exception {
        mTaxiStartupRestClient.getTaxiStartupService().requestSMS(authorizationPassword);
    }


    /**
     * Received token should be used in every API request when it’s required until token expiration
     * (401 UNAUTHORIZED response status code indicates that using token expired).
     * New token should be requested by Authorize method call with recent received
     * password when it’s necessary.
     * @param authorizationRequest
     * @return
     */
    public AuthorizationRequest getAuthorizationRequest(AuthorizationRequest authorizationRequest) throws Exception {
        return mTaxiStartupRestClient.getTaxiStartupService().postAuthorization(authorizationRequest);
    }

    public void estimateTripAsync(String sessionId, EstimateTrip estimateTrip) {
        mTaxiStartupRestClient.getTaxiStartupService().estimateTrip(sessionId, estimateTrip, new Callback<EstimateTripResponse>() {
            @Override
            public void success(EstimateTripResponse estimateTripResponse, Response response) {
                Log.d(TAG, "estimateTripAsync - Success! Price estimated: " + estimateTripResponse.cost.formattedName);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "estimateTripAsync - Failed: " + error.getMessage());
            }
        });
    }

    public EstimateTripResponse estimateTrip(String sessionId, EstimateTrip estimateTrip) throws Exception {
        return mTaxiStartupRestClient.getTaxiStartupService().estimateTrip(sessionId, estimateTrip);
    }
}
