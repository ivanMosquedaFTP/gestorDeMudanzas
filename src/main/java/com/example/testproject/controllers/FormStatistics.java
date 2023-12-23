package com.example.testproject.controllers;

import com.example.testproject.data.ServiciosDatos;
import com.example.testproject.data.SolicitudesDatos;
import com.example.testproject.models.SolicitudesModelo;
import com.example.testproject.models.serviciosModelo;
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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@SuppressWarnings("unchecked")
public class FormStatistics implements Initializable {
    @FXML
    private VBox formStatisticsPie;

    SolicitudesDatos sc = new SolicitudesDatos();
    ServiciosDatos sd = new ServiciosDatos();
    @FXML
    private Button  btnWeek, btnMonth, btnYear, btnServices;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onLoadBarChartMonth();
        btnWeek.setOnAction(handlerButtons);
        btnMonth.setOnAction(handlerButtons);
        btnYear.setOnAction(handlerButtons);
        btnServices.setOnAction(handlerButtons);
    }

    EventHandler<ActionEvent> handlerButtons = (event) -> {
       if (event.getSource() == btnWeek) {
            generateReportSemanal();
        } else if (event.getSource() == btnMonth) {
            generateReportMensual();
        } else if (event.getSource() == btnYear) {
            generateReportAnual();
        } else if (event.getSource() == btnServices) {
           generateListaServicios();
        }
    };


    private void onLoadBarChartMonth() {
        NumberAxis xAxis = new NumberAxis();
        CategoryAxis yAxis = new CategoryAxis();
        BarChart<Number, String> bc = new BarChart<Number, String>(xAxis, yAxis);

        xAxis.setLabel("Ventas");
        xAxis.setTickLabelRotation(90);
        yAxis.setLabel("Reportes");
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Ventas por Reportes");

        series1.getData().add(new XYChart.Data(sc.SolicitudesSemanalesConteo(), "Reporte semanal"));
        series1.getData().add(new XYChart.Data(sc.SolicitudesMensualesConteo(), "Reporte mensual"));
        series1.getData().add(new XYChart.Data(sc.SolicitudesAnualesConteo(), "Reporte anual"));


        bc.getData().addAll(series1);
        formStatisticsPie.getChildren().add(bc);
    }

    private void generateReportAnual() {
        try {
            File file = new File("results/ReporteAnual.pdf");
            file.getParentFile().mkdirs();
            createPdf("results/ReporteAnual.pdf", "Reporte Anual", 3);
            openPDFFile("results/ReporteAnual.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateReportMensual() {
        try {
            File file = new File("results/ReporteMensual.pdf");
            file.getParentFile().mkdirs();
            createPdf("results/ReporteMensual.pdf", "Reporte Mensual", 2);
            openPDFFile("results/ReporteMensual.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateReportSemanal() {
        try {
            File file = new File("results/ReporteSemanal.pdf");
            file.getParentFile().mkdirs();
            createPdf("results/ReporteSemanal.pdf", "Reporte Semanal", 1);
            openPDFFile("results/ReporteSemanal.pdf");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void generateListaServicios() {
        try {
            File file = new File("results/ListaDeServicios.pdf");
            file.getParentFile().mkdirs();
            createPdf("results/ListaDeServicios.pdf", "Services List", 4);
            openPDFFile("results/ListaDeServicios.pdf");
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

    public void createPdf(String dest, String message, int caso) throws IOException {
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
        Table table1 = new Table(UnitValue.createPercentArray(new float[]{2, 3, 3, 3, 1, 3}))
                .useAllAvailableWidth();
        Table table2 = new Table(UnitValue.createPercentArray(new float[]{2, 2}))
                .useAllAvailableWidth();


        if (caso == 1) {
            process1(table1, null, bold, true);

            s= String.valueOf(sc.SolicitudesSemanalesConteo());
            document.add(new Paragraph("Weekly Sales: "+s).setBold());
            for (SolicitudesModelo solicitud : sc.SolicitudesSemanales()) {
                process1(table1, solicitud, font, false);
            }
            document.add(table1);

        } else if (caso == 2) {
            process1(table1, null, bold, true);

            s= String.valueOf(sc.SolicitudesMensualesConteo());
            document.add(new Paragraph("Monthly Sales: "+s).setBold());
            for (SolicitudesModelo solicitud : sc.SolicitudesMensuales()) {
                process1(table1, solicitud, font, false);
            }
            document.add(table1);

        } else if (caso == 3) {
            process1(table1, null, bold, true);
            s= String.valueOf(sc.SolicitudesAnualesConteo());
            document.add(new Paragraph("Yearly Sales: "+s).setBold());
            for (SolicitudesModelo solicitud : sc.SolicitudesAnuales()) {
                process1(table1, solicitud, font, false);
            }
            document.add(table1);
        }else if (caso == 4) {
            process2(table2, null, bold, true);
            for (serviciosModelo servicios : sd.GetNameAndId()) {
                process2(table2, servicios, font, false);
            }
            document.add(table2);
        }

        //Close document
        document.close();
    }

    public void process1(Table table, SolicitudesModelo solicitud, PdfFont font, boolean isHeader) {

        if (isHeader) {
            table.addHeaderCell(new Cell().add(new Paragraph("ID SOLICITUD.").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("ID SERVICIO").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("FECHA").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("OBSERVACIONES").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("DESTINOS").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("COSTO").setFont(font)));
        } else {
            table.addCell(new Cell().add(new Paragraph(solicitud.getIdSolicitud() + "").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(solicitud.getIdServicio() + "").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(solicitud.getFechaCreacion() + "").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(solicitud.getObservaciones() + "").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(solicitud.getDestino() + "").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(solicitud.getCostoFinal() + "").setFont(font)));
        }
    }

    public void process2(Table table, serviciosModelo servicios, PdfFont font, boolean isHeader) {

        if (isHeader) {
            table.addHeaderCell(new Cell().add(new Paragraph("ID SOLICITUD.").setFont(font)));
            table.addHeaderCell(new Cell().add(new Paragraph("ID SERVICIO").setFont(font)));
        } else {
            table.addCell(new Cell().add(new Paragraph(servicios.getIdServicio() + "").setFont(font)));
            table.addCell(new Cell().add(new Paragraph(servicios.getDescripcion() + "").setFont(font)));
        }
    }
}