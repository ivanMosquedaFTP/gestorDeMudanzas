package com.example.testproject.data;

import com.example.testproject.temporarydata.CacheUsuarios;
import com.example.testproject.conexion.ConexionSQL;

import java.sql.*;

public class UsuariosDatos {
    ConexionSQL conexionSQL = new ConexionSQL();

    public void insertarUsuario(String nombre, String login, String contraseña, String telefono, String correo)
            throws SQLException {
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;
        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "INSERT INTO Usuario(Nombre, Login, Contraseña, Telefono, Correo, IdTipo) VALUES (?, ?, ?, ?, ?, 2)";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, nombre);
            statement.setString(2, login);
            statement.setString(3, contraseña);
            statement.setString(4, telefono);
            statement.setString(5, correo);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new user was inserted successfully!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void insertarEmpleado(String nombre, String login, String contraseña, String telefono, String correo) throws SQLException {
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;
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
                System.out.println("A new employee was inserted successfully!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public boolean validateLogin(String username, String password) {
        boolean flag = false;

        String url = conexionSQL.url;
        String dbUsername = conexionSQL.user;
        String dbPassword = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {

            String sql = "SELECT COUNT(*) FROM Usuario WHERE Login = ? AND Contraseña = ?";

            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();

            //si query > 0 entonces el usuario existe
            if (resultSet.next()) {
                int count = resultSet.getInt(1);

                if (count > 0) {
                    System.out.println("User does exist, logging in");
                    flag = true;
                } else {
                    System.out.println("User does not exist");
                }
            }
            return flag;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public int getUserId(String username) {
        int id = 0;

        String url = conexionSQL.url;
        String dbUsername = conexionSQL.user;
        String dbPassword = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {

            String sql = "SELECT IdUsuario FROM Usuario WHERE Login = ?";

            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    CacheUsuarios cacheUsuarios = new CacheUsuarios();

    public void getUserInfo(String user, String pass){
        String url = conexionSQL.url;
        String dbUsername = conexionSQL.user;
        String dbPassword = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword)) {

            String sql = "SELECT * FROM Usuario WHERE Login = ? and Contraseña = ?";

            PreparedStatement statement = conn.prepareStatement(sql);

            statement.setString(1, user);
            statement.setString(2, pass);

            ResultSet resultSet = statement.executeQuery();

            //Para obtener toda la info del usuario y la guardara en las variables que estan een la clase de CacheUsuario
            if (resultSet.next()) {
                CacheUsuarios.IdUsuario = resultSet.getInt(1);
                CacheUsuarios.Nombre = resultSet.getString(2);
                CacheUsuarios.Login = resultSet.getString(3);
                CacheUsuarios.Telefono = resultSet.getString(5);
                CacheUsuarios.Correo = resultSet.getString(6);
                CacheUsuarios.IdTipo = resultSet.getInt(7);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}