package br.com.italoromeiro.arctouchtest.views.fragments;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
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

    @ViewById(R.id.rv_departures_weekday)
    RecyclerView mWeekdayRv;

    @ViewById(R.id.rv_departures_saturday)
    RecyclerView mSaturdayRv;

    @ViewById(R.id.rv_departures_sunday)
    RecyclerView mSundayRv;

    @ViewById(R.id.departures_weekday_container)
    FrameLayout mWeekdayContainer;

    @ViewById(R.id.departures_saturday_container)
    FrameLayout mSaturdayContainer;

    @ViewById(R.id.departures_sunday_container)
    FrameLayout mSundayContainer;

    @ViewById(R.id.fragment_departures)
    LinearLayout mContainer;

    @Bean
    DeparturesAdapter mWeekdayAdapter;

    @Bean
    DeparturesAdapter mSaturdayAdapter;

    @Bean
    DeparturesAdapter mSundayAdapter;

    private List<Departure> mDepartures;

    @AfterViews
    public void departuresFragmentAfterViews() {
        Log.d(TAG, "afterViews");
        prepareView(mWeekdayRv, mWeekdayAdapter, 5);
        prepareView(mSaturdayRv, mSaturdayAdapter, 4);
        prepareView(mSundayRv, mSundayAdapter, 3);
    }

    private void prepareView(RecyclerView view, DeparturesAdapter adapter, int spanCount) {
        GridLayoutManager layoutManager = new GridLayoutManager(mBaseActivity, spanCount, LinearLayoutManager.HORIZONTAL, false);
        view.setLayoutManager(layoutManager);
        view.setHasFixedSize(true);
        view.setAdapter(adapter);
    }

    public void setDepartures(List<Departure> departures) {
        mDepartures = departures;

        List<Departure> weekdayListAux = new ArrayList<>();
        List<Departure> saturdayListAux = new ArrayList<>();
        List<Departure> sundayListAux = new ArrayList<>();

        for (Departure departure : departures) {
            switch (departure.getCalendar()) {
                case "WEEKDAY":
                    weekdayListAux.add(departure);
                    break;
                case "SATURDAY":
                    saturdayListAux.add(departure);
                    break;
                case "SUNDAY":
                    sundayListAux.add(departure);
                    break;
            }
        }

        applyChangesToDataAndView(mWeekdayAdapter, weekdayListAux, mSaturdayRv);
        applyChangesToDataAndView(mSaturdayAdapter, saturdayListAux, mSaturdayRv);
        applyChangesToDataAndView(mSundayAdapter, sundayListAux, mSundayRv);
    }

    private void applyChangesToDataAndView(DeparturesAdapter adapter, List<Departure> departures, RecyclerView view) {
        adapter.setDepartures(departures);
        adapter.notifyDataSetChanged();
        if (departures.isEmpty()) {
            view.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        Log.d(TAG, "onResume");
        super.onResume();
        if (mDepartures == null) {
            EventBus.getDefault().post(new Events.DeparturesViewReadyEvent());
        }
    }
}
