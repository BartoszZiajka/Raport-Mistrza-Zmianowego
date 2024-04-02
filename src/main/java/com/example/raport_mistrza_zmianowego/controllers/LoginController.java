package com.example.raport_mistrza_zmianowego.controllers;

import com.example.raport_mistrza_zmianowego.core.connectors.DatabaseConnector;
import com.example.raport_mistrza_zmianowego.core.connectors.UserAccountConnector;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Window;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class LoginController implements Initializable {
    public static String login;
    public static boolean isAdmin = false;
    public static boolean isSpecialReport = false;
    public static int userAccountId;
    public static String userAccountName;
    @FXML
    public TextField loginTextField;
    @FXML
    public PasswordField passwordField;
    @FXML
    public Button logInButton;
    @FXML
    public AnchorPane pane;
    @FXML
    public Button settingsButton;
    private UserAccountConnector userAccountConnector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Preferences preferences = Preferences.userNodeForPackage(SettingsController.class);
        DatabaseConnector.DATABASE_URL = preferences.get("location", DatabaseConnector.DATABASE_URL);
        DatabaseConnector.DATABASE_USERNAME = preferences.get("user", DatabaseConnector.DATABASE_USERNAME);
        DatabaseConnector.DATABASE_PASSWORD = preferences.get("password", DatabaseConnector.DATABASE_PASSWORD);
        userAccountConnector = new UserAccountConnector();
    }

    @FXML
    public void logIn() throws IOException {
//        set window for alert
        Window owner = logInButton.getScene().getWindow();
//        check if fields are empty
        if (!loginTextField.getText().isEmpty() && !passwordField.getText().isEmpty()) {
//            check if mistrz exists in database
            if (userAccountConnector.checkIfLoginExists(loginTextField.getText())) {
//                check if password is valid
                if (userAccountConnector.checkPasswordForUserAccount(loginTextField.getText(), passwordField.getText())) {
//                    set logged in username
                    login = loginTextField.getText();
                    userAccountId = userAccountConnector.getUserAccountID(loginTextField.getText(), passwordField.getText());
                    userAccountName = userAccountConnector.getUserAccount(userAccountId).getUserAccountName();
//                    check if admin is logged in
                    if (userAccountConnector.isAdmin(login, passwordField.getText()))
                        isAdmin = true;
//                    check if account has moderator permissions
                    if (userAccountConnector.isSpecialReport(login, passwordField.getText())) {
                        isSpecialReport = true;
                    }
                    loadFXML("/com/example/raport_mistrza_zmianowego/home_page.fxml");
                } else showAlert(owner, "Błędne hasło!");
            } else showAlert(owner, "Podany mistrz zmiany nie istnieje!");
        } else showAlert(owner, "Uzupełnij wszystkie pola!");

    }

    public void loadFXML(String file) throws IOException {
        pane.getChildren().clear();

        Pane newLoadedPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(file)));
        pane.getChildren().add(newLoadedPane);
    }

    private static void showAlert(Window owner, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ups!");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    @FXML
    public void openSettings() {
        try {
            loadFXML("/com/example/raport_mistrza_zmianowego/settings.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void loginOnEnterPressed(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode().toString().equals("ENTER"))
            logIn();
    }
}
