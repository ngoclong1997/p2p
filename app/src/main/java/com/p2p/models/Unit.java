package com.p2p.models;

import com.google.gson.annotations.SerializedName;

public class Unit {
    @SerializedName("code")
    String code;
    @SerializedName("name")
    String name;
    @SerializedName("rate")
    double rate;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Unit() {

    }
}
