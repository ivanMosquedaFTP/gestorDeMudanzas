package com.example.testproject.controllers;

import com.example.testproject.data.ServiciosDatos;
import com.example.testproject.models.serviciosModelo;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class TableServices implements Initializable {

    @FXML
    private Button btnAddService, btnEditService, btnDeleteService;
    @FXML
    private VBox form_Table;
    @FXML
    private Image img;
    @FXML
    private ImageView imgColumn;
    @FXML
    private TableView<serviciosModelo> tblService;
    @FXML
    private TableColumn<serviciosModelo,String> col1,col2,col3,col4,col5,col6;

    ServiciosDatos objServ = new ServiciosDatos();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
            btnAddService.setOnAction(handlerButtons);
            btnDeleteService.setOnAction(handlerButtons);
            btnEditService.setOnAction(handlerButtons);
            onLoadTable();
    }

    private void widthTable(){
        col1.prefWidthProperty().bind(tblService.widthProperty().divide(6));
        col2.prefWidthProperty().bind(tblService.widthProperty().divide(6));
        col3.prefWidthProperty().bind(tblService.widthProperty().divide(6));
        col4.prefWidthProperty().bind(tblService.widthProperty().divide(6));
        col5.prefWidthProperty().bind(tblService.widthProperty().divide(6));
        col6.prefWidthProperty().bind(tblService.widthProperty().divide(6));
    }

    private void onLoadTable() {
        widthTable();
        ServiciosDatos serviciosDatos = new ServiciosDatos();
        tblService.setItems((ObservableList<serviciosModelo>) serviciosDatos.mostrarServicio());
    }

    EventHandler<ActionEvent> handlerButtons = (event) -> {
        if (event.getSource() == btnAddService) {
           onCreateService();
        } else if (event.getSource() == btnDeleteService) {
            deleteService();
        } else if (event.getSource() == btnEditService) {
            editService();
        }
    };

    private void onCreateService() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/testproject/formServices.fxml"));
        try {
            FormServices formServices = new FormServices();
            loader.setController(formServices);

            Parent root = loader.load();
            form_Table.getChildren().clear();
            form_Table.getChildren().add(root);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void deleteService() {
        if (tblService.getSelectionModel().getSelectedIndex() > 0) {
            try {
                if (objServ.deleteService(tblService.getSelectionModel().getSelectedItem().getDescripcion().toString())) {
                    showMessage("Error", "Service " + tblService.getSelectionModel().getSelectedItem().getDescripcion().toString() + " was not deleted", Alert.AlertType.ERROR);
                } else {
                    showMessage("Success", "Service " + tblService.getSelectionModel().getSelectedItem().getDescripcion().toString() + " deleted successfully", Alert.AlertType.INFORMATION);
                    onLoadTable();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void editService() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/testproject/formServices.fxml"));

        if (tblService.getSelectionModel().getSelectedItem() !=null){
            try {
                FormServices formServices = new FormServices();
                loader.setController(formServices);

                Parent root = loader.load();

                formServices.setData(tblService.getSelectionModel().getSelectedItem().getDescripcion(),
                        tblService.getSelectionModel().getSelectedItem().getStatus(),
                        tblService.getSelectionModel().getSelectedItem().getImagen(),
                        tblService.getSelectionModel().getSelectedItem().getCosto(),
                        tblService.getSelectionModel().getSelectedItem().getIdServicio());
                form_Table.getChildren().clear();
                form_Table.getChildren().add(root);
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }else{
            showMessage("Warning","Select a field", Alert.AlertType.INFORMATION);
        }
    }

    private void showMessage(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}