package com.example.testproject.controllers;

import com.example.testproject.data.InfoContactoDatos;
import com.example.testproject.temporarydata.CacheInfoEmpresa;
import com.example.testproject.temporarydata.CacheUsuarios;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class FormHome implements Initializable {
    @FXML
    private ImageView imgCard_1;

    @FXML
    private Label lblEmail,lblMobil, lblPhone, lblSocial,lblName;

    private Image img1 = new Image(getClass().getResource("/img/w_User.png").toString());

    InfoContactoDatos obj = new InfoContactoDatos();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        imgCard_1.setImage(img1);
        lblName.setText("Welcome! "+CacheUsuarios.Nombre);
        try {
            loadInformation();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void loadInformation() throws SQLException {
        obj.displayInfo();
        lblEmail.setText(CacheInfoEmpresa.Email);
        lblMobil.setText(CacheInfoEmpresa.TelefonoMovil);
        lblPhone.setText(CacheInfoEmpresa.TelefonoOficina);
        lblSocial.setText(CacheInfoEmpresa.RedSocial);
    }
}