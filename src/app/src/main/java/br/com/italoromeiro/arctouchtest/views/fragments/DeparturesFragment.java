package br.com.italoromeiro.arctouchtest.views.fragments;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import java.util.List;

import br.com.italoromeiro.arctouchtest.R;
import br.com.italoromeiro.arctouchtest.core.adapters.DeparturesAdapter;
import br.com.italoromeiro.arctouchtest.core.bus.Events;
import br.com.italoromeiro.arctouchtest.models.Departure;

/**
 * Created by italo on 11/08/16.
 */
@EFragment(R.layout.fragment_departures)
public class DeparturesFragment extends BaseFragment {
    private static final String TAG = DeparturesFragment.class.getSimpleName();

    @ViewById(R.id.rv_departures)
    RecyclerView mDeparturesRv;

    @Bean
    DeparturesAdapter mDeparturesAdapter;

    private List<Departure> mDepartures;

    @AfterViews
    public void departuresFragmentAfterViews() {
        Log.d(TAG, "afterViews");
        LinearLayoutManager stopsLayoutManager = new LinearLayoutManager(mBaseActivity);
        mDeparturesRv.setLayoutManager(stopsLayoutManager);
        mDeparturesRv.setHasFixedSize(true);
        mDeparturesRv.setAdapter(mDeparturesAdapter);
    }

    public void setDepartures(List<Departure> departures) {
        mDepartures = departures;
        mDeparturesAdapter.setDepartures(departures);
        mDeparturesAdapter.notifyDataSetChanged();
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        if(mDepartures == null) {
            EventBus.getDefault().post(new Events.DeparturesViewReadyEvent());
        }
    }
}
