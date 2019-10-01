package com.p2p.models;

public class Asset {
    private int icon;
    private String name;
    private String code;
    private double amount;
    private double amountInDollar;

    public Asset(int icon, String name, String code, double amount, double amountInDollar) {
        this.icon = icon;
        this.name = name;
        this.code = code;
        this.amount = amount;
        this.amountInDollar = amountInDollar;
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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getAmountInDollar() {
        return amountInDollar;
    }

    public void setAmountInDollar(double amountInDollar) {
        this.amountInDollar = amountInDollar;
    }
}
