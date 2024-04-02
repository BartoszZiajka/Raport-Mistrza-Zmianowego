package com.example.raport_mistrza_zmianowego.controllers;

import com.example.raport_mistrza_zmianowego.core.connectors.UserAccountConnector;
import com.example.raport_mistrza_zmianowego.core.model.UserAccount;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class UsersAccountsController implements Initializable {
    @FXML
    public TextField addLoginTextField;
    @FXML
    public TextField addPasswordTextField;
    @FXML
    public TextField editLoginTextField;
    @FXML
    public TextField editPasswordTextField;
    @FXML
    public Button addUserAccountButton;
    @FXML
    public Button saveUserAccount;
    @FXML
    public Button deleteUserAccount;
    @FXML
    public ListView<String> usersAccountsListView;
    @FXML
    public TextField editUserAccountNameTextField;
    @FXML
    public TextField addUserAccountNameTextField;
    @FXML
    public CheckBox editAdminPermissionCheckBox;
    @FXML
    public CheckBox editSpecialReportsPermissionCheckBox;
    @FXML
    public CheckBox addAdminPermissionCheckBox;
    @FXML
    public CheckBox addSpecialReportsPermissionCheckBox;
    private List<UserAccount> userAccountsList;
    private UserAccountConnector userAccountConnector;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        userAccountConnector = new UserAccountConnector();

        userAccountsList = userAccountConnector.getUserAccounts();

        setupUserAccountsList();
    }

    private void loadUserAccount(int index) {
        userAccountsList = userAccountConnector.getUserAccounts();
        if (index < userAccountsList.size()) {
            editUserAccountNameTextField.setText(userAccountsList.get(index).getUserAccountName());
            editLoginTextField.setText(userAccountsList.get(index).getLogin());
            editPasswordTextField.setText(userAccountsList.get(index).getPassword());
            editSpecialReportsPermissionCheckBox.setSelected(userAccountsList.get(index).isSpecialReport());
            editAdminPermissionCheckBox.setSelected(userAccountsList.get(index).isAdmin());
        }
    }

    @FXML
    public void addUserAccount() {
        userAccountConnector.addUserAccountRecord(addLoginTextField.getText(), addPasswordTextField.getText(), addUserAccountNameTextField.getText(), addAdminPermissionCheckBox.isSelected(), addSpecialReportsPermissionCheckBox.isSelected());

        addUserAccountNameTextField.clear();
        addLoginTextField.clear();
        addPasswordTextField.clear();
        addAdminPermissionCheckBox.setSelected(false);
        addSpecialReportsPermissionCheckBox.setSelected(false);
        setupUserAccountsList();
    }

    @FXML
    public void editUserAccount() {
        userAccountConnector.updateUserAccount(editLoginTextField.getText(), editPasswordTextField.getText(), editUserAccountNameTextField.getText(), userAccountsList.get(usersAccountsListView.getSelectionModel().getSelectedIndex()).getUserAccountID(), editAdminPermissionCheckBox.isSelected(), editSpecialReportsPermissionCheckBox.isSelected());
        setupUserAccountsList();
    }

    @FXML
    public void deleteUserAccount() {
        userAccountConnector.deleteUserAccount(userAccountsList.get(usersAccountsListView.getSelectionModel().getSelectedIndex()).getUserAccountID());
        setupUserAccountsList();
    }


    private void setupUserAccountsList() {
        List<String> userAccountsList = new ArrayList<>();
        for (UserAccount userAccount : userAccountConnector.getUserAccounts())
            userAccountsList.add(userAccount.getUserAccountName());
        usersAccountsListView.setItems(FXCollections.observableList(userAccountsList));
        loadUserAccount(0);
        usersAccountsListView.setOnMouseClicked(event -> loadUserAccount(usersAccountsListView.getSelectionModel().getSelectedIndex()));
    }
}

