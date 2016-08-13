package br.com.italoromeiro.arctouchtest.rest;

import java.io.IOException;

import br.com.italoromeiro.arctouchtest.R;
import br.com.italoromeiro.arctouchtest.models.rest.ParamsMap;
import br.com.italoromeiro.arctouchtest.models.rest.Result;
import br.com.italoromeiro.arctouchtest.rest.interfaces.AppGluService;
import br.com.italoromeiro.arctouchtest.views.activities.BaseActivity;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.mock.BehaviorDelegate;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * Created by italo on 13/08/16.
 */
public class MockRestController extends RestController {

    private MockRetrofit mMockRetrofit;
    private Retrofit mRetrofit;
    private MockAppGluService mMockAppGluService;

    public MockRestController(BaseActivity activity) {
        mActivity = activity;

        mRetrofit = new Retrofit.Builder().baseUrl(mActivity.getString(R.string.api_url))
                .client(new OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NetworkBehavior behavior = NetworkBehavior.create();

        mMockRetrofit = new MockRetrofit.Builder(mRetrofit)
                .networkBehavior(behavior)
                .build();

        BehaviorDelegate<AppGluService> delegate = mMockRetrofit.create(AppGluService.class);
        mMockAppGluService = new MockAppGluService(mActivity, delegate);
    }

    @Override
    public void findRoutesByStopName(String stopName) throws IOException {
        Call<Result> call = mMockAppGluService.findRoutesByStopName(new ParamsMap());
        Response<Result> response = call.execute();
        ((RestClient.OnRestListener) mActivity).onIncomeSuccess(response.body(), call);
    }

    @Override
    public void findStopsByRouteId(Integer routeId) throws IOException {
        Call<Result> call = mMockAppGluService.findStopsByRouteId(new ParamsMap());
        Response<Result> response = call.execute();
        ((RestClient.OnRestListener) mActivity).onIncomeSuccess(response.body(), call);
    }

    @Override
    public void findDeparturesByRouteId(Integer routeId) throws IOException {
        Call<Result> call = mMockAppGluService.findDeparturesByRouteId(new ParamsMap());
        Response<Result> response = call.execute();
        ((RestClient.OnRestListener) mActivity).onIncomeSuccess(response.body(), call);
    }
}
