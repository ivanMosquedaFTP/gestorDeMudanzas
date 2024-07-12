package com.example.testproject.data;

import com.example.testproject.conexion.ConexionSQL;
import java.sql.*;

public class EmpleadoDatos {
    ConexionSQL conexionSQL = new ConexionSQL();

    public void insertarEmpleado(String nombre, String login, String contraseña, String telefono, String correo) throws SQLException {
        String url= conexionSQL.url;
        String username=conexionSQL.user;
        String password=conexionSQL.password;
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO Usuario(Nombre, Login, Contraseña, Telefono, Correo, IdTipo) VALUES (?, ?, ?, ?, ?, 3)";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, nombre);
            statement.setString(2, login);
            statement.setString(3, contraseña);
            statement.setString(4, telefono);
            statement.setString(5, correo);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new employee  was inserted successfully!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void editarEmpleado(String nombre, String login, String contraseña, String telefono, String correo, int idEmpleado) throws SQLException {
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "UPDATE Usuario Set nombre=?,login=?,contraseña=?,telefono=?,correo? where IdUsuario=?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, nombre);
            statement.setString(2, login);
            statement.setString(3, contraseña);
            statement.setString(4, telefono);
            statement.setString(5, correo);
            statement.setString(6, String.valueOf(idEmpleado));

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("An existing employee was updated successfully!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
    public void eliminarEmpleado( int idEmpleado)
            throws SQLException {

        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;
        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "DELETE FROM Usuario WHERE IdUsuario=?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, String.valueOf(idEmpleado));

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("A employee was deleted successfully!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}