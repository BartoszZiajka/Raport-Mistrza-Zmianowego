package com.example.raport_mistrza_zmianowego.core.model;

public class DutyOfficer {
    private int dutyOfficerID;
    private String dutyOfficerName;

    public DutyOfficer(int dutyOfficerID, String dutyOfficerName) {
        this.dutyOfficerID = dutyOfficerID;
        this.dutyOfficerName = dutyOfficerName;
    }

    public int getDutyOfficerID() {
        return dutyOfficerID;
    }

    public void setDutyOfficerID(int dutyOfficerID) {
        this.dutyOfficerID = dutyOfficerID;
    }

    public String getDutyOfficerName() {
        return dutyOfficerName;
    }

    public void setDutyOfficerName(String dutyOfficerName) {
        this.dutyOfficerName = dutyOfficerName;
    }

}
