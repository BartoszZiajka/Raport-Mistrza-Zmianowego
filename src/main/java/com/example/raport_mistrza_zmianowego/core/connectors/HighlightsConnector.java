package com.example.raport_mistrza_zmianowego.core.connectors;

import com.example.raport_mistrza_zmianowego.core.model.DrivingCostAccount;
import com.example.raport_mistrza_zmianowego.core.model.Vehicle;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.raport_mistrza_zmianowego.core.connectors.DatabaseConnector.*;

public class HighlightsConnector {
    private static final String INSERT_VEHICLE_PRICING_QUERY = "INSERT INTO pojazdy (nazwa_pojazdu, cennik_normalny, cennik_nadgodzinny) VALUES (?, ?, ?)";
    private static final String INSERT_DIVING_COST_ACCOUNT_QUERY = "INSERT INTO konta_kosztow_jazdy (numer_konta, nazwa_konta) VALUES (?, ?)";
    private static final String SELECT_DRIVING_COST_ACCOUNTS_QUERY = "SELECT numer_konta, nazwa_konta FROM konta_kosztow_jazdy";
    private static final String SELECT_VEHICLES_PRICING_QUERY = "SELECT nazwa_pojazdu, cennik_normalny, cennik_nadgodzinny FROM pojazdy ORDER BY nazwa_pojazdu";
    private static final String SELECT_VEHICLE_PRICING_ID_QUERY = "SELECT id_pojazdu FROM pojazdy WHERE nazwa_pojazdu=(?) AND cennik_normalny=(?) AND cennik_nadgodzinny=(?)";
    private static final String SELECT_DRIVING_COST_ACCOUNT_ID_QUERY = "SELECT id_konta FROM konta_kosztow_jazdy WHERE numer_konta=(?) AND nazwa_konta=(?)";
    private static final String UPDATE_VEHICLE_PRICING_QUERY = "UPDATE pojazdy SET nazwa_pojazdu=(?), cennik_normalny=(?), cennik_nadgodzinny=(?) WHERE id_pojazdu=(?)";
    private static final String UPDATE_DRIVING_COST_ACCOUNT_QUERY = "UPDATE konta_kosztow_jazdy SET numer_konta=(?), nazwa_konta=(?) WHERE id_konta=(?)";
    private static final String DELETE_VEHICLE_PRICING_QUERY = "DELETE FROM pojazdy WHERE id_pojazdu=(?)";
    private static final String DELETE_DRIVING_COST_ACCOUNT_QUERY = "DELETE FROM konta_kosztow_jazdy WHERE id_konta=(?)";

    public void addVehiclePricing(String vehicleName, String regularPricing, String overtimePricing) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement insertQuery = connection.prepareStatement(INSERT_VEHICLE_PRICING_QUERY);
            insertQuery.setString(1, vehicleName);
            insertQuery.setString(2, regularPricing);
            insertQuery.setString(3, overtimePricing);
            insertQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void addDrivingCostAccount(String accountNumber, String accountName) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement insertQuery = connection.prepareStatement(INSERT_DIVING_COST_ACCOUNT_QUERY);
            insertQuery.setString(1, accountNumber);
            insertQuery.setString(2, accountName);
            insertQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public List<DrivingCostAccount> getDrivingCostAccounts() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement preparedStatement = connection.prepareStatement(SELECT_DRIVING_COST_ACCOUNTS_QUERY);
            ResultSet result = preparedStatement.executeQuery();
            List<DrivingCostAccount> drivingCostAccountsList = new ArrayList<>();
            while (result.next()) {
                drivingCostAccountsList.add(new DrivingCostAccount(result.getString("numer_konta"), result.getString("nazwa_konta")));
            }
            return drivingCostAccountsList;
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public List<Vehicle> getVehicles() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_VEHICLES_PRICING_QUERY);
            ResultSet result = selectQuery.executeQuery();
            List<Vehicle> pojazdyList = new ArrayList<>();
            while (result.next()) {
                pojazdyList.add(new Vehicle(result.getString("nazwa_pojazdu"), result.getString("cennik_normalny"), result.getString("cennik_nadgodzinny")));
            }
            return pojazdyList;
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public int getVehiclePricingID(String vehicleName, String regularPricing, String overtimePricing) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_VEHICLE_PRICING_ID_QUERY);
            selectQuery.setString(1, vehicleName);
            selectQuery.setString(2, regularPricing);
            selectQuery.setString(3, overtimePricing);
            ResultSet result = selectQuery.executeQuery();
            result.next();
            return result.getInt("id_pojazdu");
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public int getDrivingCostAccountID(String accountNumber, String accountName) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_DRIVING_COST_ACCOUNT_ID_QUERY);
            selectQuery.setString(1, accountNumber);
            selectQuery.setString(2, accountName);
            ResultSet result = selectQuery.executeQuery();
            result.next();
            return result.getInt("id_konta");
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void updateVehiclePricing(String vehicleName, String regularPricing, String overtimePricing, Vehicle oldVehicle) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement updateQuery = connection.prepareStatement(UPDATE_VEHICLE_PRICING_QUERY);
            updateQuery.setString(1, vehicleName);
            updateQuery.setString(2, regularPricing);
            updateQuery.setString(3, overtimePricing);
            updateQuery.setInt(4, getVehiclePricingID(oldVehicle.getVehicleName(), oldVehicle.getRegularPricing(), oldVehicle.getOvertimePricing()));
            updateQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void updateDrivingCostAccount(String accontNumber, String accountName, int drivingCostAccountID) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement updateQuery = connection.prepareStatement(UPDATE_DRIVING_COST_ACCOUNT_QUERY);
            updateQuery.setString(1, accontNumber);
            updateQuery.setString(2, accountName);
            updateQuery.setInt(3, drivingCostAccountID);
            updateQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void deleteVehiclePricing(int vehicleID) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement deleteQuery = connection.prepareStatement(DELETE_VEHICLE_PRICING_QUERY);
            deleteQuery.setInt(1, vehicleID);
            deleteQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void deleteDrivingCostAccount(int drivingCostAccountID) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement deleteQuery = connection.prepareStatement(DELETE_DRIVING_COST_ACCOUNT_QUERY);
            deleteQuery.setInt(1, drivingCostAccountID);
            deleteQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }
}
