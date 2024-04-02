package com.example.raport_mistrza_zmianowego.controllers;

import com.example.raport_mistrza_zmianowego.core.connectors.DutyOfficersConnector;
import com.example.raport_mistrza_zmianowego.core.connectors.OvertimesConnector;
import com.example.raport_mistrza_zmianowego.core.connectors.PortersConnector;
import com.example.raport_mistrza_zmianowego.core.model.DutyOfficer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class EmployeesController implements Initializable {
    @FXML
    public TextField editEmployeeTextField;
    @FXML
    public TextField addEmployeeTextField;
    @FXML
    public TextField editDutyOfficerTextField;
    @FXML
    public TextField addDutyOfficerTextField;
    @FXML
    public TextField editPorterTextField;
    @FXML
    public TextField addPorterTextField;
    @FXML
    public ListView<String> employeesListView;
    @FXML
    public ListView<String> dutyOfficersListView;
    @FXML
    public ListView<String> portersListView;
    @FXML
    public Button deleteEmployeeButton;
    @FXML
    public Button saveEmployeeButton;
    @FXML
    public Button addEmployeeButton;
    @FXML
    public Button deleteDutyOfficerButton;
    @FXML
    public Button saveDutyOfficerButton;
    @FXML
    public Button addDutyOfficerButton;
    @FXML
    public Button deletePorterButton;
    @FXML
    public Button savePorterButton;
    @FXML
    public Button addPorterButton;
    private List<String> employeesList;
    private List<DutyOfficer> dutyOfficersList;
    private List<String> portersList;
    private DutyOfficersConnector dutyOfficersConnector;
    private PortersConnector portersConnector;
    private OvertimesConnector overtimesConnector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupDbConnectors();
        setupEmployeesSection();
        setupPortersSection();
        setupDutyOfficersSection();
    }

    private void loadEmployee(int index) {
        employeesList = overtimesConnector.getEmployees();

        if (index < employeesList.size())
            editEmployeeTextField.setText(employeesList.get(index));
    }

    private void loadDutyOfficer(int index) {
        dutyOfficersList = dutyOfficersConnector.getDutyOfficers();

        if (index < dutyOfficersList.size()) {
            editDutyOfficerTextField.setText(dutyOfficersList.get(index).getDutyOfficerName());
        }
    }

    private void loadPorter(int index) {
        portersList = portersConnector.getPorters();

        if (index < portersList.size())
            editPorterTextField.setText(portersList.get(index));
    }

    @FXML
    public void addEmployee() {
        overtimesConnector.addEmployeePromptRecord(addEmployeeTextField.getText());

        addEmployeeTextField.clear();

        employeesList = overtimesConnector.getEmployees();
        employeesListView.setItems(FXCollections.observableList(employeesList));
        employeesListView.getSelectionModel().select(0);
        loadEmployee(0);
    }

    @FXML
    public void addDutyOfficer() {
        dutyOfficersConnector.addDutyOfficerRecord(addDutyOfficerTextField.getText());

        addDutyOfficerTextField.clear();

        dutyOfficersList = dutyOfficersConnector.getDutyOfficers();
        List<String> dyzurniToListView = new ArrayList<>();
        for (DutyOfficer dutyOfficer : dutyOfficersList) dyzurniToListView.add(dutyOfficer.getDutyOfficerName());
        dutyOfficersListView.setItems(FXCollections.observableList(dyzurniToListView));
        dutyOfficersListView.getSelectionModel().select(0);
        loadDutyOfficer(0);
    }

    @FXML
    public void addPorter() {
        portersConnector.addPorterRecord(addPorterTextField.getText());

        addPorterTextField.clear();

        portersList = portersConnector.getPorters();
        portersListView.setItems(FXCollections.observableList(portersList));
        portersListView.getSelectionModel().select(0);
        loadPorter(0);
    }

    @FXML
    public void saveEmployee() {
        overtimesConnector.updateEmployee(editEmployeeTextField.getText(), employeesList.get(employeesListView.getSelectionModel().getSelectedIndex()));

        employeesList = overtimesConnector.getEmployees();
        employeesListView.setItems(FXCollections.observableList(employeesList));
        employeesListView.getSelectionModel().select(0);
        loadEmployee(0);
    }

    @FXML
    public void saveDutyOfficer() {
        dutyOfficersConnector.updateDutyOfficer(editDutyOfficerTextField.getText(), dutyOfficersList.get(dutyOfficersListView.getSelectionModel().getSelectedIndex()));
        dutyOfficersList = dutyOfficersConnector.getDutyOfficers();
        List<String> dutyOfficersToListView = new ArrayList<>();
        for (DutyOfficer dutyOfficer : dutyOfficersList) dutyOfficersToListView.add(dutyOfficer.getDutyOfficerName());
        dutyOfficersListView.setItems(FXCollections.observableList(dutyOfficersToListView));
        dutyOfficersListView.getSelectionModel().select(0);
        loadDutyOfficer(0);
    }

    @FXML
    public void savePorter() {
        portersConnector.updatePorter(editPorterTextField.getText(), portersList.get(portersListView.getSelectionModel().getSelectedIndex()));
        portersList = portersConnector.getPorters();
        portersListView.setItems(FXCollections.observableList(portersList));
        portersListView.getSelectionModel().select(0);
        loadPorter(portersListView.getSelectionModel().getSelectedIndex());
    }

    @FXML
    public void deleteEmployee() {
        overtimesConnector.deleteEmployee(editEmployeeTextField.getText());

        List<String> employeesList = new ArrayList<>(overtimesConnector.getEmployees());
        employeesListView.setItems(FXCollections.observableList(employeesList));
        employeesListView.getSelectionModel().select(0);
        loadEmployee(0);
    }

    @FXML
    public void deleteDutyOfficer() {
        dutyOfficersConnector.deleteDutyOfficer(dutyOfficersList.get(dutyOfficersListView.getSelectionModel().getSelectedIndex()));
        dutyOfficersList = dutyOfficersConnector.getDutyOfficers();
        List<String> dutyOfficersToListView = new ArrayList<>();
        for (DutyOfficer dutyOfficer : dutyOfficersList) dutyOfficersToListView.add(dutyOfficer.getDutyOfficerName());
        dutyOfficersListView.setItems(FXCollections.observableList(dutyOfficersToListView));
        dutyOfficersListView.getSelectionModel().select(0);
        loadDutyOfficer(0);
    }

    @FXML
    public void deletePorter() {
        portersConnector.deletePorter(editPorterTextField.getText());

        portersList = portersConnector.getPorters();
        portersListView.setItems(FXCollections.observableList(portersList));
        portersListView.getSelectionModel().select(0);
        loadPorter(0);
    }

    private void setupDbConnectors() {
        dutyOfficersConnector = new DutyOfficersConnector();
        portersConnector = new PortersConnector();
        overtimesConnector = new OvertimesConnector();
    }

    private void setupEmployeesSection() {
        employeesList = overtimesConnector.getEmployees();
        employeesListView.setItems(FXCollections.observableList(employeesList));
        employeesListView.setOnMouseClicked(event -> loadEmployee(employeesListView.getSelectionModel().getSelectedIndex()));

        loadEmployee(0);
        employeesListView.getSelectionModel().select(0);
    }

    private void setupPortersSection() {
        portersList = portersConnector.getPorters();
        portersListView.setItems(FXCollections.observableList(portersList));
        portersListView.setOnMouseClicked(event -> loadPorter(portersListView.getSelectionModel().getSelectedIndex()));

        loadPorter(0);
        portersListView.getSelectionModel().select(0);
    }

    private void setupDutyOfficersSection() {
        dutyOfficersList = dutyOfficersConnector.getDutyOfficers();
        List<String> dyzurniToListView = new ArrayList<>();
        for (DutyOfficer dutyOfficer : dutyOfficersList) dyzurniToListView.add(dutyOfficer.getDutyOfficerName());
        dutyOfficersListView.setItems(FXCollections.observableList(dyzurniToListView));
        dutyOfficersListView.setOnMouseClicked(event -> loadDutyOfficer(dutyOfficersListView.getSelectionModel().getSelectedIndex()));

        loadDutyOfficer(0);
        dutyOfficersListView.getSelectionModel().select(0);
    }
}

