package br.com.italoromeiro.arctouchtest.views.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import br.com.italoromeiro.arctouchtest.R;
import br.com.italoromeiro.arctouchtest.core.adapters.DeparturesAdapter;
import br.com.italoromeiro.arctouchtest.core.adapters.StopsAdapter;
import br.com.italoromeiro.arctouchtest.models.Departure;
import br.com.italoromeiro.arctouchtest.models.Route;
import br.com.italoromeiro.arctouchtest.models.Stop;
import br.com.italoromeiro.arctouchtest.views.activities.DetailActivity;

/**
 * Created by italo on 10/08/16.
 */
@EFragment(R.layout.fragment_detail)
public class DetailFragment extends Fragment {
    private static final String TAG = DetailFragment.class.getSimpleName();

    @ViewById(R.id.rv_stops)
    RecyclerView mStopsRv;

    @ViewById(R.id.rv_departures)
    RecyclerView mDeparturesRv;

    @Bean
    StopsAdapter mStopsAdapter;

    @Bean
    DeparturesAdapter mDeparturesAdapter;

    private DetailActivity mActivity;
    private Route mRoute;

    @AfterViews
    public void afterViews() {
        // Initial config for the view
        LinearLayoutManager stopsLayoutManager = new LinearLayoutManager(mActivity);
        mStopsRv.setLayoutManager(stopsLayoutManager);
        mStopsRv.setHasFixedSize(true);
        mStopsRv.setAdapter(mStopsAdapter);

        LinearLayoutManager departuresLayoutManager = new LinearLayoutManager(mActivity);
        mDeparturesRv.setLayoutManager(departuresLayoutManager);
        mDeparturesRv.setHasFixedSize(true);
        mDeparturesRv.setAdapter(mDeparturesAdapter);
    }

    public void setRoute(Route route) {
        Log.d(TAG, route.toString());
        mRoute = route;
    }

    public void setStops(List<Stop> stops) {
        mStopsAdapter.setStops(stops);
        mStopsAdapter.notifyDataSetChanged();
    }

    public void setDepartures(List<Departure> departures) {
        mDeparturesAdapter.setDepartures(departures);
        mDeparturesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
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
        mActivity = (DetailActivity) context;
    }
}
