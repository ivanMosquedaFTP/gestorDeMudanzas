package com.example.testproject.data;

import com.example.testproject.models.MensajeModelo;
import com.example.testproject.models.UsuariosModelo;
import com.example.testproject.conexion.ConexionSQL;
import com.example.testproject.temporarydata.CacheUsuarios;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;
import java.util.List;

public class MensajesDatos {
    ConexionSQL conexionSQL = new ConexionSQL();

    private ObservableList<UsuariosModelo> listUsers = FXCollections.observableArrayList();

    private ObservableList<MensajeModelo> listMensaje = FXCollections.observableArrayList();


    public void usuarioEnviaMensaje(int idUsuario, String mensaje, int idEstatus) throws SQLException {
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO Mensaje(IdUsuario, Mensaje, FechaEnvio, IdEstatus) VALUES (?, ?, SYSDATETIME(), ?)";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, idUsuario);
            statement.setString(2, mensaje);
            statement.setString(3, String.valueOf(idEstatus));

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new message  was sent successfully!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public void adminEnviaMensaje(int idUsuario, String mensaje, int idEstatus, int IdRecibe) throws SQLException {
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {
            String sql = "INSERT INTO Mensaje(IdUsuario, Mensaje, FechaEnvio, IdEstatus, IdRecibe) VALUES (?, ?, SYSDATETIME(), ?, ?)";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, idUsuario);
            statement.setString(2, mensaje);
            statement.setString(3, String.valueOf(idEstatus));
            statement.setInt(4, CacheUsuarios.IdUsuario);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("A new message  was sent successfully!");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public List<UsuariosModelo> getUserNameForMessagee() {
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "SELECT u.Login, u.IdUsuario FROM Usuario as u INNER JOIN Mensaje as m ON u.IdUsuario = m.IdUsuario where u.IdUsuario >= 3";

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                listUsers.add(new UsuariosModelo(resultSet.getString("Login"), resultSet.getInt("IdUsuario")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listUsers;
    }

    public String getUserTypeByMessageId(int msgId) {
        String mensaje= "";
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "SELECT TOP 1 m.Mensaje from Mensaje m inner join Usuario u on u.IdUsuario = m.IdUsuario where u.IdUsuario =? order by FechaEnvio desc";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, msgId);

            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                mensaje = resultSet.getString(1);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return mensaje;
    }

    public MensajeModelo actualizarMensajes(int idUsuario) throws SQLException {
        MensajeModelo mensaje = new MensajeModelo();

        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "SELECT Mensaje, FechaEnvio FROM Mensaje WHERE IdUsuario = ?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, idUsuario);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                mensaje.setMensaje(resultSet.getString(1));
                mensaje.setFechaEnvio(resultSet.getDate(2));
                mensaje.setIdUsuario(idUsuario);
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return mensaje;
    }

    public ObservableList<MensajeModelo> actualizarMensajesDeAdmin() throws SQLException {
        MensajeModelo mensaje = new MensajeModelo();

        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "select Mensaje from Mensaje where IdUsuario in(select IdUsuario from Usuario where IdTipo in(select IdTipo from TipoUsuario where IdTipo = 1 or IdTipo = 3 and IdEstatus = 2))";

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                mensaje.setMensaje(resultSet.getString(1));
                listMensaje.add(new MensajeModelo(resultSet.getString("Mensaje")));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listMensaje;
    }

    public MensajeModelo actualizarMensajesDeUsuarios() throws SQLException {
        MensajeModelo mensaje = new MensajeModelo();

        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "select Mensaje from Mensaje where IdUsuario in(select IdUsuario from Usuario where IdTipo in(select IdTipo from TipoUsuario where IdTipo = 1 or IdTipo = 3 and IdEstatus = 2))";

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                mensaje.setMensaje(resultSet.getString(1));
                mensaje.setFechaEnvio(resultSet.getDate(2));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return mensaje;
    }

    public ObservableList<MensajeModelo> showAdmins() {
        String url = conexionSQL.url;
        String username = conexionSQL.user;
        String password = conexionSQL.password;

        try (Connection conn = DriverManager.getConnection(url, username, password)) {

            String sql = "select u.Login, m.Mensaje from Mensaje as m right join Usuario as u on u.IdUsuario = m.IdUsuario where IdTipo = 1 or IdTipo=3";

            PreparedStatement statement = conn.prepareStatement(sql);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                listMensaje.add(new MensajeModelo(resultSet.getString("Login"), resultSet.getString("Mensaje")));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return listMensaje;
    }
}