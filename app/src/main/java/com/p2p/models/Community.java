package com.p2p.models;

import com.google.gson.annotations.SerializedName;

public class Community {
    @SerializedName("total_members")
    int teamMembers;
    @SerializedName("total_investments")
    double totalInvestment;
    @SerializedName("total_profit")
    double totalProfit;

    public Community() {

    }

    public int getTeamMembers() {
        return teamMembers;
    }

    public void setTeamMembers(int teamMembers) {
        this.teamMembers = teamMembers;
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
}
