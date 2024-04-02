package com.example.raport_mistrza_zmianowego.controllers;

import com.example.raport_mistrza_zmianowego.core.connectors.DutyOfficersConnector;
import com.example.raport_mistrza_zmianowego.core.connectors.OvertimesConnector;
import com.example.raport_mistrza_zmianowego.core.connectors.PortersConnector;
import com.example.raport_mistrza_zmianowego.core.connectors.ReportConnector;
import com.example.raport_mistrza_zmianowego.core.controls.MaskField;
import com.example.raport_mistrza_zmianowego.core.model.Overtime;
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
import java.util.ResourceBundle;

public class AddSpecialReportController implements Initializable {

    //    fields from raport layout
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
    public Button createReportButton;
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
    public MaskField porterUntilHourMaskField;
    @FXML
    public MaskField porterFromHourMaskField;
    @FXML
    public Button deleteEmployeeButton;
    private PortersConnector portersConnector;
    private DutyOfficersConnector dutyOfficersConnector;
    private OvertimesConnector overtimesConnector;
    private ReportConnector reportConnector;
    //    list containing prompts for the type of work of overtime employees
    private List<String> entriesList;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        datePicker.setValue(LocalDate.now());
        entriesList = new ArrayList<>();
        shiftMasterTextField.setText(LoginController.userAccountName);

