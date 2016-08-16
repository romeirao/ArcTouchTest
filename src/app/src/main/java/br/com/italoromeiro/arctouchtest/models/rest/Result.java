package br.com.italoromeiro.arctouchtest.models.rest;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.italoromeiro.arctouchtest.models.Row;

/**
 * Created by italo on 09/08/16.
 */
public class Result implements Serializable {

    @SerializedName("rows")
    private List<Row> mRows = new ArrayList<>();

    @SerializedName("rowsAffected")
    private Integer mRowsAffected;

    public List<Row> getRows() {
        return mRows;
    }

    public void setRows(List<Row> rows) {
        this.mRows = rows;
    }

    public Integer getRowsAffected() {
        return mRowsAffected;
    }

    public void setRowsAffected(Integer rowsAffected) {
        this.mRowsAffected = rowsAffected;
    }

    @Override
    public String toString() {
        return "Result { rows: " + mRows + "; rowsAffected: " + mRowsAffected + "; }";
    }
}
