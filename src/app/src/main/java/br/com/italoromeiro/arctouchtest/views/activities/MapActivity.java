package br.com.italoromeiro.arctouchtest.views.activities;

import android.Manifest;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.maps.SupportMapFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.DimensionRes;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;
import java.util.Locale;

import br.com.italoromeiro.arctouchtest.R;
import br.com.italoromeiro.arctouchtest.core.GeneralConstants;
import br.com.italoromeiro.arctouchtest.core.bus.Events;
import br.com.italoromeiro.arctouchtest.core.controllers.GoogleApiClientController;
import br.com.italoromeiro.arctouchtest.core.controllers.GoogleMapsController;
import br.com.italoromeiro.arctouchtest.utils.PermissionUtils;

/**
 * Created by italo on 13/08/16.
 */
@EActivity(R.layout.activity_map)
public class MapActivity extends BaseActivity {
    private static final String TAG = MapActivity.class.getSimpleName();

    @Bean
    GoogleMapsController mGoogleMapsController;

    @Bean
    GoogleApiClientController mGoogleApiClientController;

    @FragmentById(R.id.map)
    SupportMapFragment mMapFragment;

    @ViewById(R.id.bottom_sheet)
    View mBottomSheet;

    @ViewById(R.id.fab)
    FloatingActionButton mFab;

    @ViewById(R.id.tv_address_result)
    TextView mAddressResult;

    @DimensionRes(R.dimen.dim_peek_height)
    float mPeekMinHeight;

    @InstanceState
    String mSelectedStreet;

    /**
     * This constant express how often the readings need to occur
     * to get the current location of the user
     */
    private static final int READINGS_PER_DISTRICT = 10;

    private BottomSheetBehavior mBottomSheetBehavior;
    private boolean mLocationPermissionDenied = false;

    @AfterViews
    public void mapActivityAfterViews() {
        Log.d(TAG, "afterViews");
        setTitle(getString(R.string.textview_label_map));

        mBottomSheetBehavior = BottomSheetBehavior.from(mBottomSheet);
        mBottomSheetBehavior.setPeekHeight((int) mPeekMinHeight);
        mBottomSheetBehavior.setBottomSheetCallback(getBottomSheetCallback());

        mGoogleMapsController.applyMap(mMapFragment);
        mGoogleApiClientController.setNumberOfReadings(READINGS_PER_DISTRICT);

        changeGPSIcon(R.drawable.ic_gps_fixed_black_48dp);

        if (mSelectedStreet != null) {
            mAddressResult.setText(mSelectedStreet);
        }
    }

    @UiThread
    public void changeGPSIcon(int iconId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            mFab.setImageDrawable(getResources().getDrawable(iconId, getTheme()));
        } else {
            mFab.setImageDrawable(getResources().getDrawable(iconId));
        }
    }

    @Click(R.id.bt_ok)
    public void setResultForActivity() {
        if (mSelectedStreet != null) {
            Intent intent = getIntent().putExtra(GeneralConstants.REQUEST_STREET_NAME, mSelectedStreet);
            setResult(RESULT_OK, intent);
            finish();
        }
    }

    @Click(R.id.fab)
    public void startAutomaticMapUpdate() {
        handleMapAction(new Events.MapAction(Events.MapAction.ActionType.MY_LOCATION_CLICK, null));
    }

    @Subscribe
    public void handleMapAction(Events.MapAction action) {
        Log.d(TAG, "handleMapAction");

        switch (action.getActionType()) {
            case CLICK:
            case LONG_CLICK:
                mGoogleMapsController.goToThatPoint(action.getLatLng());
                getAddress(action.getLatLng().longitude, action.getLatLng().latitude);
            case CAMERA_MOVES:
                changeGPSIcon(R.drawable.ic_gps_not_fixed_black_48dp);
                mGoogleApiClientController.stopLocationUpdates();
                break;
            case MY_LOCATION_CLICK:
                changeGPSIcon(R.drawable.ic_gps_fixed_black_48dp);
                mGoogleApiClientController.startLocationUpdates();
                mGoogleMapsController.fakeMyLocationButtonClick();
                break;
        }
    }

    @Background
    public void getAddress(double longitude, double latitude) {
        try {
            Geocoder geo = new Geocoder(this.getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geo.getFromLocation(latitude, longitude, 1);
            if (addresses.size() == 0) {
                mSelectedStreet = null;
                setAddressResult(getString(R.string.textview_label_map_street_selected_none));
                return;
            }

            if (addresses.get(0).getThoroughfare().isEmpty()) {
                mSelectedStreet = null;
                setAddressResult(getString(R.string.textview_label_map_street_selected_none));
                return;
            }

            mSelectedStreet = addresses.get(0).getThoroughfare();
            setAddressResult(mSelectedStreet);
        } catch (Exception e) {
            e.printStackTrace(); // getFromLocation() may sometimes fail

            mSelectedStreet = null;
            setAddressResult(getString(R.string.textview_label_map_street_selected_none));
        }
    }

    @UiThread
    public void setAddressResult(String result) {
        mAddressResult.setText(result);
    }

    @Subscribe
    public void handleGoogleAPIAction(Events.GoogleAPIClientAction action) {
        switch (action.getActionType()) {
            case START:
                mGoogleApiClientController.buildGoogleApiClient();
                break;
            case STOP:
                break;
        }
    }

    @Subscribe
    public void handleLocationAction(Events.LocationAction action) {
        mGoogleMapsController.handleLocationAction(action.getLocation());
    }

    private BottomSheetBehavior.BottomSheetCallback getBottomSheetCallback() {
        return new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }

            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                String stateName = "";
                switch (newState) {
                    case 1:
                        stateName = "STATE_DRAGGING";
                        break;
                    case 2:
                        stateName = "STATE_SETTLING";
                        break;
                    case 3:
                        stateName = "STATE_EXPANDED";
                        break;
                    case 4:
                        stateName = "STATE_COLLAPSED";
                        break;
                    case 5:
                        stateName = "STATE_HIDDEN";
                        break;
                }
                Log.d(TAG, "onStateChanged: " + stateName);
            }
        };
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        mGoogleMapsController.setPaddingToMap(0, mToolbar.getHeight(), 0, (int) mPeekMinHeight);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != GeneralConstants.LOCATION_PERMISSION_REQUEST_CODE) {
            return;
        }

        if (PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Enable the my location layer if the permission has been granted.
            mGoogleMapsController.enableMyLocation();
        } else {
            // Display the missing permission error dialog when the fragments resume.
            mLocationPermissionDenied = true;
        }
    }

    @Override
    public boolean isBack() {
        return true;
    }

    @Override
    protected void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();

        mGoogleApiClientController.start();
    }

    @Override
    protected void onPause() {
        Log.d(TAG, "onPause");

        mGoogleApiClientController.stopLocationUpdates();
        mGoogleApiClientController.stop();

        super.onPause();
    }

    @Override
    protected void onResumeFragments() {
        super.onResumeFragments();
        Log.d(TAG, "onResumeFragments");

        if (mLocationPermissionDenied) {
            // Permission was not granted, display error dialog.
            showMissingPermissionError();
            mLocationPermissionDenied = false;
        }
    }
}
