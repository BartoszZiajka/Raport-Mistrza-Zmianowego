package com.example.raport_mistrza_zmianowego.core.model;

import java.util.List;

public class Report {
    private int id;
    private String reportDate;
    private String workingHours;
    private String dutyOfficer;
    private String porterHourFrom;
    private String porterHourTo;
    private String porterNameFrom;
    private String porterNameTo;
    private String standbyZasoleFirstShift;
    private String standbyZasoleSecondShift;
    private String standbyZaborzeFirstShift;
    private String standbyZaborzeSecondShift;
    private String standbyHydroforniaFirstShift;
    private String standbyHydroforniaSecondShift;
    private String standbyPrzepompowniaFirstShift;
    private String standbyPrzepompowniaSecondShift;
    private String pw15Zasole;
    private String c15Zasole;
    private String pw20Zasole;
    private String c20Zasole;
    private String przeplywMinZasole;
    private String przeplywMaxZasole;
    private String pw15Zaborze;
    private String c15Zaborze;
    private String pw20Zaborze;
    private String c20Zaborze;
    private String przeplywMinZaborze;
    private String przeplywMaxZaborze;
    private String pw15Hydrofornia;
    private String c15Hydrofornia;
    private String pw20Hydrofornia;
    private String c20Hydrofornia;
    private String sprzedazChelmek;
    private String zuzycieChelmek;
    private String shiftReport;
    private List<Overtime> overtimes;

    public Report() {
    }

