package com.example.testproject.controllers;


import com.example.testproject.data.UsuariosDatos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class FormRegisterEmployee implements Initializable {

    @FXML
    private TextField txtLogin,txtName,txtPhone,txtEmail;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private Button btnSave;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        btnSave.setOnAction(handlerButtons);
    }

    EventHandler<ActionEvent> handlerButtons = (event)-> {
        if (event.getSource() == btnSave) {
            createEmployee();
        }
    };

    private void createEmployee() {
        UsuariosDatos obj = new UsuariosDatos();
        if (!(txtEmail.getText().trim().isEmpty() || txtName.getText().trim().isEmpty() || txtLogin.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty())){
            try {
                    obj.insertarEmpleado(txtName.getText().trim(),
                                 txtLogin.getText().trim(),
                                 txtPassword.getText().trim(),
                                 txtPhone.getText().trim(),
                                 txtEmail.getText().trim());
                                     clearField();
                showMessage("Success", "The employee has been successfully registered", Alert.AlertType.INFORMATION);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else{
            showMessage("Warning","Empty Fields", Alert.AlertType.INFORMATION);
        }
    }

    private void clearField() {
      txtEmail.setText("");
      txtName.setText("");
      txtLogin.setText("");
      txtPhone.setText("");
      txtPassword.setText("");
    }

    private void showMessage(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.show();
    }
}