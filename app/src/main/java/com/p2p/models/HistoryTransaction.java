package com.p2p.models;

import com.google.gson.annotations.SerializedName;

public class HistoryTransaction {
    @SerializedName("to_wallet")
    String toWallet;
    @SerializedName("createTs")
    String createTs;
    @SerializedName("ppn_amount")
    double PPNAmount;
    @SerializedName("btc_amount")
    double BTCAmount;
    @SerializedName("eth_amount")
    double ETHAmount;
    @SerializedName("bat_amount")
    double BATAmount;
    @SerializedName("etc_amount")
    double LTCAmount;
    @SerializedName("usdc_amount")
    double USDCAmount;
    @SerializedName("zrx_amount")
    double ZRXAmount;
    @SerializedName("etc_amount")
    double ETCAmount;

    public HistoryTransaction() {

    }

    public double getBATAmount() {
        return BATAmount;
    }

    public void setBATAmount(double BATAmount) {
        this.BATAmount = BATAmount;
    }

    public double getLTCAmount() {
        return LTCAmount;
    }

    public void setLTCAmount(double LTCAmount) {
        this.LTCAmount = LTCAmount;
    }

    public double getUSDCAmount() {
        return USDCAmount;
    }

    public void setUSDCAmount(double USDCAmount) {
        this.USDCAmount = USDCAmount;
    }

    public double getZRXAmount() {
        return ZRXAmount;
    }

    public void setZRXAmount(double ZRXAmount) {
        this.ZRXAmount = ZRXAmount;
    }

    public double getETCAmount() {
        return ETCAmount;
    }

    public void setETCAmount(double ETCAmount) {
        this.ETCAmount = ETCAmount;
    }

    public String getToWallet() {
        return toWallet;
    }

    public void setToWallet(String toWallet) {
        this.toWallet = toWallet;
    }

    public String getCreateTs() {
        return createTs;
    }

    public void setCreateTs(String createTs) {
        this.createTs = createTs;
    }

    public double getPPNAmount() {
        return PPNAmount;
    }

    public void setPPNAmount(double PPNAmount) {
        this.PPNAmount = PPNAmount;
    }

    public double getBTCAmount() {
        return BTCAmount;
    }

    public void setBTCAmount(double BTCAmount) {
        this.BTCAmount = BTCAmount;
    }

    public double getETHAmount() {
        return ETHAmount;
    }

    public void setETHAmount(double ETHAmount) {
        this.ETHAmount = ETHAmount;
    }
}
