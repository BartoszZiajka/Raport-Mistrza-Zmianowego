package com.example.raport_mistrza_zmianowego.controllers;

import com.example.raport_mistrza_zmianowego.core.connectors.DutyOfficersConnector;
import com.example.raport_mistrza_zmianowego.core.connectors.OvertimesConnector;
import com.example.raport_mistrza_zmianowego.core.connectors.PortersConnector;
import com.example.raport_mistrza_zmianowego.core.connectors.ReportConnector;
import com.example.raport_mistrza_zmianowego.core.controls.MaskField;
import com.example.raport_mistrza_zmianowego.core.model.Overtime;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.controlsfx.control.textfield.TextFields;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class EditReportController implements Initializable {
    @FXML
    public DatePicker datePicker;
    @FXML
    public TextField shiftMasterTextField;
    @FXML
    public ComboBox<String> porterUntilComboBox;
    @FXML
    public TextField porterUntilTextField;
    @FXML
    public ComboBox<String> porterFromComboBox;
    @FXML
    public TextField porterFromTextField;
    @FXML
    public ComboBox<String> standbyZasoleComboBox;
    @FXML
    public TextField standbyZasoleTextField;
    @FXML
    public ComboBox<String> standbyZasoleComboBoxSecondShift;
    @FXML
    public TextField standbyZasoleTextFieldSecondShift;
    @FXML
    public ComboBox<String> standbyZaborzeComboBox;
    @FXML
    public TextField standbyZaborzeTextField;
    @FXML
    public ComboBox<String> standbyZaborzeComboBoxSecondShift;
    @FXML
    public TextField standbyZaborzeTextFieldSecondShift;
    @FXML
    public ComboBox<String> standbyHydroforniaComboBox;
    @FXML
    public TextField standbyHydroforniaTextField;
    @FXML
    public ComboBox<String> standbyHydroforniaComboBoxSecondShift;
    @FXML
    public TextField standbyHydroforniaTextFieldSecondShift;
    @FXML
    public ComboBox<String> standbyPrzepompowniaComboBox;
    @FXML
    public TextField standbyPrzepompowniaTextField;
    @FXML
    public ComboBox<String> standbyPrzepompowniaComboBoxSecondShift;
    @FXML
    public TextField standbyPrzepompowniaTextFieldSecondShift;
    @FXML
    public TextArea shiftReportTextArea;
    @FXML
    public MaskField overtimesFromMaskField;
    @FXML
    public MaskField overtimesUntilMaskField;
    @FXML
    public TextField overtimesTypeTextField;
    @FXML
    public TextField pw15ZasoleTextField;
    @FXML
    public TextField c15ZasoleTextField;
    @FXML
    public TextField pw20ZasoleTextField;
    @FXML
    public TextField c20ZasoleTextField;
    @FXML
    public TextField przepMinZasoleTextField;
    @FXML
    public TextField przepMaxZasoleTextField;
    @FXML
    public TextField pw15ZaborzeTextField;
    @FXML
    public TextField c15ZaborzeTextField;
    @FXML
    public TextField pw20ZaborzeTextField;
    @FXML
    public TextField c20ZaborzeTextField;
    @FXML
    public TextField przepMinZaborzeTextField;
    @FXML
    public TextField przepMaxZaborzeTextField;
    @FXML
    public TextField pw15HydroforniaTextField;
    @FXML
    public TextField c15HydroforniaTextField;
    @FXML
    public TextField pw20HydroforniaTextField;
    @FXML
    public TextField c20HydroforniaTextField;
    @FXML
    public TextField odczytChelmekTextField;
    @FXML
    public TextField zuzycieChelmekTextField;
    @FXML
    public Button insertEmployeeButton;
    @FXML
    public Button deleteEmployeeButton;
    public Button saveChangesButton;
    @FXML
    public TableView<Overtime> overtimesTableView;
    @FXML
    public TableColumn<Overtime, String> nameAndSurnameColumn;
    @FXML
    public TableColumn<Overtime, String> overtimeFromColumn;
    @FXML
    public TableColumn<Overtime, String> overtimeUntilColumn;
    @FXML
    public TableColumn<Overtime, String> typeOfWorkColumn;
    @FXML
    public ComboBox<String> overtimesEmployeeComboBox;
    @FXML
    public ComboBox<String> workingHoursComboBox;
    @FXML
    public ListView<String> reportsDatesListView;
    @FXML
    public MaskField porterFromHourMaskField;
    @FXML
    public MaskField porterUntilHourMaskField;
    private OvertimesConnector overtimesConnector;
    private PortersConnector portersConnector;
    private DutyOfficersConnector dutyOfficersConnector;
    private ReportConnector reportConnector;
    private Map<String, String> loadedReport;
    private List<String> entriesList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupDbConnectors();
        setupReportsAbleToEdit();
        setupOvertimesTable();
        setupComboBoxes();
        setupMaskFields();
    }

    private void loadReport(String raportDate) {
        loadedReport = reportConnector.getReportFrom(raportDate.substring(0, 10), raportDate.substring(11));
        if (!loadedReport.isEmpty()) {
            datePicker.setValue(LocalDate.parse(loadedReport.get("data_raportu")));
            datePicker.getEditor().setText(loadedReport.get("data_raportu"));
            workingHoursComboBox.setValue(loadedReport.get("godziny_pracy"));
            shiftMasterTextField.setText(loadedReport.get("mistrz_zmiany"));
            porterUntilHourMaskField.setText(loadedReport.get("do_godz"));
            porterUntilTextField.setText(loadedReport.get("portier_do"));
            porterFromHourMaskField.setText(loadedReport.get("od_godz"));
            porterFromTextField.setText(loadedReport.get("portier_od"));
            standbyZasoleTextField.setText(loadedReport.get("dyzur_zasole"));
            standbyZasoleTextFieldSecondShift.setText(loadedReport.get("dyzur_zasole_2_zmiana"));
            standbyZaborzeTextField.setText(loadedReport.get("dyzur_zaborze"));
            standbyZaborzeTextFieldSecondShift.setText(loadedReport.get("dyzur_zaborze_2_zmiana"));
            standbyHydroforniaTextField.setText(loadedReport.get("dyzur_hydrofornia"));
            standbyHydroforniaTextFieldSecondShift.setText(loadedReport.get("dyzur_hydrofornia_2_zmiana"));
            standbyPrzepompowniaTextField.setText(loadedReport.get("dyzur_przepompownia"));
            standbyPrzepompowniaTextFieldSecondShift.setText(loadedReport.get("dyzur_przepompownia_2_zmiana"));
            shiftReportTextArea.setText(loadedReport.get("raport_zmiany"));
            pw15ZasoleTextField.setText(loadedReport.get("pw15_zasole"));
            c15ZasoleTextField.setText(loadedReport.get("c15_zasole"));
            pw20ZasoleTextField.setText(loadedReport.get("pw20_zasole"));
            c20ZasoleTextField.setText(loadedReport.get("c20_zasole"));
            przepMinZasoleTextField.setText(loadedReport.get("przeplyw_min_zasole"));
            przepMaxZasoleTextField.setText(loadedReport.get("przeplyw_max_zasole"));
            pw15ZaborzeTextField.setText(loadedReport.get("pw15_zaborze"));
            c15ZaborzeTextField.setText(loadedReport.get("c15_zaborze"));
            pw20ZaborzeTextField.setText(loadedReport.get("pw20_zaborze"));
            c20ZaborzeTextField.setText(loadedReport.get("c20_zaborze"));
            przepMinZaborzeTextField.setText(loadedReport.get("przeplyw_min_zaborze"));
            przepMaxZaborzeTextField.setText(loadedReport.get("przeplyw_max_zaborze"));
            pw15HydroforniaTextField.setText(loadedReport.get("pw15_hydrofornia"));
            c15HydroforniaTextField.setText(loadedReport.get("c15_hydrofornia"));
            pw20HydroforniaTextField.setText(loadedReport.get("pw20_hydrofornia"));
            c20HydroforniaTextField.setText(loadedReport.get("c20_hydrofornia"));
            odczytChelmekTextField.setText(loadedReport.get("odczyt_chelmek"));
            zuzycieChelmekTextField.setText(loadedReport.get("zuzycie_chelmek"));
//        clear table from existing records
            overtimesTableView.getItems().clear();
//        load new records to table
            List<Overtime> nadgodzinyFromDB = overtimesConnector.getOvertimesFrom(reportConnector.getReportID(raportDate.substring(0, 10), raportDate.substring(11)));
            for (Overtime overtime : nadgodzinyFromDB) overtimesTableView.getItems().add(overtime);
//        set entries for rodzaj pracy TextField
            entriesList = new ArrayList<>();
            for (Overtime overtime : nadgodzinyFromDB)
                if (!entriesList.contains(overtime.getWorkName())) entriesList.add(overtime.getWorkName());
            TextFields.bindAutoCompletion(overtimesTypeTextField, entriesList);
        }
    }

    @FXML
    public void insertOvertimeToTable() {
        if (overtimesEmployeeComboBox.getValue().isBlank() || overtimeFromColumn.getText().isBlank() || overtimeUntilColumn.getText().isBlank() || overtimesTypeTextField.getText().isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Błąd formularza!", "Proszę wypełnić dane pracownika");
            return;
        }

        Overtime overtime = new Overtime(overtimesEmployeeComboBox.getValue(), overtimesFromMaskField.getText(), overtimesUntilMaskField.getText(), overtimesTypeTextField.getText());
        boolean exist = false;
        if (!overtimesTableView.getItems().isEmpty()) {
            for (Overtime overtimeFromTable : overtimesTableView.getItems())
                if (overtimeFromTable.getEmployee().equals(overtime.getEmployee()) && overtimeFromTable.getFrom().equals(overtime.getFrom()) && overtimeFromTable.getUntil().equals(overtime.getUntil()) && overtimeFromTable.getWorkName().equals(overtime.getWorkName())) {
                    exist = true;
                    break;
                }
        }
        if (!exist) overtimesTableView.getItems().add(overtime);
        else return;

        List<String> pracownicy = overtimesConnector.getEmployees();
        if (!pracownicy.contains(overtimesEmployeeComboBox.getValue()))
            overtimesConnector.addEmployeePromptRecord(overtimesEmployeeComboBox.getValue());

        List<Overtime> nadgodziny = overtimesTableView.getItems();
        for (Overtime overtimeRodzaj : nadgodziny)
            if (!entriesList.contains(overtimeRodzaj.getWorkName())) entriesList.add(overtimeRodzaj.getWorkName());
        TextFields.bindAutoCompletion(overtimesTypeTextField, entriesList);
    }

    @FXML
    public void deleteOvertimeFromTable() {
        overtimesConnector.deleteOvertime(new Overtime(overtimesEmployeeComboBox.getValue(), overtimesFromMaskField.getText(), overtimesUntilMaskField.getText(), overtimesTypeTextField.getText()));
        overtimesTableView.getItems().clear();

        List<Overtime> nadgodzinyFromDB = overtimesConnector.getOvertimesFrom(reportConnector.getReportID(datePicker.getValue().toString(), workingHoursComboBox.getValue()));
        for (Overtime overtime : nadgodzinyFromDB) overtimesTableView.getItems().add(overtime);

        List<String> entries = new ArrayList<>();
        for (Overtime overtime : nadgodzinyFromDB)
            if (!entries.contains(overtime.getWorkName())) entries.add(overtime.getWorkName());
        TextFields.bindAutoCompletion(overtimesTypeTextField, entries);
        overtimesFromMaskField.clear();
        overtimesUntilMaskField.clear();
        overtimesTypeTextField.clear();
    }

    @FXML
    public void updateReport() {
//        form validating
        if (datePicker.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Błąd formularza!", "Proszę wypełnić datę wprowadzanego raportu");
            return;
        }
        if (workingHoursComboBox.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Błąd formularza!", "Proszę wybrać godziny pracy");
            return;
        }
        if (shiftMasterTextField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Błąd formularza!", "Proszę wypełnić pole Mistrz Zmiany");
            return;
        }
        if (porterUntilTextField.getText().isEmpty() || porterFromTextField.getText().isEmpty() || porterUntilHourMaskField.getText().isEmpty() || porterFromHourMaskField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Błąd formularza!", "Proszę wypełnić pola Portierów");
            return;
        }
        if (standbyZasoleTextField.getText().isEmpty() || standbyZasoleTextFieldSecondShift.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Błąd formularza!", "Proszę wypełnić pole dyżurnego SUW Zasole");
            return;
        }
        if (standbyZaborzeTextField.getText().isEmpty() || standbyZaborzeTextFieldSecondShift.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Błąd formularza!", "Proszę wypełnić pole dyżurnego SUW Zaborze");
            return;
        }
        if (standbyHydroforniaTextField.getText().isEmpty() || standbyHydroforniaTextFieldSecondShift.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Błąd formularza!", "Proszę wypełnić pole dyżurnego Centralnej Hydrofornii");
            return;
        }
        if (standbyPrzepompowniaTextField.getText().isEmpty() || standbyPrzepompowniaTextFieldSecondShift.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Błąd formularza!", "Proszę wypełnić pole dyżurnego Przepompowni Ścieków");
            return;
        }
        if (shiftReportTextArea.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Błąd formularza!", "Proszę wypełnić pole Raport Zmiany");
            return;
        }
        if (pw15ZasoleTextField.getText().isEmpty() || c15ZasoleTextField.getText().isEmpty() || pw20ZasoleTextField.getText().isEmpty() || c20ZasoleTextField.getText().isEmpty() || przepMinZasoleTextField.getText().isEmpty() || przepMaxZasoleTextField.getText().isEmpty() || pw15ZaborzeTextField.getText().isEmpty() || c15ZaborzeTextField.getText().isEmpty() || pw20ZaborzeTextField.getText().isEmpty() || c20ZaborzeTextField.getText().isEmpty() || przepMinZaborzeTextField.getText().isEmpty() || przepMaxZaborzeTextField.getText().isEmpty() || pw15HydroforniaTextField.getText().isEmpty() || c15HydroforniaTextField.getText().isEmpty() || pw20HydroforniaTextField.getText().isEmpty() || c20HydroforniaTextField.getText().isEmpty() || odczytChelmekTextField.getText().isEmpty() || zuzycieChelmekTextField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Błąd formularza!", "Proszę wypełnić pola Danych z obiektów");
            return;
        }
//        get values from raport and update in database
        reportConnector.updateReport(String.valueOf(datePicker.getValue()), workingHoursComboBox.getValue(), shiftMasterTextField.getText(), porterUntilHourMaskField.getText(), porterUntilTextField.getText(), porterFromHourMaskField.getText(), porterFromTextField.getText(), standbyZasoleTextField.getText(), standbyZasoleTextFieldSecondShift.getText(), standbyZaborzeTextField.getText(), standbyZaborzeTextFieldSecondShift.getText(), standbyHydroforniaTextField.getText(), standbyHydroforniaTextFieldSecondShift.getText(), standbyPrzepompowniaTextField.getText(), standbyPrzepompowniaTextFieldSecondShift.getText(), shiftReportTextArea.getText(), pw15ZasoleTextField.getText(), c15ZasoleTextField.getText(), pw20ZasoleTextField.getText(), c20ZasoleTextField.getText(), przepMinZasoleTextField.getText(), przepMaxZasoleTextField.getText(), pw15ZaborzeTextField.getText(), c15ZaborzeTextField.getText(), pw20ZaborzeTextField.getText(), c20ZaborzeTextField.getText(), przepMinZaborzeTextField.getText(), przepMaxZaborzeTextField.getText(), pw15HydroforniaTextField.getText(), c15HydroforniaTextField.getText(), pw20HydroforniaTextField.getText(), c20HydroforniaTextField.getText(), odczytChelmekTextField.getText(), zuzycieChelmekTextField.getText());
        overtimesConnector.updateOvertimesRecords(overtimesTableView.getItems(), Integer.parseInt(loadedReport.get("id_raportu")));
//        show confrimation message
        showAlert(Alert.AlertType.CONFIRMATION, "Sukces!", "Raport zmieniony pomyślnie");
        loadReport(datePicker.getValue() + " " + workingHoursComboBox.getValue());
    }

    private static void showAlert(Alert.AlertType alertType, String title, String message) {
//        create alert
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null));
        alert.show();
    }

    private void setupDbConnectors() {
        reportConnector = new ReportConnector();
        overtimesConnector = new OvertimesConnector();
        portersConnector = new PortersConnector();
        dutyOfficersConnector = new DutyOfficersConnector();
    }

    private void setupReportsAbleToEdit() {
        ObservableList<String> reportsDatesFromDatabase;
        if (LoginController.isAdmin) reportsDatesFromDatabase = reportConnector.getEditReportDates();
        else reportsDatesFromDatabase = reportConnector.getReportDates(LoginController.userAccountId);
        reportsDatesListView.setItems(reportsDatesFromDatabase);
//        load newest raport
        if (!reportsDatesFromDatabase.isEmpty()) loadReport(reportsDatesFromDatabase.get(0));
//        set listener to ListView to change displaying raports
        reportsDatesListView.setOnMouseClicked(event -> loadReport(reportsDatesListView.getSelectionModel().getSelectedItem()));
    }

    private void setupOvertimesTable() {
        overtimesTableView.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            Overtime overtime = overtimesTableView.getSelectionModel().getSelectedItem();
            if (!(overtime == null)) {
                overtimesEmployeeComboBox.setValue(overtime.getEmployee());
                overtimesFromMaskField.setText(overtime.getFrom());
                overtimesUntilMaskField.setText(overtime.getUntil());
                overtimesTypeTextField.setText(overtime.getWorkName());
            }

        });

