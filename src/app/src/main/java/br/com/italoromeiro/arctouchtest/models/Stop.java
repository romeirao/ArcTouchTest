package br.com.italoromeiro.arctouchtest.models;

import java.io.Serializable;

/**
 * Created by italo on 10/08/16.
 */
public class Stop implements Serializable {
    private Integer mId;
    private String mName;
    private Integer mSequence;
    private Integer mRouteId;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
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

    @Override
    public String toString() {
        return "Stop { id: " + mId + "; name: " + mName + "; sequence: " + mSequence + "; route_id: " + mRouteId + "; }";
    }
}
