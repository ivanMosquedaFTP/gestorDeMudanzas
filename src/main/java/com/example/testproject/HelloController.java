package com.example.testproject;

import com.example.testproject.data.UsuariosDatos;
import com.example.testproject.temporarydata.CacheUsuarios;
import com.example.testproject.controllers.FormDash;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class HelloController implements Initializable {
  Stage stage;
  @FXML
  private FontIcon R_icon, L_icon, L_iconPass, R_iconPass;
  @FXML
  private TextField txtLogin, R_txtUsername, txtEmail, txtPhone, R_txtLoginUsername;
  @FXML
  private TabPane tabPane;
  @FXML
  private Tab tab2;
  @FXML
  private PasswordField txtPassword, R_txtPassword;
  @FXML
  private StackPane main_Stackpane, show_PassStackPane, R_show_PassStackPane;
  @FXML
  private ImageView imgLogo, imgLoad;
  @FXML
  private VBox wrapper_title, w_Login, sub_w_Login, R_wrapInfo, contentR;
  @FXML
  private Label lblDesc, lblTitle, R_lblInformation, L_lblInformation;
  @FXML
  private Button B_btnSignIn, btnShowPass, R_btnShowPass, btnGetStart, L_btnSignIn, L_btnSignUp;

  private PauseTransition pause = new PauseTransition(Duration.seconds(3));
  private Image img1 = new Image(getClass().getResource("/img/load.png").toString());
  private Image img2 = new Image(getClass().getResource("/img/logo.png").toString());

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    onLoadControllers();
  }

  private void onLoadControllers() {
    imgLogo.setImage(img1);
    imgLoad.setImage(img2);
    main_Stackpane.getChildren().remove(w_Login);
    B_btnSignIn.setOnAction(handlerButtons);
    btnGetStart.setOnAction(handlerButtons);
    L_btnSignIn.setOnAction(handlerButtons);
    L_btnSignUp.setOnAction(handlerButtons);
  }

  EventHandler<ActionEvent> handlerButtons = (event) -> {
    if (event.getSource() == B_btnSignIn) {
      onWindowLogin();
    } else if (event.getSource() == btnGetStart) {
      onWindowRegister();
    } else if (event.getSource() == L_btnSignIn) {
      onStageMain();
    } else if (event.getSource() == L_btnSignUp) {
      createAccount();
    }
  };

  private void createAccount() {
    if (txtEmail.getText().trim().isEmpty() || R_txtUsername.getText().trim().isEmpty()
        || R_txtPassword.getText().trim().isEmpty() || txtPhone.getText().trim().isEmpty()
        || R_txtLoginUsername.getText().trim().isEmpty()) {
      R_wrapInfo.setVisible(true);
      showError(R_lblInformation, R_icon, "Empty fields :|");
      txtEmail.requestFocus();
    } else {
      R_wrapInfo.setVisible(true);
      pause.setOnFinished(event -> {
        R_wrapInfo.setVisible(false);
      });

      UsuariosDatos usuariosDatos = new UsuariosDatos();
      try {
        usuariosDatos.insertarUsuario(R_txtUsername.getText().trim(), R_txtLoginUsername.getText().trim(),
            R_txtPassword.getText().trim(), txtPhone.getText().trim(), txtEmail.getText().trim());
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }

      R_wrapInfo.setVisible(true);
      showCheck(R_lblInformation, R_icon, "Created successfully :)");
      pause.play();
      clearFieldR();
    }
  }

  private void clearFielL() {
    txtLogin.setText("");
    txtPassword.setText("");
  }

  private void clearFieldR() {
    txtEmail.setText("");
    R_txtUsername.setText("");
    R_txtPassword.setText("");
    R_txtLoginUsername.setText("");
    txtPhone.setText("");
  }

  private void onWindowRegister() {
    main_Stackpane.getChildren().add(w_Login);
    tabPane.getSelectionModel().select(1);
  }

  private void onWindowLogin() {
    main_Stackpane.getChildren().add(w_Login);
    tabPane.getSelectionModel().select(0);
    tab2.setContent(null);
  }

  private void onStageMain() {
    int userId = 0, userType = 0;

    if (!(txtLogin.getText().trim().isEmpty() || txtPassword.getText().trim().isEmpty())) {
      FXMLLoader loader = new FXMLLoader(getClass().getResource("formDash.fxml"));
      UsuariosDatos instance = new UsuariosDatos();
      CacheUsuarios staticInfo = new CacheUsuarios();

      if (instance.validateLogin(txtLogin.getText().trim(), txtPassword.getText().trim())) {
        instance.getUserInfo(txtLogin.getText().trim(), txtPassword.getText().trim());

        // =================================================================
        // LLAMA A LA VISTA DE USUARIO EN CASO DE IdTipo = 2
        // =================================================================
        if (CacheUsuarios.IdTipo == 2) {
          // CARGAR AQUI LA VISTA DE USUARIO GENERAL
          System.out.println("Tipo de usuario general, crgando vista de usuario general");
          try {
            FormDash formDash = new FormDash();
            loader.setController(formDash);

            Parent root = loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/main_dash.css").toString());
            stage.setScene(scene);
            formDash.setStage(stage);
            formDash.validateUser(CacheUsuarios.IdTipo);
            clearFielL();
            stage.show();
            stage.setMaximized(true);
            stage.setTitle("Content");
            this.stage.close();
          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }

          // EN CASO DE IdTipo = 1 || IdTipo == 3, ENTONCES DEPLOY VISTA ADMIN:
        } else if (staticInfo.IdTipo == 1 || staticInfo.IdTipo == 3) {

          try {
            FormDash formDash = new FormDash();
            loader.setController(formDash);

            Parent root = loader.load();

            Stage stage = new Stage();
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/css/main_dash.css").toString());
            stage.setScene(scene);
            formDash.setStage(stage);
            formDash.validateUser(CacheUsuarios.IdTipo);
            clearFielL();
            stage.show();
            stage.setMaximized(true);
            stage.setTitle("Content");
            this.stage.close();

          } catch (IOException ex) {
            throw new RuntimeException(ex);
          }
        }

        System.out.println("IdUsuario: " + staticInfo.IdUsuario + " user type: " + staticInfo.IdTipo);
      } else {
        showError(L_lblInformation, L_icon, "Incorrect user or password");
        txtLogin.requestFocus();
      }
    } else {
      showError(L_lblInformation, L_icon, "Empty fields :|");
      txtLogin.requestFocus();
    }
  }

  public void setStage(Stage primaryStage) {
    stage = primaryStage;
    stage.widthProperty().addListener((observable, oldValue, newValue) -> {
      if (newValue.doubleValue() < 1500) {
        imgLoad.setFitWidth(520);
        imgLoad.setFitHeight(520);
        wrapper_title.setStyle("-fx-padding:0;");
        lblTitle.setStyle("-fx-font-size:52px;");
        lblDesc.setStyle("-fx-padding:0;");
      } else {
        imgLoad.setFitWidth(820);
        imgLoad.setFitHeight(820);
        wrapper_title.setStyle("-fx-padding:100px;");
        lblTitle.setStyle("-fx-font-size:82px; -fx-line-spacing: -0.4em;");
        lblDesc.setStyle("-fx-padding:0.31333em 0px;");
      }
    });
  }

  @FXML
  private void closeLogin(MouseEvent event) {
    if (!sub_w_Login.getBoundsInParent().contains(event.getX(), event.getY())) {
      main_Stackpane.getChildren().remove(w_Login);
    }
  }

  @FXML
  private void showPassword(PasswordField pass, StackPane pane, Button btn, FontIcon icon) {
    icon.setIconLiteral("bi-eye-fill");
    String password = pass.getText();
    TextField textField = new TextField();
    textField.setText(password);
    textField.setPromptText("Password");
    textField.getStyleClass().addAll(pass.getStyleClass());
    pane.getChildren().remove(pass);
    pane.getChildren().add(0, textField);
    textField.setOnKeyPressed(event -> {
      pass.setText(textField.getText());
    });
    btn.setOnAction(e -> hidePassword(pass, pane, btn, icon));
  }

  @FXML
  private void hidePassword(PasswordField pass, StackPane pane, Button btn, FontIcon icon) {
    icon.setIconLiteral("bi-eye-slash-fill");
    TextField textField = (TextField) pane.getChildrenUnmodifiable().get(0);
    String password = textField.getText();
    pass.setText(password);
    pass.getStyleClass().addAll(textField.getStyleClass());
    pane.getChildren().remove(textField);
    pane.getChildren().add(0, pass);
    pass.setOnKeyPressed(event -> {
      textField.setText(pass.getText());
    });
    btn.setOnAction(e -> showPassword(pass, pane, btn, icon));
  }

  @FXML
  private void onShowPass() {
    showPassword(txtPassword, show_PassStackPane, btnShowPass, L_iconPass);
  }

  @FXML
  private void onR_ShowPass() {
    showPassword(R_txtPassword, R_show_PassStackPane, R_btnShowPass, R_iconPass);
  }

  private void showError(Label lbError, FontIcon icon, String text) {
    lbError.getStyleClass().remove("lbl-Valid");
    lbError.setText(text);
    lbError.getStyleClass().add("lbl-Error");
    icon.setIconLiteral("bi-exclamation-circle-fill");
    icon.setIconColor(Paint.valueOf("#FF2929"));
  }

  private void showCheck(Label lblV, FontIcon icon, String text) {
    lblV.setText(text);
    lblV.getStyleClass().add("lbl-Valid");
    icon.setIconLiteral("bi-check-circle-fill");
    icon.setIconColor(Paint.valueOf("#00D783"));
  }

  public void changeTab1() {
    if (tab2 != null) {
      tab2.setContent(null);
    }
  }

  public void changeTab2() {
    tab2.setContent(contentR);
  }
}
