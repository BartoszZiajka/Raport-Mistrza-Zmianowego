package com.example.raport_mistrza_zmianowego.core.connectors;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

import static com.example.raport_mistrza_zmianowego.core.connectors.DatabaseConnector.*;

public class DataFromFacilitiesConnector {

    private static final String INSERT_DATA_FROM_FACILITIES_QUERY = "INSERT INTO dane_z_obiektow (pw15_zasole, c15_zasole, pw20_zasole, c20_zasole, przeplyw_min_zasole, przeplyw_max_zasole, pw15_zaborze, c15_zaborze, pw20_zaborze, c20_zaborze, przeplyw_min_zaborze, przeplyw_max_zaborze, pw15_hydrofornia, c15_hydrofornia, pw20_hydrofornia, c20_hydrofornia, odczyt_chelmek, zuzycie_chelmek) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private static final String SELECT_DATA_FROM_FACILITIES_FROM_REPORT_QUERY = "SELECT * FROM dane_z_obiektow WHERE id_danych_z_obiektow=(?)";
    private static final String SELECT_LAST_RECORD_OF_DATA_FROM_FACILITIES_QUERY = "SELECT id_danych_z_obiektow FROM dane_z_obiektow ORDER BY id_danych_z_obiektow DESC LIMIT 1";
    private static final String UPDATE_DATA_FROM_FACILITIES_QUERY = "UPDATE dane_z_obiektow SET pw15_zasole=(?), c15_zasole=(?), pw20_zasole=(?), c20_zasole=(?), przeplyw_min_zasole=(?), przeplyw_max_zasole=(?), pw15_zaborze=(?), c15_zaborze=(?), pw20_zaborze=(?), c20_zaborze=(?), przeplyw_min_zaborze=(?), przeplyw_max_zaborze=(?), pw15_hydrofornia=(?), c15_hydrofornia=(?), pw20_hydrofornia=(?), c20_hydrofornia=(?), odczyt_chelmek=(?), zuzycie_chelmek=(?) WHERE id_danych_z_obiektow=(?)";

