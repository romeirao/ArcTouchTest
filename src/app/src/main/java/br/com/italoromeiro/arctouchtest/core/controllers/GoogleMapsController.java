package br.com.italoromeiro.arctouchtest.core.controllers;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

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
public class GoogleMapsController implements GoogleMap.OnCameraMoveStartedListener, GoogleMap.OnMapClickListener, GoogleMap.OnMapLongClickListener, OnMapReadyCallback, GoogleMap.OnMyLocationButtonClickListener {
    private static final String TAG = GoogleMapsController.class.getSimpleName();

    @RootContext
    BaseActivity activity;

    private GoogleMap mMap;
    private Location mLastLocation;
    private boolean mIsUpdatingLocation = true;
    private int mLeft, mTop, mRight, mBottom;

    public void setPaddingToMap(int left, int top, int right, int bottom) {
        if (mMap != null) {
            mMap.setPadding(left, top, right, bottom);
        } else {
            mLeft = left;
            mTop = top;
            mRight = right;
            mBottom = bottom;
        }
    }

    public void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED && mMap != null) {
            EventBus.getDefault().post(new Events.GoogleAPIClientAction(Events.GoogleAPIClientAction.ActionType.START));
        } else {
            PermissionUtils.requestPermission(activity, GeneralConstants.LOCATION_PERMISSION_REQUEST_CODE, Manifest.permission.ACCESS_FINE_LOCATION, true);
        }
    }

    public void applyMap(SupportMapFragment supportMapFragment) {
        supportMapFragment.getMapAsync(this);
    }

    public void goToThatPoint(LatLng latLng) {
        if (latLng == null) {
            Log.d(TAG, "latLng came null. is it correct?");
            return;
        }
        mLastLocation.setLatitude(latLng.latitude);
        mLastLocation.setLongitude(latLng.longitude);
        moveCamera(new LatLng(latLng.latitude, latLng.longitude));
    }

    public void handleLocationAction(Location location) {
        if (!mIsUpdatingLocation) {
            return;
        }

        mLastLocation = location;
        moveCamera(new LatLng(location.getLatitude(), location.getLongitude()));
    }

    private void moveCamera(LatLng latLng) {
        CameraPosition cameraPosition = new CameraPosition.Builder()
                .target(latLng)
                .zoom(16.0f)
                .build();
        mMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    public void onMapClick(LatLng latLng) {
        mIsUpdatingLocation = false;
        EventBus.getDefault().post(new Events.MapAction(Events.MapAction.ActionType.CLICK, latLng));
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mIsUpdatingLocation = false;
        EventBus.getDefault().post(new Events.MapAction(Events.MapAction.ActionType.LONG_CLICK, latLng));
    }

    @Override
    public boolean onMyLocationButtonClick() {
        mIsUpdatingLocation = true;
        EventBus.getDefault().post(new Events.MapAction(Events.MapAction.ActionType.MY_LOCATION_CLICK));
        return false;
    }

    public void fakeMyLocationButtonClick() {
        mIsUpdatingLocation = true;
    }

//    @Override
//    public void onCameraMove() {
//        mIsUpdatingLocation = false;
//        EventBus.getDefault().post(new Events.MapAction(Events.MapAction.ActionType.CAMERA_MOVES));
//    }

//    @Override
//    public void onCameraMoveCanceled() {
//        mIsUpdatingLocation = false;
//        EventBus.getDefault().post(new Events.MapAction(Events.MapAction.ActionType.CAMERA_MOVES));
//    }

    @Override
    public void onCameraMoveStarted(int i) {
        Log.d("italo italo", "" + i);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.d(TAG, "onMapReady");
        mMap = googleMap;
        mMap.setPadding(mLeft, mTop, mRight, mBottom);

        googleMap.setOnMyLocationButtonClickListener(this);
        googleMap.setOnMapClickListener(this);
        googleMap.setOnMapLongClickListener(this);
        googleMap.setOnCameraMoveStartedListener(this);

        enableMyLocation();
    }

    public GoogleMap getMap() {
        return mMap;
    }

    public Location getLastLocation() {
        return mLastLocation;
    }
}
