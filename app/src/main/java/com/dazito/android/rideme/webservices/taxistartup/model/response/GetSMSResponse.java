package com.dazito.android.rideme.webservices.taxistartup.model.response;

/**
 * Created by Pedro on 09-02-2015.
 */
public class GetSMSResponse {

    public final String recoveryCode;
    public final int codeLenght;
    public final boolean success;

    public GetSMSResponse(String recoveryCode, int codeLenght, boolean success) {
        this.recoveryCode = recoveryCode;
        this.codeLenght = codeLenght;
        this.success = success;
    }
}
