package com.example.raport_mistrza_zmianowego.core.connectors;

import com.example.raport_mistrza_zmianowego.core.model.UserAccount;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.raport_mistrza_zmianowego.core.connectors.DatabaseConnector.*;

public class UserAccountConnector {
    private static final String INSERT_USER_ACCOUNT_QUERY = "INSERT INTO konta_uzytkownikow (nazwa_konta, login, haslo, administrator, raport_specjalny) VALUES (?, ?, ?, ?, ?)";
    private static final String SELECT_USER_ACCOUNT_QUERY = "SELECT * FROM konta_uzytkownikow WHERE id_konta_uzytkownika=(?)";
    private static final String SELECT_USER_ACCOUNTS_QUERY = "SELECT * FROM konta_uzytkownikow";
    private static final String SELECT_USER_ACCOUNT_ID_QUERY = "SELECT id_konta_uzytkownika FROM konta_uzytkownikow WHERE login=(?) AND haslo=(?)";
    private static final String UPDATE_USER_ACCOUNT_QUERY = "UPDATE konta_uzytkownikow SET nazwa_konta=(?), login=(?), haslo=(?), administrator=(?), raport_specjalny=(?) WHERE id_konta_uzytkownika=(?)";
    private static final String DELETE_USER_ACCOUNT_QUERY = "DELETE FROM konta_uzytkownikow WHERE id_konta_uzytkownika=(?)";
    private static final String SELECT_COUNT_OF_LOGIN_QUERY = "SELECT COUNT(login) AS count FROM konta_uzytkownikow WHERE login=(?)";
    private static final String SELECT_PASSWORD_FOR_LOGIN_QUERY = "SELECT haslo FROM konta_uzytkownikow WHERE login=(?)";
    private static final String SELECT_ADMIN_FOR_ACCOUNT_QUERY = "SELECT administrator FROM konta_uzytkownikow WHERE login=(?) AND haslo=(?)";
    private static final String SELECT_SPECIAL_REPORT_FOR_ACCOUNT_QUERY = "SELECT raport_specjalny FROM konta_uzytkownikow WHERE login=(?) AND haslo=(?)";

    public void addUserAccountRecord(String login, String password, String accountName, boolean isAdmin, boolean isSpecialReport) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement insertQuery = connection.prepareStatement(INSERT_USER_ACCOUNT_QUERY);
            insertQuery.setString(1, accountName);
            insertQuery.setString(2, login);
            insertQuery.setString(3, password);
            insertQuery.setBoolean(4, isAdmin);
            insertQuery.setBoolean(5, isSpecialReport);
            insertQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public List<UserAccount> getUserAccounts() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_USER_ACCOUNTS_QUERY);
            ResultSet result = selectQuery.executeQuery();
            List<UserAccount> userAccountsList = new ArrayList<>();
            while (result.next())
                userAccountsList.add(new UserAccount(result.getString("login"), result.getString("haslo"), result.getString("nazwa_konta"), result.getInt("id_konta_uzytkownika"), result.getBoolean("administrator"), result.getBoolean("raport_specjalny")));
            return userAccountsList;
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public int getUserAccountID(String login, String password) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_USER_ACCOUNT_ID_QUERY);
            selectQuery.setString(1, login);
            selectQuery.setString(2, password);
            ResultSet result = selectQuery.executeQuery();
            result.next();
            return result.getInt("id_konta_uzytkownika");
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public UserAccount getUserAccount(int userAccountID) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_USER_ACCOUNT_QUERY);
            selectQuery.setInt(1, userAccountID);
            ResultSet result = selectQuery.executeQuery();
            result.next();
            return new UserAccount(result.getString("login"), result.getString("haslo"), result.getString("nazwa_konta"), result.getInt("id_konta_uzytkownika"), result.getBoolean("administrator"), result.getBoolean("raport_specjalny"));
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void updateUserAccount(String login, String password, String userAccountName, int userAccountID, boolean isAdmin, boolean isSpecialReport) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement updateQuery = connection.prepareStatement(UPDATE_USER_ACCOUNT_QUERY);
            updateQuery.setString(1, userAccountName);
            updateQuery.setString(2, login);
            updateQuery.setString(3, password);
            updateQuery.setBoolean(4, isAdmin);
            updateQuery.setBoolean(5, isSpecialReport);
            updateQuery.setInt(6, userAccountID);
            updateQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void deleteUserAccount(int userAccountID) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement deleteQuery = connection.prepareStatement(DELETE_USER_ACCOUNT_QUERY);
            deleteQuery.setInt(1, userAccountID);
            deleteQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public boolean checkIfLoginExists(String login) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_COUNT_OF_LOGIN_QUERY);
            selectQuery.setString(1, login);
            ResultSet result = selectQuery.executeQuery();
            result.next();
            return result.getInt("count") > 0;
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public boolean checkPasswordForUserAccount(String login, String password) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_PASSWORD_FOR_LOGIN_QUERY);
            selectQuery.setString(1, login);
            ResultSet resultSet = selectQuery.executeQuery();
            resultSet.next();
            return resultSet.getString("haslo").equals(password);
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public boolean isAdmin(String login, String password) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_ADMIN_FOR_ACCOUNT_QUERY);
            selectQuery.setString(1, login);
            selectQuery.setString(2, password);
            ResultSet resultSet = selectQuery.executeQuery();
            resultSet.next();
            return resultSet.getBoolean("administrator");
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public boolean isSpecialReport(String login, String password) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_SPECIAL_REPORT_FOR_ACCOUNT_QUERY);
            selectQuery.setString(1, login);
            selectQuery.setString(2, password);
            ResultSet result = selectQuery.executeQuery();
            result.next();
            return result.getBoolean("raport_specjalny");
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }
}
