package br.com.italoromeiro.arctouchtest.core;

/**
 * Created by italo on 13/08/16.
 */
public class GeneralConstants {
    public static final String REQUEST_STREET_NAME = "street_name";

    public static final int LOCATION_PERMISSION_REQUEST_CODE = 100;

    public static final double LAT_DEFAULT = -23.563223;
    public static final double LON_DEFAULT = -46.680953;

    /**
     * Consider the following info:
     * The average walking speed for a man: 5,63km/h ~ 5630 / 60 / 60 * 1000 * 100 (m/ms)
     * The average for a regular district: 100m
     * With these informations we have the time to walk for a district in milliseconds
     */
    public static final int DEFAULT_WALK_TIME_IN_MILLI = 156388;
}
