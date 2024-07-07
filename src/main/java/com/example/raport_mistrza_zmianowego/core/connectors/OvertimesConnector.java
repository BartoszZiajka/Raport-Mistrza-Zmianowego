package com.example.raport_mistrza_zmianowego.core.connectors;

import com.example.raport_mistrza_zmianowego.core.model.Overtime;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static com.example.raport_mistrza_zmianowego.core.connectors.DatabaseConnector.*;

public class OvertimesConnector {
    private static final String INSERT_OVERTIME_QUERY = "INSERT INTO nadgodziny_raport (pracownik, od_godz, do_godz, rodzaj_pracy, id_raportu) VALUES (?, ?, ?, ?, ?)";
    private static final String INSERT_EMPLOYEE_QUERY = "INSERT INTO pracownicy (pracownik) VALUES (?)";
    private static final String SELECT_OVERTIMES_FROM_QUERY = "SELECT id_nadgodziny, pracownik, od_godz, do_godz, rodzaj_pracy FROM nadgodziny_raport WHERE id_raportu=(?) ORDER BY pracownik";
    private static final String SELECT_EMPLOYEES_QUERY = "SELECT pracownik FROM pracownicy ORDER BY pracownik";
    private static final String SELECT_OVERTIME_ID_QUERY = "SELECT id_nadgodziny FROM nadgodziny_raport WHERE pracownik=(?) AND od_godz=(?) AND do_godz=(?) AND rodzaj_pracy=(?) AND id_raportu=(?)";
    private static final String UPDATE_EMPLOYEE_QUERY = "UPDATE pracownicy SET pracownik=(?) WHERE pracownik=(?)";
    private static final String DELETE_OVERTIME_WHERE_ID_RAPORTU_QUERY = "DELETE FROM nadgodziny_raport WHERE id_raportu=(?)";
    private static final String DELETE_OVERTIME_QUERY = "DELETE FROM nadgodziny_raport WHERE pracownik=(?) AND od_godz=(?) AND do_godz=(?) AND rodzaj_pracy=(?)";
    private static final String DELETE_EMPLOYEE_QUERY = "DELETE FROM pracownicy WHERE pracownik=(?)";

    public void addOvertimeRecord(Overtime overtime, int raportID) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement insertQuery = connection.prepareStatement(INSERT_OVERTIME_QUERY);
            insertQuery.setString(1, overtime.getEmployee());
            insertQuery.setString(2, overtime.getFrom());
            insertQuery.setString(3, overtime.getUntil());
            insertQuery.setString(4, overtime.getWorkName());
            insertQuery.setInt(5, raportID);
            insertQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void addOvertimesRecords(ObservableList<Overtime> overtimes, int raportID) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            for (Overtime overtime : overtimes) {
                PreparedStatement insertQuery = connection.prepareStatement(INSERT_OVERTIME_QUERY);
                insertQuery.setString(1, overtime.getEmployee());
                insertQuery.setString(2, overtime.getFrom());
                insertQuery.setString(3, overtime.getUntil());
                insertQuery.setString(4, overtime.getWorkName());
                insertQuery.setInt(5, raportID);
                insertQuery.executeUpdate();
            }
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void addEmployeePromptRecord(String employeeName) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement insertQuery = connection.prepareStatement(INSERT_EMPLOYEE_QUERY);
            insertQuery.setString(1, employeeName);
            insertQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public List<Overtime> getOvertimesById(int raportID) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_OVERTIMES_FROM_QUERY);
            selectQuery.setInt(1, raportID);
            ResultSet result = selectQuery.executeQuery();
            List<Overtime> overtimesList = new ArrayList<>();
            while (result.next())
                overtimesList.add(new Overtime(result.getString("pracownik"), result.getString("od_godz"),
                        result.getString("do_godz"), result.getString("rodzaj_pracy"), result.getInt("id_nadgodziny")));
            return overtimesList;
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public List<String> getEmployees() {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_EMPLOYEES_QUERY);
            ResultSet result = selectQuery.executeQuery();
            List<String> pracownicyList = new ArrayList<>();
            while (result.next()) pracownicyList.add(result.getString("pracownik"));
            return pracownicyList;
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    private int getOvertimeID(Overtime overtime, int raportID) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement selectQuery = connection.prepareStatement(SELECT_OVERTIME_ID_QUERY);
            selectQuery.setString(1, overtime.getEmployee());
            selectQuery.setString(2, overtime.getFrom());
            selectQuery.setString(3, overtime.getUntil());
            selectQuery.setString(4, overtime.getWorkName());
            selectQuery.setInt(5, raportID);
            ResultSet result = selectQuery.executeQuery();
            result.next();
            return result.getInt("id_nadgodziny");
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void updateOvertimesRecords(ObservableList<Overtime> overtimes, int raportID) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement deleteQuery = connection.prepareStatement(DELETE_OVERTIME_WHERE_ID_RAPORTU_QUERY);
            deleteQuery.setInt(1, raportID);
            deleteQuery.executeUpdate();
            for (Overtime overtime : overtimes) {
                addOvertimeRecord(overtime, raportID);
            }
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void updateEmployee(String newEmployee, String oldEmployee) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement updateQuery = connection.prepareStatement(UPDATE_EMPLOYEE_QUERY);
            updateQuery.setString(1, newEmployee);
            updateQuery.setString(2, oldEmployee);
            updateQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }
    }

    public void deleteOvertime(Overtime overtime) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement deleteQuery = connection.prepareStatement(DELETE_OVERTIME_QUERY);
            deleteQuery.setString(1, overtime.getEmployee());
            deleteQuery.setString(2, overtime.getFrom());
            deleteQuery.setString(3, overtime.getUntil());
            deleteQuery.setString(4, overtime.getWorkName());
            deleteQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }

    }

    public void deleteEmployee(String employee) {
        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
            PreparedStatement deleteQuery = connection.prepareStatement(DELETE_EMPLOYEE_QUERY);
            deleteQuery.setString(1, employee);
            deleteQuery.executeUpdate();
        } catch (SQLException e) {
            showAlert(e.getMessage());
            printSQLException(e);
            throw new RuntimeException(e);
        }

    }
}
