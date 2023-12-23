module com.example.testproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.ikonli.javafx;
    requires java.sql;
    requires io;
    requires java.desktop;
    requires kernel;
    requires layout;
    requires org.slf4j;

    opens com.example.testproject to javafx.fxml;
    opens com.example.testproject.conexion;
    opens com.example.testproject.controllers;
    opens com.example.testproject.data;
    opens com.example.testproject.models;
    exports com.example.testproject;
}