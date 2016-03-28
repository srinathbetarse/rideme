package com.dazito.android.rideme;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Created by Pedro on 28-02-2015.
 */
public class Utils {

    private static final String TAG = Utils.class.getSimpleName();

    /**
     * One meter is equal to 0.0006 miles
     */
    private static final double milesUnitConvertValue = 0.621;

    /**
     * One mile is equal to 1.609 meters;
     */
    private static final double metersUnitConvertValue = 1.609;

    public static double convertMilesToKms(double distanceInMiles, int decimalUnits) {
        final double distanceInKm = distanceInMiles * metersUnitConvertValue;

        return decimalUnits(distanceInKm, decimalUnits);
    }

    public static double convertKiloUnitToUnit(double distanceInMeters, int decimalUnits) {
        final double kilo = 1000;
        final double distanceInKm = distanceInMeters / kilo;

        return decimalUnits(distanceInKm, decimalUnits);
    }

    public static int convertSecondsToMinutes(int timeInSeconds) {
        final double oneSecondInMinutes = 0.017;
        final double timeInMinutes = timeInSeconds * oneSecondInMinutes;

        final int t = (int) Math.round(timeInMinutes);

        return t;
    }

    public static int convertSecondsToMinutesRoundUp(int timeInSeconds) {
        final double oneSecondInMinutes = 0.017;
        final double timeInMinutes = timeInSeconds * oneSecondInMinutes;

        final int t = (int) Math.ceil(timeInMinutes);

        return t;
    }


    public static double decimalUnits(double value, int decimalUnits) {
        if (decimalUnits < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(decimalUnits, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public static double convertKmsToMiles(double distanceInMeters, int decimalUnits) {
        final double distanceInMiles = distanceInMeters * milesUnitConvertValue;

        return decimalUnits(distanceInMiles, decimalUnits);
    }
}
