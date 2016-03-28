package com.dazito.android.rideme.model;

/**
 * Created by Pedro on 05-02-2015.
 */
public class RouteDataResult {/**implements Parcelable {



    private final String productId;
    private final String formattedPrice;
    private final String companyName;
    private final String serviceDescription;
    private final String currencyName;
    private final double lowerPriceEstimate;
    private final double higherPriceEstimate;
    private final double price;
    private final double distance;
    private final int duration;

    public RouteDataResult(String productId, String companyName, String serviceDescription, String currencyName, double lowerPriceEstimate, double higherPriceEstimate, double price, double distance, int duration) {
        this.productId = productId;
        this.companyName = companyName;
        this.serviceDescription = serviceDescription;
        this.currencyName = currencyName;
        this.lowerPriceEstimate = lowerPriceEstimate;
        this.higherPriceEstimate = higherPriceEstimate;
        this.distance = distance;
        this.duration = duration;
        this.price = price;

        if(companyName.equalsIgnoreCase(Constants.UBER)) {
            formattedPrice = currencyName + " " + lowerPriceEstimate + "-" + higherPriceEstimate;
        }
        else if (companyName.equalsIgnoreCase(Constants.TAXI_FARE_FINDER)) {
            formattedPrice = currencyName + " " + String.valueOf(price);
        }
        else {
            formattedPrice = "Error";
            String x = "22.3";

            double y = Double.p
        }
    }

    public RouteDataResult(String companyName, String serviceDescription, String currencyName, double price, double distance, int duration) {
        this("", companyName, serviceDescription, currencyName, 0, 0, price, distance, duration);
    }
**/

}

