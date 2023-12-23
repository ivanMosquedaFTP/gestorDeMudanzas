package com.example.testproject.models;

public class RespuestaModelo {
    int IdRespuesta, IdMensaje;
    String Respuesta;

    public RespuestaModelo() {}

    public RespuestaModelo(int idRespuesta, int idMensaje, String respuesta) {
        IdRespuesta = idRespuesta;
        IdMensaje = idMensaje;
        Respuesta = respuesta;
    }

    public int getIdRespuesta() {
        return IdRespuesta;
    }

    public void setIdRespuesta(int idRespuesta) {
        IdRespuesta = idRespuesta;
    }

    public int getIdMensaje() {
        return IdMensaje;
    }

    public void setIdMensaje(int idMensaje) {
        IdMensaje = idMensaje;
    }

    public String getRespuesta() {
        return Respuesta;
    }

    public void setRespuesta(String respuesta) {
        Respuesta = respuesta;
    }

    @Override
    public String toString() {
        return "RespuestaModelo{" +
                "IdRespuesta=" + IdRespuesta +
                ", IdMensaje=" + IdMensaje +
                ", Respuesta='" + Respuesta + '\'' +
                '}';
    }
}