//        set table editable
        overtimesTableView.setEditable(true);
//        set cells values names and columns values updating after editing
        nameAndSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
        overtimeFromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        overtimeFromColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        overtimeFromColumn.setOnEditCommit(event -> {
            Overtime overtime = event.getRowValue();
            overtime.setFrom(event.getNewValue());
        });
        overtimeUntilColumn.setCellValueFactory(new PropertyValueFactory<>("until"));
        overtimeUntilColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        overtimeUntilColumn.setOnEditCommit(event -> {
            Overtime overtime = event.getRowValue();
            overtime.setUntil(event.getNewValue());
        });
        typeOfWorkColumn.setCellValueFactory(new PropertyValueFactory<>("workName"));
        typeOfWorkColumn.setCellFactory(TextFieldTableCell.forTableColumn());
        typeOfWorkColumn.setOnEditCommit(event -> {
            Overtime overtime = event.getRowValue();
            overtime.setWorkName(event.getNewValue());
        });
//        fill choice box with workers
        List<String> pracownicy = overtimesConnector.getEmployees();
        for (String pracownik : pracownicy)
            overtimesEmployeeComboBox.getItems().add(pracownik);
    }

    private void setupComboBoxes() {
        //        initialize portierzy list
        List<String> portersList = portersConnector.getPorters();
//        initialize dyzurni lists
        List<String> dutyOfficersList = dutyOfficersConnector.getDutyOfficersPrompts();
//        fill combo boxes
        workingHoursComboBox.getItems().add("7:00 - 15:00");
        workingHoursComboBox.getItems().add("12:00 - 20:00");
        workingHoursComboBox.getItems().add("15:00 - 23:00");
        workingHoursComboBox.setEditable(true);
        porterUntilComboBox.getItems().addAll(portersList);
        porterFromComboBox.getItems().addAll(portersList);
        standbyZasoleComboBox.getItems().addAll(dutyOfficersList);
        standbyZasoleComboBoxSecondShift.getItems().addAll(dutyOfficersList);
        standbyZaborzeComboBox.getItems().addAll(dutyOfficersList);
        standbyZaborzeComboBoxSecondShift.getItems().addAll(dutyOfficersList);
        standbyHydroforniaComboBox.getItems().addAll(dutyOfficersList);
        standbyHydroforniaComboBoxSecondShift.getItems().addAll(dutyOfficersList);
        standbyPrzepompowniaComboBox.getItems().addAll(dutyOfficersList);
        standbyPrzepompowniaComboBoxSecondShift.getItems().addAll(dutyOfficersList);


        standbyZasoleComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!standbyZasoleTextField.getText().isEmpty())
                standbyZasoleTextField.setText(standbyZasoleTextField.getText() + ", " + newValue);
            else standbyZasoleTextField.setText(newValue);
        });

        standbyZasoleComboBoxSecondShift.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!standbyZasoleTextFieldSecondShift.getText().isEmpty())
                standbyZasoleTextFieldSecondShift.setText(standbyZasoleTextFieldSecondShift.getText() + ", " + newValue);
            else standbyZasoleTextFieldSecondShift.setText(newValue);
        });

        standbyZaborzeComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!standbyZaborzeTextField.getText().isEmpty())
                standbyZaborzeTextField.setText(standbyZaborzeTextField.getText() + ", " + newValue);
            else standbyZaborzeTextField.setText(newValue);
        });

        standbyZaborzeComboBoxSecondShift.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!standbyZaborzeTextFieldSecondShift.getText().isEmpty())
                standbyZaborzeTextFieldSecondShift.setText(standbyZaborzeTextFieldSecondShift.getText() + ", " + newValue);
            else standbyZaborzeTextFieldSecondShift.setText(newValue);
        });

        standbyHydroforniaComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!standbyHydroforniaTextField.getText().isEmpty())
                standbyHydroforniaTextField.setText(standbyHydroforniaTextField.getText() + ", " + newValue);
            else standbyHydroforniaTextField.setText(newValue);
        });

        standbyHydroforniaComboBoxSecondShift.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!standbyHydroforniaTextFieldSecondShift.getText().isEmpty())
                standbyHydroforniaTextFieldSecondShift.setText(standbyHydroforniaTextFieldSecondShift.getText() + ", " + newValue);
            else standbyHydroforniaTextFieldSecondShift.setText(newValue);
        });

        standbyPrzepompowniaComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!standbyPrzepompowniaTextField.getText().isEmpty())
                standbyPrzepompowniaTextField.setText(standbyPrzepompowniaTextField.getText() + ", " + newValue);
            else standbyPrzepompowniaTextField.setText(newValue);
        });

        standbyPrzepompowniaComboBoxSecondShift.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!standbyPrzepompowniaTextFieldSecondShift.getText().isEmpty())
                standbyPrzepompowniaTextFieldSecondShift.setText(standbyPrzepompowniaTextFieldSecondShift.getText() + ", " + newValue);
            else standbyPrzepompowniaTextFieldSecondShift.setText(newValue);
        });

        porterUntilComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!porterUntilTextField.getText().isEmpty())
                porterUntilTextField.setText(porterUntilTextField.getText() + ", " + newValue);
            else porterUntilTextField.setText(newValue);
        });

        porterFromComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!porterFromTextField.getText().isEmpty())
                porterFromTextField.setText(porterFromTextField.getText() + ", " + newValue);
            else porterFromTextField.setText(newValue);
        });
    }

    private void setupMaskFields() {
        porterUntilHourMaskField.focusedProperty().addListener((obs, oldVal, newVal) -> porterUntilHourMaskField.setMask("DD:DD"));
        porterFromHourMaskField.focusedProperty().addListener((obs, oldVal, newVal) -> porterFromHourMaskField.setMask("DD:DD"));
        overtimesFromMaskField.focusedProperty().addListener((obs, oldVal, newVal) -> overtimesFromMaskField.setMask("DD:DD"));
        overtimesUntilMaskField.focusedProperty().addListener((obs, oldVal, newVal) -> overtimesUntilMaskField.setMask("DD:DD"));
    }
}
