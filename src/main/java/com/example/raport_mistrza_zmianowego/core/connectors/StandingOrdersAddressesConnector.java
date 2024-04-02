package com.example.raport_mistrza_zmianowego.core.connectors;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.raport_mistrza_zmianowego.core.connectors.DatabaseConnector.*;

public class StandingOrdersAddressesConnector {
    private static final String INSERT_STANDING_ORDER_ADDRESS_QUERY = "INSERT INTO adresy_zlecen_stalych (adres, id_zlecenia_stalego) VALUES (?, ?)";
    private static final String SELECT_ADDRESSES_FOR_STANDING_ORDER_QUERY = "SELECT adres FROM adresy_zlecen_stalych WHERE id_zlecenia_stalego=(?) ORDER BY adres";
    private static final String UPDATE_STANDING_ORDER_ADDRESSES_QUERY = "UPDATE adresy_zlecen_stalych SET adres=(?) WHERE adres=(?)";
    private static final String DELETE_STANDING_ORDER_ADDRESSES_QUERY = "DELETE FROM adresy_zlecen_stalych WHERE id_zlecenia_stalego=(?)";

    public void addStandingOrderAddressRecord(String addressName, int standingOrderID) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement insertQuery = connection.prepareStatement(INSERT_STANDING_ORDER_ADDRESS_QUERY);
            insertQuery.setString(1, addressName);
            insertQuery.setInt(2, standingOrderID);
            insertQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public List<String> getAddressesRecordsForStandingOrder(int standingOrderID) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_ADDRESSES_FOR_STANDING_ORDER_QUERY);
            selectQuery.setInt(1, standingOrderID);
            ResultSet result = selectQuery.executeQuery();
            List<String> adresyZlecenList = new ArrayList<>();
            while (result.next()) {
                adresyZlecenList.add(result.getString("adres"));
            }
            return adresyZlecenList;
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void updateStandingOrderAddressesRecords(String newAddress, String oldAddress) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement updateQuery = connection.prepareStatement(UPDATE_STANDING_ORDER_ADDRESSES_QUERY);
            updateQuery.setString(1, newAddress);
            updateQuery.setString(2, oldAddress);
            updateQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }

    }

    public void deleteStandingOrderAddressesRecord(String standingOrderName) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement deleteQuery = connection.prepareStatement(DELETE_STANDING_ORDER_ADDRESSES_QUERY);
            deleteQuery.setInt(1, new StandingOrdersConnector().getStandingOrderRecordID(standingOrderName));
            deleteQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }
}
