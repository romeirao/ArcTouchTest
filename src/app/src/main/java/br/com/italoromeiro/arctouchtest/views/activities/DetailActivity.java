package br.com.italoromeiro.arctouchtest.views.activities;

import android.util.Log;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.FragmentById;

import java.util.ArrayList;
import java.util.List;

import br.com.italoromeiro.arctouchtest.R;
import br.com.italoromeiro.arctouchtest.models.Departure;
import br.com.italoromeiro.arctouchtest.models.Result;
import br.com.italoromeiro.arctouchtest.models.Route;
import br.com.italoromeiro.arctouchtest.models.Row;
import br.com.italoromeiro.arctouchtest.models.Stop;
import br.com.italoromeiro.arctouchtest.presenters.DetailPresenter;
import br.com.italoromeiro.arctouchtest.rest.RestClient;
import br.com.italoromeiro.arctouchtest.views.fragments.DetailFragment;
import retrofit2.Call;

/**
 * Created by italo on 10/08/16.
 */
@EActivity(R.layout.activity_detail)
public class DetailActivity extends BaseActivity implements RestClient.OnRestListener<Result> {
    private static final String TAG = DetailActivity.class.getSimpleName();

    @Bean
    DetailPresenter presenter;

    @Extra
    Route mRoute;

    @FragmentById(R.id.fragment_detail)
    DetailFragment mDetailFragment;

    @AfterViews
    public void afterViews() {
        mDetailFragment.setRoute(mRoute);
        presenter.findStopsByRouteId(mRoute.getId());
        presenter.findDeparturesByRouteId(mRoute.getId());
    }

    @Override
    public void onIncomeSuccess(Result result, Call call) {
        Log.d(TAG, "result has come: " + result.toString());
        manageResult(result);
    }

    private void manageResult(Result result) {
        List<Stop> stops = new ArrayList<>();
        List<Departure> departures = new ArrayList<>();

        for (Row row : result.getRows()) {
            if (row.getStop() != null) {
                Log.d(TAG, row.getStop().toString());
                stops.add(row.getStop());
            }
            if (row.getDeparture() != null) {
                Log.d(TAG, row.getDeparture().toString());
                departures.add(row.getDeparture());
            }
        }

        if (!stops.isEmpty()) {
            mDetailFragment.setStops(stops);
        }

        if (!departures.isEmpty()) {
            mDetailFragment.setDepartures(departures);
        }
    }

    @Override
    public void onIncomeFailure(Throwable t) {
        Log.e(TAG, t.getMessage(), t);
    }
}
