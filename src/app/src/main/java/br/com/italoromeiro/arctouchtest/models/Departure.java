package br.com.italoromeiro.arctouchtest.models;

import java.io.Serializable;

/**
 * Created by italo on 10/08/16.
 */
public class Departure implements Serializable {
    private Integer mId;
    private String mCalendar;
    private String mTime;

    public Integer getId() {
        return mId;
    }

    public void setId(Integer id) {
        this.mId = id;
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

    @Override
    public String toString() {
        return "Departure { id: " + mId + "; calendar: " + mCalendar + "; time: " + mTime + "; }";
    }
}
