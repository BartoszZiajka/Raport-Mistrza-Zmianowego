package com.example.raport_mistrza_zmianowego.core.connectors;

import com.example.raport_mistrza_zmianowego.core.model.DutyOfficer;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.raport_mistrza_zmianowego.core.connectors.DatabaseConnector.*;

public class DutyOfficersConnector {
    private static final String INSERT_DUTY_OFFICER_QUERY = "INSERT INTO dyzurni (dyzurny) VALUES (?)";
    private static final String INSERT_DUTY_OFFICERS_QUERY = "INSERT INTO dyzurni_raport (dyzur_zasole, dyzur_zasole_2_zmiana, dyzur_zaborze, dyzur_zaborze_2_zmiana, dyzur_hydrofornia, dyzur_hydrofornia_2_zmiana, dyzur_przepompownia, dyzur_przepompownia_2_zmiana) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_DUTY_OFFICERS_QUERY = "SELECT id_dyzurnego, dyzurny FROM dyzurni ORDER BY dyzurny";
    private static final String SELECT_DUTY_OFFICERS_PROMPTS_QUERY = "SELECT dyzurny FROM dyzurni ORDER BY dyzurny";
    private static final String SELECT_DUTY_OFFICERS_FROM_REPORT_QUERY = "SELECT * FROM dyzurni_raport WHERE id_dyzurnych=(?)";
    private static final String SELECT_LAST_ID_OF_DUTY_OFFICERS_FROM_REPORT_QUERY = "SELECT id_dyzurnych FROM dyzurni_raport ORDER BY id_dyzurnych DESC LIMIT 1";
    private static final String UPDATE_DUTY_OFFICER_QUERY = "UPDATE dyzurni SET dyzurny=(?) WHERE id_dyzurnego=(?)";
    private static final String UPDATE_DUTY_OFFICERS_FROM_REPORT = "UPDATE dyzurni_raport SET dyzur_zasole=(?), dyzur_zaborze=(?), dyzur_hydrofornia=(?), dyzur_przepompownia=(?), dyzur_zasole_2_zmiana=(?), dyzur_zaborze_2_zmiana=(?), dyzur_hydrofornia_2_zmiana=(?), dyzur_przepompownia_2_zmiana=(?) WHERE id_dyzurnych=(?)";
    private static final String DELETE_DUTY_OFFICER_QUERY = "DELETE FROM dyzurni WHERE id_dyzurnego=(?)";

    public void addDutyOfficerRecord(String dutyOfficerName) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement insertQuery = connection.prepareStatement(INSERT_DUTY_OFFICER_QUERY);
            insertQuery.setString(1, dutyOfficerName);
            insertQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void addDutyOfficersRecord(String dyzurZasole, String dyzurZasole1, String dyzurZaborze, String dyzurZaborze1,
                                      String dyzurHydrofornia, String dyzurHydrofornia1, String dyzurPrzepompownia, String dyzurPrzepompownia1) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement insertQuery = connection.prepareStatement(INSERT_DUTY_OFFICERS_QUERY);
            insertQuery.setString(1, dyzurZasole);
            insertQuery.setString(2, dyzurZasole1);
            insertQuery.setString(3, dyzurZaborze);
            insertQuery.setString(4, dyzurZaborze1);
            insertQuery.setString(5, dyzurHydrofornia);
            insertQuery.setString(6, dyzurHydrofornia1);
            insertQuery.setString(7, dyzurPrzepompownia);
            insertQuery.setString(8, dyzurPrzepompownia1);
            insertQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public List<DutyOfficer> getDutyOfficers() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_DUTY_OFFICERS_QUERY);
            ResultSet result = selectQuery.executeQuery();
            List<DutyOfficer> portierzyList = new ArrayList<>();
            while (result.next())
                portierzyList.add(new DutyOfficer(result.getInt("id_dyzurnego"), result.getString("dyzurny")));
            return portierzyList;
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public List<String> getDutyOfficersPrompts() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_DUTY_OFFICERS_PROMPTS_QUERY);
            ResultSet result = selectQuery.executeQuery();
            List<String> dutyOfficersPromptsList = new ArrayList<>();
            while (result.next()) dutyOfficersPromptsList.add(result.getString("dyzurny"));
            return dutyOfficersPromptsList;
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> getDutyOfficersFromReport(int dutyOfficersID) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_DUTY_OFFICERS_FROM_REPORT_QUERY);
            selectQuery.setInt(1, dutyOfficersID);
            ResultSet result = selectQuery.executeQuery();
            result.next();
            Map<String, String> dutyOfficersFieldsValuesList = new HashMap<>();
            dutyOfficersFieldsValuesList.put("dyzur_zasole", result.getString("dyzur_zasole"));
            dutyOfficersFieldsValuesList.put("dyzur_zasole_2_zmiana", result.getString("dyzur_zasole_2_zmiana"));
            dutyOfficersFieldsValuesList.put("dyzur_zaborze", result.getString("dyzur_zaborze"));
            dutyOfficersFieldsValuesList.put("dyzur_zaborze_2_zmiana", result.getString("dyzur_zaborze_2_zmiana"));
            dutyOfficersFieldsValuesList.put("dyzur_hydrofornia", result.getString("dyzur_hydrofornia"));
            dutyOfficersFieldsValuesList.put("dyzur_hydrofornia_2_zmiana", result.getString("dyzur_hydrofornia_2_zmiana"));
            dutyOfficersFieldsValuesList.put("dyzur_przepompownia", result.getString("dyzur_przepompownia"));
            dutyOfficersFieldsValuesList.put("dyzur_przepompownia_2_zmiana", result.getString("dyzur_przepompownia_2_zmiana"));
            return dutyOfficersFieldsValuesList;
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public int getLastDutyOfficersID() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_LAST_ID_OF_DUTY_OFFICERS_FROM_REPORT_QUERY);
            ResultSet result = selectQuery.executeQuery();
            result.next();
            return result.getInt("id_dyzurnych");
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void updateDutyOfficer(String newDutyOfficer, DutyOfficer oldDutyOfficer) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement updateQuery = connection.prepareStatement(UPDATE_DUTY_OFFICER_QUERY);
            updateQuery.setString(1, newDutyOfficer);
            updateQuery.setInt(2, oldDutyOfficer.getDutyOfficerID());
            updateQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void updateDutyOfficersFromReport(int dutyOfficersID, String dyzurZasole, String dyzurZasole1, String dyzurZaborze, String dyzurZaborze1, String dyzurHydrofornia, String dyzurHydrofornia1, String dyzurPrzepompownia, String dyzurPrzepompownia1) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement updateQuery = connection.prepareStatement(UPDATE_DUTY_OFFICERS_FROM_REPORT);
            updateQuery.setString(1, dyzurZasole);
            updateQuery.setString(2, dyzurZaborze);
            updateQuery.setString(3, dyzurHydrofornia);
            updateQuery.setString(4, dyzurPrzepompownia);
            updateQuery.setString(5, dyzurZasole1);
            updateQuery.setString(6, dyzurZaborze1);
            updateQuery.setString(7, dyzurHydrofornia1);
            updateQuery.setString(8, dyzurPrzepompownia1);
            updateQuery.setInt(9, dutyOfficersID);
            updateQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void deleteDutyOfficer(DutyOfficer dutyOfficer) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement deleteQuery = connection.prepareStatement(DELETE_DUTY_OFFICER_QUERY);
            deleteQuery.setInt(1, dutyOfficer.getDutyOfficerID());
            deleteQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }
}
