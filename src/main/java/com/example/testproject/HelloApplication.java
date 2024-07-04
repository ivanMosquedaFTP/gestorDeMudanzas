package com.example.testproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class HelloApplication extends Application {
  @Override
  public void start(Stage stage) throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
    Scene scene = new Scene(fxmlLoader.load());
    scene.getStylesheets().add(getClass().getResource("/css/main.css").toString());
    stage.setTitle("Moving services.");
    stage.setScene(scene);
    HelloController helloController = fxmlLoader.getController();
    helloController.setStage(stage);
    stage.show();
    stage.setMaximized(true);
  }

  public static void main(String[] args) {
    launch();
  }
}