    public void addDataFromFacilitiesRecord(String pw15Zasole, String c15Zasole, String pw20Zasole, String c20Zasole, String przeplywMinZasole, String przeplywMaxZasole, String pw15Zaborze, String c15Zaborze, String pw20Zaborze, String c20Zaborze, String przeplywMinZaborze, String przeplywMaxZaborze, String pw15Hydrofornia, String c15Hydrofornia, String pw20Hydrofornia, String c20Hydrofornia, String odczytChelmek, String zuzycieChelmek) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement insertQuery = connection.prepareStatement(INSERT_DATA_FROM_FACILITIES_QUERY);
            insertQuery.setString(1, pw15Zasole);
            insertQuery.setString(2, c15Zasole);
            insertQuery.setString(3, pw20Zasole);
            insertQuery.setString(4, c20Zasole);
            insertQuery.setString(5, przeplywMinZasole);
            insertQuery.setString(6, przeplywMaxZasole);
            insertQuery.setString(7, pw15Zaborze);
            insertQuery.setString(8, c15Zaborze);
            insertQuery.setString(9, pw20Zaborze);
            insertQuery.setString(10, c20Zaborze);
            insertQuery.setString(11, przeplywMinZaborze);
            insertQuery.setString(12, przeplywMaxZaborze);
            insertQuery.setString(13, pw15Hydrofornia);
            insertQuery.setString(14, c15Hydrofornia);
            insertQuery.setString(15, pw20Hydrofornia);
            insertQuery.setString(16, c20Hydrofornia);
            insertQuery.setString(17, odczytChelmek);
            insertQuery.setString(18, zuzycieChelmek);
            insertQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public Map<String, String> getDataFromFacilitiesFromReport(int dataFromFacilitiesID) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_DATA_FROM_FACILITIES_FROM_REPORT_QUERY);
            selectQuery.setInt(1, dataFromFacilitiesID);
            ResultSet result = selectQuery.executeQuery();
            result.next();
            Map<String, String> dataFromFacilitiesFieldsValuesList = new HashMap<>();
            dataFromFacilitiesFieldsValuesList.put("pw15_zasole", result.getString("pw15_zasole"));
            dataFromFacilitiesFieldsValuesList.put("c15_zasole", result.getString("c15_zasole"));
            dataFromFacilitiesFieldsValuesList.put("pw20_zasole", result.getString("pw20_zasole"));
            dataFromFacilitiesFieldsValuesList.put("c20_zasole", result.getString("c20_zasole"));
            dataFromFacilitiesFieldsValuesList.put("przeplyw_min_zasole", result.getString("przeplyw_min_zasole"));
            dataFromFacilitiesFieldsValuesList.put("przeplyw_max_zasole", result.getString("przeplyw_max_zasole"));
            dataFromFacilitiesFieldsValuesList.put("pw15_zaborze", result.getString("pw15_zaborze"));
            dataFromFacilitiesFieldsValuesList.put("c15_zaborze", result.getString("c15_zaborze"));
            dataFromFacilitiesFieldsValuesList.put("pw20_zaborze", result.getString("pw20_zaborze"));
            dataFromFacilitiesFieldsValuesList.put("c20_zaborze", result.getString("c20_zaborze"));
            dataFromFacilitiesFieldsValuesList.put("przeplyw_min_zaborze", result.getString("przeplyw_min_zaborze"));
            dataFromFacilitiesFieldsValuesList.put("przeplyw_max_zaborze", result.getString("przeplyw_max_zaborze"));
            dataFromFacilitiesFieldsValuesList.put("pw15_hydrofornia", result.getString("pw15_hydrofornia"));
            dataFromFacilitiesFieldsValuesList.put("c15_hydrofornia", result.getString("c15_hydrofornia"));
            dataFromFacilitiesFieldsValuesList.put("pw20_hydrofornia", result.getString("pw20_hydrofornia"));
            dataFromFacilitiesFieldsValuesList.put("c20_hydrofornia", result.getString("c20_hydrofornia"));
            dataFromFacilitiesFieldsValuesList.put("odczyt_chelmek", result.getString("odczyt_chelmek"));
            dataFromFacilitiesFieldsValuesList.put("zuzycie_chelmek", result.getString("zuzycie_chelmek"));
            return dataFromFacilitiesFieldsValuesList;
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public int getLastDataFromFacilitiesID() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_LAST_RECORD_OF_DATA_FROM_FACILITIES_QUERY);
            ResultSet result = selectQuery.executeQuery();
            result.next();
            return result.getInt("id_danych_z_obiektow");
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void updateDataFromFacilitiesRecord(String idDanychZObiektow, String pw15Zasole, String c15Zasole, String pw20Zasole, String c20Zasole, String przeplywMinZasole, String przeplywMaxZasole, String pw15Zaborze, String c15Zaborze, String pw20Zaborze, String c20Zaborze, String przeplywMinZaborze, String przeplywMaxZaborze, String pw15Hydrofornia, String c15Hydrofornia, String pw20Hydrofornia, String c20Hydrofornia, String odczytChelmek, String zuzycieChelmek) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement updateQuery = connection.prepareStatement(UPDATE_DATA_FROM_FACILITIES_QUERY);
            updateQuery.setString(1, pw15Zasole);
            updateQuery.setString(2, c15Zasole);
            updateQuery.setString(3, pw20Zasole);
            updateQuery.setString(4, c20Zasole);
            updateQuery.setString(5, przeplywMinZasole);
            updateQuery.setString(6, przeplywMaxZasole);
            updateQuery.setString(7, pw15Zaborze);
            updateQuery.setString(8, c15Zaborze);
            updateQuery.setString(9, pw20Zaborze);
            updateQuery.setString(10, c20Zaborze);
            updateQuery.setString(11, przeplywMinZaborze);
            updateQuery.setString(12, przeplywMaxZaborze);
            updateQuery.setString(13, pw15Hydrofornia);
            updateQuery.setString(14, c15Hydrofornia);
            updateQuery.setString(15, pw20Hydrofornia);
            updateQuery.setString(16, c20Hydrofornia);
            updateQuery.setString(17, odczytChelmek);
            updateQuery.setString(18, zuzycieChelmek);
            updateQuery.setString(19, idDanychZObiektow);
            updateQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }
}
