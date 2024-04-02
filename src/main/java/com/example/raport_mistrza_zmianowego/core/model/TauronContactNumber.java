package com.example.raport_mistrza_zmianowego.core.model;

public class TauronContactNumber {
    private int tauronContactNumberID;
    private String authorisedPersonName;
    private String workplace;
    private String email;
    private String contactNumber;

    public TauronContactNumber(int tauronContactNumberID, String authorisedPersonName, String workplace, String email, String contactNumber) {
        this.tauronContactNumberID = tauronContactNumberID;
        this.authorisedPersonName = authorisedPersonName;
        this.workplace = workplace;
        this.email = email;
        this.contactNumber = contactNumber;
    }

    public int getTauronContactNumberID() {
        return tauronContactNumberID;
    }

    public void setTauronContactNumberID(int tauronContactNumberID) {
        this.tauronContactNumberID = tauronContactNumberID;
    }

    public String getAuthorisedPersonName() {
        return authorisedPersonName;
    }

    public void setAuthorisedPersonName(String authorisedPersonName) {
        this.authorisedPersonName = authorisedPersonName;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }
}
