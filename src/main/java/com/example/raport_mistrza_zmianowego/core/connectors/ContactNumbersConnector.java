package com.example.raport_mistrza_zmianowego.core.connectors;

import com.example.raport_mistrza_zmianowego.core.model.TauronContactNumber;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.raport_mistrza_zmianowego.core.connectors.DatabaseConnector.*;

public class ContactNumbersConnector {
    private static final String INSERT_CONTACT_NUMBER_QUERY = "INSERT INTO numery_kontaktowe (numer_telefonu, obiekt_numeru_telefonu) VALUES (?, ?)";
    private static final String INSERT_TAURON_CONTACT_NUMBERS_QUERY = "INSERT INTO numery_telefonow_tauron (podmiot_kontaktowy, stanowisko_podmiotu, email_kontaktowy, numer_kontaktowy) VALUES (?, ?, ?, ?)";
    private static final String SELECT_CONTACT_NUMBERS_QUERY = "SELECT numer_telefonu, obiekt_numeru_telefonu FROM numery_kontaktowe ORDER BY obiekt_numeru_telefonu";
    private static final String SELECT_TAURON_CONTACT_NUMBERS_QUERY = "SELECT * FROM numery_telefonow_tauron ORDER BY podmiot_kontaktowy";
    private static final String UPDATE_CONTACT_NUMBER_QUERY = "UPDATE numery_kontaktowe SET numer_telefonu=(?), obiekt_numeru_telefonu=(?) WHERE numer_telefonu=(?)";
    private static final String UPDATE_TAURON_CONTACT_NUMBER_QUERY = "UPDATE numery_telefonow_tauron SET podmiot_kontaktowy=(?), stanowisko_podmiotu=(?), email_kontaktowy=(?), numer_kontaktowy=(?)  WHERE id_numeru_telefonu =(?)";
    private static final String DELETE_TAURON_CONTACT_NUMBER_QUERY = "DELETE FROM numery_telefonow_tauron WHERE podmiot_kontaktowy=(?) AND stanowisko_podmiotu=(?) AND email_kontaktowy=(?) AND numer_kontaktowy=(?)";
    private static final String DELETE_CONTACT_NUMBER_QUERY = "DELETE FROM numery_kontaktowe WHERE numer_telefonu=(?) AND obiekt_numeru_telefonu=(?)";

    public void addContactNumber(String number, String objectName) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement insertQuery = connection.prepareStatement(INSERT_CONTACT_NUMBER_QUERY);
            insertQuery.setString(1, number);
            insertQuery.setString(2, objectName);
            insertQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void addTauronPhoneNumber(TauronContactNumber tauronContactNumber) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement insertQuery = connection.prepareStatement(INSERT_TAURON_CONTACT_NUMBERS_QUERY);
            insertQuery.setString(1, tauronContactNumber.getAuthorisedPersonName());
            insertQuery.setString(2, tauronContactNumber.getWorkplace());
            insertQuery.setString(3, tauronContactNumber.getEmail());
            insertQuery.setString(4, tauronContactNumber.getContactNumber());
            insertQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public List<String> getContactNumbers() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_CONTACT_NUMBERS_QUERY);
            ResultSet result = selectQuery.executeQuery();
            List<String> contactNumbersList = new ArrayList<>();
            while (result.next()) {
                contactNumbersList.add(result.getString("numer_telefonu") + " " + result.getString("obiekt_numeru_telefonu"));
            }
            return contactNumbersList;
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public List<TauronContactNumber> getTauronContactNumbers() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_TAURON_CONTACT_NUMBERS_QUERY);
            ResultSet result = selectQuery.executeQuery();
            List<TauronContactNumber> tauronContactNumbersList = new ArrayList<>();
            while (result.next()) {
                tauronContactNumbersList.add(new TauronContactNumber(result.getInt("id_numeru_telefonu"), result.getString("podmiot_kontaktowy"), result.getString("stanowisko_podmiotu"), result.getString("email_kontaktowy"), result.getString("numer_kontaktowy")));
            }
            return tauronContactNumbersList;
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void updateContactNumber(String contactNumber, String objectName, String oldContactNumber) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement updateQuery = connection.prepareStatement(UPDATE_CONTACT_NUMBER_QUERY);
            updateQuery.setString(1, contactNumber);
            updateQuery.setString(2, objectName);
            updateQuery.setString(3, oldContactNumber);
            updateQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void updateTauronContactNumber(TauronContactNumber tauronContactNumber) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement updateQuery = connection.prepareStatement(UPDATE_TAURON_CONTACT_NUMBER_QUERY);
            updateQuery.setString(1, tauronContactNumber.getAuthorisedPersonName());
            updateQuery.setString(2, tauronContactNumber.getWorkplace());
            updateQuery.setString(3, tauronContactNumber.getEmail());
            updateQuery.setString(4, tauronContactNumber.getContactNumber());
            updateQuery.setInt(5, tauronContactNumber.getTauronContactNumberID());
            updateQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void deleteContactNumber(String number, String objectName) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement deleteQuery = connection.prepareStatement(DELETE_CONTACT_NUMBER_QUERY);
            deleteQuery.setString(1, number);
            deleteQuery.setString(2, objectName);
            deleteQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void deleteTauronContactNumber(TauronContactNumber tauronContactNumber) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement deleteQuery = connection.prepareStatement(DELETE_TAURON_CONTACT_NUMBER_QUERY);
            deleteQuery.setString(1, tauronContactNumber.getAuthorisedPersonName());
            deleteQuery.setString(2, tauronContactNumber.getWorkplace());
            deleteQuery.setString(3, tauronContactNumber.getEmail());
            deleteQuery.setString(4, tauronContactNumber.getContactNumber());
            deleteQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }
}
