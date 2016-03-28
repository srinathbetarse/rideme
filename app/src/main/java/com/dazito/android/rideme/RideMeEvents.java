package com.dazito.android.rideme;

import com.dazito.android.rideme.gps.AddressPOJO;
import com.dazito.android.rideme.gps.CoordinatesPOJO;
import com.dazito.android.rideme.webservices.gmaps.model.nearbylocation.NearbyLocation;
import com.dazito.android.rideme.webservices.gmaps.model.referencedata.PlaceDetails;
import com.dazito.android.rideme.webservices.hailo.model.DriversLocation;
import com.dazito.android.rideme.webservices.hailo.model.EstimatedTimesOfArrivals;
import com.dazito.android.rideme.webservices.taxifarefinder.model.Businesses;
import com.dazito.android.rideme.webservices.taxifarefinder.model.Entity;
import com.dazito.android.rideme.webservices.taxifarefinder.model.Fare;
import com.dazito.android.rideme.webservices.uber.model.Prices;
import com.dazito.android.rideme.webservices.uber.model.Products;
import com.dazito.android.rideme.webservices.uber.model.TimeEstimates;

import java.util.ArrayList;

/**
 * Created by Pedro on 23-01-2015.
 */
public class RideMeEvents {

    public static class GeolocationAddressFetchedSuccess {
        private final ArrayList<AddressPOJO> address;

        public GeolocationAddressFetchedSuccess(ArrayList<AddressPOJO> address) {
            this.address = address;
        }

        public ArrayList<AddressPOJO>getAddress() {
            return address;
        }
    }

    public static class GeolocationAddressFetchedError {
        private final String errorDescription;
        private final int errorCode;

        public GeolocationAddressFetchedError(int errorCode, String errorDescription) {
            this.errorDescription = errorDescription;
            this.errorCode = errorCode;
        }

        public String getErrorDescription() {
            return errorDescription;
        }

        public int getErrorCode() {
            return errorCode;
        }
    }

    public static class GeolocationCoordinatesFetchedSuccess {
        private final ArrayList<CoordinatesPOJO> resultCoordinates;

        public GeolocationCoordinatesFetchedSuccess(ArrayList<CoordinatesPOJO> resultCoordinates) {
            this.resultCoordinates = resultCoordinates;
        }

        public ArrayList<CoordinatesPOJO> getResultCoordinates() {
            return resultCoordinates;
        }
    }

    public static class GeolocationCoordinatesFetchedError {
        private final String errorDescription;
        private final int errorCode;

        public GeolocationCoordinatesFetchedError(int errorCode, String errorDescription) {
            this.errorDescription = errorDescription;
            this.errorCode = errorCode;
        }

        public String getErrorDescription() {
            return errorDescription;
        }

        public int getErrorCode() {
            return errorCode;
        }
    }


    /**
     *          Hailo Events
     */

    public static class HailoDriversLocationSuccess {
        private DriversLocation mDriversLocation;

        public HailoDriversLocationSuccess(DriversLocation driversLocation) {
            mDriversLocation = driversLocation;
        }

        public DriversLocation getDriversLocation() {
            return mDriversLocation;
        }
    }

    public static class HailoDriversLocationError {
        private String errorMessage;

