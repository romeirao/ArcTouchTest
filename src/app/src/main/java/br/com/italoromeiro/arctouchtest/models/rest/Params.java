package br.com.italoromeiro.arctouchtest.models.rest;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by italo on 08/08/16.
 */
public class Params implements Serializable {

    @SerializedName("stopName")
    private String mStopName;

    @SerializedName("routeId")
    private Integer mRouteId;

    public String getStopName() {
        return mStopName;
    }

    public void setStopName(String stopName) {
        this.mStopName = stopName;
    }

    public Integer getRouteId() {
        return mRouteId;
    }

    public void setRouteId(Integer routeId) {
        this.mRouteId = routeId;
    }

    @Override
    public String toString() {
        return "Params { stopName: " + mStopName + "; routeId: " + mRouteId + "; }";
    }
}
