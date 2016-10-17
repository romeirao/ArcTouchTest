package br.com.italoromeiro.arctouchtest.views.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.Serializable;

import br.com.italoromeiro.arctouchtest.R;
import br.com.italoromeiro.arctouchtest.core.bus.Events;
import br.com.italoromeiro.arctouchtest.presenters.BasePresenter;
import br.com.italoromeiro.arctouchtest.utils.AlertUtils;
import br.com.italoromeiro.arctouchtest.utils.PermissionUtils;

/**
 * Created by italo on 08/08/16.
 */
@EActivity
public abstract class BaseActivity extends AppCompatActivity implements Serializable {
    private static final String TAG = BaseActivity.class.getSimpleName();

    @ViewById(R.id.tabs)
    TabLayout mTabs;

    protected Toolbar mToolbar;
    private AlertDialog mAlertDialog;
    private ProgressDialog mProgressDialog;
    private FrameLayout mContent;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawer;
    private boolean mBackPressedFromToolbar = false;

    @AfterViews
    public void baseAfterViews() {
        Log.e(TAG, "afterViews");

        getSupportActionBar().setDisplayHomeAsUpEnabled(isBack());
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mBackPressedFromToolbar = true;
                onBackPressed();
            }
        });
    }

    public boolean isBack() {
        return false;
    }

    @Override
    public void setContentView(int layoutResID) {
        Log.d(TAG, "setContentView");
        super.setContentView(R.layout.activity_base);
        mContent = (FrameLayout) findViewById(R.id.content);

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        mToggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawer.addDrawerListener(mToggle);
        mToggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View header = mNavigationView.getHeaderView(0);
            View userAvatar = header.findViewById(R.id.user_avatar);
            userAvatar.setClipToOutline(true);
        }

        final View view = getLayoutInflater().inflate(layoutResID, mContent, false);
        mContent.addView(view);
    }

    @UiThread
    public void showDialogProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            return;
        }

        mProgressDialog = ProgressDialog.show(this, null,
                getString(R.string.dialog_loading_waiting), true, true, new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                    }
                });

        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setCancelable(false);
    }

    @UiThread
    public void dismissDialogProgress() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
            mProgressDialog = null;
        }
    }

    @UiThread
    public void dismissAlertDialog() {
        if (mAlertDialog != null && mAlertDialog.isShowing()) {
            mAlertDialog.dismiss();
            mAlertDialog = null;
        }
    }

    @Override
    public void onBackPressed() {
        if (mDrawer.isDrawerOpen(GravityCompat.START)) {
            mDrawer.closeDrawer(GravityCompat.START);
        } else if (!isBack() && !mDrawer.isDrawerOpen(GravityCompat.START) && mBackPressedFromToolbar) {
            mDrawer.openDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
        mBackPressedFromToolbar = false;
    }

    @Subscribe
    public void onEvent(Events events) {
    }

    public void showMissingPermissionError() {
        PermissionUtils.PermissionDeniedDialog.newInstance(true).show(getSupportFragmentManager(), "dialog");
    }

    @UiThread
    public void showGeneralErrorDialog() {
        mAlertDialog = AlertUtils.alert(this, R.string.dialog_title_default, R.string.dialog_message_no_info);
        mAlertDialog.show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");

        dismissDialogProgress();
        dismissAlertDialog();

        EventBus.getDefault().unregister(this);
        mDrawer.removeDrawerListener(mToggle);

        super.onDestroy();
    }

    public BasePresenter getPresenter() {
        return null;
    }
}
