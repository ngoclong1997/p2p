package com.p2p.models;

import com.google.gson.annotations.SerializedName;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

    @SerializedName("country")
    private String country;

    @SerializedName("invitation_code")
    private String invitationCode;

    @SerializedName("btc_code")
    private String btcCode;

    @SerializedName("eth_code")
    private String ethCode;

    @SerializedName("login")
    private String login;

    @SerializedName("email")
    private String email;

    @SerializedName("full_name")
    private String fullName;

    @SerializedName("phone_number")
    private String phoneNumber;

    @SerializedName("id")
    private String id;

    @SerializedName("bat_code")
    private String batCode;

    @SerializedName("ltc_code")
    private String ltcCode;

    @SerializedName("usdc_code")
    private String usdcCode;

    @SerializedName("zrx_code")
    private String zrxCode;

    @SerializedName("etc_code")
    private String etcCode;

    @SerializedName("profit_amount")
    private double profit;

    @SerializedName("community_profit")
    private double system;

    @SerializedName("eth_invest_amount")
    private double ethInvestment;

    @SerializedName("btc_invest_amount")
    private double btcInvestment;

    @SerializedName("bat_invest_amount")
    private double batInvestment;

    @SerializedName("ltc_invest_amount")
    private double ltcInvestment;

    @SerializedName("usdc_invest_amount")
    private double usdcInvestment;

    @SerializedName("zrx_invest_amount")
    private double zrxInvestment;

    @SerializedName("etc_invest_amount")
    private double etcInvestment;

    public String getCountry() {
        return country;
    }

    public String getBatCode() {
        return batCode;
    }

    public void setBatCode(String batCode) {
        this.batCode = batCode;
    }

    public String getLtcCode() {
        return ltcCode;
    }

    public void setLtcCode(String ltcCode) {
        this.ltcCode = ltcCode;
    }

    public String getUsdcCode() {
        return usdcCode;
    }

    public void setUsdcCode(String usdcCode) {
        this.usdcCode = usdcCode;
    }

    public String getZrxCode() {
        return zrxCode;
    }

    public void setZrxCode(String zrxCode) {
        this.zrxCode = zrxCode;
    }

    public String getEtcCode() {
        return etcCode;
    }

    public void setEtcCode(String etcCode) {
        this.etcCode = etcCode;
    }

    public double getBatInvestment() {
        return batInvestment;
    }

    public void setBatInvestment(double batInvestment) {
        this.batInvestment = batInvestment;
    }

    public double getLtcInvestment() {
        return ltcInvestment;
    }

    public void setLtcInvestment(double ltcInvestment) {
        this.ltcInvestment = ltcInvestment;
    }

    public double getUsdcInvestment() {
        return usdcInvestment;
    }

    public void setUsdcInvestment(double usdcInvestment) {
        this.usdcInvestment = usdcInvestment;
    }

    public double getZrxInvestment() {
        return zrxInvestment;
    }

    public void setZrxInvestment(double zrxInvestment) {
        this.zrxInvestment = zrxInvestment;
    }

    public double getEtcInvestment() {
        return etcInvestment;
    }

    public void setEtcInvestment(double etcInvestment) {
        this.etcInvestment = etcInvestment;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getInvitationCode() {
        return invitationCode;
    }

    public void setInvitationCode(String invitationCode) {
        this.invitationCode = invitationCode;
    }

    public String getBtcCode() {
        return btcCode;
    }

    public void setBtcCode(String btcCode) {
        this.btcCode = btcCode;
    }

    public String getEthCode() {
        return ethCode;
    }

    public void setEthCode(String ethCode) {
        this.ethCode = ethCode;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
    }

    public double getSystem() {
        return system;
    }

    public void setSystem(double system) {
        this.system = system;
    }

    public double getEthInvestment() {
        return ethInvestment;
    }

    public void setEthInvestment(double ethInvestment) {
        this.ethInvestment = ethInvestment;
    }

    public double getBtcInvestment() {
        return btcInvestment;
    }

    public void setBtcInvestment(double btcInvestment) {
        this.btcInvestment = btcInvestment;
    }

    public User() {

    }
}
