package com.example.raport_mistrza_zmianowego.core.model;

public class UserAccount {
    private String login;
    private String password;
    private String userAccountName;
    private int userAccountID;
    private boolean isAdmin;
    private boolean isSpecialReport;

    public UserAccount(String login, String password, String userAccountName, int userAccountID, boolean isAdmin, boolean isSpecialReport) {
        this.login = login;
        this.password = password;
        this.userAccountName = userAccountName;
        this.userAccountID = userAccountID;
        this.isAdmin = isAdmin;
        this.isSpecialReport = isSpecialReport;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserAccountName() {
        return userAccountName;
    }

    public void setUserAccountName(String userAccountName) {
        this.userAccountName = userAccountName;
    }

    public int getUserAccountID() {
        return userAccountID;
    }

    public void setUserAccountID(int userAccountID) {
        this.userAccountID = userAccountID;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isSpecialReport() {
        return isSpecialReport;
    }

    public void setSpecialReport(boolean specialReport) {
        isSpecialReport = specialReport;
    }
}

