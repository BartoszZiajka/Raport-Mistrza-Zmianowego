package com.example.raport_mistrza_zmianowego.core.connectors;

import com.example.raport_mistrza_zmianowego.controllers.LoginController;
import com.example.raport_mistrza_zmianowego.core.model.Overtime;
import com.example.raport_mistrza_zmianowego.core.model.Report;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.raport_mistrza_zmianowego.core.connectors.DatabaseConnector.*;

public class ReportConnector {
    private static final String INSERT_REPORT_QUERY = "INSERT INTO raporty (data_raportu, godziny_pracy, mistrz_zmiany, raport_zmiany, id_konta_uzytkownika, id_portierow, id_dyzurnych, id_danych_z_obiektow) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_REPORT_FROM_QUERY = "SELECT * FROM raporty WHERE data_raportu=(?) AND godziny_pracy=(?)";
    private static final String SELECT_REPORT_ID_QUERY = "SELECT id_raportu FROM raporty WHERE data_raportu=(?) AND godziny_pracy=(?)";
    private static final String SELECT_REPORT_DATES_QUERY = "SELECT id_raportu,data_raportu, godziny_pracy FROM raporty ORDER BY data_raportu DESC";
    private static final String SELECT_REPORT_DATES_FROM_SEARCH_QUERY = "SELECT data_raportu, godziny_pracy FROM raporty WHERE concat(data_raportu, ' ' ,godziny_pracy) LIKE '%(?)%'ORDER BY data_raportu DESC";
    private static final String SELECT_REPORT_DATES_FROM_ACCOUNT_QUERY = "SELECT data_raportu, godziny_pracy FROM raporty WHERE id_konta_uzytkownika=(?) ORDER BY data_raportu DESC";
    private static final String SELECT_REPORT_BY_ID_QUERY = "SELECT * FROM raporty " + "WHERE id_raportu=(?)";
    private static final String SELECT_COUNT_OF_RECORDS_QUERY = "SELECT COUNT(id_raportu) AS count " + "FROM raporty WHERE data_raportu=(?) AND godziny_pracy=(?)";
    private static final String UPDATE_REPORT_QUERY = "UPDATE raporty SET godziny_pracy=(?), mistrz_zmiany=(?), raport_zmiany=(?) WHERE id_raportu=(?)";

    public void createNewRecord(LocalDate dataRaportu, String godzinyPracy, String mistrzZmiany, String doGodz, String odGodz, String raportZmiany, String dyzurZasole, String dyzurZasole1, String dyzurZaborze, String dyzurZaborze1, String dyzurHydrofornia, String dyzurHydrofornia1, String dyzurPrzepompownia, String dyzurPrzepompownia1, String pw15Zasole, String c15Zasole, String pw20Zasole, String c20Zasole, String przeplywMinZasole, String przeplywMaxZasole, String pw15Zaborze, String c15Zaborze, String pw20Zaborze, String c20Zaborze, String przeplywMinZaborze, String przeplywMaxZaborze, String pw15Hydrofornia, String c15Hydrofornia, String pw20Hydrofornia, String c20Hydrofornia, String odczytChelmek, String zuzycieChelmek, ObservableList<Overtime> nadgodziny, String portierDo, String portierOd) {
        new PortersConnector().addPortersFromReportRecord(doGodz, odGodz, portierDo, portierOd);
        new DutyOfficersConnector().addDutyOfficersRecord(dyzurZasole, dyzurZasole1, dyzurZaborze, dyzurZaborze1, dyzurHydrofornia, dyzurHydrofornia1, dyzurPrzepompownia, dyzurPrzepompownia1);
        new DataFromFacilitiesConnector().addDataFromFacilitiesRecord(pw15Zasole, c15Zasole, pw20Zasole, c20Zasole, przeplywMinZasole, przeplywMaxZasole, pw15Zaborze, c15Zaborze, pw20Zaborze, c20Zaborze, przeplywMinZaborze, przeplywMaxZaborze, pw15Hydrofornia, c15Hydrofornia, pw20Hydrofornia, c20Hydrofornia, odczytChelmek, zuzycieChelmek);
        addReportRecord(String.valueOf(dataRaportu), godzinyPracy, mistrzZmiany, raportZmiany, LoginController.userAccountId, new PortersConnector().getLastPortersID(), new DutyOfficersConnector().getLastDutyOfficersID(), new DataFromFacilitiesConnector().getLastDataFromFacilitiesID());
        new OvertimesConnector().addOvertimesRecords(nadgodziny, getReportID(String.valueOf(dataRaportu), godzinyPracy));
    }

