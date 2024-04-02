package com.example.raport_mistrza_zmianowego.core.model;

public class StandingOrder {
    private String standingOrderName;
    private int standingOrderID;

    public StandingOrder(String standingOrderName, int standingOrderID) {
        this.standingOrderName = standingOrderName;
        this.standingOrderID = standingOrderID;
    }

    public String getStandingOrderName() {
        return standingOrderName;
    }

    public void setStandingOrderName(String standingOrderName) {
        this.standingOrderName = standingOrderName;
    }

    public int getStandingOrderID() {
        return standingOrderID;
    }

    public void setStandingOrderID(int standingOrderID) {
        this.standingOrderID = standingOrderID;
    }
}
