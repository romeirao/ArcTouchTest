package br.com.italoromeiro.arctouchtest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by italo on 08/08/16.
 */
public class Row implements Serializable {

    @SerializedName("id")
    private Integer mId;

    @SerializedName("shortName")
    private String mShortName;

    @SerializedName("longName")
    private String mLongName;

    @SerializedName("lastModifiedDate")
    private String mLastModifiedDate;

    @SerializedName("agencyId")
    private Integer mAgencyId;

    @SerializedName("name")
    private String mName;

    @SerializedName("sequence")
    private Integer mSequence;

    @SerializedName("route_id")
    private Integer mRouteId;

    @SerializedName("calendar")
    private String mCalendar;

    @SerializedName("time")
    private String mTime;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
    }

    public String getShortName() {
        return mShortName;
    }

    public void setShortName(String shortName) {
        this.mShortName = shortName;
    }

    public String getLongName() {
        return mLongName;
    }

    public void setLongName(String longName) {
        this.mLongName = longName;
    }

    public String getLastModifiedDate() {
        return mLastModifiedDate;
    }

    public void setLastModifiedDate(String lastModifiedDate) {
        this.mLastModifiedDate = lastModifiedDate;
    }

    public Integer getAgencyId() {
        return mAgencyId;
    }

    public void setAgencyId(Integer agencyId) {
        this.mAgencyId = agencyId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public Integer getSequence() {
        return mSequence;
    }

    public void setSequence(Integer sequence) {
        this.mSequence = sequence;
    }

    public Integer getRouteId() {
        return mRouteId;
    }

    public void setRouteId(Integer routeId) {
        this.mRouteId = routeId;
    }

    public String getCalendar() {
        return mCalendar;
    }

    public void setCalendar(String calendar) {
        this.mCalendar = calendar;
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        this.mTime = time;
    }

    public Route getRoute() {
        if (!verifyExistence(mId, mShortName, mLongName, mLastModifiedDate, mAgencyId)) {
            return null;
        }

        Route route = new Route();
        route.setId(mId);
        route.setShortName(mShortName);
        route.setLongName(mLongName);
        route.setLastModifiedDate(mLastModifiedDate);
        route.setAgencyId(mAgencyId);
        return route;
    }

    public Stop getStop() {
        if (!verifyExistence(mId, mName, mSequence, mRouteId)) {
            return null;
        }

        Stop stop = new Stop();
        stop.setId(mId);
        stop.setName(mName);
        stop.setSequence(mSequence);
        stop.setRouteId(mRouteId);
        return stop;
    }

    public Departure getDeparture() {
        if (!verifyExistence(mId, mCalendar, mTime)) {
            return null;
        }

        Departure departure = new Departure();
        departure.setId(mId);
        departure.setCalendar(mCalendar);
        departure.setTime(mTime);
        return departure;
    }

    private boolean verifyExistence(Object... objs) {
        if (objs.length == 0) return false;

        for (Object obj : objs) {
            if (obj == null) return false;
        }

        return true;
    }

    @Override
    public String toString() {
        Route route = getRoute();
        if (route != null) {
            return "Row { route: " + route + "; }";
        }

        Stop stop = getStop();
        if (stop != null) {
            return "Row { stop: " + stop + "; }";
        }

        Departure departure = getDeparture();
        if (departure != null) {
            return "Row { departure: " + departure + "; }";
        }

        return "There is no well-formed result";
    }
}
