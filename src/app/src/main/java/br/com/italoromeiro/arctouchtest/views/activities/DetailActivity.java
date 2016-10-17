package br.com.italoromeiro.arctouchtest.views.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.InstanceState;
import org.androidannotations.annotations.ViewById;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import br.com.italoromeiro.arctouchtest.R;
import br.com.italoromeiro.arctouchtest.core.bus.Events;
import br.com.italoromeiro.arctouchtest.models.Departure;
import br.com.italoromeiro.arctouchtest.models.Route;
import br.com.italoromeiro.arctouchtest.models.Row;
import br.com.italoromeiro.arctouchtest.models.Stop;
import br.com.italoromeiro.arctouchtest.models.aux.DeparturesMap;
import br.com.italoromeiro.arctouchtest.models.aux.StopsMap;
import br.com.italoromeiro.arctouchtest.models.rest.Result;
import br.com.italoromeiro.arctouchtest.presenters.DetailPresenter;
import br.com.italoromeiro.arctouchtest.rest.RestClient;
import br.com.italoromeiro.arctouchtest.views.fragments.DeparturesFragment;
import br.com.italoromeiro.arctouchtest.views.fragments.DeparturesFragment_;
import br.com.italoromeiro.arctouchtest.views.fragments.StopsFragment;
import br.com.italoromeiro.arctouchtest.views.fragments.StopsFragment_;
import retrofit2.Call;

/**
 * Created by italo on 10/08/16.
 */
@EActivity(R.layout.activity_detail)
public class DetailActivity extends BaseActivity implements RestClient.OnRestListener<Result> {
    private static final String TAG = DetailActivity.class.getSimpleName();

    @ViewById(R.id.viewpager)
    ViewPager mViewPager;

    @Bean
    DetailPresenter presenter;

    @Extra
    Route mRoute;

    @InstanceState
    StopsMap mStopsMap;

    @InstanceState
    DeparturesMap mDeparturesMap;

    private StopsFragment mStopsFragment;
    private DeparturesFragment mDeparturesFragment;

    @AfterViews
    public void detailActivityAfterViews() {
        Log.d(TAG, "afterViews");
        setTitle(getString(R.string.textview_label_route) + " " + mRoute.getShortName());
        mTabs.setVisibility(View.VISIBLE);

        FragmentManager fm = getSupportFragmentManager();
        mStopsFragment = (StopsFragment) fm.findFragmentByTag(getString(R.string.fragment_tag_stop));
        mDeparturesFragment = (DeparturesFragment) fm.findFragmentByTag(getString(R.string.fragment_tag_departure));

        if (mStopsFragment == null) {
            mStopsFragment = StopsFragment_.builder().build();
        }
        if (mDeparturesFragment == null) {
            mDeparturesFragment = DeparturesFragment_.builder().build();
        }

        if (mViewPager != null) {
            setupViewPager(mViewPager);
        }

        mTabs.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        Log.d(TAG, "setupViewPager");
        Adapter adapter = new Adapter(getSupportFragmentManager());
        adapter.addFragment(mStopsFragment, getString(R.string.textview_label_stops));
        adapter.addFragment(mDeparturesFragment, getString(R.string.textview_label_departures));
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean isBack() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Events.StopsViewReadyEvent event) {
        Log.d(TAG, "StopsViewReadyEvent");
        presenter.findStopsByRouteId(mRoute.getId());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(Events.DeparturesViewReadyEvent event) {
        Log.d(TAG, "DeparturesViewReadyEvent");
        presenter.findDeparturesByRouteId(mRoute.getId());
    }

    @Override
    public void onIncomeSuccess(Result result, Call call) {
        Log.d(TAG, "result has come: " + result.toString());
        manageResult(result);
    }

    @Override
    public void onIncomeFailure(Throwable t) {
        dismissDialogProgress();
        Log.e(TAG, t.getMessage(), t);

        showGeneralErrorDialog();
    }

    private void manageResult(Result result) {
        List<Stop> stops = new ArrayList<>();
        List<Departure> departures = new ArrayList<>();

        for (Row row : result.getRows()) {
            if (row.getStop() != null) {
                stops.add(row.getStop());
            }
            if (row.getDeparture() != null) {
                departures.add(row.getDeparture());
            }
        }

        if (!stops.isEmpty()) {
            mStopsMap = new StopsMap();
            mStopsMap.setStops(stops);
            setStops(mStopsMap.getStops());
        }

        if (!departures.isEmpty()) {
            mDeparturesMap = new DeparturesMap();
            mDeparturesMap.setDepartures(departures);
            setDepartures(mDeparturesMap.getDepartures());
        }
    }

    public void setStops(List<Stop> stops) {
        mStopsFragment.setStops(stops);
    }

    public void setDepartures(List<Departure> departures) {
        mDeparturesFragment.setDepartures(departures);
    }

    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragments = new ArrayList<>();
        private final List<String> mFragmentTitles = new ArrayList<>();

        public Adapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mFragmentTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitles.get(position);
        }
    }
}
