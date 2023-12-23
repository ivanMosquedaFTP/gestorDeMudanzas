package com.example.testproject.models;

import java.util.Date;

public class MensajeModelo {
    int IdMensaje, IdUsuario, IdEstatus, IdRecibe;
    String Mensaje, Login;
    Date FechaEnvio;

    public MensajeModelo() {}

    public MensajeModelo(String login, String mensaje) {
        Login = login;
        Mensaje = mensaje;
    }

    public MensajeModelo(int idMensaje, int idUsuario, int idEstatus, int idRecibe, String mensaje, Date fechaEnvio) {
        IdMensaje = idMensaje;
        IdUsuario = idUsuario;
        IdEstatus = idEstatus;
        IdRecibe = idRecibe;
        Mensaje = mensaje;
        FechaEnvio = fechaEnvio;
    }

    public MensajeModelo(int idMensaje, int idUsuario, int idEstatus, String mensaje, Date fechaEnvio) {
        IdMensaje = idMensaje;
        IdUsuario = idUsuario;
        IdEstatus = idEstatus;
        Mensaje = mensaje;
        FechaEnvio = fechaEnvio;
    }

    public MensajeModelo(String mensaje) {
        Mensaje = mensaje;
    }

    public int getIdRecibe() {
        return IdRecibe;
    }

    public void setIdRecibe(int idRecibe) {
        IdRecibe = idRecibe;
    }

    public int getIdMensaje() {
        return IdMensaje;
    }

    public void setIdMensaje(int idMensaje) {
        IdMensaje = idMensaje;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }

    public int getIdEstatus() {
        return IdEstatus;
    }

    public void setIdEstatus(int idEstatus) {
        IdEstatus = idEstatus;
    }

    public String getMensaje() {
        return Mensaje;
    }

    public void setMensaje(String mensaje) {
        Mensaje = mensaje;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public Date getFechaEnvio() {
        return FechaEnvio;
    }

    public void setFechaEnvio(Date fechaEnvio) {
        FechaEnvio = fechaEnvio;
    }

    @Override
    public String toString() {
        return "MensajeModelo{" +
                "IdMensaje=" + IdMensaje +
                ", IdUsuario=" + IdUsuario +
                ", IdEstatus=" + IdEstatus +
                ", Mensaje='" + Mensaje + '\'' +
                ", FechaEnvio=" + FechaEnvio +
                '}';
    }
}