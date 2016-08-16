package br.com.italoromeiro.arctouchtest.rest;

import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.res.StringRes;

import java.io.IOException;

import br.com.italoromeiro.arctouchtest.R;
import br.com.italoromeiro.arctouchtest.models.rest.Params;
import br.com.italoromeiro.arctouchtest.models.rest.ParamsMap;
import br.com.italoromeiro.arctouchtest.models.rest.Result;
import br.com.italoromeiro.arctouchtest.rest.interfaces.AppGluService;
import br.com.italoromeiro.arctouchtest.views.activities.BaseActivity;
import retrofit2.Call;

/**
 * Created by italo on 08/08/16.
 */
@EBean
public class RestController {
    private static final String TAG = RestController.class.getSimpleName();

    @RootContext
    BaseActivity mActivity;

    @StringRes(R.string.api_url)
    String mApiUrl;

    @StringRes(R.string.user)
    String mUser;

    @StringRes(R.string.password)
    String mPassword;

    public void findRoutesByStopName(String stopName) throws IOException {
        Params params = new Params();
        params.setStopName("%" + stopName + "%");

        ParamsMap paramsMap = new ParamsMap();
        paramsMap.setParams(params);

        RestClient<AppGluService> restClient = RestClient.getInstance(mApiUrl, mUser, mPassword);
        AppGluService appGluService = restClient.getService(AppGluService.class);
        Call<Result> call = appGluService.findRoutesByStopName(paramsMap);
        restClient.getRestData((RestClient.OnRestListener) mActivity, call);
    }

    public void findStopsByRouteId(Integer routeId) throws IOException {
        Params params = new Params();
        params.setRouteId(routeId);

        ParamsMap paramsMap = new ParamsMap();
        paramsMap.setParams(params);

        RestClient<AppGluService> restClient = RestClient.getInstance(mApiUrl, mUser, mPassword);
        AppGluService appGluService = restClient.getService(AppGluService.class);
        Call<Result> call = appGluService.findStopsByRouteId(paramsMap);
        restClient.getRestData((RestClient.OnRestListener) mActivity, call);
    }

    public void findDeparturesByRouteId(Integer routeId) throws IOException {
        Params params = new Params();
        params.setRouteId(routeId);

        ParamsMap paramsMap = new ParamsMap();
        paramsMap.setParams(params);

        RestClient<AppGluService> restClient = RestClient.getInstance(mApiUrl, mUser, mPassword);
        AppGluService appGluService = restClient.getService(AppGluService.class);
        Call<Result> call = appGluService.findDeparturesByRouteId(paramsMap);
        restClient.getRestData((RestClient.OnRestListener) mActivity, call);
    }
}
