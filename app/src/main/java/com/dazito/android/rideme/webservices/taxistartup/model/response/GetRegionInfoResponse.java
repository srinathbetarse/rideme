package com.dazito.android.rideme.webservices.taxistartup.model.response;


import java.util.List;

/**
 * Created by Pedro on 11-02-2015.
 */
public class GetRegionInfoResponse {

    public final RegionInfo regionInfo;

    public GetRegionInfoResponse(RegionInfo regionInfo) {
        this.regionInfo = regionInfo;
    }
}

class RegionInfo{
    public final String addressConfirmRequired;
    public final List<String> allowedMerchantIds;
    public final IncludeRegion includeRegion;
    public final List<String> paymentMethods;
    public final String selectDriver;
    public final String serviceAvailable;
    public final String showEstimatedCost;
    public final List<Zone> zones;

    public RegionInfo(String addressConfirmRequired, List<String> allowedMerchantIds, IncludeRegion includeRegion,
                      List<String> paymentMethods, String selectDriver, String serviceAvailable,
                      String showEstimatedCost, List<Zone> zones) {
        this.addressConfirmRequired = addressConfirmRequired;
        this.allowedMerchantIds = allowedMerchantIds;
        this.includeRegion = includeRegion;
        this.paymentMethods = paymentMethods;
        this.selectDriver = selectDriver;
        this.serviceAvailable = serviceAvailable;
        this.showEstimatedCost = showEstimatedCost;
        this.zones = zones;
    }
}

//class AllowedMerchantIds {
//    public String allowedMerchantIds;
//}

class IncludeRegion {
    public final String latitude;
    public final String longitude;
    public final String radius;

    public IncludeRegion(String latitude, String longitude, String radius) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }
}

class Zone {
    public final String futureBookings;
    public final Region region;

    public Zone(String futureBookings, Region region) {
        this.futureBookings = futureBookings;
        this.region = region;
    }
}

class Region {
    public final String latitude;
    public final String longitude;
    public final String radius;

    public Region(String latitude, String longitude, String radius) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.radius = radius;
    }
}


