package com.example.raport_mistrza_zmianowego.controllers;

import com.example.raport_mistrza_zmianowego.core.connectors.OvertimesConnector;
import com.example.raport_mistrza_zmianowego.core.connectors.ReportConnector;
import com.example.raport_mistrza_zmianowego.core.model.Overtime;
import com.example.raport_mistrza_zmianowego.core.model.Report;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class ShowRaportsController implements Initializable {
    @FXML
    public Text dataText;
    @FXML
    public Text workingHoursText;
    @FXML
    public Text shiftMasterText;
    @FXML
    public Text porterUntilText;
    @FXML
    public Text porterFromText;
    @FXML
    public Text standbyZasoleText;
    @FXML
    public Text standbyZasoleText1;
    @FXML
    public Text standbyZaborzeText;
    @FXML
    public Text standbyZaborzeText1;
    @FXML
    public Text standbyHydroforniaText;
    @FXML
    public Text standbyHydroforniaText1;
    @FXML
    public Text standbyPrzepompowniaText;
    @FXML
    public Text standbyPrzepompowniaText1;
    @FXML
    public Text pw15ZasoleText;
    @FXML
    public Text c15ZasoleText;
    @FXML
    public Text pw20ZasoleText;
    @FXML
    public Text c20ZasoleText;
    @FXML
    public Text przepMinZasoleText;
    @FXML
    public Text przepMaxZasoleText;
    @FXML
    public Text pw15ZaborzeText;
    @FXML
    public Text c15ZaborzeText;
    @FXML
    public Text pw20ZaborzeText;
    @FXML
    public Text c20ZaborzeText;
    @FXML
    public Text przepMinZaborzeText;
    @FXML
    public Text przepMaxZaborzeText;
    @FXML
    public Text pw15HydroforniaText;
    @FXML
    public Text c15HydroforniaText;
    @FXML
    public Text pw20HydroforniaText;
    @FXML
    public Text c20HydroforniaText;
    @FXML
    public Text odczytChelmekText;
    @FXML
    public Text zuzycieChelmekText;
    @FXML
    public Text measurementTime1;
    @FXML
    public Text measurementTime2;
    @FXML
    public TextArea shiftReportTextArea;
    @FXML
    public TableView<Overtime> overtimesTableView;
    @FXML
    public TableColumn<Overtime, String> nameAndSurnameColumn;
    @FXML
    public TableColumn<Overtime, String> overtimesFromColumn;
    @FXML
    public TableColumn<Overtime, String> overtimesUntilColumn;
    @FXML
    public TableColumn<Overtime, String> typeOfWorkColumn;
    @FXML
    public Button generatePDFButton;
    @FXML
    public ListView<String> raportsListView = new ListView<>();
    @FXML
    public Button generateJustificationButton;
    @FXML
    public TextField searchRaportTextField;
    private OvertimesConnector overtimesConnector;
    private ReportConnector reportConnector;
    private BaseFont bf;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
//        initialize database instance
        overtimesConnector = new OvertimesConnector();
        reportConnector = new ReportConnector();
//        get report dates from database and insert into ListView
        ObservableList<String> raportDates = reportConnector.getReportDates();
        raportsListView.setItems(raportDates);
//        load the newest showing report
        if (!raportDates.isEmpty()) {
            raportsListView.getSelectionModel().selectFirst();
            loadReport(raportsListView.getSelectionModel().getSelectedItem());
        }
//        set listener to ListView to change displaying reports
        raportsListView.setOnMouseClicked(event -> loadReport(raportsListView.getSelectionModel().getSelectedItem()));
//        set cells values names
        nameAndSurnameColumn.setCellValueFactory(new PropertyValueFactory<>("employee"));
        overtimesFromColumn.setCellValueFactory(new PropertyValueFactory<>("from"));
        overtimesUntilColumn.setCellValueFactory(new PropertyValueFactory<>("until"));
        typeOfWorkColumn.setCellValueFactory(new PropertyValueFactory<>("workName"));

        searchRaportTextField.setOnKeyReleased(e -> {
            raportsListView.getItems().clear();
            ObservableList<String> reportDatesFromSearch = reportConnector.getReportDatesFromSearch(searchRaportTextField.getText());
            raportsListView.setItems(reportDatesFromSearch);
//        load the newest showing report
            if (!reportDatesFromSearch.isEmpty())
                loadReport(reportDatesFromSearch.get(0));
//        set listener to ListView to change displaying reports
            raportsListView.setOnMouseClicked(event -> loadReport(raportsListView.getSelectionModel().getSelectedItem()));
        });
    }

    private void loadReport(String raportDate) {
        String[] arr = raportDate.split("\\s");
        Report report = reportConnector.getReportById(Integer.parseInt(arr[0]));
        dataText.setText(report.getReportDate());
        workingHoursText.setText(report.getWorkingHours());
        shiftMasterText.setText(report.getDutyOfficer());
        porterUntilText.setText(report.getPorterHourTo() + " " + report.getPorterNameTo());
        porterFromText.setText(report.getPorterHourFrom() + " " + report.getPorterNameFrom());
        standbyZasoleText.setText(report.getStandbyZasoleFirstShift());
        standbyZasoleText1.setText(report.getStandbyZasoleSecondShift());
        standbyZaborzeText.setText(report.getStandbyZaborzeFirstShift());
        standbyZaborzeText1.setText(report.getStandbyZaborzeSecondShift());
        standbyHydroforniaText.setText(report.getStandbyHydroforniaFirstShift());
        standbyHydroforniaText1.setText(report.getStandbyHydroforniaSecondShift());
        standbyPrzepompowniaText.setText(report.getStandbyPrzepompowniaFirstShift());
        standbyPrzepompowniaText1.setText(report.getStandbyPrzepompowniaSecondShift());
        shiftReportTextArea.setText(report.getShiftReport());
        pw15ZasoleText.setText(report.getPw15Zasole());
        c15ZasoleText.setText(report.getC15Zasole());
        pw20ZasoleText.setText(report.getPw20Zasole());
        c20ZasoleText.setText(report.getC20Zasole());
        przepMinZasoleText.setText(report.getPrzeplywMinZasole());
        przepMaxZasoleText.setText(report.getPrzeplywMaxZasole());
        pw15ZaborzeText.setText(report.getPw15Zaborze());
        c15ZaborzeText.setText(report.getC15Zaborze());
        pw20ZaborzeText.setText(report.getPw20Zaborze());
        c20ZaborzeText.setText(report.getC20Zaborze());
        przepMinZaborzeText.setText(report.getPrzeplywMinZaborze());
        przepMaxZaborzeText.setText(report.getPrzeplywMaxZaborze());
        pw15HydroforniaText.setText(report.getPw15Hydrofornia());
        c15HydroforniaText.setText(report.getC15Hydrofornia());
        pw20HydroforniaText.setText(report.getPw20Hydrofornia());
        c20HydroforniaText.setText(report.getC20Hydrofornia());
        odczytChelmekText.setText(report.getSprzedazChelmek());
        zuzycieChelmekText.setText(report.getZuzycieChelmek());

        overtimesTableView.getItems().clear();
        List<Overtime> overtimes = overtimesConnector.getOvertimesById(Integer.parseInt(arr[0]));
        for (Overtime overtime : overtimes) overtimesTableView.getItems().add(overtime);
    }

    @FXML
    private void generatePDF() throws DocumentException, IOException {
        Rectangle pageSize = new Rectangle(595, 842);
        Document document = new Document(pageSize, 30, 30, 50, 30);
        bf = BaseFont.createFont("Poppins-Light.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        try {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Wybierz folder docelowy");
            File initialDirectory = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "Pliki");
            if (!initialDirectory.exists()) {
                initialDirectory.mkdirs();
            }
            chooser.setInitialDirectory(initialDirectory);
            File file = chooser.showDialog(generatePDFButton.getScene().getWindow());
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsolutePath() + "/raport_mistrza_zmianowego-" + dataText.getText() + ".pdf"));
            document.open();
            Paragraph title = new Paragraph("RAPORT MISTRZA ZMIANY", new Font(bf, 12));
            title.setAlignment(Element.ALIGN_CENTER);

            PdfPTable reportTable = new PdfPTable(8);
            reportTable.setTotalWidth(document.getPageSize().getWidth() - 80);
            reportTable.setLockedWidth(true);
            reportTable.setSpacingBefore(10);

            addReportTableContent(reportTable);

            PdfPTable dataFromFacilitiesTable = new PdfPTable(8);
            dataFromFacilitiesTable.setTotalWidth(document.getPageSize().getWidth() - 80);
            dataFromFacilitiesTable.setLockedWidth(true);
            dataFromFacilitiesTable.setSpacingBefore(25);
            dataFromFacilitiesTable.setSpacingAfter(25);

            addDataFromFacilitiesTableHeader(dataFromFacilitiesTable);
            addDataFromFacilitiesRows(dataFromFacilitiesTable);

            PdfPTable overtimesTable = new PdfPTable(10);
            overtimesTable.setTotalWidth(document.getPageSize().getWidth() - 40);
            overtimesTable.setLockedWidth(true);
            overtimesTable.setSpacingBefore(25);

            addOvertimesHeader(overtimesTable);
            addOvertimesRows(overtimesTable);

            PdfPTable dataTable = new PdfPTable(8);
            dataTable.setTotalWidth(document.getPageSize().getWidth() - 80);
            dataTable.setLockedWidth(true);
            dataTable.setSpacingBefore(10);

            addDataTableContent(dataTable);

            Paragraph shiftReportTextPDF = new Paragraph("Raport zmiany:", new Font(bf, 10));
            Paragraph shiftReport = new Paragraph(shiftReportTextArea.getText(), new Font(bf, 10));

            document.add(title);
            document.add(reportTable);
            document.add(dataFromFacilitiesTable);
            document.add(shiftReportTextPDF);
            document.add(shiftReport);

            document.newPage();

            document.add(title);
            document.add(dataTable);
            document.add(overtimesTable);
            document.close();
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addDataTableContent(PdfPTable table) {
        PdfPCell cell = new PdfPCell();

        cell.setBorderWidthLeft(0);
        cell.setBorderWidthRight(0);
        cell.setBorderWidthTop(0);
        cell.setBorderWidthBottom(0);
        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPhrase(new Phrase("Data:", new Font(bf, 10)));
        table.addCell(cell);


        cell.setBorderWidthBottom(1);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPhrase(new Phrase(dataText.getText(), new Font(bf, 10)));
        table.addCell(cell);

        cell.setBorderWidthBottom(0);
        cell.setColspan(4);
        cell.setPhrase(new Phrase(" "));
        table.addCell(cell);
    }

    private void addReportTableContent(PdfPTable table) {
        PdfPCell headerCell = new PdfPCell();
        PdfPCell cell = new PdfPCell();
        PdfPCell blankCell = new PdfPCell();

        headerCell.setFixedHeight(30);
        cell.setFixedHeight(30);

        headerCell.setBorderWidthLeft(0);
        headerCell.setBorderWidthBottom(0);
        headerCell.setBorderWidthTop(0);
        headerCell.setColspan(2);
        headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerCell.setPhrase(new Phrase("Data:", new Font(bf, 10)));
        table.addCell(headerCell);

        cell.setBorderWidthLeft(0);
        cell.setBorderWidthRight(0);
        cell.setBorderWidthTop(0);
        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setPhrase(new Phrase(dataText.getText(), new Font(bf, 10)));
        table.addCell(cell);

        cell.setColspan(4);
        cell.setPhrase(new Phrase(" "));
        table.addCell(cell);

        headerCell.setPhrase(new Phrase("Rodzaj zmiany", new Font(bf, 10)));
        table.addCell(headerCell);

        cell.setColspan(6);
        cell.setPhrase(new Phrase(workingHoursText.getText(), new Font(bf, 10)));
        table.addCell(cell);

        headerCell.setPhrase(new Phrase("Mistrz zmiany", new Font(bf, 10)));
        table.addCell(headerCell);

        cell.setPhrase(new Phrase(shiftMasterText.getText(), new Font(bf, 10)));
        table.addCell(cell);

        headerCell.setPhrase(new Phrase("Portier", new Font(bf, 10)));
        table.addCell(headerCell);

        cell.setColspan(1);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPhrase(new Phrase("do godz.", new Font(bf, 10)));
        table.addCell(cell);

        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPhrase(new Phrase(porterUntilText.getText(), new Font(bf, 10)));
        table.addCell(cell);

        cell.setColspan(1);
        cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        cell.setPhrase(new Phrase("od godz.", new Font(bf, 10)));
        table.addCell(cell);

        cell.setColspan(2);
        cell.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell.setPhrase(new Phrase(porterFromText.getText(), new Font(bf, 10)));
        table.addCell(cell);

        blankCell.setColspan(2);
        blankCell.setBorderWidthLeft(0);
        blankCell.setBorderWidthTop(0);
        blankCell.setBorderWidthBottom(0);
        blankCell.setPhrase(new Phrase(" "));
        table.addCell(blankCell);

        headerCell.setBorderWidthBottom(1);
        headerCell.setPhrase(new Phrase("Rodzaj zmiany", new Font(bf, 10)));
        table.addCell(headerCell);

        headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell.setPhrase(new Phrase("I zmiana", new Font(bf, 10)));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("II zmiana", new Font(bf, 10)));
        table.addCell(headerCell);

        headerCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerCell.setBorderWidthBottom(0);
        headerCell.setPhrase(new Phrase("Dyżurni:", new Font(bf, 10)));
        table.addCell(headerCell);

        headerCell.setBackgroundColor(new BaseColor(0, 147, 221));
        headerCell.setPhrase(new Phrase("SUW Zasole", new Font(bf, 10)));
        table.addCell(headerCell);

        cell.setColspan(2);
        cell.setPhrase(new Phrase(standbyZasoleText.getText(), new Font(bf, 10)));
        table.addCell(cell);

        cell.setPhrase(new Phrase(standbyZasoleText1.getText(), new Font(bf, 10)));
        table.addCell(cell);

        blankCell.setBorderWidthLeft(0);
        blankCell.setBorderWidthTop(0);
        blankCell.setBorderWidthBottom(0);
        blankCell.setColspan(2);
        blankCell.setPhrase(new Phrase(" "));
        table.addCell(blankCell);

        headerCell.setPhrase(new Phrase("SUW Zaborze", new Font(bf, 10)));
        table.addCell(headerCell);

        cell.setPhrase(new Phrase(standbyZaborzeText.getText(), new Font(bf, 10)));
        table.addCell(cell);

        cell.setPhrase(new Phrase(standbyZaborzeText1.getText(), new Font(bf, 10)));
        table.addCell(cell);

        table.addCell(blankCell);

        headerCell.setPhrase(new Phrase("Centralna Hydrofornia", new Font(bf, 10)));
        table.addCell(headerCell);

        cell.setPhrase(new Phrase(standbyHydroforniaText.getText(), new Font(bf, 10)));
        table.addCell(cell);

        cell.setPhrase(new Phrase(standbyHydroforniaText1.getText(), new Font(bf, 10)));
        table.addCell(cell);

        table.addCell(blankCell);

        headerCell.setBorderWidthBottom(1);
        headerCell.setPhrase(new Phrase("Przepompownia ścieków", new Font(bf, 10)));
        table.addCell(headerCell);

        cell.setPhrase(new Phrase(standbyPrzepompowniaText.getText(), new Font(bf, 10)));
        table.addCell(cell);

        cell.setPhrase(new Phrase(standbyPrzepompowniaText1.getText(), new Font(bf, 10)));
        table.addCell(cell);

    }

    private void addOvertimesHeader(PdfPTable table) {
        PdfPCell headerCell = new PdfPCell();

        headerCell.setFixedHeight(20);
        headerCell.setBackgroundColor(new BaseColor(0, 147, 221));
        headerCell.setColspan(10);
        headerCell.setPhrase(new Phrase("Pracownicy pracujący po godz 15:00 (godz. nadliczbowe)", new Font(bf, 11)));
        table.addCell(headerCell);

        headerCell.setFixedHeight(30);
        headerCell.setColspan(3);
        headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell.setPhrase(new Phrase("imię i nazwisko", new Font(bf, 10)));
        table.addCell(headerCell);

        headerCell.setColspan(1);
        headerCell.setPhrase(new Phrase("od godz.", new Font(bf, 10)));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("do godz.", new Font(bf, 10)));
        table.addCell(headerCell);

        headerCell.setColspan(5);
        headerCell.setPhrase(new Phrase("rodzaj pracy\n(awaria, godziny planowane, inne)", new Font(bf, 10)));
        table.addCell(headerCell);
    }

    private void addOvertimesRows(PdfPTable table) {
        PdfPCell row = new PdfPCell();
        row.setVerticalAlignment(Element.ALIGN_MIDDLE);
        String[] arr = raportsListView.getSelectionModel().getSelectedItem().split("\\s");
        List<Overtime> overtimes = overtimesConnector.getOvertimesById(Integer.parseInt(arr[0]));
        for (Overtime overtime : overtimes) {
            row.setColspan(3);
            row.setPhrase(new Phrase(overtime.getEmployee(), new Font(bf, 10)));
            table.addCell(row);

            row.setColspan(1);
            row.setPhrase(new Phrase(overtime.getFrom(), new Font(bf, 10)));
            table.addCell(row);

            row.setColspan(1);
            row.setPhrase(new Phrase(overtime.getUntil(), new Font(bf, 10)));
            table.addCell(row);

            row.setColspan(5);
            row.setPhrase(new Phrase(overtime.getWorkName(), new Font(bf, 10)));
            table.addCell(row);
        }
    }

    private void addDataFromFacilitiesTableHeader(PdfPTable table) {
        PdfPCell headerR2 = new PdfPCell();
        PdfPCell headerC2 = new PdfPCell();
        PdfPCell header = new PdfPCell();

        headerR2.setFixedHeight(40);
        headerR2.setBackgroundColor(new BaseColor(0, 147, 221));
        headerR2.setRowspan(2);
        headerR2.setColspan(2);
        headerR2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerR2.setHorizontalAlignment(Element.ALIGN_RIGHT);
        headerR2.setPhrase(new Phrase("Dane z obiektów:", new Font(bf, 11)));
        table.addCell(headerR2);

        headerC2.setFixedHeight(15);
        headerC2.setBackgroundColor(new BaseColor(0, 147, 221));
        headerC2.setColspan(2);
        headerC2.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerC2.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerC2.setPhrase(new Phrase("godzina 8:00/15:00", new Font(bf, 9)));
        table.addCell(headerC2);

        headerC2.setPhrase(new Phrase("godzina 14:00/20:00", new Font(bf, 9)));
        table.addCell(headerC2);

        headerR2.setColspan(1);
        headerR2.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerR2.setPhrase(new Phrase("przepływ\nmin.\n[m3/h]", new Font(bf, 9)));
        table.addCell(headerR2);

        headerR2.setPhrase(new Phrase("przepływ\nmax.\n[m3/h]", new Font(bf, 9)));
        table.addCell(headerR2);

        header.setFixedHeight(20);
        header.setBackgroundColor(new BaseColor(0, 147, 221));
        header.setHorizontalAlignment(Element.ALIGN_CENTER);
        header.setVerticalAlignment(Element.ALIGN_MIDDLE);
        header.setPhrase(new Phrase("poziom\nwody\n[m]", new Font(bf, 8)));
        table.addCell(header);

        header.setPhrase(new Phrase("ciśnienie\n[atm] lub [bar]", new Font(bf, 8)));
        table.addCell(header);

        header.setPhrase(new Phrase("poziom\nwody\n[m]", new Font(bf, 8)));
        table.addCell(header);

        header.setPhrase(new Phrase("ciśnienie\n[atm] lub [bar]", new Font(bf, 8)));
        table.addCell(header);
    }

    private void addDataFromFacilitiesRows(PdfPTable table) {
        PdfPCell rowHead = new PdfPCell();
        PdfPCell cell = new PdfPCell();

        rowHead.setFixedHeight(35);
        rowHead.setBackgroundColor(new BaseColor(0, 147, 221));
        rowHead.setColspan(2);
        rowHead.setVerticalAlignment(Element.ALIGN_MIDDLE);
        rowHead.setHorizontalAlignment(Element.ALIGN_RIGHT);
        rowHead.setPhrase(new Phrase("SUW Zasole", new Font(bf, 10)));
        table.addCell(rowHead);


        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        cell.setHorizontalAlignment(Element.ALIGN_CENTER);

        List<String> zasole = Arrays.asList(pw15ZasoleText.getText(), c15ZasoleText.getText(), pw20ZasoleText.getText(),
                c20ZasoleText.getText(), przepMinZasoleText.getText(), przepMaxZasoleText.getText());
        for (String dana : zasole) {
            cell.setPhrase(new Phrase(dana, new Font(bf, 10)));
            table.addCell(cell);
        }

        rowHead.setPhrase(new Phrase("SUW Zaborze", new Font(bf, 10)));
        table.addCell(rowHead);

        List<String> zaborze = Arrays.asList(pw15ZaborzeText.getText(), c15ZaborzeText.getText(), pw20ZaborzeText.getText(), c20ZaborzeText.getText(), przepMinZaborzeText.getText(), przepMaxZaborzeText.getText());
        for (String dana : zaborze) {
            cell.setPhrase(new Phrase(dana, new Font(bf, 10)));
            table.addCell(cell);
        }

        rowHead.setPhrase(new Phrase("Centralna Hydrofornia", new Font(bf, 10)));
        table.addCell(rowHead);

        List<String> hydrofornia = Arrays.asList(pw15HydroforniaText.getText(), c15HydroforniaText.getText(), pw20HydroforniaText.getText(), c20HydroforniaText.getText(), "X", "X");
        for (String dana : hydrofornia) {
            cell.setPhrase(new Phrase(dana, new Font(bf, 10)));
            table.addCell(cell);
        }

        rowHead.setPhrase(new Phrase("Sprzedaż wody Chełmek", new Font(bf, 10)));
        table.addCell(rowHead);

        rowHead.setHorizontalAlignment(Element.ALIGN_CENTER);
        rowHead.setPhrase(new Phrase("Odczyt (stan urządzenia pomiarowego)", new Font(bf, 10)));
        table.addCell(rowHead);

        cell.setPhrase(new Phrase(odczytChelmekText.getText(), new Font(bf, 10)));
        table.addCell(cell);

        rowHead.setPhrase(new Phrase("Zużycie [m3]", new Font(bf, 10)));
        table.addCell(rowHead);

        cell.setPhrase(new Phrase(zuzycieChelmekText.getText(), new Font(bf, 10)));
        table.addCell(cell);
    }

    public void generateJustification() throws DocumentException, IOException {
        Rectangle pageSize = new Rectangle(595, 842);
        Document document = new Document(pageSize, 30, 30, 50, 30);
        bf = BaseFont.createFont("Poppins-Light.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        try {
            DirectoryChooser chooser = new DirectoryChooser();
            chooser.setTitle("Wybierz folder docelowy");
            File initialDirectory = new File(System.getProperty("user.dir") + System.getProperty("file.separator") + "Pliki");
            if (!initialDirectory.exists()) {
                initialDirectory.mkdirs();
            }
            chooser.setInitialDirectory(initialDirectory);
            File file = chooser.showDialog(generateJustificationButton.getScene().getWindow());
            PdfWriter.getInstance(document, new FileOutputStream(file.getAbsolutePath() + "/uzasadnienie_godzin_nadliczbowych-" + dataText.getText() + ".pdf"));
            document.open();

            Paragraph stopka = new Paragraph("Załącznik do zarządzenia nr 13/2003\nw sprawie pracy w godzinach nadliczbowych\nz dnia 20. maja 2003 r.", new Font(bf, 12));
            stopka.setAlignment(Element.ALIGN_RIGHT);

            Paragraph title = new Paragraph("Dzienny raport - uzasadnienie pracy w godzinach nadliczbowych", new Font(bf, 12));
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingBefore(20);

            PdfPTable uzasadnienieTable = new PdfPTable(14);
            uzasadnienieTable.setTotalWidth(document.getPageSize().getWidth() - 20);
            uzasadnienieTable.setLockedWidth(true);
            uzasadnienieTable.setSpacingBefore(10);

            addUzasadnienieTableHeaders(uzasadnienieTable);
            addUzasadnienieTableContent(uzasadnienieTable);

            Paragraph dataUzasadnienia = new Paragraph("            Oświęcim dnia,..................................................             " +
                    "                                  ..................................................", new Font(bf, 12));
            dataUzasadnienia.setSpacingBefore(100);
            Paragraph podpisMistrza = new Paragraph("(podpis mistrza zmianowego)            ", new Font(bf, 8));
            podpisMistrza.setAlignment(Element.ALIGN_RIGHT);

            document.add(stopka);
            document.add(title);
            document.add(uzasadnienieTable);
            document.add(dataUzasadnienia);
            document.add(podpisMistrza);
            document.close();
        } catch (DocumentException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void addUzasadnienieTableHeaders(PdfPTable table) {
        PdfPCell headerCell = new PdfPCell();

        headerCell.setFixedHeight(20);
        headerCell.setColspan(1);
        headerCell.setRowspan(2);
        headerCell.setVerticalAlignment(Element.ALIGN_MIDDLE);
        headerCell.setHorizontalAlignment(Element.ALIGN_CENTER);
        headerCell.setBackgroundColor(new BaseColor(0, 147, 221));
        headerCell.setPhrase(new Phrase("Lp.", new Font(bf, 11)));
        table.addCell(headerCell);

        headerCell.setColspan(3);
        headerCell.setPhrase(new Phrase("Imię i Nazwisko pracownika", new Font(bf, 11)));
        table.addCell(headerCell);

        headerCell.setColspan(2);
        headerCell.setPhrase(new Phrase("Podpis Pracownika", new Font(bf, 11)));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("Data wystąpienia godzin", new Font(bf, 11)));
        table.addCell(headerCell);

        headerCell.setRowspan(1);
        headerCell.setPhrase(new Phrase("Praca w godz", new Font(bf, 11)));
        table.addCell(headerCell);

        headerCell.setColspan(1);
        headerCell.setRowspan(2);
        headerCell.setPhrase(new Phrase("Ilość godzin", new Font(bf, 11)));
        table.addCell(headerCell);

        headerCell.setColspan(3);
        headerCell.setPhrase(new Phrase("Uzasadnienie godzin nadliczbowych", new Font(bf, 11)));
        table.addCell(headerCell);

        headerCell.setColspan(1);
        headerCell.setRowspan(1);
        headerCell.setPhrase(new Phrase("od", new Font(bf, 11)));
        table.addCell(headerCell);

        headerCell.setPhrase(new Phrase("do", new Font(bf, 11)));
        table.addCell(headerCell);

    }

    private void addUzasadnienieTableContent(PdfPTable table) {
        PdfPCell row = new PdfPCell();
        row.setVerticalAlignment(Element.ALIGN_MIDDLE);
        row.setHorizontalAlignment(Element.ALIGN_CENTER);
        List<Overtime> nadgodziny = overtimesConnector.getOvertimesById(reportConnector.getReportID(dataText.getText(), workingHoursText.getText()));
        for (int i = 0, j = 1; i < nadgodziny.size(); i++, j++) {
            row.setColspan(1);
            row.setPhrase(new Phrase(String.valueOf(j), new Font(bf, 10)));
            table.addCell(row);

            row.setColspan(3);
            row.setPhrase(new Phrase(nadgodziny.get(i).getEmployee(), new Font(bf, 10)));
            table.addCell(row);

            row.setColspan(2);
            row.setPhrase(new Phrase(" "));
            table.addCell(row);

            row.setPhrase(new Phrase(dataText.getText(), new Font(bf, 10)));
            table.addCell(row);

            row.setColspan(1);
            row.setPhrase(new Phrase(nadgodziny.get(i).getFrom(), new Font(bf, 10)));
            table.addCell(row);

            row.setPhrase(new Phrase(nadgodziny.get(i).getUntil(), new Font(bf, 10)));
            table.addCell(row);

            float wartoscNadgodziny = getOvertimeValue(nadgodziny, i);

            if (wartoscNadgodziny < 0 || wartoscNadgodziny > 24)
                row.setPhrase(new Phrase(" ", new Font(bf, 10)));
            else
                row.setPhrase(new Phrase(String.valueOf(wartoscNadgodziny), new Font(bf, 10)));
            table.addCell(row);

            row.setColspan(3);
            row.setPhrase(new Phrase(nadgodziny.get(i).getWorkName(), new Font(bf, 10)));
            table.addCell(row);
        }
    }

    private static float getOvertimeValue(List<Overtime> overtimes, int i) {
        float startingHour = Float.parseFloat(overtimes.get(i).getFrom().substring(0, 2));
        float closingHour = Float.parseFloat(overtimes.get(i).getUntil().substring(0, 2));
        float startingMinutes;
        float closingMinutes;

        if (overtimes.get(i).getFrom().length() > 2)
            startingMinutes = Float.parseFloat((overtimes.get(i).getFrom().substring(3)));
        else
            startingMinutes = 0f;

        if (overtimes.get(i).getUntil().length() > 2)
            closingMinutes = Float.parseFloat((overtimes.get(i).getUntil().substring(3)));
        else
            closingMinutes = 0f;

        float numberOfHours = closingHour - startingHour;
        float numberOfMinutes = ((startingMinutes / 60) - (closingMinutes / 60)) * -1;
        float overtimeValue = Math.round((numberOfHours + numberOfMinutes) * 100);
        overtimeValue /= 100;
        return overtimeValue;
    }
}