package com.example.raport_mistrza_zmianowego.controllers;

import com.example.raport_mistrza_zmianowego.core.connectors.*;
import com.example.raport_mistrza_zmianowego.core.model.DrivingCostAccount;
import com.example.raport_mistrza_zmianowego.core.model.StandingOrder;
import com.example.raport_mistrza_zmianowego.core.model.Vehicle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class HighlightsController implements Initializable {

    @FXML
    public ListView<String> drivingCostAccountsListView;
    @FXML
    public ListView<String> contactNumbersListView;
    @FXML
    public TableView<Vehicle> vehiclesPricingTableView;
    @FXML
    public TableColumn<Vehicle, String> vehicleNameColumn;
    @FXML
    public TableColumn<Vehicle, String> regularPricingColumn;
    @FXML
    public TableColumn<Vehicle, String> overtimePricingColumn;
    @FXML
    public TreeView<String> standingOrdersTreeView;
    @FXML
    public Text yearOfStandingOrderText;
    @FXML
    public Text pricingChangeDataText;
    private StandingOrdersConnector standingOrdersConnector;
    private ContactNumbersConnector contactNumbersConnector;
    private HighlightsConnector highlightsConnector;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        standingOrdersConnector = new StandingOrdersConnector();
        contactNumbersConnector = new ContactNumbersConnector();
        highlightsConnector = new HighlightsConnector();

        setupDrivingCostAccountsSection();
        setupStandingOrdersSection();
        setupVehiclesPricingSection();

        Preferences preferences = Preferences.userNodeForPackage(HighlightsController.class);
        preferences.put("password", DatabaseConnector.DATABASE_PASSWORD);
    }

    private static class TreeCellTextField extends TreeCell<String> {

        private TextField textField;

        private final ContextMenu addFaculty = new ContextMenu();

        private StandingOrdersConnector standingOrdersConnector;

        public TreeCellTextField() {
            MenuItem addMenuItem = new MenuItem("Dodaj nowy adres");
            addFaculty.getItems().add(addMenuItem);
            addMenuItem.setOnAction((ActionEvent t) -> {
                TreeItem<String> newFaculty = new TreeItem<>("Nowy adres");
                standingOrdersConnector = new StandingOrdersConnector();
                getTreeItem().getChildren().add(newFaculty);
                new StandingOrdersAddressesConnector().addStandingOrderAddressRecord("Nowy adres", standingOrdersConnector.getStandingOrderRecordID(getTreeItem().getValue()));
            });
        }

        /**
         * On editing, create new text field and set it using setGraphic method.
         */


        @Override
        public void startEdit() {
            super.startEdit();

            if (textField == null) {
                createTextField();
            }
            setText(null);
            setGraphic(textField);
            textField.selectAll();
        }

        /**
         * On cancel edit, set the original content back
         */
        @Override
        public void cancelEdit() {
            super.cancelEdit();
            setText(getItem());
            setGraphic(getTreeItem().getGraphic());
        }

        @Override
        public void updateItem(String item, boolean empty) {
            super.updateItem(item, empty);

            if (empty) {
                setText(null);
                setGraphic(null);
                return;
            }

            if (!isEditing()) {
                setText(getString());
                setGraphic(getTreeItem().getGraphic());

                if (getTreeItem().getParent() != null) {
                    setContextMenu(addFaculty);
                }

                return;
            }

            if (textField != null) {
                textField.setText(getString());
            }
            setText(null);
            setGraphic(textField);

        }

        private void createTextField() {
            textField = new TextField(getString());
            String oldText = textField.getText();
            textField.setOnKeyReleased((KeyEvent t) -> {
                if (t.getCode() == KeyCode.ENTER) {
                    commitEdit(textField.getText());
                    standingOrdersConnector.updateStandingOrderRecords(textField.getText(), oldText);
                    new StandingOrdersAddressesConnector().updateStandingOrderAddressesRecords(textField.getText(), oldText);
                } else if (t.getCode() == KeyCode.ESCAPE) {
                    cancelEdit();
                }
            });
        }

        private String getString() {
            return getItem() == null ? "" : getItem();
        }


    }

    private void setupDrivingCostAccountsSection() {
        for (DrivingCostAccount drivingCostAccount : highlightsConnector.getDrivingCostAccounts())
            drivingCostAccountsListView.getItems().add(drivingCostAccount.getDrivingCostAccountNumber() + " " + drivingCostAccount.getDrivingCostAccountName());
    }

    private void setupStandingOrdersSection() {
        TreeItem<String> standingOrdersTreeItem = new TreeItem<>("Zlecenia");
        List<StandingOrder> standingOrderList = standingOrdersConnector.getStandingOrdersRecords();

        StandingOrdersAddressesConnector standingOrdersAddressesConnector = new StandingOrdersAddressesConnector();
        for (StandingOrder standingOrder : standingOrderList) {
            TreeItem<String> zlecenieTreeItem = new TreeItem<>(standingOrder.getStandingOrderName());
            List<String> addresses = standingOrdersAddressesConnector.getAddressesRecordsForStandingOrder(standingOrder.getStandingOrderID());
            for (String address : addresses) zlecenieTreeItem.getChildren().add(new TreeItem<>(address));
            standingOrdersTreeItem.getChildren().add(zlecenieTreeItem);
        }

        standingOrdersTreeView.setRoot(standingOrdersTreeItem);
        if (LoginController.isAdmin) {
            standingOrdersTreeView.setEditable(true);
            standingOrdersTreeView.setCellFactory((TreeView<String> p) -> new TreeCellTextField());
        }
    }

    private void setupVehiclesPricingSection() {
        contactNumbersListView.getItems().addAll(contactNumbersConnector.getContactNumbers());

        vehicleNameColumn.setCellValueFactory(new PropertyValueFactory<>("vehicleName"));
        regularPricingColumn.setCellValueFactory(new PropertyValueFactory<>("regularPricing"));
        overtimePricingColumn.setCellValueFactory(new PropertyValueFactory<>("overtimePricing"));

        List<Vehicle> vehicles = highlightsConnector.getVehicles();
        vehiclesPricingTableView.getItems().addAll(vehicles);
    }
}

