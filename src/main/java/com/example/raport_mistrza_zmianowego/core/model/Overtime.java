package com.example.raport_mistrza_zmianowego.core.model;

public class Overtime {

    int overtimeID;
    private String employee;
    private String from;
    private String until;
    private String workName;

    public Overtime(String employee, String from, String until, String workName, int overtimeID) {
        this.overtimeID = overtimeID;
        this.employee = employee;
        this.from = from;
        this.until = until;
        this.workName = workName;
    }

    public Overtime(String employee, String from, String until, String workName) {
        this.employee = employee;
        this.from = from;
        this.until = until;
        this.workName = workName;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public void setUntil(String until) {
        this.until = until;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public void setOvertimeID(int overtimeID) {
        this.overtimeID = overtimeID;
    }

    public String getEmployee() {
        return employee;
    }

    public String getFrom() {
        return from;
    }

    public String getUntil() {
        return until;
    }

    public String getWorkName() {
        return workName;
    }

    public int getOvertimeID() {
        return overtimeID;
    }
}

