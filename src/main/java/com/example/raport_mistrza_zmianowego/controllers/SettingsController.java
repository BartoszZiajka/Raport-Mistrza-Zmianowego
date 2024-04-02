package com.example.raport_mistrza_zmianowego.controllers;

import com.example.raport_mistrza_zmianowego.core.connectors.DatabaseConnector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class SettingsController implements Initializable {
    @FXML
    public Button saveDatabaseLocationButton;
    @FXML
    public TextField databaseLocationTextField;
    @FXML
    public TextField databasePasswordTextField;
    @FXML
    public TextField databaseUserTextField;
    @FXML
    public AnchorPane pane;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Preferences preferences = Preferences.userNodeForPackage(SettingsController.class);
        DatabaseConnector.DATABASE_URL = preferences.get("location", DatabaseConnector.DATABASE_URL);
        DatabaseConnector.DATABASE_USERNAME = preferences.get("user", DatabaseConnector.DATABASE_USERNAME);
        DatabaseConnector.DATABASE_PASSWORD = preferences.get("password", DatabaseConnector.DATABASE_PASSWORD);
        databaseLocationTextField.setText(DatabaseConnector.DATABASE_URL);
        databaseUserTextField.setText(DatabaseConnector.DATABASE_USERNAME);
        databasePasswordTextField.setText(DatabaseConnector.DATABASE_PASSWORD);
    }

    @FXML
    public void saveDatabaseLocation() {
        DatabaseConnector.DATABASE_URL = databaseLocationTextField.getText();
        DatabaseConnector.DATABASE_USERNAME = databaseUserTextField.getText();
        DatabaseConnector.DATABASE_PASSWORD = databasePasswordTextField.getText();
        Preferences preferences = Preferences.userNodeForPackage(SettingsController.class);
        preferences.put("location", DatabaseConnector.DATABASE_URL);
        preferences.put("user", DatabaseConnector.DATABASE_USERNAME);
        preferences.put("password", DatabaseConnector.DATABASE_PASSWORD);
        try {
            loadFXML();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadFXML() throws IOException {
        pane.getChildren().clear();
        Pane newLoadedPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/raport_mistrza_zmianowego/login.fxml")));
        pane.getChildren().add(newLoadedPane);
    }
}
