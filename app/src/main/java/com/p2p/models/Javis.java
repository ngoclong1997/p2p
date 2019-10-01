package com.p2p.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Javis {

    @SerializedName("total_members")
    int totalMemebers;

    @SerializedName("total_investment")
    double totalInvestment;

    @SerializedName("total_profit")
    double totalProfit;

    @SerializedName("coins")
    List<Coin> coins;

    public int getTotalMemebers() {
        return totalMemebers;
    }

    public void setTotalMemebers(int totalMemebers) {
        this.totalMemebers = totalMemebers;
    }

    public double getTotalInvestment() {
        return totalInvestment;
    }

    public void setTotalInvestment(double totalInvestment) {
        this.totalInvestment = totalInvestment;
    }

    public double getTotalProfit() {
        return totalProfit;
    }

    public void setTotalProfit(double totalProfit) {
        this.totalProfit = totalProfit;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public void setCoins(List<Coin> coins) {
        this.coins = coins;
    }
}
