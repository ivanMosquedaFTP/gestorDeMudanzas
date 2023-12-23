package com.example.testproject.data;

import com.example.testproject.conexion.ConexionSQL;
import com.example.testproject.models.MensajeModelo;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class RespuestaDatos {
    ConexionSQL conexionSQL = new ConexionSQL();

    private ObservableList<RespuestaDatos> listRespuesta = FXCollections.observableArrayList();

    public ObservableList<RespuestaDatos> getAnswer() throws SQLException {
        MensajeModelo mensaje = new MensajeModelo();

        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "";

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                mensaje.setMensaje(resultSet.getString(1));
                mensaje.setFechaEnvio(resultSet.getDate(2));

                System.out.println("Loading all messages for admins");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listRespuesta;
    }
}