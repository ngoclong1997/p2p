package com.p2p.models;

public class Quotation {
    private int icon;
    private String name;
    private double value;

    public Quotation(int icon, String name, double value) {
        this.icon = icon;
        this.name = name;
        this.value = value;
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

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}
