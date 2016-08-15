package br.com.italoromeiro.arctouchtest.views.fragments;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.italoromeiro.arctouchtest.R;
import br.com.italoromeiro.arctouchtest.core.GeneralConstants;
import br.com.italoromeiro.arctouchtest.core.adapters.RoutesAdapter;
import br.com.italoromeiro.arctouchtest.models.Route;
import br.com.italoromeiro.arctouchtest.utils.FormatterUtil;
import br.com.italoromeiro.arctouchtest.views.activities.ListActivity;
import br.com.italoromeiro.arctouchtest.views.activities.MapActivity_;

/**
 * Created by italo on 10/08/16.
 */
@EFragment(R.layout.fragment_list)
public class ListFragment extends BaseFragment {
    private static final String TAG = ListFragment.class.getSimpleName();

    @ViewById(R.id.et_search)
    EditText mEtSearch;

    @ViewById(R.id.btn_search)
    Button mBtnSearch;

    @ViewById(R.id.rv_routes)
    RecyclerView mRoutesRv;

    @ViewById(R.id.progress_container)
    LinearLayout mProgressContainer;

    @ViewById(R.id.progress)
    ProgressBar mProgress;

    @ViewById(R.id.tv_amount)
    TextView mRouteAmount;

    @ViewById(R.id.input_layout_search)
    TextInputLayout mTextInputLayout;

    @Bean
    RoutesAdapter mRoutesAdapter;

    @InstanceState
    String mTxtAmount;

    private final int REQUEST_STREET_NAME_FROM_MAP = 1;

    private ListActivity mActivity;

    @AfterViews
    public void listFragmentAfterViews() {
        // Initial config for the view
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        mRoutesRv.setLayoutManager(manager);
        mRoutesRv.setHasFixedSize(true);
        mRoutesRv.setAdapter(mRoutesAdapter);

        prepareDataForView();
    }

    /*
     * If there are some data to show, it will be prepared here
     */
    private void prepareDataForView() {
        if (mTxtAmount == null) {
            return;
        }

        mRouteAmount.setText(mTxtAmount);

        Pattern p = Pattern.compile(".*\\d+.*");
        Matcher m = p.matcher(mTxtAmount);
        if (m.matches()) {
            mRoutesRv.setVisibility(View.VISIBLE);
        }
    }

    public void setRoutes(List<Route> routes) {
        if (!routes.isEmpty()) {
            mTxtAmount = getResources().getQuantityString(R.plurals.textview_label_amount, routes.size(), routes.size());
            mRouteAmount.setText(mTxtAmount);
            mRoutesAdapter.setRoutes(routes);
            mRoutesAdapter.notifyDataSetChanged();
            addRoutesRv();
        } else {
            removeProgressView();
            mTxtAmount = getResources().getString(R.string.textview_label_route_empty);
            mRouteAmount.setText(mTxtAmount);
        }
    }

    public void clearContentIfNecessary() {
        if (mRoutesAdapter.getItemCount() == 0) {
            removeProgressView();
            removeRoutesRv();
        } else {
            addRoutesRv();
        }
    }

    @Click(R.id.btn_search)
    public void btnSearchClick() {
        if (mEtSearch.getText().toString().isEmpty()) {
            mTextInputLayout.setError(getString(R.string.error_empty_string));
            requestFocus(mEtSearch);
        } else {
            mTextInputLayout.setErrorEnabled(false);
            addProgressView();
            mActivity.findByStopName(FormatterUtil.cleanString(mEtSearch.getText().toString()));
        }
    }

    @Click(R.id.btn_search_by_map)
    public void btnSearchByMapClick() {
        MapActivity_.intent(this).startForResult(REQUEST_STREET_NAME_FROM_MAP);
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @OnActivityResult(REQUEST_STREET_NAME_FROM_MAP)
    public void handleRequestStreetNameFromMap(int resultCode, @OnActivityResult.Extra(value = GeneralConstants.REQUEST_STREET_NAME) String streetName) {
        Log.d(TAG, "handleRequestStreetNameFromMap");

        if (resultCode == Activity.RESULT_OK && streetName != null) {
            mEtSearch.setText(streetName);
            mBtnSearch.performClick();
        }
    }

    @AfterTextChange(R.id.et_search)
    public void afterTextChange(TextView affectedView) {
        if (affectedView.getText().toString().isEmpty()) {
            mBtnSearch.setEnabled(false);
            return;
        }
        mBtnSearch.setEnabled(true);
    }

    @UiThread
    void addRoutesRv() {
        removeProgressView();

        mRoutesRv.setVisibility(View.VISIBLE);
    }

    @UiThread
    void removeRoutesRv() {
        mRoutesRv.setVisibility(View.GONE);
    }

    @UiThread
    void addProgressView() {
        removeRoutesRv();

        mTxtAmount = getResources().getString(R.string.textview_label_route_searching);
        mRouteAmount.setText(mTxtAmount);
        mProgress.setIndeterminate(true);
        mProgressContainer.setVisibility(View.VISIBLE);
    }

    @UiThread
    void removeProgressView() {
        mProgress.setIndeterminate(false);
        mProgressContainer.setVisibility(View.GONE);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        Log.d(TAG, "onAttach");
        super.onAttach(context);
        mActivity = (ListActivity) context;
    }
}