        public HailoDriversLocationError(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    public static class HailoDriverETASuccess {
        private EstimatedTimesOfArrivals mEstimatedTimesOfArrivals;

        public HailoDriverETASuccess(EstimatedTimesOfArrivals estimatedTimesOfArrivals) {
            mEstimatedTimesOfArrivals = estimatedTimesOfArrivals;
        }

        public EstimatedTimesOfArrivals getEstimatedTimesOfArrivals() {
            return mEstimatedTimesOfArrivals;
        }
    }

    public static class HailoDriverETAError {
        private String errorMessage;

        public HailoDriverETAError(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }


    /**
     *          Uber Events
     */


    public static class UberProductsSuccess {
        private Products mProducts;

        public UberProductsSuccess(Products products) {
            mProducts = products;
        }

        public Products getProducts() {
            return mProducts;
        }
    }

    public static class UberProductsError {
        private String mRetrofitError;

        public UberProductsError(String retrofitError) {
            mRetrofitError = retrofitError;
        }

        public String getRetrofitError() {
            return mRetrofitError;
        }
    }

    public static class UberPricesSuccess {
        private Prices mPrices;

        public UberPricesSuccess(Prices prices) {
            mPrices = prices;
        }

        public Prices getPrices() {
            return mPrices;
        }
    }

    public static class UberPricesError {
        private String mRetrofitError;

        public UberPricesError(String retrofitError) {
            mRetrofitError = retrofitError;
        }

        public String getRetrofitError() {
            return mRetrofitError;
        }
    }

    public static class UberTimeEstimatesSuccess {
        private TimeEstimates mTimeEstimates;

        public UberTimeEstimatesSuccess(TimeEstimates timeEstimates) {
            mTimeEstimates = timeEstimates;
        }

        public TimeEstimates getTimeEstimates() {
            return mTimeEstimates;
        }
    }

    public static class UberTimeEstimatesError {
        private String mRetrofitError;

        public UberTimeEstimatesError(String retrofitError) {
            mRetrofitError = retrofitError;
        }

        public String getRetrofitError() {
            return mRetrofitError;
        }
    }


    /**
     *          TaxiFaraFinder Events
     */

    public static class TFFEntitySuccess{
        private Entity mEntity;

        public TFFEntitySuccess(Entity entity) {
            mEntity = entity;
        }

        public Entity getEntity() {
            return mEntity;
        }
    }

    public static class TFFEntityError {
        private String mRetrofitError;

        public TFFEntityError(String retrofitError) {
            mRetrofitError = retrofitError;
        }

        public String getRetrofitError() {
            return mRetrofitError;
        }
    }

    public static class TFFFareSuccess {
        private Fare mFare;

        public TFFFareSuccess(Fare fare) {
            mFare = fare;
        }

        public Fare getFare() {
            return mFare;
        }
    }

    public static class TFFFareError {
        private String mRetrofitError;

        public TFFFareError(String retrofitError) {
            mRetrofitError = retrofitError;
        }

        public String getRetrofitError() {
            return mRetrofitError;
        }
    }

    public static class TFFBusinessesSuccess {
        private Businesses mBusinesses;

        public TFFBusinessesSuccess(Businesses businesses) {
            mBusinesses = businesses;
        }

        public Businesses getBusinesses() {
            return mBusinesses;
        }
    }

    public static class TFFBusinessesError {
        private String mRetrofitError;

        public TFFBusinessesError(String retrofitError) {
            mRetrofitError = retrofitError;
        }

        public String getRetrofitError() {
            return mRetrofitError;
        }
    }

    public static class GetRoutePricesFinished {

    }

    public static class NearbyLocationSuccess{
        private final NearbyLocation mNearbyLocation;

        public NearbyLocationSuccess(NearbyLocation nearbyLocation) {
            mNearbyLocation = nearbyLocation;
        }

        public NearbyLocation getNearbyLocation() {
            return mNearbyLocation;
        }
    }

    public static class NearbyLocationError {
        private final String errorMessage;

        public NearbyLocationError(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }

    public static class PlaceDetailsSuccess {
        private final ArrayList<PlaceDetails> mPlaceDetails;

        public PlaceDetailsSuccess(ArrayList<PlaceDetails> placeDetails) {
            mPlaceDetails = placeDetails;
        }

        public ArrayList<PlaceDetails> getPlaceDetails() {
            return mPlaceDetails;
        }
    }

    public static class PlaceDetailsError {
        private final String errorMessage;

        public PlaceDetailsError(String errorMessage) {
            this.errorMessage = errorMessage;
        }

        public String getErrorMessage() {
            return errorMessage;
        }
    }
}
