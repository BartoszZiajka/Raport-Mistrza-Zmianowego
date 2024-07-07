package com.example.raport_mistrza_zmianowego.core.connectors;

import javafx.scene.control.Alert;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.sql.SQLException;

public class DatabaseConnector {
    //    connection settings
    public static String DATABASE_URL = "jdbc:mysql://localhost:3306/raport_mistrza_zmianowego?allowPublicKeyRetrieval=true&useSSL=false";
    public static String DATABASE_USERNAME = "root";
    public static String DATABASE_PASSWORD = "";


    public static void printSQLException(SQLException ex) {
        for (Throwable e : ex) {
            if (e instanceof SQLException) {
                e.printStackTrace(System.err);
                System.err.println("SQLState: " + ((SQLException) e).getSQLState());
                System.err.println("Error Code: " + ((SQLException) e).getErrorCode());
                System.err.println("Message: " + e.getMessage());
                Throwable t = ex.getCause();
                while (t != null) {
                    System.out.println("Cause: " + t);
                    t = t.getCause();
                }
            }
        }
    }

    public static void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ups!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null));
        alert.show();
    }
}

