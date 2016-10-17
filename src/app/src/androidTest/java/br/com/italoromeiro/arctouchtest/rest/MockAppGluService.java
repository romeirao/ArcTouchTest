package br.com.italoromeiro.arctouchtest.rest;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.core.deps.guava.io.CharStreams;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import br.com.italoromeiro.arctouchtest.core.TestConstants;
import br.com.italoromeiro.arctouchtest.models.rest.Params;
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

    public MockAppGluService(BehaviorDelegate<AppGluService> delegate) {
        mContextApi = InstrumentationRegistry.getContext();
        mDelegate = delegate;
    }

    @Override
    public Call<Result> findRoutesByStopName(@Body ParamsMap paramsMap) {
        Result result = null;
        Params params = paramsMap.getParams();
        switch (params.getStopName()) {
            case TestConstants.STOP_NAME_GOOD_WITH_MANY_RESULTS:
                result = new Gson().fromJson(readMockedJson("200_success_list_with_many_results"), Result.class);
                break;
            case TestConstants.STOP_NAME_GOOD_WITH_ONE_RESULT:
                result = new Gson().fromJson(readMockedJson("200_success_list_with_one_result"), Result.class);
                break;
            case TestConstants.STOP_NAME_GOOD_WITHOUT_RESULTS:
                result = new Gson().fromJson(readMockedJson("200_success_list_without_results"), Result.class);
                break;
            case TestConstants.STOP_NAME_FAIL:
                result = null;
                break;
        }
        return mDelegate.returningResponse(result).findRoutesByStopName(paramsMap);
    }

    @Override
    public Call<Result> findStopsByRouteId(@Body ParamsMap paramsMap) {
        Result result = new Gson().fromJson(readMockedJson("200_success_details_stops"), Result.class);
        return mDelegate.returningResponse(result).findRoutesByStopName(paramsMap);
    }

    @Override
    public Call<Result> findDeparturesByRouteId(@Body ParamsMap paramsMap) {
        Result result = new Gson().fromJson(readMockedJson("200_success_details_departures"), Result.class);
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
}
