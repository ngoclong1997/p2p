package com.p2p.models;

import com.google.gson.annotations.SerializedName;

public class Coin {
    private int icon;
    @SerializedName("code")
    private String name;
    @SerializedName("invest")
    private double amount;
    @SerializedName("rate_invest")
    private double changeRate;

    public Coin() {
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getChangeRate() {
        return changeRate;
    }

    public void setChangeRate(double changeRate) {
        this.changeRate = changeRate;
    }
}
