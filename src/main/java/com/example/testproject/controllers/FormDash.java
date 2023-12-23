package com.example.testproject.controllers;

import com.example.testproject.HelloController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class FormDash implements Initializable {

    Stage stage;
    @FXML
    private ToggleButton btnDashBoard,btnHome,btnMoving, btnServices, btnMessages, btnShowInfo, btnRegisterE, btnLogOut,btnRequest,btnPending;

    @FXML
    private ImageView imgLogo;

    @FXML
    private Label lbl_Tab_Title;
    @FXML
    private VBox contentMain,container_Dash;

    private Image img1 = new Image(getClass().getResource("/img/load.png").toString());

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        onLoadControllers();
    }

    private void onLoadControllers() {
        imgLogo.setImage(img1);
        btnDashBoard.setOnAction(handlerButtons);
        btnHome.setOnAction(handlerButtons);
        btnMoving.setOnAction(handlerButtons);
        btnRequest.setOnAction(handlerButtons);
        btnServices.setOnAction(handlerButtons);
        btnMessages.setOnAction(handlerButtons);
        btnRegisterE.setOnAction(handlerButtons);
        btnShowInfo.setOnAction(handlerButtons);
        btnLogOut.setOnAction(handlerButtons);
        btnPending.setOnAction(handlerButtons);
    }

    EventHandler<ActionEvent> handlerButtons = (event) -> {
        if (event.getSource() == btnDashBoard) {
            clearSelect();
            btnDashBoard.setSelected(true);
            onMainAdmin();
        } else if (event.getSource() == btnHome) {
            clearSelect();
            btnHome.setSelected(true);
            onMainUser();
        } else if (event.getSource() == btnMoving) {
            clearSelect();
            btnMoving.setSelected(true);
            onMovingRequest();
        } else if (event.getSource() == btnServices) {
            clearSelect();
            btnServices.setSelected(true);
            onCreateService();
        } else if (event.getSource() == btnMessages) {
            clearSelect();
            btnMessages.setSelected(true);
            onShowMessages();
        } else if (event.getSource() == btnShowInfo) {
            clearSelect();
            btnShowInfo.setSelected(true);
            onEditInfo();
        } else if (event.getSource() == btnRegisterE) {
            clearSelect();
            btnRegisterE.setSelected(true);
            onRegisterEmployee();
        } else if (event.getSource() == btnLogOut) {
            onShowLoad();
        } else if (event.getSource() == btnRequest) {
            clearSelect();
            btnRequest.setSelected(true);
            onRequestAdmin();
        } else if (event.getSource() == btnPending) {
            clearSelect();
            btnPending.setSelected(true);
            onPendingRequest();
        }
    };

    private void onPendingRequest() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/testproject/formPending.fxml"));
        try {
            FormPending formPending = new FormPending();
            loader.setController(formPending);

            Parent root = loader.load();
            contentMain.getStylesheets().add(getClass().getResource("/css/tableServices.css").toString());

            lbl_Tab_Title.setText("Pending Request");
            contentMain.getChildren().clear();
            contentMain.getChildren().add(root);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void onRequestAdmin() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/testproject/formRequestAdmin.fxml"));
        try {
            FormRequestAdmin formRequestAdmin = new FormRequestAdmin();
            loader.setController(formRequestAdmin);

            Parent root = loader.load();
            contentMain.getStylesheets().add(getClass().getResource("/css/tableServices.css").toString());

            lbl_Tab_Title.setText("Request Admin");
            contentMain.getChildren().clear();
            contentMain.getChildren().add(root);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void clearSelect() {
        btnDashBoard.setSelected(false);
        btnHome.setSelected(false);
        btnMoving.setSelected(false);
        btnServices.setSelected(false);
        btnMessages.setSelected(false);
        btnShowInfo.setSelected(false);
        btnRegisterE.setSelected(false);
        btnRequest.setSelected(false);
        btnPending.setSelected(false);
    }

    private void onShowLoad() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/testproject/hello-view.fxml"));

        try {
            Parent root = loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/main.css").toString());
            stage.setScene(scene);
            stage.setTitle("Project");
            HelloController helloController = loader.getController();
            helloController.setStage(stage);
            stage.show();

            stage.setMaximized(true);
            this.stage.close();

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void onMainAdmin() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/testproject/formStatistics.fxml"));
        try {
            FormStatistics formStatistics = new FormStatistics();
            loader.setController(formStatistics);

            Parent root = loader.load();
            contentMain.getStylesheets().add(getClass().getResource("/css/formStatistics.css").toString());

            lbl_Tab_Title.setText("DashBoard");
            contentMain.getChildren().clear();
            contentMain.getChildren().add(root);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void onMainUser() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/testproject/formHome.fxml"));

        try {
            FormHome formHome = new FormHome();
            loader.setController(formHome);

            Parent root = loader.load();
            contentMain.getStylesheets().add(getClass().getResource("/css/formHome.css").toString());

            lbl_Tab_Title.setText("Home");
            contentMain.getChildren().clear();
            contentMain.getChildren().add(root);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void onCreateService() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/testproject/tableServices.fxml"));

        try {
            TableServices tableServices = new TableServices();
            loader.setController(tableServices);

            Parent root = loader.load();
            contentMain.getStylesheets().add(getClass().getResource("/css/tableServices.css").toString());

            lbl_Tab_Title.setText("Services");
            contentMain.getChildren().clear();
            contentMain.getChildren().add(root);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void onMovingRequest() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/testproject/formMovingApplication.fxml"));
        try {
            FormMovingApplication formMovingApplication = new FormMovingApplication();
            loader.setController(formMovingApplication);
            Parent root = loader.load();

            lbl_Tab_Title.setText("Moving Application");
            contentMain.getChildren().clear();
            contentMain.getChildren().add(root);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void onShowMessages() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/testproject/formMessages.fxml"));
        try {
            FormMessages formMessages = new FormMessages();
            loader.setController(formMessages);

            Parent root = loader.load();
            contentMain.getStylesheets().add(getClass().getResource("/css/formMessages.css").toString());

            lbl_Tab_Title.setText("Messages");
            contentMain.getChildren().clear();
            contentMain.getChildren().add(root);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void onEditInfo(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/testproject/formCompanyInfo.fxml"));

        try {
            FormCompanyInfo formCompanyInfo = new FormCompanyInfo();
            loader.setController(formCompanyInfo);

            Parent root = loader.load();
            lbl_Tab_Title.setText("Company Information");
            contentMain.getChildren().clear();
            contentMain.getChildren().add(root);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    private void onRegisterEmployee() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/testproject/formRegisterEmployee.fxml"));

        try {
            FormRegisterEmployee formRegisterEmployee = new FormRegisterEmployee();
            loader.setController(formRegisterEmployee);

            Parent root = loader.load();
            lbl_Tab_Title.setText("Register Employee");
            contentMain.getChildren().clear();
            contentMain.getChildren().add(root);

        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public void setStage(Stage secundaryStage){
        stage = secundaryStage;
    }

    public void validateUser(int idTipo) {
        if(idTipo == 1  || idTipo == 3){
           container_Dash.getChildren().removeAll(btnHome,btnMoving,btnPending);
            onMainAdmin();
        }else{
            container_Dash.getChildren().removeAll(btnDashBoard,btnRequest,btnServices,btnShowInfo,btnRegisterE);
           onMainUser();
        }
    }
}