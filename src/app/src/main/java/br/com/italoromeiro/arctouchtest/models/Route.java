package br.com.italoromeiro.arctouchtest.models;

import java.io.Serializable;

/**
 * Created by italo on 10/08/16.
 */
public class Route implements Serializable {
    private Integer mId;
    private String mShortName;
    private String mLongName;
    private String mLastModifiedDate;
    private Integer mAgencyId;

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

    @Override
    public String toString() {
        return "Route { id: " + mId + "; shortName: " + mShortName + "; longName: " + mLongName + "; lastModifiedDate: " + mLastModifiedDate + "; agencyId: " + mAgencyId + "; }";
    }
}
