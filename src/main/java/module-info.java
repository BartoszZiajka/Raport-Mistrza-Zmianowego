module com.example.raport_mistrza_zmianowego {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.sql;
    requires itextpdf;
    requires java.prefs;


    opens com.example.raport_mistrza_zmianowego to javafx.fxml;
    exports com.example.raport_mistrza_zmianowego.controllers;
    opens com.example.raport_mistrza_zmianowego.controllers to javafx.fxml;
    exports com.example.raport_mistrza_zmianowego.core.model;
    opens com.example.raport_mistrza_zmianowego.core.model to javafx.fxml;
    exports com.example.raport_mistrza_zmianowego.core.controls;
    opens com.example.raport_mistrza_zmianowego.core.controls to javafx.fxml;
    exports com.example.raport_mistrza_zmianowego.core.connectors;
    opens com.example.raport_mistrza_zmianowego.core.connectors to javafx.fxml;
    exports com.example.raport_mistrza_zmianowego;
}