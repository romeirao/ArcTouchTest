package br.com.italoromeiro.arctouchtest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by italo on 09/08/16.
 */
public class ParamsMap implements Serializable {

    @SerializedName("params")
    private Params mParams;

    public Params getParams() {
        return mParams;
    }

    public void setParams(Params params) {
        this.mParams = params;
    }

    @Override
    public String toString() {
        return "ParamsMap { params: " + mParams + "; }";
    }
}
