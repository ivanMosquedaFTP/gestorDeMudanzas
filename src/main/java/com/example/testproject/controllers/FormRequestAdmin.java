package com.example.testproject.controllers;

import com.example.testproject.data.ServiciosDatos;
import com.example.testproject.data.SolicitudesDatos;
import com.example.testproject.models.SolicitudesModelo;
import com.example.testproject.models.serviciosModelo;
import com.example.testproject.temporarydata.CacheSolicitud;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FormRequestAdmin implements Initializable {

    @FXML
    private TableView<SolicitudesModelo> tblRequest;
    @FXML
    private VBox form_TableR;
    @FXML
    private Button btnEditRequest;
    @FXML
    private TableColumn<SolicitudesModelo,String> col1,col2,col3,col4,col5,col6,col7,col8,col9,col10,col11,col12;

    private void onLoadTable() {
        widthTable();
        SolicitudesDatos solicitudesDatos = new SolicitudesDatos();
        tblRequest.setItems(solicitudesDatos.mostrarSolicitudes());
        System.out.println(solicitudesDatos.mostrarSolicitudes().get(0));
    }

    private void widthTable(){
        col1.prefWidthProperty().bind(tblRequest.widthProperty().divide(12));
        col2.prefWidthProperty().bind(tblRequest.widthProperty().divide(12));
        col3.prefWidthProperty().bind(tblRequest.widthProperty().divide(12));
        col4.prefWidthProperty().bind(tblRequest.widthProperty().divide(12));
        col5.prefWidthProperty().bind(tblRequest.widthProperty().divide(12));
        col6.prefWidthProperty().bind(tblRequest.widthProperty().divide(12));
        col7.prefWidthProperty().bind(tblRequest.widthProperty().divide(12));
        col8.prefWidthProperty().bind(tblRequest.widthProperty().divide(12));
        col9.prefWidthProperty().bind(tblRequest.widthProperty().divide(12));
        col10.prefWidthProperty().bind(tblRequest.widthProperty().divide(12));
        col11.prefWidthProperty().bind(tblRequest.widthProperty().divide(12));
        col12.prefWidthProperty().bind(tblRequest.widthProperty().divide(12));
    }

    EventHandler<ActionEvent> handlerButtons = (event) -> {
        if (event.getSource() == btnEditRequest) {
            onCreateRequest();
        }
    };

    private void onCreateRequest() {
        if (tblRequest.getSelectionModel().getSelectedItem() !=null){
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/testproject/formRequestValite.fxml"));

            try {
                FormRequestValite formRequestValite = new FormRequestValite();
                loader.setController(formRequestValite);
                formRequestValite.setId(CacheSolicitud.IdSolicitud = tblRequest.getSelectionModel().getSelectedItem().getIdSolicitud());

                Parent root = loader.load();

                form_TableR.getChildren().clear();
                form_TableR.getChildren().add(root);

            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }else{
           showMessage("Warning","Select a field", Alert.AlertType.INFORMATION);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onLoadTable();
        btnEditRequest.setOnAction(handlerButtons);
    }

    private void showMessage(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}