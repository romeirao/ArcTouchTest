package br.com.italoromeiro.arctouchtest.views.fragments;

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
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.italoromeiro.arctouchtest.R;
import br.com.italoromeiro.arctouchtest.core.adapters.RoutesAdapter;
import br.com.italoromeiro.arctouchtest.models.Route;
import br.com.italoromeiro.arctouchtest.utils.FormatterUtil;
import br.com.italoromeiro.arctouchtest.views.activities.ListActivity;

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

    private ListActivity mActivity;

    @AfterViews
    public void listFragmentAfterViews() {
        // Initial config for the view
        LinearLayoutManager manager = new LinearLayoutManager(mActivity);
        mRoutesRv.setLayoutManager(manager);
        mRoutesRv.setHasFixedSize(true);
        mRoutesRv.setAdapter(mRoutesAdapter);
    }

    public void setRoutes(List<Route> routes) {
        if (!routes.isEmpty()) {
            mRouteAmount.setText(getResources().getQuantityString(R.plurals.textview_label_amount, routes.size(), routes.size()));
            mRoutesAdapter.setRoutes(routes);
            mRoutesAdapter.notifyDataSetChanged();
            addRoutesRv();
        } else {
            removeProgressView();
            mRouteAmount.setText(getResources().getString(R.string.textview_label_route_empty));
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

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            mActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
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

        mRouteAmount.setText(getResources().getString(R.string.textview_label_route_searching));
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
