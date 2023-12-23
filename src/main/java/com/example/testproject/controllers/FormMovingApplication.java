package com.example.testproject.controllers;

import com.example.testproject.data.ServiciosDatos;
import com.example.testproject.data.SolicitudesDatos;
import com.example.testproject.models.SolicitudesModelo;
import com.example.testproject.models.serviciosModelo;
import com.example.testproject.temporarydata.CacheUsuarios;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;


public class FormMovingApplication implements Initializable {
    @FXML
    private Button btnMakeRequest, btnServicesPdf;

    @FXML
    private DatePicker txtDueDate;

    @FXML
    private TextField txtOrigin, txtDestiny;

    @FXML
    private ComboBox<Integer> cmbService;

    SolicitudesDatos obj = new SolicitudesDatos();
    ServiciosDatos sd = new ServiciosDatos();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnMakeRequest.setOnAction(handlerButtons);
        btnServicesPdf.setOnAction(handlerButtons);
        loadDataService();
    }

    private void loadDataService() {
        ServiciosDatos serviciosDatos = new ServiciosDatos();

        cmbService.setItems((ObservableList) serviciosDatos.mostrarIdServicios());
        cmbService.getSelectionModel().select(0);
    }

    EventHandler<ActionEvent> handlerButtons = (event) -> {
        if (event.getSource() == btnMakeRequest) {
            makeRequest();
        }
        if (event.getSource() == btnServicesPdf) {
            generateListaServicios();
        }
    };

    private void makeRequest() {
        if (txtDueDate == null || txtOrigin.getText().trim() == null || txtDestiny.getText().trim() == null
                || cmbService.getValue() == null) {
            showMessage("Error", "Please enter data", Alert.AlertType.ERROR);
        } else if (txtDueDate != null && txtOrigin.getText().trim() != null && txtDestiny.getText().trim() != null) {
            try {

                if (obj.insertRequestAsUser(CacheUsuarios.IdUsuario, cmbService.getValue(), txtOrigin.getText().trim(), txtDestiny.getText().trim(), txtDueDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")))) {

                    showMessage("Success", "Request successfully created", Alert.AlertType.INFORMATION);

                    String s = txtDueDate.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    String a = String.valueOf(cmbService.getValue());
                    String o = txtOrigin.getText().trim();
                    String d = txtDestiny.getText().trim();
                    clearFields();
                    generateReport1(CacheUsuarios.Nombre, s, a, o, d);

                } else {
                    showMessage("Error", "Request failed", Alert.AlertType.ERROR);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void clearFields() {
        txtDueDate.setValue(null);
        txtDestiny.setText("");
        txtOrigin.setText("");
        cmbService.getSelectionModel().select(0);
    }

    private void showMessage(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    private void generateReport1(String nombre, String date, String a, String o, String d) {
        try {
            File file = new File("results/Solicitud.pdf");
            file.getParentFile().mkdirs();
            createPdf("results/Solicitud.pdf", nombre, date, a, o, d);
            openPDFFile("results/Solicitud.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void openPDFFile(String filename) {
        if (Desktop.isDesktopSupported()) {
            try {
                File myFile = new File(filename);
                Desktop.getDesktop().open(myFile);
            } catch (IOException ex) {
                // no application registered for PDFs
            }
        }
    }

    public void createPdf(String dest, String nombre, String DATE, String a, String o, String d) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf);
        document.setTopMargin(12);
        document.setLeftMargin(10);
        document.setFontSize(12);
        document.setWordSpacing(5);

        document.add(new Paragraph("\n\nA P P L I C A T I O N\n\n").setTextAlignment(TextAlignment.CENTER).setBold().setFontSize(30));
        document.add(new Paragraph("Due Date : " + DATE + "\n\\n").setTextAlignment(TextAlignment.RIGHT));
        document.add(new Paragraph("You have picked up option number:" + a + " for your awesome service\n\n\n\n").setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph(" Congratulations " + nombre.toUpperCase() + "\n\nYour application has been send... \n\n " +
                "please wait for the admin to approve your applicattion\n\n\n\n Please keep this document until further instruction\n\n\n\n").setTextAlignment(TextAlignment.CENTER));
        document.add(new Paragraph("  From:  " + o.toUpperCase().strip()).setTextAlignment(TextAlignment.LEFT));
        document.add(new Paragraph("  To: " + d.toUpperCase().strip() + "\n\n\n\n").setTextAlignment(TextAlignment.LEFT));

        document.close();
    }

    public void process2(Table table, serviciosModelo servicios, PdfFont font, boolean isHeader) {

        if (isHeader) {
            table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("ID SOLICITUD.").setFont(font)));
            table.addHeaderCell(new com.itextpdf.layout.element.Cell().add(new Paragraph("ID SERVICIO").setFont(font)));
        } else {
            table.addCell(new com.itextpdf.layout.element.Cell().add(new Paragraph(servicios.getIdServicio() + "").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(servicios.getDescripcion() + "").setFont(font)));
        }
    }

    public void createPdf(String dest, String message) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf, PageSize.A3);
        String s = "";
        //Add paragraph to the document
        document.add(new Paragraph(message).setFontSize(19).setBold().setTextAlignment(TextAlignment.CENTER));
        PdfFont font = PdfFontFactory.createFont(StandardFonts.HELVETICA);
        PdfFont bold = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);

        Table table2 = new Table(UnitValue.createPercentArray(new float[]{2, 2}))
                .useAllAvailableWidth();


        process2(table2, null, bold, true);
        for (serviciosModelo servicios : sd.GetNameAndId(1)) {
            process2(table2, servicios, font, false);
        }
        document.add(table2);


        //Close document
        document.close();
    }
    private void generateListaServicios() {
        try {
            File file = new File("results/ListaDeServicios.pdf");
            file.getParentFile().mkdirs();
            createPdf("results/ListaDeServicios.pdf", "Services List");
            openPDFFile("results/ListaDeServicios.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}