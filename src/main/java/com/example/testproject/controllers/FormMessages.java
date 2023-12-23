package com.example.testproject.controllers;

import com.example.testproject.data.MensajesDatos;
import com.example.testproject.temporarydata.CacheUsuarios;
import com.example.testproject.models.MensajeModelo;
import com.example.testproject.models.UsuariosModelo;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FormMessages implements Initializable {
    @FXML
    private VBox content_Users,content_Message,content_ShowMessages;

    @FXML
    private ToggleButton btnHide, btnNew;

    private ToggleButton lastSelectedButton;
    @FXML
    private Button btnSend,btnHidden;

    @FXML
    private Label lbl_NameUser;

    @FXML
    private TextArea txtMessage;

    @FXML
    private GridPane body_Messsage;

    private List<HBox> selectedMessages = new ArrayList<>();

    MensajesDatos model = new MensajesDatos();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onLoadUsers();
        btnSend.setOnAction(handlerButtons);
        btnHidden.setOnAction(handlerButtons);
        btnNew.setOnAction(handlerButtons);
        btnHide.setOnAction(handlerButtons);
    }

    EventHandler<ActionEvent> handlerButtons = (event) -> {
        if (event.getSource() == btnSend) {
            onSendMessages();
        } else if (event.getSource() == btnHidden) {
            onHiddenMessages();
        } else if (event.getSource() == btnNew) {
            btnNew.setSelected(true);
            btnHide.setSelected(false);
            onLoadUsers();
        } else if (event.getSource() == btnHide) {
            btnHide.setSelected(true);
            btnNew.setSelected(false);
            onLoadUsersHidden();
        }
    };

    private void onLoadUsersHidden() {

    }

    private void onHiddenMessages() {
        if (selectedMessages.isEmpty()) {
            System.out.println("No se selecciono");
        } else {
            List<String> selectedTexts = new ArrayList<>();
            for (HBox selectedMessage : selectedMessages) {
                TextArea lblReply = (TextArea) selectedMessage.getChildren().get(0);
                String text = lblReply.getText();
                selectedTexts.add(text);
            }
        }
    }

    private void onSendMessages() {
        if (txtMessage.getText().isEmpty()){
            txtMessage.requestFocus();
        }else{
            content_ShowMessages.getChildren().add(createMessage(txtMessage.getText()));
            txtMessage.setText("");
        }
    }

    private void onShowMessages(String name) {
        body_Messsage.getColumnConstraints().get(0).setPercentWidth(30);
        body_Messsage.getColumnConstraints().get(1).setPercentWidth(70);
        content_Message.setVisible(true);
        lbl_NameUser.setText(name);
        content_ShowMessages.getChildren().clear();
        content_ShowMessages.getChildren().add(loadMessages(model.getUserTypeByMessageId(CacheUsuarios.IdUsuario)));
    }

    private HBox loadMessages(String message){
        HBox contentMessage = new HBox();
        contentMessage.setAlignment(Pos.CENTER_RIGHT);
        TextArea lblMessage = new TextArea(message);
        lblMessage.setPrefHeight(50);
        lblMessage.setPrefWidth(400);
        lblMessage.setEditable(false);
        lblMessage.setWrapText(true);
        lblMessage.getStyleClass().add("lbl-Reply");
        contentMessage.getChildren().add(lblMessage);

        return contentMessage;
    }

    private void onLoadUsers() {
        content_Users.getChildren().clear();
        MensajesDatos mensajesDatos = new MensajesDatos();

        for (MensajeModelo mensajeModelo: mensajesDatos.showAdmins()) {
            content_Users.getChildren().add(createUser(mensajeModelo.getLogin(),mensajeModelo.getMensaje()));
        }
    }

    private HBox createMessage(String message) {
        HBox contentMessage = new HBox();
        contentMessage.setAlignment(Pos.CENTER_RIGHT);
        TextArea lblReply = new TextArea(message);
        lblReply.setEditable(false);
        lblReply.setWrapText(true);
        lblReply.setPrefHeight(50);
        lblReply.setPrefWidth(400);
        lblReply.getStyleClass().add("lbl-Reply");
        lblReply.setOnMouseClicked(event -> {
            if (lblReply.getStyleClass().contains("lbl-Reply-Selected")){
                lblReply.getStyleClass().remove("lbl-Reply-Selected");
                selectedMessages.remove(contentMessage);
            }else {
                lblReply.getStyleClass().add("lbl-Reply-Selected");
                selectedMessages.add(contentMessage);
            }
        });

        try {
            model.usuarioEnviaMensaje(CacheUsuarios.IdUsuario, lblReply.getText().trim(), 2);
            lblReply.setText(txtMessage.getText());

        } catch (SQLException e) {
            e.printStackTrace();
        }

        contentMessage.getChildren().add(lblReply);
        return contentMessage;
    }


    private ToggleButton createUser(String userName,String message) {
        ToggleButton toggleButton = new ToggleButton();
        toggleButton.setMaxWidth(Double.MAX_VALUE);
        toggleButton.getStyleClass().add("btnUser");
        toggleButton.setAlignment(javafx.geometry.Pos.CENTER);

        HBox hbox = new HBox();

        HBox imageBox = new HBox();
        imageBox.getStyleClass().add("lbl-Image-User");
        imageBox.setAlignment(javafx.geometry.Pos.CENTER);
        FontIcon iconLabel = new FontIcon();
        iconLabel.setIconLiteral("bi-person-fill");
        iconLabel.setIconColor(Paint.valueOf("#696969"));
        iconLabel.setIconSize(20);
        imageBox.getChildren().add(iconLabel);

        VBox messageBox = new VBox();
        Label userLabel = new Label(userName);
        userLabel.getStyleClass().add("lbl-User");
        Label messageLabel = new Label(message);
        messageLabel.getStyleClass().add("lbl-text-Message");
        messageBox.getChildren().addAll(userLabel,messageLabel);

        hbox.getChildren().addAll(imageBox, messageBox);
        toggleButton.setGraphic(hbox);


        toggleButton.setOnAction(event -> {
            if (toggleButton.isSelected()) {
                // Deseleccionar el botón anterior
                if (lastSelectedButton != null && lastSelectedButton != toggleButton) {
                    lastSelectedButton.setSelected(false);
                }
                // Actualizar el último botón seleccionado
                lastSelectedButton = toggleButton;
                onShowMessages(userName);
            } else {
                // Si el botón actual se deselecciona, establecer lastSelectedButton a null
                lastSelectedButton = null;
            }
        });

        return toggleButton;
    }
}