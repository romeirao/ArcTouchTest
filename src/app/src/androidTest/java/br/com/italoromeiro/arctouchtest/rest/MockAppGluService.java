package br.com.italoromeiro.arctouchtest.rest;

import android.content.Context;
import android.support.test.espresso.core.deps.guava.io.CharStreams;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.com.italoromeiro.arctouchtest.models.rest.ParamsMap;
import br.com.italoromeiro.arctouchtest.models.rest.Result;
import br.com.italoromeiro.arctouchtest.rest.interfaces.AppGluService;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.mock.BehaviorDelegate;

/**
 * Created by italo on 13/08/16.
 */
public class MockAppGluService implements AppGluService {

    private static Context mContextApi;
    private final BehaviorDelegate<AppGluService> mDelegate;

    private int mStatusCode = 200;
    private String mStatusResponse = "success";

    public MockAppGluService(Context contextApi, BehaviorDelegate<AppGluService> delegate) {
        mContextApi = contextApi;
        mDelegate = delegate;
    }

    @Override
    public Call<Result> findRoutesByStopName(@Body ParamsMap paramsMap) {
        Result result = new Gson().fromJson(readMockedJson(mStatusCode + "_" + mStatusResponse + "_list"), Result.class);
        return mDelegate.returningResponse(result).findRoutesByStopName(paramsMap);
    }

    @Override
    public Call<Result> findStopsByRouteId(@Body ParamsMap paramsMap) {
        Result result = new Gson().fromJson(readMockedJson(mStatusCode + "_" + mStatusResponse + "_details_stops"), Result.class);
        return mDelegate.returningResponse(result).findRoutesByStopName(paramsMap);
    }

    @Override
    public Call<Result> findDeparturesByRouteId(@Body ParamsMap paramsMap) {
        Result result = new Gson().fromJson(readMockedJson(mStatusCode + "_" + mStatusResponse + "_details_departures"), Result.class);
        return mDelegate.returningResponse(result).findRoutesByStopName(paramsMap);
    }

    private String readMockedJson(final String fileName) {
        try {
            final InputStream is = mContextApi.getResources().getAssets().open("mock_json/" + fileName + ".json");
            return CharStreams.toString(new InputStreamReader(is));
        } catch (IOException e) {
            throw new RuntimeException("Could not open test assets folder mock_json/" + fileName);
        }
    }

    public int getStatusCode() {
        return mStatusCode;
    }

    public void setStatusCode(int statusCode) {
        mStatusCode = statusCode;
    }

    public String getStatusResponse() {
        return mStatusResponse;
    }

    public void setStatusResponse(String statusResponse) {
        mStatusResponse = statusResponse;
    }
}
