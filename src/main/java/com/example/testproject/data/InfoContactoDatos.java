package com.example.testproject.data;

import com.example.testproject.conexion.ConexionSQL;
import com.example.testproject.temporarydata.CacheInfoEmpresa;

import java.sql.*;

public class InfoContactoDatos {

    ConexionSQL conexionSQL = new ConexionSQL();

    public boolean editInfo(String Email, String TelefonoMovil, String TelefonoOficina, String RedSocial) throws SQLException {
        boolean flag = false;
        String dbURL = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;
        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            String sql = "UPDATE InfoContacto SET Email = ?, TelefonoMovil = ?, TelefonoOficina = ?, RedSocial = ? WHERE IdInfo = 1";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, Email);
            statement.setString(2, TelefonoMovil);
            statement.setString(3, TelefonoOficina);
            statement.setString(4, RedSocial);

            if (statement.execute()) {
                System.out.println("The company info was modified successfully");
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return flag;
    }

    public void displayInfo() throws SQLException {
        String dbURL = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;
        try (Connection conn = DriverManager.getConnection(dbURL, username, password)) {

            String sql = "SELECT * FROM InfoContacto WHERE IdInfo = 1";
            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                CacheInfoEmpresa.IdInfo = rs.getInt("IdInfo");
                CacheInfoEmpresa.Email = rs.getString("Email");
                CacheInfoEmpresa.TelefonoMovil = rs.getString("TelefonoMovil");
                CacheInfoEmpresa.TelefonoOficina = rs.getString("TelefonoOficina");
                CacheInfoEmpresa.RedSocial = rs.getString("RedSocial");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}