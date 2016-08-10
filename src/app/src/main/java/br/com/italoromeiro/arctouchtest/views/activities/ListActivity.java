package br.com.italoromeiro.arctouchtest.views.activities;

import android.util.Log;

import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FragmentById;

import java.util.ArrayList;
import java.util.List;

import br.com.italoromeiro.arctouchtest.R;
import br.com.italoromeiro.arctouchtest.models.Result;
import br.com.italoromeiro.arctouchtest.models.Route;
import br.com.italoromeiro.arctouchtest.models.Row;
import br.com.italoromeiro.arctouchtest.presenters.MainPresenter;
import br.com.italoromeiro.arctouchtest.rest.RestClient;
import br.com.italoromeiro.arctouchtest.utils.AlertUtils;
import br.com.italoromeiro.arctouchtest.views.fragments.ListFragment;
import retrofit2.Call;

@EActivity(R.layout.activity_list)
public class ListActivity extends BaseActivity implements RestClient.OnRestListener<Result> {
    private static final String TAG = ListActivity.class.getSimpleName();

    @Bean
    MainPresenter presenter;

    @FragmentById(R.id.fragment_list)
    ListFragment mListFragment;

    public void findByStopName(String search) {
        presenter.findRoutesByStopName(search);
    }

    @Override
    public void onIncomeSuccess(Result result, Call call) {
        Log.d(TAG, "result has come: " + result.toString());
        manageResult(result);
    }

    private void manageResult(Result result) {
        dismissDialogProgress();
        List<Route> routes = new ArrayList<>();
        for (Row row : result.getRows()) {
            if (row.getRoute() != null) {
                Log.d(TAG, row.getRoute().toString());
                routes.add(row.getRoute());
            }
        }
        mListFragment.setRoutes(routes);
    }

    @Override
    public void onIncomeFailure(Throwable t) {
        dismissDialogProgress();
        Log.e(TAG, t.getMessage(), t);

        AlertUtils.alert(this, R.string.dialog_title_default, R.string.dialog_message_no_info);
        mListFragment.clearContentIfNecessary();
    }
}
