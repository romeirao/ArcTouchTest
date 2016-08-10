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

    @Override
    public String toString() {
        return "Row { id: " + mId + "; shortName: " + mShortName + "; longName: " + mLongName + "; }";
    }
}
