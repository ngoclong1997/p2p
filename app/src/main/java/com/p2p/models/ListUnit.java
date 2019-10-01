package com.p2p.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ListUnit {
    @SerializedName("units")
    List<Unit> units;

    public ListUnit() {

    }

    public List<Unit> getUnits() {
        return units;
    }

    public void setUnits(List<Unit> units) {
        this.units = units;
    }
}
