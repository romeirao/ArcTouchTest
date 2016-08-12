package br.com.italoromeiro.arctouchtest.rest.interfaces;

import br.com.italoromeiro.arctouchtest.models.rest.ParamsMap;
import br.com.italoromeiro.arctouchtest.models.rest.Result;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by italo on 08/08/16.
 */
public interface AppGluService {
    @POST("queries/findRoutesByStopName/run")
    Call<Result> findRoutesByStopName(@Body ParamsMap paramsMap);

    @POST("queries/findStopsByRouteId/run")
    Call<Result> findStopsByRouteId(@Body ParamsMap paramsMap);

    @POST("queries/findDeparturesByRouteId/run")
    Call<Result> findDeparturesByRouteId(@Body ParamsMap paramsMap);
}
