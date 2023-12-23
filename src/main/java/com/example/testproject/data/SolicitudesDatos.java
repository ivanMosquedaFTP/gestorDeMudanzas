package com.example.testproject.data;

import com.example.testproject.conexion.ConexionSQL;
import com.example.testproject.temporarydata.CacheServicios;
import com.example.testproject.models.SolicitudesModelo;

import java.time.LocalDateTime;
import java.util.List;

import com.example.testproject.temporarydata.CacheUsuarios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;

public class SolicitudesDatos {
    ConexionSQL conexionSQL = new ConexionSQL();

    ObservableList<SolicitudesModelo> listSolicitudes = FXCollections.observableArrayList();

    public ObservableList<SolicitudesModelo> mostrarSolicitudes(){
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "SELECT * FROM Solicitud WHERE Atendio = 2";

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                listSolicitudes.add(new SolicitudesModelo(
                        rs.getInt("IdSolicitud"),
                        rs.getInt("IdUsuario"),
                        rs.getInt("IdServicio"),
                        rs.getInt("Estatus"),
                        rs.getInt("Atendio"),
                        rs.getString("Origen"),
                        rs.getString("Destino"),
                        rs.getString("Observaciones"),
                        rs.getDate("FechaProgramada"),
                        rs.getDate("FechaCreacion"),
                        rs.getDouble("CostoAdicional"),
                        rs.getDouble("CostoFinal")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listSolicitudes;
    }

    public boolean insertRequestAsAdmin(int IdUsuario, int idServicio, String origen, String destino, String observaciones, int status, String fecha, int atendio, double costoAdicional, double costoFinal) {
        boolean flag = false;

        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "INSERT INTO Solicitud(IdUsuario, IdServicio, Origen, Destino, Observaciones, Estatus, FechaCreacion, Atendio, CostoAdicional, CostoFinal) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, IdUsuario);
            statement.setInt(2, idServicio);
            statement.setString(3, origen);
            statement.setString(4, destino);
            statement.setString(5, observaciones);
            statement.setInt(6, status);
            statement.setString(7, fecha);
            statement.setInt(8, atendio);
            statement.setDouble(9, costoAdicional);
            statement.setDouble(10, costoFinal);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new request as admin was inserted successfully!");
                flag = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return flag;
    }

    public boolean insertRequestAsUser(int IdUsuario, int idServicio, String origen, String destino, String  fechaProgramada) {
        boolean flag = false;

        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "INSERT INTO Solicitud(IdUsuario, IdServicio, Origen, Destino, FechaProgramada, Observaciones, Estatus, FechaCreacion, Atendio, CostoAdicional, CostoFinal) VALUES(?, ?, ?, ?, ?, 'To be review by admin', 2, SYSDATETIME(), 2, 0, 0);";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, IdUsuario);
            statement.setInt(2, idServicio);
            statement.setString(3, origen);
            statement.setString(4, destino);
            statement.setString(5, fechaProgramada);

            int rowsInserted = statement.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new request was inserted successfully!");
                flag = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return flag;
    }

    public boolean getServiceIdByDesc(String desc) {
        boolean flag = false;

        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "SELECT IdServicio FROM SERVICIO WHERE Descripcion = ?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, desc);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                CacheServicios.IdServicio = rs.getInt("IdServicio");
                System.out.println("Service found" + rs.getInt("IdServicio"));
                flag = true;
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return flag;
    }
    //SEMANAL PAI
    public List<SolicitudesModelo> SolicitudesSemanales() {
        ObservableList<SolicitudesModelo> listSolicitudes = FXCollections.observableArrayList();
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;
//
        LocalDateTime fechaActual = LocalDateTime.now().withNano(0);
        LocalDateTime Semanal = fechaActual.minusDays(7).withNano(0);
        Timestamp t1 = Timestamp.valueOf(fechaActual);
        Timestamp t2 = Timestamp.valueOf(Semanal);

        try {
            Connection conn = DriverManager.getConnection(url, username, password);
            String sql = "select * from Solicitud where FechaCreacion>= ? and FechaCreacion<=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setTimestamp(1, t2);
            statement.setTimestamp(2, t1);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                listSolicitudes.add(new SolicitudesModelo(
                        rs.getInt("IdSolicitud"),
                        rs.getInt("IdUsuario"),
                        rs.getInt("IdServicio"),
                        rs.getInt("Estatus"),
                        rs.getInt("Atendio"),
                        rs.getString("Origen"),
                        rs.getString("Destino"),
                        rs.getString("Observaciones"),
                        rs.getDate("FechaProgramada"),
                        rs.getDate("FechaCreacion"),
                        rs.getDouble("CostoAdicional"),
                        rs.getDouble("CostoFinal")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(listSolicitudes);
        return listSolicitudes;
    }

    public Integer SolicitudesSemanalesConteo() {
        int a = 0;
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        LocalDateTime fechaActual = LocalDateTime.now().withNano(0);
        LocalDateTime Semanal = fechaActual.minusDays(7).withNano(0);
        Timestamp t1 = Timestamp.valueOf(fechaActual);
        Timestamp t2 = Timestamp.valueOf(Semanal);

        try {
            Connection conn = DriverManager.getConnection(url, username, password);

            String sql = "Select sum (CostoFinal) as Suma from Solicitud where FechaCreacion>= ? and FechaCreacion<=?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setTimestamp(1, t2);
            statement.setTimestamp(2, t1);


            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                a = rs.getInt("Suma");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        System.out.println(a);
        return a;
    }

    public List<SolicitudesModelo> SolicitudesMensuales() {
        ObservableList<SolicitudesModelo> listSolicitudes = FXCollections.observableArrayList();
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        LocalDateTime fechaActual = LocalDateTime.now().withNano(0);
        LocalDateTime Mensual = fechaActual.minusDays(30).withNano(0);
        Timestamp t1 = Timestamp.valueOf(fechaActual);
        Timestamp t2 = Timestamp.valueOf(Mensual);

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "Select * from Solicitud where FechaCreacion>= ? and FechaCreacion<=?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setTimestamp(1, t2);
            statement.setTimestamp(2, t1);

            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                listSolicitudes.add(new SolicitudesModelo(rs.getInt("IdSolicitud"),
                        rs.getInt("IdUsuario"),
                        rs.getInt("IdServicio"),
                        rs.getInt("Estatus"),
                        rs.getInt("Atendio"),
                        rs.getString("Origen"),
                        rs.getString("Destino"),
                        rs.getString("Observaciones"),
                        rs.getDate("FechaProgramada"),
                        rs.getDate("FechaCreacion"),
                        rs.getDouble("CostoAdicional"),
                        rs.getDouble("CostoFinal")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listSolicitudes;
    }

    public Integer SolicitudesMensualesConteo() {
        int a = 0;
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        LocalDateTime fechaActual = LocalDateTime.now().withNano(0);
        LocalDateTime Mensual = fechaActual.minusDays(30).withNano(0);
        Timestamp t1 = Timestamp.valueOf(fechaActual);
        Timestamp t2 = Timestamp.valueOf(Mensual);

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "Select sum(CostoFinal) as Suma from Solicitud where FechaCreacion>= ? and FechaCreacion<=?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setTimestamp(1, t2);
            statement.setTimestamp(2, t1);

            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                a = rs.getInt("Suma");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return a;
    }

    public List<SolicitudesModelo> SolicitudesAnuales() {
        ObservableList<SolicitudesModelo> listSolicitudes = FXCollections.observableArrayList();
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        LocalDateTime fechaActual = LocalDateTime.now().withNano(0);
        LocalDateTime Anual = fechaActual.minusDays(365).withNano(0);
        Timestamp t1 = Timestamp.valueOf(fechaActual);
        Timestamp t2 = Timestamp.valueOf(Anual);

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "Select * from Solicitud where FechaCreacion>= ? and FechaCreacion<=?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setTimestamp(1, t2);
            statement.setTimestamp(2, t1);

            ResultSet rs = statement.executeQuery();

            while(rs.next()) {
                listSolicitudes.add(new SolicitudesModelo(rs.getInt("IdSolicitud"),
                        rs.getInt("IdUsuario"),
                        rs.getInt("IdServicio"),
                        rs.getInt("Estatus"),
                        rs.getInt("Atendio"),
                        rs.getString("Origen"),
                        rs.getString("Destino"),
                        rs.getString("Observaciones"),
                        rs.getDate("FechaProgramada"),
                        rs.getDate("FechaCreacion"),
                        rs.getDouble("CostoAdicional"),
                        rs.getDouble("CostoFinal")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return listSolicitudes;
    }

    public Integer SolicitudesAnualesConteo() {
        int a = 0;
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        LocalDateTime fechaActual = LocalDateTime.now().withNano(0);
        LocalDateTime Anual = fechaActual.minusDays(365).withNano(0);
        Timestamp t1 = Timestamp.valueOf(fechaActual);
        Timestamp t2 = Timestamp.valueOf(Anual);

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "Select sum(CostoFinal) as Suma from Solicitud where FechaCreacion>= ? and FechaCreacion<=?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setTimestamp(1, t2);
            statement.setTimestamp(2, t1);


            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                a = rs.getInt("Suma");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return a;
    }

    public boolean adminValidateRequest(int idRequest, String observations, double aditionalCost, double finalCost) {
        boolean flag = false;

        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "UPDATE Solicitud SET Observaciones = ?, Estatus = 1, Atendio = 1, CostoAdicional = ?, CostoFinal = ? WHERE IdSolicitud = ?;";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, observations);
            statement.setDouble(2, aditionalCost);
            statement.setDouble(3, finalCost);
            statement.setInt(4, idRequest);

            if (statement.execute()) {
                flag = true;
                System.out.println("request " + idRequest + " is now enabled");
            } else {
                System.out.println("request " + idRequest + "was not enabled due to an error");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return flag;
    }

    //llamar esta consulta cuando el usuario carga el form de pending requests
    public ObservableList<SolicitudesModelo> showUserPendingRequests(){
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "SELECT * FROM Solicitud WHERE Atendio = 2 and IdUsuario = ?;";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, CacheUsuarios.IdUsuario);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                listSolicitudes.add(new SolicitudesModelo(
                        rs.getInt("IdSolicitud"),
                        rs.getInt("IdUsuario"),
                        rs.getInt("IdServicio"),
                        rs.getInt("Estatus"),
                        rs.getInt("Atendio"),
                        rs.getString("Origen"),
                        rs.getString("Destino"),
                        rs.getString("Observaciones"),
                        rs.getDate("FechaProgramada"),
                        rs.getDate("FechaCreacion"),
                        rs.getDouble("CostoAdicional"),
                        rs.getDouble("CostoFinal")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listSolicitudes;
    }
}