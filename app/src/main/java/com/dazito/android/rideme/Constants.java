package com.dazito.android.rideme;

/**
 * Created by Pedro on 22-01-2015.
 */
public class Constants {

    /**
     * Success when doing the Geolocation coordinates to address operation.
     */
    public static final int COORDINATES_TO_ADDRESS_SUCCESS_RESULT = 0;

    /**
     * Failure when doing the Geolocation coordinates to address operation
     */
    public static final int COORDINATES_TO_ADDRESS_FAILURE_RESULT = 1;

    /**
     * Success when doing the Geolocation address to coordinates operation.
     */
    public static final int ADDRESS_TO_COORDINATES_SUCCESS_RESULT = 2;

    /**
     * Failure when doing the Geolocation address to coordinates operation
     */
    public static final int ADDRESS_TO_COORDINATES_FAILURE_RESULT = 3;

    /**
     * Convert from an address to GPS coordinates
     */
    public static final int ADDRESS_TO_COORDENATES = 4;

    /**
     * Convert from GPS coordinates to an address
     */
    public static final int COORDINATES_TO_ADDRESS = 5;

    public static final int UBER_GET_PRICE_EESTIMATES = 6;

    public static final int UBER_GET_PRODUCTS = 7;

    public static final int HAILO_GET_NEARBY_DRIVERS = 8;

    public static final int HAILO_GET_ESTIMATED_TIME_OF_ARRIVAL = 9;

    public static final int TFF_GET_FARES = 10;

    public static final int TFF_GET_BUSINESS = 11;

    public static final int TFF_GET_ENTITY = 12;

    public static final int UBER_X = 13;

    public static final int UBER_XL = 14;

    public static final int UBER_PLUS = 15;

    public static final int UBER_BLACK = 16;

    public static final int UBER_SUV = 17;

    public static final int GET_PLACE_DETAILS = 19;

    public static final int UBER_GET_TIME_ESTIMATE = 20;


    public static final String PACKAGE_NAME = "com.dazito.android.rideme";

    public static final String LOCATION_RESULT_RECEIVER_INTENT = PACKAGE_NAME + ".LOCATION_RESULT_RECEIVER_INTENT";

    public static final String NETWORK_RESULT_RECEIVER = PACKAGE_NAME + ".NETWORK_RESULT_RECEIVER";

    public static final String COORDINATES_TO_ADDRESS_RECEIVER = PACKAGE_NAME + ".COORDINATES_TO_ADDRESS_RECEIVER";

    public static final String ADDRESS_TO_COORDINATES_RECEIVER = PACKAGE_NAME + ".ADDRESS_TO_COORDINATES_RECEIVER";

    public static final String ADDRESS_RESULT_DATA_KEY = PACKAGE_NAME + ".ADDRESS_RESULT_DATA_KEY";

    public static final String COORDINATES_RESULT_DATA_KEY = PACKAGE_NAME + ".COORDINATES_RESULT_DATA_KEY";

    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";

    public static final String ADDRESS_DATA_EXTRA = PACKAGE_NAME + ".ADDRESS_DATA_EXTRA";

    public static final String GEOCODING_OPERATION_TYPE = PACKAGE_NAME + ".GEOCODING_OPERATION_TYPE";

    public static final String NETWORK_OPERATION_TYPE = PACKAGE_NAME + ".NETWORK_OPERATION_TYPE";

    public static final String START_LOCATION = PACKAGE_NAME + ".START_LOCATION";

    public static final String END_LOCATION = PACKAGE_NAME + ".END_LOCATION";

    public static final String NETWORK_OPERATION_DATA = PACKAGE_NAME + ".NETWORK_OPERATION_DATA";

    public static final String COORDINATES_START_LOCATION = PACKAGE_NAME + ".COORDINATES_START_LOCATION";

    public static final String COORDINATES_END_LOCATION = PACKAGE_NAME + ".COORDINATES_END_LOCATION";

    public static final String START_LOCATION_APPEND_TO_ADDRESS_FLAG = ".START_LOCATION_APPEND_TO_ADDRESS_FLAG";

    public static final String ROUTE_DATA_RESULTS = PACKAGE_NAME + ".ROUTE_DATA_RESULTS";

    public static final String NEARBY_TAXI_STANDS_RESULTS = PACKAGE_NAME + ".NEARBY_TAXI_STANDS_RESULTS";

    public static final String ROUTE_PRICE = PACKAGE_NAME + ".ROUTE_PRICE";

    public static final String SLIDE_UP_PANEL_STATE = PACKAGE_NAME + ".SLIDE_UP_PANEL_STATE";

    public static final String UBER = "Uber";
    public static final String TAXI_FARE_FINDER = "TaxiFareFinder";
    public static final String TAXI_FARE_FINDER_SERVICE_TYPE = "Taxi Service";
    public static final int TAXI_FARE_FINDER_CAPACITY = 4;
    public static final String TAXI_STAND_TYPE = "taxi_stand";

}

