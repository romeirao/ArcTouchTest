package br.com.italoromeiro.arctouchtest.views.activities;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;

import br.com.italoromeiro.arctouchtest.R;
import br.com.italoromeiro.arctouchtest.utils.AlertUtils;

/**
 * Created by italo on 08/08/16.
 */
@EActivity
public abstract class BaseActivity extends AppCompatActivity {
    private static final String TAG = BaseActivity.class.getSimpleName();

    private ProgressDialog mProgressDialog;
    private FrameLayout mContent;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mToggle;
    private NavigationView mNavigationView;
    private DrawerLayout mDrawer;
    private boolean mBackPressedFromToolbar = false;

    @AfterViews
    public void afterViews() {
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

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy");
        super.onDestroy();
        dismissDialogProgress();
        AlertUtils.dismissDialog();
        mDrawer.removeDrawerListener(mToggle);
    }
}
