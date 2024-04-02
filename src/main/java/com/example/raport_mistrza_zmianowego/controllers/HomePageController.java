package com.example.raport_mistrza_zmianowego.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HomePageController implements Initializable {
    @FXML
    public MenuItem addReportMenuItem;
    @FXML
    public MenuItem reportViewingMenuItem;
    @FXML
    public MenuItem usersMenuItem;
    @FXML
    public MenuItem employeesMenuItem;
    @FXML
    public Pane contentContainerPane;
    @FXML
    public MenuItem importantInformationMenuItem;
    @FXML
    public MenuItem editImportantInformationMenuItem;
    @FXML
    public MenuItem tauronContactNumbersMenuItem;
    @FXML
    public MenuItem aboutMenuItem;
    @FXML
    public MenuItem editReportMenuItem;
    @FXML
    public Menu adminMenu;
    @FXML
    public MenuItem logoutMenuItem;
    @FXML
    public MenuItem closeProgramMenuItem;
    public VBox mainPane;
    @FXML
    public MenuItem addSpecialReportMenuItem;
    @FXML
    public MenuItem editSpecialReportMenuItem;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (LoginController.isAdmin) {
            adminMenu.setVisible(true);
            addSpecialReportMenuItem.setVisible(true);
            editSpecialReportMenuItem.setVisible(true);
        }
        if (LoginController.isSpecialReport) {
            addSpecialReportMenuItem.setVisible(true);
            editSpecialReportMenuItem.setVisible(true);
        }
        try {
            loadFXML("/com/example/raport_mistrza_zmianowego/show_raports.fxml");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void menuItemClick(ActionEvent actionEvent) throws IOException {
//        operate menu item click
        switch (((MenuItem) actionEvent.getSource()).getText()) {
            case "Dodaj raport" -> loadFXML("/com/example/raport_mistrza_zmianowego/add_report.fxml");
            case "Podgląd raportów" -> loadFXML("/com/example/raport_mistrza_zmianowego/show_raports.fxml");
            case "Edytuj raport" -> loadFXML("/com/example/raport_mistrza_zmianowego/edit_report.fxml");
            case "Dodaj raport specjalny" ->
                    loadFXML("/com/example/raport_mistrza_zmianowego/add_special_report.fxml");
            case "Edytuj raport specjalny" ->
                    loadFXML("/com/example/raport_mistrza_zmianowego/edit_special_report.fxml");
            case "Pracownicy" -> loadFXML("/com/example/raport_mistrza_zmianowego/employees.fxml");
            case "Użytkownicy" -> loadFXML("/com/example/raport_mistrza_zmianowego/users_accounts.fxml");
            case "Najważniejsze informacje" -> openImportantInformation();
            case "Edytuj ważne informacje" ->
                    loadFXML("/com/example/raport_mistrza_zmianowego/edit_highlights.fxml");
            case "Numery kontaktowe Tauron" -> openTauronInformation();
            case "O programie" -> loadFXML("/com/example/raport_mistrza_zmianowego/about.fxml");
            case "Wyloguj" -> logout();
            case "Zamknij program" -> System.exit(0);
        }
    }

    private void logout() throws IOException {
        mainPane.getChildren().clear();

        Pane newLoadedPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/com/example/raport_mistrza_zmianowego/login.fxml")));

        mainPane.getChildren().add(newLoadedPane);
        LoginController.isAdmin = false;
        LoginController.isSpecialReport = false;
    }

    public void loadFXML(String file) throws IOException {

        contentContainerPane.getChildren().clear();

        Pane newLoadedPane = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(file)));

        contentContainerPane.getChildren().add(newLoadedPane);
    }

    public void openImportantInformation() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/raport_mistrza_zmianowego/highlights.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1300, 700);
            Stage stage = new Stage();
            stage.setTitle("Najważniejsze informacje");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Nie udało się otworzyć okna", e);
        }

    }

    public void openTauronInformation() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/com/example/raport_mistrza_zmianowego/tauron_contact_numbers.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 1100, 700);
            Stage stage = new Stage();
            stage.setTitle("Numery kontaktowe Tauron");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            Logger logger = Logger.getLogger(getClass().getName());
            logger.log(Level.SEVERE, "Nie udało się otworzyć okna", e);
        }

    }

}
