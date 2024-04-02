package com.example.raport_mistrza_zmianowego.core.connectors;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.raport_mistrza_zmianowego.core.connectors.DatabaseConnector.*;

public class PortersConnector {
    private static final String INSERT_PORTER_QUERY = "INSERT INTO portierzy (portier) VALUES (?)";
    private static final String INSERT_PORTERS_FROM_RAPORT_QUERY = "INSERT INTO portierzy_raport (do_godz, od_godz, portier_do, portier_od) VALUES (?, ?, ?, ?)";
    private static final String SELECT_PORTERS_QUERY = "SELECT portier FROM portierzy ORDER BY portier";
    private static final String SELECT_PORTERS_FROM_RAPORT_QUERY = "SELECT * FROM portierzy_raport WHERE id_portierow=(?)";
    private static final String SELECT_LAST_ID_OF_PORTERS_FROM_RAPORT_QUERY = "SELECT id_portierow FROM portierzy_raport ORDER BY id_portierow DESC LIMIT 1";
    private static final String UPDATE_PORTER_QUERY = "UPDATE portierzy SET portier=(?) WHERE portier=(?)";
    private static final String UPDATE_PORTERS_FROM_REPORT_QUERY = "UPDATE portierzy_raport SET do_godz=(?), od_godz=(?), portier_do=(?), portier_od=(?) WHERE id_portierow=(?)";
    private static final String DELETE_PORTER_QUERY = "DELETE FROM portierzy WHERE portier=(?)";

    public void addPorterRecord(String porter) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement insertQuery = connection.prepareStatement(INSERT_PORTER_QUERY);
            insertQuery.setString(1, porter);
            insertQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void addPortersFromReportRecord(String until, String from, String porterUntil, String porterFrom) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement insertQuery = connection.prepareStatement(INSERT_PORTERS_FROM_RAPORT_QUERY);
            insertQuery.setString(1, until);
            insertQuery.setString(2, from);
            insertQuery.setString(3, porterUntil);
            insertQuery.setString(4, porterFrom);
            insertQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public List<String> getPorters() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_PORTERS_QUERY);
            ResultSet result = selectQuery.executeQuery();
            List<String> portersList = new ArrayList<>();
            while (result.next()) portersList.add(result.getString("portier"));
            return portersList;
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> getPortersFromReport(int portersID) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_PORTERS_FROM_RAPORT_QUERY);
            selectQuery.setInt(1, portersID);
            ResultSet result = selectQuery.executeQuery();
            result.next();
            Map<String, String> portersFieldsValuesList = new HashMap<>();
            portersFieldsValuesList.put("do_godz", result.getString("do_godz"));
            portersFieldsValuesList.put("od_godz", result.getString("od_godz"));
            portersFieldsValuesList.put("portier_do", result.getString("portier_do"));
            portersFieldsValuesList.put("portier_od", result.getString("portier_od"));
            return portersFieldsValuesList;
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public int getLastPortersID() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_LAST_ID_OF_PORTERS_FROM_RAPORT_QUERY);
            ResultSet rs = selectQuery.executeQuery();
            rs.next();
            return rs.getInt("id_portierow");
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void updatePorter(String newPorter, String oldPorter) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement updateQuery = connection.prepareStatement(UPDATE_PORTER_QUERY);
            updateQuery.setString(1, newPorter);
            updateQuery.setString(2, oldPorter);
            updateQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void updatePortersFromReport(String portersID, String until, String from, String porterUntil, String porterFrom) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement updateQuery = connection.prepareStatement(UPDATE_PORTERS_FROM_REPORT_QUERY);
            updateQuery.setString(1, until);
            updateQuery.setString(2, from);
            updateQuery.setString(3, porterUntil);
            updateQuery.setString(4, porterFrom);
            updateQuery.setString(5, portersID);
            updateQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void deletePorter(String porter) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement deleteQuery = connection.prepareStatement(DELETE_PORTER_QUERY);
            deleteQuery.setString(1, porter);
            deleteQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }
}
