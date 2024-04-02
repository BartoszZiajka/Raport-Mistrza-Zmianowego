package com.example.raport_mistrza_zmianowego.core.connectors;

import com.example.raport_mistrza_zmianowego.core.model.StandingOrder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.raport_mistrza_zmianowego.core.connectors.DatabaseConnector.*;

public class StandingOrdersConnector {
    private static final String INSERT_STANDING_ORDER_QUERY = "INSERT INTO zlecenia_stale (podmiot_zlecenia) VALUES (?)";
    private static final String SELECT_STANDING_ORDERS_QUERY = "SELECT podmiot_zlecenia, id_zlecenia_stalego FROM zlecenia_stale ORDER BY podmiot_zlecenia";
    private static final String SELECT_STANDING_ORDER_ID_QUERY = "SELECT id_zlecenia_stalego FROM zlecenia_stale WHERE podmiot_zlecenia=(?)";
    private static final String UPDATE_STANDING_ORDER_QUERY = "UPDATE zlecenia_stale SET podmiot_zlecenia=(?) WHERE podmiot_zlecenia=(?)";
    private static final String DELETE_STANDING_ORDER_QUERY = "DELETE FROM zlecenia_stale WHERE podmiot_zlecenia=(?)";


    public void addStandingOrderRecord(String standingOrderName) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement insertQuery = connection.prepareStatement(INSERT_STANDING_ORDER_QUERY);
            insertQuery.setString(1, standingOrderName);
            insertQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public List<StandingOrder> getStandingOrdersRecords() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_STANDING_ORDERS_QUERY);
            ResultSet result = selectQuery.executeQuery();
            List<StandingOrder> standingOrdersList = new ArrayList<>();
            while (result.next()) {
                standingOrdersList.add(new StandingOrder(result.getString("podmiot_zlecenia"), result.getInt("id_zlecenia_stalego")));
            }
            return standingOrdersList;
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public int getStandingOrderRecordID(String standingOrderName) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_STANDING_ORDER_ID_QUERY);
            selectQuery.setString(1, standingOrderName);
            ResultSet result = selectQuery.executeQuery();
            result.next();
            return result.getInt("id_zlecenia_stalego");
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void updateStandingOrderRecords(String newStandingOrder, String oldStandingOrder) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement updateQuery = connection.prepareStatement(UPDATE_STANDING_ORDER_QUERY);
            updateQuery.setString(1, newStandingOrder);
            updateQuery.setString(2, oldStandingOrder);
            updateQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void deleteStandingOrderRecord(String standingOrderName) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement deleteQuery = connection.prepareStatement(DELETE_STANDING_ORDER_QUERY);
            deleteQuery.setString(1, standingOrderName);
            deleteQuery.executeUpdate();
            new StandingOrdersAddressesConnector().deleteStandingOrderAddressesRecord(standingOrderName);
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }
}
