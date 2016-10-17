package br.com.italoromeiro.arctouchtest.models.aux;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.italoromeiro.arctouchtest.models.Stop;

/**
 * Created by italo on 11/08/16.
 */
public class StopsMap implements Serializable {
    private List<Stop> mStops = new ArrayList<>();

    public List<Stop> getStops() {
        return mStops;
    }

    public void setStops(List<Stop> stops) {
        this.mStops = stops;
    }
}
