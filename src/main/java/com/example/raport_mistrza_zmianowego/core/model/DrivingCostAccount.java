package com.example.raport_mistrza_zmianowego.core.model;

public class DrivingCostAccount {
    private String drivingCostAccountNumber;
    private String drivingCostAccountName;

    public DrivingCostAccount(String drivingCostAccountNumber, String drivingCostAccountName) {
        this.drivingCostAccountNumber = drivingCostAccountNumber;
        this.drivingCostAccountName = drivingCostAccountName;
    }

    public String getDrivingCostAccountNumber() {
        return drivingCostAccountNumber;
    }

    public void setDrivingCostAccountNumber(String drivingCostAccountNumber) {
        this.drivingCostAccountNumber = drivingCostAccountNumber;
    }

    public String getDrivingCostAccountName() {
        return drivingCostAccountName;
    }

    public void setDrivingCostAccountName(String drivingCostAccountName) {
        this.drivingCostAccountName = drivingCostAccountName;
    }
}
