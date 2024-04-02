package com.example.raport_mistrza_zmianowego.controllers;

import com.example.raport_mistrza_zmianowego.core.connectors.ContactNumbersConnector;
import com.example.raport_mistrza_zmianowego.core.connectors.HighlightsConnector;
import com.example.raport_mistrza_zmianowego.core.connectors.StandingOrdersConnector;
import com.example.raport_mistrza_zmianowego.core.model.DrivingCostAccount;
import com.example.raport_mistrza_zmianowego.core.model.StandingOrder;
import com.example.raport_mistrza_zmianowego.core.model.Vehicle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class EditHighlightsController implements Initializable {
    //    fields from important information layout
    public ListView<String> contactNumbersListView;
    public TextField contactNumberTextField;
    public ComboBox<String> objectOfContactNumberComboBox;
    public Button deleteContactNumbersButton;
    public Button saveContactNumbersButton;
    public Button addContactNumbersButton;
    public ListView<String> standingOrdersListView;
    public TextField standingOrderTextField;
    public Button saveStandingOrderButton;
    public Button addStandingOrderButton;
    public Button deleteStandingOrderButton;
    public TableView<Vehicle> vehiclePricingTableView;
    public TableColumn<Vehicle, String> vehicleNameColumn;
    public TableColumn<Vehicle, String> regularPricingColumn;
    public TableColumn<Vehicle, String> overtimePricingColumn;
    public TextField vehicleNameTextField;
    public TextField regularPricingTextField;
    public TextField overtimePricingTextField;
    public Button saveVehiclePricingButton;
    public Button addVehiclePricingButton;
    public Button deleteVehiclePricingButton;
    public ListView<String> drivingCostAccountsListView;
    public TextField drivingCostAccountNumberTextField;
    public TextField drivingCostAccountsNameTextField;
    public Button saveDrivingCostAccountButton;
    public Button addDrivingCostAccountButton;
    public Button deleteDrivingCostAccountButton;
    private StandingOrdersConnector standingOrdersConnector;
    private ContactNumbersConnector contactNumbersConnector;
    private HighlightsConnector highlightsConnector;
    private List<String> contactNumbersList;

    //    fields for store old values
    private String oldContactNumber;
    private DrivingCostAccount oldDrivingCostAccount;
    private StandingOrder oldStandingOrder;
    private Vehicle oldVehicle;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupDbConnectors();
        setupContactNumbersSection();
        setupCostDrivingAccountsSection();
        setupVehiclePricingSection();
        setupStandingOrdersSection();
    }

    @FXML
    public void addContactNumber() {
        contactNumbersConnector.addContactNumber(contactNumberTextField.getText(), objectOfContactNumberComboBox.getValue());
        contactNumbersListView.getItems().clear();
        contactNumbersList = contactNumbersConnector.getContactNumbers();
        contactNumbersListView.getItems().addAll(contactNumbersList);
    }

    @FXML
    public void saveContactNumber() {
        String[] numer = oldContactNumber.split(" ", 2);
        contactNumbersConnector.updateContactNumber(contactNumberTextField.getText(), objectOfContactNumberComboBox.getValue(), numer[0]);
        contactNumbersListView.getItems().clear();
        contactNumbersList = contactNumbersConnector.getContactNumbers();
        contactNumbersListView.getItems().addAll(contactNumbersList);
    }

    @FXML
    public void deleteContactNumber() {
        contactNumbersConnector.deleteContactNumber(contactNumberTextField.getText(), objectOfContactNumberComboBox.getValue());
        contactNumbersListView.getItems().clear();
        contactNumbersList = contactNumbersConnector.getContactNumbers();
        contactNumbersListView.getItems().addAll(contactNumbersList);
    }

    @FXML
    public void addStandingOrder() {
        standingOrdersConnector.addStandingOrderRecord(standingOrderTextField.getText());
        standingOrdersListView.getItems().clear();
        for (StandingOrder zlecenie : standingOrdersConnector.getStandingOrdersRecords())
            standingOrdersListView.getItems().add(zlecenie.getStandingOrderName());
    }

    @FXML
    public void saveStandingOrder() {
        new StandingOrdersConnector().updateStandingOrderRecords(standingOrderTextField.getText(), oldStandingOrder.getStandingOrderName());
        standingOrdersListView.getItems().clear();
        for (StandingOrder zlecenie : standingOrdersConnector.getStandingOrdersRecords())
            standingOrdersListView.getItems().add(zlecenie.getStandingOrderName());
        oldStandingOrder = new StandingOrder(standingOrderTextField.getText(), oldStandingOrder.getStandingOrderID());
    }

    @FXML
    public void deleteStandingOrder() {
        standingOrdersConnector.deleteStandingOrderRecord(standingOrderTextField.getText());
        standingOrdersListView.getItems().clear();
        for (StandingOrder zlecenie : standingOrdersConnector.getStandingOrdersRecords())
            standingOrdersListView.getItems().add(zlecenie.getStandingOrderName());
    }

    @FXML
    public void addVehiclePricing() {
        highlightsConnector.addVehiclePricing(vehicleNameTextField.getText(), regularPricingTextField.getText(), overtimePricingTextField.getText());
        vehiclePricingTableView.getItems().clear();
        vehiclePricingTableView.getItems().addAll(highlightsConnector.getVehicles());
    }

    @FXML
    public void saveVehiclePricing() {
        highlightsConnector.updateVehiclePricing(vehicleNameTextField.getText(), regularPricingTextField.getText(), overtimePricingTextField.getText(), oldVehicle);
        vehiclePricingTableView.getItems().clear();
        vehiclePricingTableView.getItems().addAll(highlightsConnector.getVehicles());
        oldVehicle = new Vehicle(vehicleNameTextField.getText(), regularPricingTextField.getText(), overtimePricingTextField.getText());
    }

    @FXML
    public void deleteVehiclePricing() {
        highlightsConnector.deleteVehiclePricing(highlightsConnector.getVehiclePricingID(vehicleNameTextField.getText(), regularPricingTextField.getText(), overtimePricingTextField.getText()));
        vehiclePricingTableView.getItems().clear();
        vehiclePricingTableView.getItems().addAll(highlightsConnector.getVehicles());
    }

    @FXML
    public void addDrivingCostAccount() {
        highlightsConnector.addDrivingCostAccount(drivingCostAccountNumberTextField.getText(), drivingCostAccountsNameTextField.getText());
        drivingCostAccountsListView.getItems().clear();
        for (DrivingCostAccount konto : highlightsConnector.getDrivingCostAccounts())
            drivingCostAccountsListView.getItems().add(konto.getDrivingCostAccountNumber() + " " + konto.getDrivingCostAccountName());
    }

    @FXML
    public void saveDrivingCostAccount() {
        highlightsConnector.updateDrivingCostAccount(drivingCostAccountNumberTextField.getText(), drivingCostAccountsNameTextField.getText(), highlightsConnector.getDrivingCostAccountID(oldDrivingCostAccount.getDrivingCostAccountNumber(), oldDrivingCostAccount.getDrivingCostAccountName()));
        drivingCostAccountsListView.getItems().clear();
        for (DrivingCostAccount konto : highlightsConnector.getDrivingCostAccounts())
            drivingCostAccountsListView.getItems().add(konto.getDrivingCostAccountNumber() + " " + konto.getDrivingCostAccountName());
        oldDrivingCostAccount = new DrivingCostAccount(drivingCostAccountNumberTextField.getText(), drivingCostAccountsNameTextField.getText());
    }

    @FXML
    public void deleteDrivingCostAccount() {
        highlightsConnector.deleteDrivingCostAccount(highlightsConnector.getDrivingCostAccountID(drivingCostAccountNumberTextField.getText(), drivingCostAccountsNameTextField.getText()));
        drivingCostAccountsListView.getItems().clear();
        for (DrivingCostAccount konto : highlightsConnector.getDrivingCostAccounts())
            drivingCostAccountsListView.getItems().add(konto.getDrivingCostAccountNumber() + " " + konto.getDrivingCostAccountName());
    }

    private void loadVehiclePricing(int index) {
        oldVehicle = new Vehicle(vehiclePricingTableView.getItems().get(index).getVehicleName(), vehiclePricingTableView.getItems().get(index).getRegularPricing(), vehiclePricingTableView.getItems().get(index).getOvertimePricing());
        vehicleNameTextField.setText(vehiclePricingTableView.getItems().get(index).getVehicleName());
        regularPricingTextField.setText(vehiclePricingTableView.getItems().get(index).getRegularPricing());
        overtimePricingTextField.setText(vehiclePricingTableView.getItems().get(index).getOvertimePricing());
    }

    private void loadContactNumbers(int index) {
        oldContactNumber = contactNumbersListView.getItems().get(index);
        String[] numerObiekt = contactNumbersListView.getItems().get(index).split(" ", 2);
        contactNumberTextField.setText(numerObiekt[0]);
        objectOfContactNumberComboBox.setValue(numerObiekt[1]);

    }

    private void loadDrivingCostAccounts(int index) {
        String[] konto = drivingCostAccountsListView.getItems().get(index).split(" ", 2);
        drivingCostAccountNumberTextField.setText(konto[0]);
        drivingCostAccountsNameTextField.setText(konto[1]);
        oldDrivingCostAccount = new DrivingCostAccount(konto[0], konto[1]);
    }

    private void setupDbConnectors() {
        standingOrdersConnector = new StandingOrdersConnector();
        contactNumbersConnector = new ContactNumbersConnector();
        highlightsConnector = new HighlightsConnector();
    }

    private void setupContactNumbersSection() {
        List<String> objects = Arrays.asList("SUW Zasole", "SUW Zaborze", "Centralna Hydrofornia", "Przepompownia ścieków");
        objectOfContactNumberComboBox.setItems(FXCollections.observableList(objects));

        contactNumbersList = contactNumbersConnector.getContactNumbers();
        contactNumbersListView.getItems().addAll(contactNumbersList);

        contactNumbersListView.setOnMouseClicked(event -> {
            loadContactNumbers(contactNumbersListView.getSelectionModel().getSelectedIndex());
            oldContactNumber = contactNumbersListView.getSelectionModel().getSelectedItem();
        });
//        loading the first record from the contact numbers
        if (!contactNumbersListView.getItems().isEmpty())
            loadContactNumbers(0);
//        assigning old values to a variable storing old values
        oldContactNumber = contactNumberTextField.getText();
    }

    private void setupCostDrivingAccountsSection() {
        for (DrivingCostAccount konto : highlightsConnector.getDrivingCostAccounts())
            drivingCostAccountsListView.getItems().add(konto.getDrivingCostAccountNumber() + " " + konto.getDrivingCostAccountName());
        drivingCostAccountsListView.setOnMouseClicked(event -> loadDrivingCostAccounts(drivingCostAccountsListView.getSelectionModel().getSelectedIndex()));

        if (!drivingCostAccountsListView.getItems().isEmpty())
            loadDrivingCostAccounts(0);
    }

    private void setupVehiclePricingSection() {
        vehiclePricingTableView.setOnMouseClicked(event -> loadVehiclePricing(vehiclePricingTableView.getSelectionModel().getSelectedIndex()));

        vehicleNameColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleName"));
        regularPricingColumn.setCellValueFactory(new PropertyValueFactory<>("regularPricing"));
        overtimePricingColumn.setCellValueFactory(new PropertyValueFactory<>("overtimePricing"));

        vehiclePricingTableView.getItems().addAll(highlightsConnector.getVehicles());

        if (!vehiclePricingTableView.getItems().isEmpty())
            loadVehiclePricing(0);
    }

    private void setupStandingOrdersSection() {
        for (StandingOrder zlecenie : standingOrdersConnector.getStandingOrdersRecords())
            standingOrdersListView.getItems().add(zlecenie.getStandingOrderName());

        standingOrdersListView.setOnMouseClicked(event -> {
            if (!standingOrdersListView.getSelectionModel().getSelectedItem().isEmpty()) {
                oldStandingOrder = new StandingOrder(standingOrdersListView.getSelectionModel().getSelectedItem(), standingOrdersConnector.getStandingOrderRecordID(standingOrdersListView.getSelectionModel().getSelectedItem()));
                standingOrderTextField.setText(standingOrdersListView.getSelectionModel().getSelectedItem());
            }
        });

        if (!standingOrdersListView.getItems().isEmpty()) {
            standingOrderTextField.setText(standingOrdersListView.getItems().get(0));
        }

        if (!standingOrderTextField.getText().isEmpty())
            oldStandingOrder = new StandingOrder(standingOrderTextField.getText(), standingOrdersConnector.getStandingOrderRecordID(standingOrderTextField.getText()));

    }
}
