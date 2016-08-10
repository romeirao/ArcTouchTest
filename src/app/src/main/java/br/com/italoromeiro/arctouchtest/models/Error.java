package br.com.italoromeiro.arctouchtest.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by italo on 09/08/16.
 */
public class Error implements Serializable {

    @SerializedName("code")
    private String mCode;

    @SerializedName("message")
    private String mMessage;

    public String getCode() {
        return mCode;
    }

    public void setCode(String code) {
        this.mCode = code;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        this.mMessage = message;
    }

    @Override
    public String toString() {
        return "Error { code: " + mCode + "; message: " + mMessage + "; }";
    }
}
