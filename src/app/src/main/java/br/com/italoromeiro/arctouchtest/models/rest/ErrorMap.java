package br.com.italoromeiro.arctouchtest.models.rest;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

import br.com.italoromeiro.arctouchtest.models.Error;

/**
 * Created by italo on 09/08/16.
 */
public class ErrorMap implements Serializable {

    @SerializedName("error")
    private Error mError;

    public Error getError() {
        return mError;
    }

    public void setError(Error error) {
        mError = error;
    }

    @Override
    public String toString() {
        return "ErrorMap { error: " + mError + "; }";
    }
}
