package com.example.testproject.controllers;

import com.example.testproject.data.ServiciosDatos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FormServices implements Initializable {
    ServiciosDatos serviciosDatos = new ServiciosDatos();

    @FXML
    private TextArea txtDescription;
    @FXML
    private ComboBox cmbStatus;
    @FXML
    private TextField txtCost;
    @FXML
    private Button btnReturn, btnImage, btnAddService;
    @FXML
    private VBox form_Service;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnReturn.setOnAction(handlerButtons);
        btnImage.setOnAction(handlerButtons);
        btnAddService.setOnAction(handlerButtons);
    }

    EventHandler<ActionEvent> handlerButtons = (event)-> {
        if (event.getSource() == btnReturn) {
            onMain();
        } else if(event.getSource() == btnImage) {
            selectImage();
        } else if(event.getSource() == btnAddService) {
            int status = 0;

            //status = 1 para activo, 2 para inactivo
            if (cmbStatus.getSelectionModel().getSelectedItem().equals("On")) {
                status = 1;
            } else if (cmbStatus.getSelectionModel().getSelectedItem().equals("Off")) {
                status = 2;
            }
            addService(txtDescription.getText().toString(), status, Double.parseDouble(txtCost.getText()),btnImage.getText().toString());
        }
    };

    private void addService(String desc, int status, double costo, String imagen) {
        try {
            if (serviciosDatos.insertarServicio(desc, status, costo, imagen)) {
                clearField();
                showMessage("Success", "Service " + desc + " was successfully created", Alert.AlertType.INFORMATION);
                onMain();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void selectImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Archivos de imagen", "*.jpg", "*.png", "*.gif")
        );
        Stage stage = (Stage) btnImage.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            btnImage.setText(selectedFile.getName());
        }
    }

    private void onMain() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/testproject/tableServices.fxml"));
        try{
            TableServices tableServices = new TableServices();
            loader.setController(tableServices);
            Parent root = loader.load();
            form_Service.getChildren().clear();
            form_Service.getChildren().add(root);

        }catch (IOException ex){
            throw  new RuntimeException(ex);
        }
    }

    public void setData(String descripcion, int status, String imagen, double costo,int IdServicio) {
        txtDescription.setText(descripcion);
        btnImage.setText(imagen);
        txtCost.setText(String.valueOf(costo));

        if (status == 1){
            cmbStatus.getSelectionModel().select(0);
        }else if (status ==2){
            cmbStatus.getSelectionModel().select(1);
        }

        btnAddService.setOnAction(event -> {
            int estatus = 0;

            if (cmbStatus.getValue().equals("On")) {
                estatus = 1;
            } else if (cmbStatus.getValue().equals("Off")) {
                estatus = 2;
            }

            try {
                serviciosDatos.editService(txtDescription.getText(), estatus, Double.parseDouble(txtCost.getText()), IdServicio);
                clearField();
                showMessage("Success", "Service was edited", Alert.AlertType.INFORMATION);
                onMain();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void clearField(){
        txtCost.setText("");
        txtDescription.setText("");
        btnImage.setText("");
        cmbStatus.setValue("On");
    }

    private void showMessage(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}