package com.example.raport_mistrza_zmianowego.core.model;

public class Vehicle {
    private String vehicleName;
    private String regularPricing;
    private String overtimePricing;

    public Vehicle(String vehicleName, String regularPricing, String overtimePricing) {
        this.vehicleName = vehicleName;
        this.regularPricing = regularPricing;
        this.overtimePricing = overtimePricing;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getRegularPricing() {
        return regularPricing;
    }

    public void setRegularPricing(String regularPricing) {
        this.regularPricing = regularPricing;
    }

    public String getOvertimePricing() {
        return overtimePricing;
    }

    public void setOvertimePricing(String overtimePricing) {
        this.overtimePricing = overtimePricing;
    }
}
