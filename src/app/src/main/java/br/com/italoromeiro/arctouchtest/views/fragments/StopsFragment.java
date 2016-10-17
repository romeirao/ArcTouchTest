package br.com.italoromeiro.arctouchtest.views.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

import br.com.italoromeiro.arctouchtest.R;
import br.com.italoromeiro.arctouchtest.core.adapters.StopsAdapter;
import br.com.italoromeiro.arctouchtest.core.bus.Events;
import br.com.italoromeiro.arctouchtest.models.Stop;

/**
 * Created by italo on 11/08/16.
 */
@EFragment(R.layout.fragment_stops)
public class StopsFragment extends BaseFragment {
    private static final String TAG = StopsFragment.class.getSimpleName();

    @ViewById(R.id.rv_stops)
    RecyclerView mStopsRv;

    @ViewById(R.id.tv_stops_empty)
    TextView mStopsEmpty;

    @Bean
    StopsAdapter mStopsAdapter;

    private List<Stop> mStops;

    @AfterViews
    public void stopsFragmentAfterViews() {
        Log.d(TAG, "afterViews");
        LinearLayoutManager stopsLayoutManager = new LinearLayoutManager(mBaseActivity);
        mStopsRv.setLayoutManager(stopsLayoutManager);
        mStopsRv.setHasFixedSize(true);
        mStopsRv.setAdapter(mStopsAdapter);
    }

    public void setStops(List<Stop> stops) {
        if (mStopsAdapter == null || mStopsRv == null) {
            return;
        }

        mStops = stops;
        mStopsAdapter.setStops(stops);
        mStopsAdapter.notifyDataSetChanged();

        mStopsRv.setVisibility(View.VISIBLE);
        mStopsEmpty.setVisibility(View.GONE);
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        if (mStops == null) {
            EventBus.getDefault().post(new Events.StopsViewReadyEvent());
        } else if (!mStops.isEmpty()) {
            mStopsRv.setVisibility(View.VISIBLE);
            mStopsEmpty.setVisibility(View.GONE);
        }
    }
}
