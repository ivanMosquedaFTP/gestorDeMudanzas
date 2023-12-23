package com.example.testproject.controllers;

import com.example.testproject.data.InfoContactoDatos;
import com.example.testproject.temporarydata.CacheInfoEmpresa;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FormCompanyInfo implements Initializable {

    @FXML
    private TextField txtEmail,txtMobilPhone,txtOfficePhone,txtMedia;

    @FXML
    private Button btnSave;

    InfoContactoDatos obj = new InfoContactoDatos();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSave.setOnAction(handlerButtons);
        loadInfoCompany();
    }

    EventHandler<ActionEvent> handlerButtons = (event)-> {
        if (event.getSource() == btnSave) {
            updateInfo();
        }
    };

    private void updateInfo() {
        if (txtEmail.getText().trim() == null || txtMobilPhone.getText().trim() == null || txtOfficePhone.getText().trim() == null || txtMedia.getText().trim() == null) {
            showMessage("Error", "Please enter data", Alert.AlertType.ERROR);
        } else if (txtEmail.getText().trim() != null && txtMobilPhone.getText().trim() != null && txtOfficePhone.getText().trim() != null && txtMedia.getText().trim() != null) {
            try {
                if (obj.editInfo(txtEmail.getText().trim(), txtMobilPhone.getText().trim(), txtOfficePhone.getText().trim(), txtMedia.getText().trim())) {
                    showMessage("Error", "The information has not been updated", Alert.AlertType.ERROR);
                } else {
                    showMessage("Success", "The information has been updated successfully", Alert.AlertType.INFORMATION);
                    clearFields();
                    loadInfoCompany();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void loadInfoCompany(){
        try {
        obj.displayInfo();
        txtEmail.setText(CacheInfoEmpresa.Email);
        txtOfficePhone.setText(CacheInfoEmpresa.TelefonoOficina);
        txtMobilPhone.setText(CacheInfoEmpresa.TelefonoMovil);
        txtMedia.setText(CacheInfoEmpresa.RedSocial);
        }catch (SQLException e) {
                e.printStackTrace();
        }
    }

    private void clearFields(){
        txtMedia.setText("");
        txtOfficePhone.setText("");
        txtMobilPhone.setText("");
        txtEmail.setText("");
    }

    private void showMessage(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}