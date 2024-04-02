package com.example.raport_mistrza_zmianowego.controllers;

import com.example.raport_mistrza_zmianowego.core.connectors.ContactNumbersConnector;
import com.example.raport_mistrza_zmianowego.core.model.TauronContactNumber;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;

public class TauronContactNumbersController implements Initializable {
    @FXML
    public TableView<TauronContactNumber> tauronContactNumbersTableView;
    @FXML
    public TableColumn<TauronContactNumber, String> authorisedPersonsColumn;
    @FXML
    public TableColumn<TauronContactNumber, String> workplaceColumn;
    @FXML
    public TableColumn<TauronContactNumber, String> emailColumn;
    @FXML
    public TableColumn<TauronContactNumber, String> contactNumberColumn;
    @FXML
    public TextField faxTextField;
    @FXML
    public TextField areaWadowiceTextField;
    @FXML
    public TextField areaKetyTextField;
    @FXML
    public TextField areaZywiecTextField;
    @FXML
    public TextField trafficDepartmentTextField;
    @FXML
    public Button addNumberButton;
    @FXML
    public Button deleteNumberButton;
    @FXML
    public TextField authorisedPersonsTextField;
    @FXML
    public TextField workplaceTextField;
    @FXML
    public TextField emailTextField;
    @FXML
    public TextField contactNumberTextField;
    private ContactNumbersConnector contactNumbersConnector;
    private List<TauronContactNumber> tauronContactNumberList;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        contactNumbersConnector = new ContactNumbersConnector();

        authorisedPersonsColumn.setCellValueFactory(new PropertyValueFactory<>("authorisedPersonName"));
        workplaceColumn.setCellValueFactory(new PropertyValueFactory<>("workplace"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        contactNumberColumn.setCellValueFactory(new PropertyValueFactory<>("contactNumber"));

        tauronContactNumberList = contactNumbersConnector.getTauronContactNumbers();
        tauronContactNumbersTableView.getItems().addAll(tauronContactNumberList);

        Preferences preferences = Preferences.userNodeForPackage(TauronContactNumbersController.class);
        String obszarWadowice = "000-000-000; 111-111-111; 222-222-222";
        areaWadowiceTextField.setText(preferences.get("obszarWadowice", obszarWadowice));
        String obszarKety = "000-000-000; 111-111-111; 222-222-222";
        areaKetyTextField.setText(preferences.get("obszarKety", obszarKety));
        String obszarZywiec = "000-000-000; 111-111-111; 222-222-222";
        areaZywiecTextField.setText(preferences.get("obszarZywiec", obszarZywiec));
        String wydzialRuchu = "000-000-000; 111-111-111; 222-222-222";
        trafficDepartmentTextField.setText(preferences.get("wydzialRuchu", wydzialRuchu));
        String fax = "000-000-000";
        faxTextField.setText(preferences.get("fax", fax));

        if (LoginController.isAdmin) {
            tauronContactNumbersTableView.setEditable(true);
            authorisedPersonsColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            authorisedPersonsColumn.setOnEditCommit(event -> {
                TauronContactNumber tauronContactNumber = event.getRowValue();
                tauronContactNumber.setAuthorisedPersonName(event.getNewValue());
                contactNumbersConnector.updateTauronContactNumber(tauronContactNumber);
            });
            workplaceColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            workplaceColumn.setOnEditCommit(event -> {
                TauronContactNumber tauronContactNumber = event.getRowValue();
                tauronContactNumber.setWorkplace(event.getNewValue());
                contactNumbersConnector.updateTauronContactNumber(tauronContactNumber);
            });
            emailColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            emailColumn.setOnEditCommit(event -> {
                TauronContactNumber tauronContactNumber = event.getRowValue();
                tauronContactNumber.setEmail(event.getNewValue());
                contactNumbersConnector.updateTauronContactNumber(tauronContactNumber);
            });
            contactNumberColumn.setCellFactory(TextFieldTableCell.forTableColumn());
            contactNumberColumn.setOnEditCommit(event -> {
                TauronContactNumber tauronContactNumber = event.getRowValue();
                tauronContactNumber.setContactNumber(event.getNewValue());
                contactNumbersConnector.updateTauronContactNumber(tauronContactNumber);
            });

            areaWadowiceTextField.setEditable(true);
            areaKetyTextField.setEditable(true);
            areaZywiecTextField.setEditable(true);
            faxTextField.setEditable(true);
            trafficDepartmentTextField.setEditable(true);

            areaWadowiceTextField.textProperty().addListener((observable, oldValue, newValue) -> preferences.put("obszarWadowice", areaWadowiceTextField.getText()));
            areaKetyTextField.textProperty().addListener((observable, oldValue, newValue) -> preferences.put("obszarKety", areaKetyTextField.getText()));
            areaZywiecTextField.textProperty().addListener((observable, oldValue, newValue) -> preferences.put("obszarZywiec", areaZywiecTextField.getText()));
            trafficDepartmentTextField.textProperty().addListener((observable, oldValue, newValue) -> preferences.put("wydzialRuchu", trafficDepartmentTextField.getText()));
            faxTextField.textProperty().addListener((observable, oldValue, newValue) -> preferences.put("fax", faxTextField.getText()));

            authorisedPersonsTextField.setVisible(true);
            workplaceTextField.setVisible(true);
            emailTextField.setVisible(true);
            contactNumberTextField.setVisible(true);
            addNumberButton.setVisible(true);
            deleteNumberButton.setVisible(true);

            tauronContactNumbersTableView.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
                TauronContactNumber selectedNumer = tauronContactNumbersTableView.getSelectionModel().getSelectedItem();
                authorisedPersonsTextField.setText(selectedNumer.getAuthorisedPersonName());
                workplaceTextField.setText(selectedNumer.getWorkplace());
                emailTextField.setText(selectedNumer.getEmail());
                contactNumberTextField.setText(selectedNumer.getContactNumber());

            });
        }


    }

    public void addTauronPhoneNumber() {
        contactNumbersConnector.addTauronPhoneNumber(new TauronContactNumber(0, authorisedPersonsTextField.getText(), workplaceTextField.getText(), emailTextField.getText(), contactNumberTextField.getText()));
        tauronContactNumberList = contactNumbersConnector.getTauronContactNumbers();
        tauronContactNumbersTableView.getItems().clear();
        tauronContactNumbersTableView.getItems().addAll(tauronContactNumberList);
    }

    public void deleteTauronContactNumber() {
        contactNumbersConnector.deleteTauronContactNumber(new TauronContactNumber(0, authorisedPersonsTextField.getText(), workplaceTextField.getText(), emailTextField.getText(), contactNumberTextField.getText()));
        tauronContactNumberList = contactNumbersConnector.getTauronContactNumbers();
        tauronContactNumbersTableView.getItems().clear();
        tauronContactNumbersTableView.getItems().addAll(tauronContactNumberList);
    }
}