        setupDbConnectors();
        setupOvertimesTable();
        setupComboBoxes();
        setupMaskFields();
    }

    @FXML
    public void insertOvertimeToTable() {
        if (overtimesEmployeeComboBox.getValue().isBlank() || overtimeFromColumn.getText().isBlank() ||
                overtimeUntilColumn.getText().isBlank() || overtimesTypeTextField.getText().isBlank()) {
            showAlert(Alert.AlertType.ERROR, "Błąd formularza!",
                    "Proszę wypełnić dane pracownika");
            return;
        }

        Overtime overtime = new Overtime(overtimesEmployeeComboBox.getValue(), overtimesFromMaskField.getText(),
                overtimesUntilMaskField.getText(), overtimesTypeTextField.getText());

        boolean exist = false;

        if (!overtimesTableView.getItems().isEmpty()) {
            for (Overtime overtimeFromTable : overtimesTableView.getItems())
                if (overtimeFromTable.getEmployee().equals(overtime.getEmployee()) &&
                        overtimeFromTable.getFrom().equals(overtime.getFrom()) &&
                        overtimeFromTable.getUntil().equals(overtime.getUntil()) &&
                        overtimeFromTable.getWorkName().equals(overtime.getWorkName())) {
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
            if (!entriesList.contains(overtimeRodzaj.getWorkName()))
                entriesList.add(overtimeRodzaj.getWorkName());

        TextFields.bindAutoCompletion(overtimesTypeTextField, entriesList);

        overtimesTableView.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            Overtime overtimeFromTable = overtimesTableView.getSelectionModel().getSelectedItem();
            if (!(overtimeFromTable == null)) {
                overtimesEmployeeComboBox.setValue(overtimeFromTable.getEmployee());
                overtimesFromMaskField.setText(overtimeFromTable.getFrom());
                overtimesUntilMaskField.setText(overtimeFromTable.getUntil());
                overtimesTypeTextField.setText(overtimeFromTable.getWorkName());
            }
        });
    }

    @FXML
    public void createSpecialReport() {
//        form validating
        if (datePicker.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Błąd formularza!",
                    "Proszę wypełnić datę wprowadzanego raportu");
            return;
        }
        if (workingHoursComboBox.getValue() == null) {
            showAlert(Alert.AlertType.ERROR, "Błąd formularza!",
                    "Proszę wybrać godziny pracy");
            return;
        }
        if (shiftMasterTextField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, "Błąd formularza!",
                    "Proszę wypełnić pole Mistrz Zmiany");
            return;
        }

        reportConnector.createNewRecord(datePicker.getValue(), workingHoursComboBox.getValue() + "*", shiftMasterTextField.getText(),
                porterUntilHourMaskField.getText(), porterFromHourMaskField.getText(), shiftReportTextArea.getText(),
                standbyZasoleTextField.getText(), standbyZasoleTextFieldSecondShift.getText(),
                standbyZaborzeTextField.getText(), standbyZaborzeTextFieldSecondShift.getText(),
                standbyHydroforniaTextField.getText(), standbyHydroforniaTextFieldSecondShift.getText(),
                standbyPrzepompowniaTextField.getText(), standbyPrzepompowniaTextFieldSecondShift.getText(),
                pw15ZasoleTextField.getText(), c15ZasoleTextField.getText(), pw20ZasoleTextField.getText(),
                c20ZasoleTextField.getText(), przepMinZasoleTextField.getText(), przepMaxZasoleTextField.getText(),
                pw15ZaborzeTextField.getText(), c15ZaborzeTextField.getText(), pw20ZaborzeTextField.getText(),
                c20ZaborzeTextField.getText(), przepMinZaborzeTextField.getText(), przepMaxZaborzeTextField.getText(),
                pw15HydroforniaTextField.getText(), c15HydroforniaTextField.getText(), pw20HydroforniaTextField.getText(),
                c20HydroforniaTextField.getText(), odczytChelmekTextField.getText(), zuzycieChelmekTextField.getText(),
                overtimesTableView.getItems(), porterUntilTextField.getText(), porterFromTextField.getText());

        showAlert(Alert.AlertType.CONFIRMATION, "Sukces!",
                "Raport dodany pomyślnie");

    }

    private static void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(Stage.getWindows().stream().filter(Window::isShowing).findFirst().orElse(null));
        alert.show();
    }

    @FXML
    public void deleteOvertime() {
        overtimesTableView.getItems().remove(overtimesTableView.getSelectionModel().getSelectedIndex());
    }

    private void setupDbConnectors() {
        portersConnector = new PortersConnector();
        dutyOfficersConnector = new DutyOfficersConnector();
        overtimesConnector = new OvertimesConnector();
        reportConnector = new ReportConnector();
    }

    private void setupOvertimesTable() {
        overtimesTableView.setEditable(true);

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
        for (String employee : overtimesConnector.getEmployees())
            overtimesEmployeeComboBox.getItems().add(employee);
    }

    private void setupComboBoxes() {
        workingHoursComboBox.getItems().add("7:00 - 15:00");
        workingHoursComboBox.getItems().add("12:00 - 20:00");
        workingHoursComboBox.getItems().add("15:00 - 23:00");
        workingHoursComboBox.getItems().add("niestandardowe");

        List<String> portersList = portersConnector.getPorters();
        List<String> dutyOfficersList = dutyOfficersConnector.getDutyOfficersPrompts();

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
            else
                standbyZasoleTextField.setText(newValue);
        });

        standbyZasoleComboBoxSecondShift.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!standbyZasoleTextFieldSecondShift.getText().isEmpty())
                standbyZasoleTextFieldSecondShift.setText(standbyZasoleTextFieldSecondShift.getText() + ", " + newValue);
            else
                standbyZasoleTextFieldSecondShift.setText(newValue);
        });

        standbyZaborzeComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!standbyZaborzeTextField.getText().isEmpty())
                standbyZaborzeTextField.setText(standbyZaborzeTextField.getText() + ", " + newValue);
            else
                standbyZaborzeTextField.setText(newValue);
        });

        standbyZaborzeComboBoxSecondShift.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!standbyZaborzeTextFieldSecondShift.getText().isEmpty())
                standbyZaborzeTextFieldSecondShift.setText(standbyZaborzeTextFieldSecondShift.getText() + ", " + newValue);
            else
                standbyZaborzeTextFieldSecondShift.setText(newValue);
        });

        standbyHydroforniaComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!standbyHydroforniaTextField.getText().isEmpty())
                standbyHydroforniaTextField.setText(standbyHydroforniaTextField.getText() + ", " + newValue);
            else
                standbyHydroforniaTextField.setText(newValue);
        });

        standbyHydroforniaComboBoxSecondShift.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!standbyHydroforniaTextFieldSecondShift.getText().isEmpty())
                standbyHydroforniaTextFieldSecondShift.setText(standbyHydroforniaTextFieldSecondShift.getText() + ", " + newValue);
            else
                standbyHydroforniaTextFieldSecondShift.setText(newValue);
        });

        standbyPrzepompowniaComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!standbyPrzepompowniaTextField.getText().isEmpty())
                standbyPrzepompowniaTextField.setText(standbyPrzepompowniaTextField.getText() + ", " + newValue);
            else
                standbyPrzepompowniaTextField.setText(newValue);
        });

        standbyPrzepompowniaComboBoxSecondShift.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!standbyPrzepompowniaTextFieldSecondShift.getText().isEmpty())
                standbyPrzepompowniaTextFieldSecondShift.setText(standbyPrzepompowniaTextFieldSecondShift.getText() + ", " + newValue);
            else
                standbyPrzepompowniaTextFieldSecondShift.setText(newValue);
        });

        porterUntilComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!porterUntilTextField.getText().isEmpty())
                porterUntilTextField.setText(porterUntilTextField.getText() + ", " + newValue);
            else
                porterUntilTextField.setText(newValue);
        });

        porterFromComboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
            if (!porterFromTextField.getText().isEmpty())
                porterFromTextField.setText(porterFromTextField.getText() + ", " + newValue);
            else
                porterFromTextField.setText(newValue);
        });
    }

    private void setupMaskFields() {
        porterUntilHourMaskField.focusedProperty().addListener((obs, oldVal, newVal) -> porterUntilHourMaskField.setMask("DD:DD"));
        porterFromHourMaskField.focusedProperty().addListener((obs, oldVal, newVal) -> porterFromHourMaskField.setMask("DD:DD"));
        overtimesFromMaskField.focusedProperty().addListener((obs, oldVal, newVal) -> overtimesFromMaskField.setMask("DD:DD"));
        overtimesUntilMaskField.focusedProperty().addListener((obs, oldVal, newVal) -> overtimesUntilMaskField.setMask("DD:DD"));
    }
}
