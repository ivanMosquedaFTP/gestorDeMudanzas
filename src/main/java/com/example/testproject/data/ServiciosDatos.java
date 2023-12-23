package com.example.testproject.data;

import com.example.testproject.conexion.ConexionSQL;
import com.example.testproject.models.SolicitudesModelo;
import com.example.testproject.models.serviciosModelo;
import com.example.testproject.temporarydata.CacheServicios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

public class ServiciosDatos {
    ConexionSQL conexionSQL = new ConexionSQL();

    ObservableList<serviciosModelo> listServicios = FXCollections.observableArrayList();
    ObservableList<Integer> listIdServicios = FXCollections.observableArrayList();

    public List<serviciosModelo> mostrarServicio(){
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "SELECT * FROM Servicio";

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                listServicios.add(new serviciosModelo(resultSet.getInt("IdServicio"),
                        resultSet.getString("Descripcion"),resultSet.getString("Imagen"),
                        resultSet.getInt("Status"), resultSet.getDouble("Costo"),
                        resultSet.getDate("FechaCreacion")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listServicios;
    }

    public ObservableList<Integer> mostrarIdServicios(){
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "SELECT IdServicio FROM Servicio WHERE Status = 1";

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                listIdServicios.add((rs.getInt("IdServicio")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listIdServicios;
    }

    public List<serviciosModelo> searchServicioByDesc(String desc){
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "SELECT IdServicio FROM Servicio WHERE Descripcion = ?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, desc);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                CacheServicios.IdServicio = resultSet.getInt(1);
                System.out.println("service found, retrieving idServicio");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listServicios;
    }

    public boolean insertarServicio(String Descripcion, int status, double costo, String imagen) throws SQLException {
        boolean flag = false;
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "INSERT INTO Servicio (Descripcion, Status, Costo, FechaCreacion, Imagen) VALUES (?, ?, ?, SYSDATETIME(), ?)";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, Descripcion);
            statement.setString(2, String.valueOf(status));
            statement.setString(3, String.valueOf(costo));
            statement.setString(4, imagen);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new service was inserted successfully!");
                searchServicioByDesc(Descripcion);
                flag = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return flag;
    }

    public boolean deleteService(String desc) throws SQLException {
        boolean flag = false;
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;
        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "DELETE FROM Servicio WHERE Descripcion = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, desc);

            if (statement.execute()) {
                System.out.println("The service was deleted successfully");
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return flag;
    }

    public boolean editService(String descripcion, int status, double cost, int IdServicio) throws SQLException {
        boolean flag = false;
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;
        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "UPDATE Servicio SET Descripcion = ?, Status = ?, Costo = ? WHERE IdServicio = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1,descripcion);
            statement.setInt(2, status);
            statement.setDouble(3, cost);
            statement.setInt(4, IdServicio);

            if (statement.execute()) {
                System.out.println("The service was modified successfully");
                return true;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return flag;
    }

    public List<serviciosModelo> GetNameAndId() {
        ObservableList<serviciosModelo> listServices2 = FXCollections.observableArrayList();
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;


        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "Select distinct IdServicio,Descripcion from Servicio";

            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                listServices2.add(new serviciosModelo(rs.getInt("IdServicio"),
                                                     rs.getString("Descripcion")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listServices2;
    }
    public List<serviciosModelo> GetNameAndId(int i) {
        ObservableList<serviciosModelo> listServices2 = FXCollections.observableArrayList();
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;


        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "Select distinct IdServicio,Descripcion from Servicio where Status=1";

            PreparedStatement statement = conn.prepareStatement(sql);

            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                listServices2.add(new serviciosModelo(rs.getInt("IdServicio"),
                                                     rs.getString("Descripcion")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listServices2;
    }
}