    private void addReportRecord(String reportDate, String workingHours, String userAccountName, String shiftReport, int userAccountID, int portersFromReportID, int dutyOfficersFromReportID, int dataFromFacilitiesID) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement insertQuery = connection.prepareStatement(INSERT_REPORT_QUERY);
            insertQuery.setString(1, String.valueOf(reportDate));
            insertQuery.setString(2, workingHours);
            insertQuery.setString(3, userAccountName);
            insertQuery.setString(4, shiftReport);
            insertQuery.setInt(5, userAccountID);
            insertQuery.setInt(6, portersFromReportID);
            insertQuery.setInt(7, dutyOfficersFromReportID);
            insertQuery.setInt(8, dataFromFacilitiesID);
            insertQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> getReportFrom(String reportDate, String workingHours) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_REPORT_FROM_QUERY);
            selectQuery.setString(1, reportDate);
            selectQuery.setString(2, workingHours);
            ResultSet result = selectQuery.executeQuery();
            Map<String, String> reportMap = new HashMap<>();
            result.next();
            reportMap.put("id_raportu", result.getString("id_raportu"));
            reportMap.put("data_raportu", result.getString("data_raportu"));
            reportMap.put("godziny_pracy", result.getString("godziny_pracy"));
            reportMap.put("mistrz_zmiany", result.getString("mistrz_zmiany"));
            reportMap.put("raport_zmiany", result.getString("raport_zmiany"));
            reportMap.put("id_konta_uzytkownika", result.getString("id_konta_uzytkownika"));
            reportMap.put("id_portierow", result.getString("id_portierow"));
            reportMap.put("id_dyzurnych", result.getString("id_dyzurnych"));
            reportMap.put("id_danych_z_obiektow", result.getString("id_danych_z_obiektow"));
            reportMap.putAll(new PortersConnector().getPortersFromReport(result.getInt("id_portierow")));
            reportMap.putAll(new DutyOfficersConnector().getDutyOfficersFromReport(result.getInt("id_dyzurnych")));
            reportMap.putAll(new DataFromFacilitiesConnector().getDataFromFacilitiesFromReport(result.getInt("id_danych_z_obiektow")));
            return reportMap;
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public int getReportID(String reportDate, String workingHours) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_REPORT_ID_QUERY);
            selectQuery.setString(1, reportDate);
            selectQuery.setString(2, workingHours);
            ResultSet result = selectQuery.executeQuery();
            result.next();
            return result.getInt("id_raportu");
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public ObservableList<String> getReportDates() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_REPORT_DATES_QUERY);
            ResultSet result = selectQuery.executeQuery();
            List<String> employeesList = new ArrayList<>();
            while (result.next())
                employeesList.add(result.getString("id_raportu") + " " + result.getString("data_raportu") + " " + result.getString("godziny_pracy"));
            return FXCollections.observableList(employeesList);
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public ObservableList<String> getEditReportDates() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_REPORT_DATES_QUERY);
            ResultSet result = preparedStatement.executeQuery();
            List<String> employeesList = new ArrayList<>();
            while (result.next())
                if (!(result.getString("godziny_pracy")).contains("*"))
                    employeesList.add(result.getString("id_raportu") + " " + result.getString("data_raportu") + " " + result.getString("godziny_pracy"));
            return FXCollections.observableList(employeesList);
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public ObservableList<String> getEditSpecialReportDates() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_REPORT_DATES_QUERY);
            ResultSet result = selectQuery.executeQuery();
            List<String> employeesList = new ArrayList<>();
            while (result.next())
                if ((result.getString("godziny_pracy")).contains("*"))
                    employeesList.add(result.getString("id_raportu") + " " + result.getString("data_raportu") + " " + result.getString("godziny_pracy"));
            return FXCollections.observableList(employeesList);
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public ObservableList<String> getReportDatesFromSearch(String searchPhrase) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_REPORT_DATES_FROM_SEARCH_QUERY);
            selectQuery.setString(1, searchPhrase);
            ResultSet result = selectQuery.executeQuery();
            List<String> employeesList = new ArrayList<>();
            while (result.next())
                employeesList.add(result.getString("id_raportu") + " " + result.getString("data_raportu") + " " + result.getString("godziny_pracy"));
            return FXCollections.observableList(employeesList);
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public ObservableList<String> getReportDates(int userAccountID) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_REPORT_DATES_FROM_ACCOUNT_QUERY);
            selectQuery.setInt(1, userAccountID);
            ResultSet result = selectQuery.executeQuery();
            List<String> employeesList = new ArrayList<>();
            while (result.next())
                employeesList.add(result.getString("id_raportu") + " " + result.getString("data_raportu") + " " + result.getString("godziny_pracy"));
            return FXCollections.observableList(employeesList);
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public Report getReportById(int id) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_REPORT_BY_ID_QUERY);
            selectQuery.setInt(1, id);
            ResultSet result = selectQuery.executeQuery();
            result.next();

            PortersConnector portersConnector = new PortersConnector();
            Map<String, String> porters = portersConnector.getPortersFromReport(result.getInt("id_portierow"));

            DutyOfficersConnector dutyOfficersConnector = new DutyOfficersConnector();
            Map<String, String> dutyOfficers = dutyOfficersConnector.getDutyOfficersFromReport(result.getInt("id_dyzurnych"));

            DataFromFacilitiesConnector dataFromFacilitiesConnector = new DataFromFacilitiesConnector();
            Map<String, String> dataFromFacilities = dataFromFacilitiesConnector.getDataFromFacilitiesFromReport(result.getInt("id_danych_z_obiektow"));

            OvertimesConnector overtimesConnector = new OvertimesConnector();

            return new Report(result.getInt("id_raportu"),
                    result.getString("data_raportu"),
                    result.getString("godziny_pracy"),
                    result.getString("mistrz_zmiany"),
                    porters.get("od_godz"),
                    porters.get("do_godz"),
                    porters.get("portier_od"),
                    porters.get("portier_do"),
                    dutyOfficers.get("dyzur_zasole"),
                    dutyOfficers.get("dyzur_zasole_2_zmiana"),
                    dutyOfficers.get("dyzur_zaborze"),
                    dutyOfficers.get("dyzur_zaborze_2_zmiana"),
                    dutyOfficers.get("dyzur_hydrofornia"),
                    dutyOfficers.get("dyzur_hydrofornia_2_zmiana"),
                    dutyOfficers.get("dyzur_przepompownia"),
                    dutyOfficers.get("dyzur_przepompownia_2_zmiana"),
                    dataFromFacilities.get("pw15_zasole"),
                    dataFromFacilities.get("c15_zasole"),
                    dataFromFacilities.get("pw20_zasole"),
                    dataFromFacilities.get("c20_zasole"),
                    dataFromFacilities.get("przeplyw_min_zasole"),
                    dataFromFacilities.get("przeplyw_max_zasole"),
                    dataFromFacilities.get("pw15_zaborze"),
                    dataFromFacilities.get("c15_zaborze"),
                    dataFromFacilities.get("pw20_zaborze"),
                    dataFromFacilities.get("c20_zaborze"),
                    dataFromFacilities.get("przeplyw_min_zaborze"),
                    dataFromFacilities.get("przeplyw_max_zaborze"),
                    dataFromFacilities.get("pw15_hydrofornia"),
                    dataFromFacilities.get("c15_hydrofornia"),
                    dataFromFacilities.get("pw20_hydrofornia"),
                    dataFromFacilities.get("c20_hydrofornia"),
                    dataFromFacilities.get("odczyt_chelmek"),
                    dataFromFacilities.get("zuzycie_chelmek"),
                    result.getString("raport_zmiany"),
                    overtimesConnector.getOvertimesById(result.getInt("id_raportu"))
            );
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void updateReport(Report report) {
        new PortersConnector().updatePortersFromReport(report.getId(), report.getPorterHourTo(), report.getPorterHourFrom(), report.getPorterNameTo(), report.getPorterNameFrom());
        new DutyOfficersConnector().updateDutyOfficersFromReport(report.getId(), report.getStandbyZasoleFirstShift(), report.getStandbyZasoleSecondShift(), report.getStandbyZaborzeFirstShift(), report.getStandbyZaborzeSecondShift(), report.getStandbyHydroforniaFirstShift(), report.getStandbyHydroforniaSecondShift(), report.getStandbyPrzepompowniaFirstShift(), report.getStandbyPrzepompowniaSecondShift());
        new DataFromFacilitiesConnector().updateDataFromFacilitiesRecord(report.getId(), report.getPw15Zasole(), report.getC15Zasole(), report.getPw20Zasole(), report.getC20Zasole(), report.getPrzeplywMinZasole(), report.getPrzeplywMaxZasole(), report.getPw15Zaborze(), report.getC15Zaborze(), report.getPw20Zaborze(), report.getC20Zaborze(), report.getPrzeplywMinZaborze(), report.getPrzeplywMaxZaborze(), report.getPw15Hydrofornia(), report.getC15Hydrofornia(), report.getPw20Hydrofornia(), report.getC20Hydrofornia(), report.getSprzedazChelmek(), report.getZuzycieChelmek());
        updateReportRecord(report.getId(), report.getWorkingHours(), report.getDutyOfficer(), report.getShiftReport());
    }

    private void updateReportRecord(int reportID, String workingHours, String userAccountName, String shiftReport) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement updateQuery = connection.prepareStatement(UPDATE_REPORT_QUERY);
            updateQuery.setString(1, workingHours);
            updateQuery.setString(2, userAccountName);
            updateQuery.setString(3, shiftReport);
            updateQuery.setInt(4, reportID);
            updateQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public boolean isDuplicate(String reportDate, String workingHours) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_COUNT_OF_RECORDS_QUERY);
            selectQuery.setString(1, reportDate);
            selectQuery.setString(2, workingHours);
            ResultSet result = selectQuery.executeQuery();
            result.next();
            return result.getInt("count") > 0;
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);

        }

    }
}
