package com.example.testproject.controllers;

import com.example.testproject.data.SolicitudesDatos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FormRequestValite implements Initializable {
    @FXML
    private Button btnReturn,btnSave;

    @FXML
    private VBox container_Request;

    @FXML
    private TextArea txtObservations;

    @FXML
    private TextField txtCostA,txtCostF;

    @FXML
    private int id;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnReturn.setOnAction(handlerButtons);
        btnSave.setOnAction(handlerButtons);
    }

    EventHandler<ActionEvent> handlerButtons = (event) -> {
        if (event.getSource() == btnReturn) {
            onTableRequest();
        } else if (event.getSource() == btnSave) {
            saveRequest();
        }
    };

    private void saveRequest() {
        if (!(txtObservations.getText().trim().isEmpty() || txtCostA.getText().trim().isEmpty() || txtCostF.getText().trim().isEmpty())){
            SolicitudesDatos obj = new SolicitudesDatos();
            try {
                if (obj.adminValidateRequest(id, txtObservations.getText().trim(), Double.parseDouble(txtCostA.getText().trim()), Double.parseDouble(txtCostF.getText().trim()))) {
                    showMessage("Error", "Request has not been validated", Alert.AlertType.ERROR);
                } else {
                    showMessage("Success", "Request is now validated!", Alert.AlertType.INFORMATION);
                    clearFields();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            onTableRequest();
        }else{
            showMessage("Warning","Empty Fields", Alert.AlertType.INFORMATION);
        }
    }

    private void onTableRequest() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/testproject/formRequestAdmin.fxml"));
        try{
            FormRequestAdmin formRequestAdmin = new FormRequestAdmin();
            loader.setController(formRequestAdmin);

            Parent root = loader.load();

            container_Request.getChildren().clear();
            container_Request.getChildren().add(root);
        }catch (IOException ex){
            throw  new RuntimeException(ex);
        }
    }

    private void showMessage(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }

    private void clearFields(){
        txtObservations.setText("");
        txtCostA.setText("");
        txtCostF.setText("");
    }

    public void setId(int i) {
        id = i;
    }
}