    public Report(int id, String reportDate, String workingHours, String dutyOfficer, String porterHourFrom, String porterHourTo, String porterNameFrom, String porterNameTo, String standbyZasoleFirstShift, String standbyZasoleSecondShift, String standbyZaborzeFirstShift, String standbyZaborzeSecondShift, String standbyHydroforniaFirstShift, String standbyHydroforniaSecondShift, String standbyPrzepompowniaFirstShift, String standbyPrzepompowniaSecondShift, String pw15Zasole, String c15Zasole, String pw20Zasole, String c20Zasole, String przeplywMinZasole, String przeplywMaxZasole, String pw15Zaborze, String c15Zaborze, String pw20Zaborze, String c20Zaborze, String przeplywMinZaborze, String przeplywMaxZaborze, String pw15Hydrofornia, String c15Hydrofornia, String pw20Hydrofornia, String c20Hydrofornia, String sprzedazChelmek, String zuzycieChelmek, String shiftReport, List<Overtime> overtimes) {
        this.id = id;
        this.reportDate = reportDate;
        this.workingHours = workingHours;
        this.dutyOfficer = dutyOfficer;
        this.porterHourFrom = porterHourFrom;
        this.porterHourTo = porterHourTo;
        this.porterNameFrom = porterNameFrom;
        this.porterNameTo = porterNameTo;
        this.standbyZasoleFirstShift = standbyZasoleFirstShift;
        this.standbyZasoleSecondShift = standbyZasoleSecondShift;
        this.standbyZaborzeFirstShift = standbyZaborzeFirstShift;
        this.standbyZaborzeSecondShift = standbyZaborzeSecondShift;
        this.standbyHydroforniaFirstShift = standbyHydroforniaFirstShift;
        this.standbyHydroforniaSecondShift = standbyHydroforniaSecondShift;
        this.standbyPrzepompowniaFirstShift = standbyPrzepompowniaFirstShift;
        this.standbyPrzepompowniaSecondShift = standbyPrzepompowniaSecondShift;
        this.pw15Zasole = pw15Zasole;
        this.c15Zasole = c15Zasole;
        this.pw20Zasole = pw20Zasole;
        this.c20Zasole = c20Zasole;
        this.przeplywMinZasole = przeplywMinZasole;
        this.przeplywMaxZasole = przeplywMaxZasole;
        this.pw15Zaborze = pw15Zaborze;
        this.c15Zaborze = c15Zaborze;
        this.pw20Zaborze = pw20Zaborze;
        this.c20Zaborze = c20Zaborze;
        this.przeplywMinZaborze = przeplywMinZaborze;
        this.przeplywMaxZaborze = przeplywMaxZaborze;
        this.pw15Hydrofornia = pw15Hydrofornia;
        this.c15Hydrofornia = c15Hydrofornia;
        this.pw20Hydrofornia = pw20Hydrofornia;
        this.c20Hydrofornia = c20Hydrofornia;
        this.sprzedazChelmek = sprzedazChelmek;
        this.zuzycieChelmek = zuzycieChelmek;
        this.shiftReport = shiftReport;
        this.overtimes = overtimes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getReportDate() {
        return reportDate;
    }

    public void setReportDate(String reportDate) {
        this.reportDate = reportDate;
    }

    public String getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(String workingHours) {
        this.workingHours = workingHours;
    }

    public String getDutyOfficer() {
        return dutyOfficer;
    }

    public void setDutyOfficer(String dutyOfficer) {
        this.dutyOfficer = dutyOfficer;
    }

    public String getPorterHourFrom() {
        return porterHourFrom;
    }

    public void setPorterHourFrom(String porterHourFrom) {
        this.porterHourFrom = porterHourFrom;
    }

    public String getPorterHourTo() {
        return porterHourTo;
    }

    public void setPorterHourTo(String porterHourTo) {
        this.porterHourTo = porterHourTo;
    }

    public String getPorterNameFrom() {
        return porterNameFrom;
    }

    public void setPorterNameFrom(String porterNameFrom) {
        this.porterNameFrom = porterNameFrom;
    }

    public String getPorterNameTo() {
        return porterNameTo;
    }

    public void setPorterNameTo(String porterNameTo) {
        this.porterNameTo = porterNameTo;
    }

    public String getStandbyZasoleFirstShift() {
        return standbyZasoleFirstShift;
    }

    public void setStandbyZasoleFirstShift(String standbyZasoleFirstShift) {
        this.standbyZasoleFirstShift = standbyZasoleFirstShift;
    }

    public String getStandbyZasoleSecondShift() {
        return standbyZasoleSecondShift;
    }

    public void setStandbyZasoleSecondShift(String standbyZasoleSecondShift) {
        this.standbyZasoleSecondShift = standbyZasoleSecondShift;
    }

    public String getStandbyZaborzeFirstShift() {
        return standbyZaborzeFirstShift;
    }

    public void setStandbyZaborzeFirstShift(String standbyZaborzeFirstShift) {
        this.standbyZaborzeFirstShift = standbyZaborzeFirstShift;
    }

    public String getStandbyZaborzeSecondShift() {
        return standbyZaborzeSecondShift;
    }

    public void setStandbyZaborzeSecondShift(String standbyZaborzeSecondShift) {
        this.standbyZaborzeSecondShift = standbyZaborzeSecondShift;
    }

    public String getStandbyHydroforniaFirstShift() {
        return standbyHydroforniaFirstShift;
    }

    public void setStandbyHydroforniaFirstShift(String standbyHydroforniaFirstShift) {
        this.standbyHydroforniaFirstShift = standbyHydroforniaFirstShift;
    }

    public String getStandbyHydroforniaSecondShift() {
        return standbyHydroforniaSecondShift;
    }

    public void setStandbyHydroforniaSecondShift(String standbyHydroforniaSecondShift) {
        this.standbyHydroforniaSecondShift = standbyHydroforniaSecondShift;
    }

    public String getStandbyPrzepompowniaFirstShift() {
        return standbyPrzepompowniaFirstShift;
    }

    public void setStandbyPrzepompowniaFirstShift(String standbyPrzepompowniaFirstShift) {
        this.standbyPrzepompowniaFirstShift = standbyPrzepompowniaFirstShift;
    }

    public String getStandbyPrzepompowniaSecondShift() {
        return standbyPrzepompowniaSecondShift;
    }

    public void setStandbyPrzepompowniaSecondShift(String standbyPrzepompowniaSecondShift) {
        this.standbyPrzepompowniaSecondShift = standbyPrzepompowniaSecondShift;
    }

    public String getPw15Zasole() {
        return pw15Zasole;
    }

    public void setPw15Zasole(String pw15Zasole) {
        this.pw15Zasole = pw15Zasole;
    }

    public String getC15Zasole() {
        return c15Zasole;
    }

    public void setC15Zasole(String c15Zasole) {
        this.c15Zasole = c15Zasole;
    }

    public String getPw20Zasole() {
        return pw20Zasole;
    }

    public void setPw20Zasole(String pw20Zasole) {
        this.pw20Zasole = pw20Zasole;
    }

    public String getC20Zasole() {
        return c20Zasole;
    }

    public void setC20Zasole(String c20Zasole) {
        this.c20Zasole = c20Zasole;
    }

    public String getPrzeplywMinZasole() {
        return przeplywMinZasole;
    }

    public void setPrzeplywMinZasole(String przeplywMinZasole) {
        this.przeplywMinZasole = przeplywMinZasole;
    }

    public String getPrzeplywMaxZasole() {
        return przeplywMaxZasole;
    }

    public void setPrzeplywMaxZasole(String przeplywMaxZasole) {
        this.przeplywMaxZasole = przeplywMaxZasole;
    }

    public String getPw15Zaborze() {
        return pw15Zaborze;
    }

    public void setPw15Zaborze(String pw15Zaborze) {
        this.pw15Zaborze = pw15Zaborze;
    }

    public String getC15Zaborze() {
        return c15Zaborze;
    }

    public void setC15Zaborze(String c15Zaborze) {
        this.c15Zaborze = c15Zaborze;
    }

    public String getPw20Zaborze() {
        return pw20Zaborze;
    }

    public void setPw20Zaborze(String pw20Zaborze) {
        this.pw20Zaborze = pw20Zaborze;
    }

    public String getC20Zaborze() {
        return c20Zaborze;
    }

    public void setC20Zaborze(String c20Zaborze) {
        this.c20Zaborze = c20Zaborze;
    }

    public String getPrzeplywMinZaborze() {
        return przeplywMinZaborze;
    }

    public void setPrzeplywMinZaborze(String przeplywMinZaborze) {
        this.przeplywMinZaborze = przeplywMinZaborze;
    }

    public String getPrzeplywMaxZaborze() {
        return przeplywMaxZaborze;
    }

    public void setPrzeplywMaxZaborze(String przeplywMaxZaborze) {
        this.przeplywMaxZaborze = przeplywMaxZaborze;
    }

    public String getPw15Hydrofornia() {
        return pw15Hydrofornia;
    }

    public void setPw15Hydrofornia(String pw15Hydrofornia) {
        this.pw15Hydrofornia = pw15Hydrofornia;
    }

    public String getC15Hydrofornia() {
        return c15Hydrofornia;
    }

    public void setC15Hydrofornia(String c15Hydrofornia) {
        this.c15Hydrofornia = c15Hydrofornia;
    }

    public String getPw20Hydrofornia() {
        return pw20Hydrofornia;
    }

    public void setPw20Hydrofornia(String pw20Hydrofornia) {
        this.pw20Hydrofornia = pw20Hydrofornia;
    }

    public String getC20Hydrofornia() {
        return c20Hydrofornia;
    }

    public void setC20Hydrofornia(String c20Hydrofornia) {
        this.c20Hydrofornia = c20Hydrofornia;
    }

    public String getSprzedazChelmek() {
        return sprzedazChelmek;
    }

    public void setSprzedazChelmek(String sprzedazChelmek) {
        this.sprzedazChelmek = sprzedazChelmek;
    }

    public String getZuzycieChelmek() {
        return zuzycieChelmek;
    }

    public void setZuzycieChelmek(String zuzycieChelmek) {
        this.zuzycieChelmek = zuzycieChelmek;
    }

    public String getShiftReport() {
        return shiftReport;
    }

    public void setShiftReport(String shiftReport) {
        this.shiftReport = shiftReport;
    }

    public List<Overtime> getOvertimes() {
        return overtimes;
    }

    public void setOvertimes(List<Overtime> overtimes) {
        this.overtimes = overtimes;
    }


}
