package com.example.testproject.controllers;

import com.example.testproject.data.SolicitudesDatos;
import com.example.testproject.models.SolicitudesModelo;
import com.example.testproject.models.serviciosModelo;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.net.URL;
import java.util.ResourceBundle;

public class FormPending implements Initializable {

    @FXML
    private TableView<SolicitudesModelo> tblPendings;

    @FXML
    private TableColumn<serviciosModelo,String> col1,col2,col3,col4,col5,col6,col7;

    SolicitudesDatos obj = new SolicitudesDatos();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       onLoadTable();
    }

    private void onLoadTable() {
        onWidth();
        tblPendings.setItems(obj.showUserPendingRequests());
    }

    private void onWidth(){
        col1.prefWidthProperty().bind(tblPendings.widthProperty().divide(7));
        col2.prefWidthProperty().bind(tblPendings.widthProperty().divide(7));
        col3.prefWidthProperty().bind(tblPendings.widthProperty().divide(7));
        col4.prefWidthProperty().bind(tblPendings.widthProperty().divide(7));
        col5.prefWidthProperty().bind(tblPendings.widthProperty().divide(7));
        col6.prefWidthProperty().bind(tblPendings.widthProperty().divide(7));
        col7.prefWidthProperty().bind(tblPendings.widthProperty().divide(7));
    }
}