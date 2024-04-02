package com.example.raport_mistrza_zmianowego.core.connectors;

import com.example.raport_mistrza_zmianowego.controllers.LoginController;
import com.example.raport_mistrza_zmianowego.core.model.Overtime;
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
    private static final String SELECT_REPORT_DATES_QUERY = "SELECT data_raportu, godziny_pracy FROM raporty ORDER BY data_raportu DESC";
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
                employeesList.add(result.getString("data_raportu") + " " + result.getString("godziny_pracy"));
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
            ResultSet resultSet = preparedStatement.executeQuery();
            List<String> employeesList = new ArrayList<>();
            while (resultSet.next())
                if (!(resultSet.getString("godziny_pracy")).contains("*"))
                    employeesList.add(resultSet.getString("data_raportu") + " " + resultSet.getString("godziny_pracy"));
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
                    employeesList.add(result.getString("data_raportu") + " " + result.getString("godziny_pracy"));
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
                employeesList.add(result.getString("data_raportu") + " " + result.getString("godziny_pracy"));
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
                employeesList.add(result.getString("data_raportu") + " " + result.getString("godziny_pracy"));
            return FXCollections.observableList(employeesList);
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> getReportRecord(int reportID) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_REPORT_BY_ID_QUERY);
            selectQuery.setInt(1, reportID);
            ResultSet result = selectQuery.executeQuery();
            result.next();
            Map<String, String> reportRecord = new HashMap<>();
            reportRecord.put("id_raportu", result.getString("id_raportu"));
            reportRecord.put("godziny_pracy", result.getString("godziny_pracy"));
            reportRecord.put("mistrz_zmiany", result.getString("mistrz_zmiany"));
            reportRecord.put("raport_zmiany", result.getString("raport_zmiany"));
            reportRecord.put("id_konta_uzytkownika", result.getString("id_konta_uzytkownika"));
            reportRecord.put("id_portierow", result.getString("id_portierow"));
            reportRecord.put("id_dyzurnych", result.getString("id_dyzurnych"));
            reportRecord.put("id_danych_z_obiektow", result.getString("id_danych_z_obiektow"));
            return reportRecord;
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void updateReport(String raportDate, String workingHours, String userAccountName, String portierDoGodz, String portierDo, String portierOdGodz, String portierOd, String dyzurZasole, String dyzurZasole1, String dyzurZaborze, String dyzurZaborze1, String dyzurHydrofornia, String dyzurHydrofornia1, String dyzurPrzepompownia, String dyzurPrzepompownia1, String raportZmiany, String pw15Zasole, String c15Zasole, String pw20Zasole, String c20Zasole, String przeplywMinZasole, String przeplywMaxZasole, String pw15Zaborze, String c15Zaborze, String pw20Zaborze, String c20Zaborze, String przeplywMinZaborze, String przeplywMaxZaborze, String pw15Hydrofornia, String c15Hydrofornia, String pw20Hydrofornia, String c20Hydrofornia, String odczytChelmek, String zuzycieChelmek) {
        Map<String, String> reportRecord = getReportRecord(getReportID(raportDate, workingHours));
        new PortersConnector().updatePortersFromReport(reportRecord.get("id_portierow"), portierDoGodz, portierOdGodz, portierDo, portierOd);
        new DutyOfficersConnector().updateDutyOfficersFromReport(Integer.parseInt(reportRecord.get("id_dyzurnych")), dyzurZasole, dyzurZasole1, dyzurZaborze, dyzurZaborze1, dyzurHydrofornia, dyzurHydrofornia1, dyzurPrzepompownia, dyzurPrzepompownia1);
        new DataFromFacilitiesConnector().updateDataFromFacilitiesRecord(reportRecord.get("id_danych_z_obiektow"), pw15Zasole, c15Zasole, pw20Zasole, c20Zasole, przeplywMinZasole, przeplywMaxZasole, pw15Zaborze, c15Zaborze, pw20Zaborze, c20Zaborze, przeplywMinZaborze, przeplywMaxZaborze, pw15Hydrofornia, c15Hydrofornia, pw20Hydrofornia, c20Hydrofornia, odczytChelmek, zuzycieChelmek);
        updateReportRecord(getReportID(raportDate, workingHours), workingHours, userAccountName, raportZmiany);
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
