package br.com.italoromeiro.arctouchtest.models.aux;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.italoromeiro.arctouchtest.models.Departure;

/**
 * Created by italo on 11/08/16.
 */
public class DeparturesMap implements Serializable {
    private List<Departure> mDepartures = new ArrayList<>();

    public List<Departure> getDepartures() {
        return mDepartures;
    }

    public void setDepartures(List<Departure> departures) {
        this.mDepartures = departures;
    }
}
