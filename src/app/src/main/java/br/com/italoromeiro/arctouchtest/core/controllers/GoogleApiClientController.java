package br.com.italoromeiro.arctouchtest.core.controllers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.greenrobot.eventbus.EventBus;

import br.com.italoromeiro.arctouchtest.core.GeneralConstants;
import br.com.italoromeiro.arctouchtest.core.bus.Events;
import br.com.italoromeiro.arctouchtest.utils.PermissionUtils;
import br.com.italoromeiro.arctouchtest.views.activities.BaseActivity;

/**
 * Created by italo on 18/07/16.
 */
@EBean
public class GoogleApiClientController implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = GoogleApiClientController.class.getSimpleName();
    public static final int TIME_5_SEC = 5000;
    public static final int TIME_2_SEC = 2000;

    @RootContext
    BaseActivity activity;

    private GoogleApiClient mGoogleApiClient;
    private LocationRequest mLocationRequest;
    private long mInterval = GeneralConstants.DEFAULT_WALK_TIME_IN_MILLI;
    private long mFastInterval = GeneralConstants.DEFAULT_WALK_TIME_IN_MILLI;
    private int mNumberOfReadings = 1;

    public void buildGoogleApiClient() {
        if (mGoogleApiClient == null) {
            mGoogleApiClient = new GoogleApiClient.Builder(activity)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
        mGoogleApiClient.connect();
        createLocationRequest();
    }

    public void createLocationRequest() {
        if (mLocationRequest == null) {
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(prepareIntervalTime());
            mLocationRequest.setFastestInterval(prepareFastIntervalTime());
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
        }
    }

    public void start() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    public void startLocationUpdates() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
        } else {
            PermissionUtils.requestPermission(activity, GeneralConstants.LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }

    public void stop() {
        if (mGoogleApiClient != null) {
            mGoogleApiClient.disconnect();
        }
    }

    public void stopLocationUpdates() {
        if (mGoogleApiClient != null) {
            LocationServices.FusedLocationApi.removeLocationUpdates(mGoogleApiClient, this);
        }
    }

    private long prepareIntervalTime() {
        long result = mInterval / mNumberOfReadings;
        return result > TIME_5_SEC ? result : TIME_5_SEC;
    }

    private long prepareFastIntervalTime() {
        long result = mFastInterval / mNumberOfReadings / 2;
        return result > TIME_2_SEC ? result : TIME_2_SEC;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "onLocationChanged");
        EventBus.getDefault().post(new Events.LocationAction(location));
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Log.d(TAG, "onConnected");

        startLocationUpdates();
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    public GoogleApiClient getGoogleApiClient() {
        return mGoogleApiClient;
    }

    public long getInterval() {
        return mInterval;
    }

    public void setInterval(long interval) {
        this.mInterval = interval;
    }

    public long getFastInterval() {
        return mFastInterval;
    }

    public void setFastInterval(long fastInterval) {
        this.mFastInterval = fastInterval;
    }

    public float getNumberOfReadings() {
        return mNumberOfReadings;
    }

    public void setNumberOfReadings(int numberOfReadings) {
        this.mNumberOfReadings = numberOfReadings;
    }
}
