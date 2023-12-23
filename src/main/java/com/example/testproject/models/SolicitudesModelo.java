package com.example.testproject.models;

import java.util.Date;

public class SolicitudesModelo {
    int IdSolicitud, IdUsuario, IdServicio, Estatus, Atendio;
    String Origen, Destino, Observaciones;
    Date FechaProgramada, FechaCreacion;
    double CostoAdicional, CostoFinal;

    public SolicitudesModelo() {}


 public SolicitudesModelo(int idSolicitud, int idUsuario, int idServicio,
                             int estatus, int atendio, String origen, String destino, String observaciones,
                             Date fechaProgramada, Date fechaCreacion, double costoAdicional, double costoFinal) {
        IdSolicitud = idSolicitud;
        IdUsuario = idUsuario;
        IdServicio = idServicio;
        Estatus = estatus;
        Atendio = atendio;
        Origen = origen;
        Destino = destino;
        Observaciones = observaciones;
        FechaProgramada = fechaProgramada;
        FechaCreacion = fechaCreacion;
        CostoAdicional = costoAdicional;
        CostoFinal = costoFinal;
    }

    public SolicitudesModelo(int idSolicitud, int idUsuario, int idServicio, int estatus, int atendio, String origen,
                             String destino, String observaciones, double costoAdicional, double costoFinal) {
        IdSolicitud = idSolicitud;
        IdUsuario = idUsuario;
        IdServicio = idServicio;
        Estatus = estatus;
        Atendio = atendio;
        Origen = origen;
        Destino = destino;
        Observaciones = observaciones;
        CostoAdicional = costoAdicional;
        CostoFinal = costoFinal;
    }

    public int getIdSolicitud() {
        return IdSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        IdSolicitud = idSolicitud;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }

    public int getIdServicio() {
        return IdServicio;
    }

    public void setIdServicio(int idServicio) {
        IdServicio = idServicio;
    }

    public int getEstatus() {
        return Estatus;
    }

    public void setEstatus(int estatus) {
        Estatus = estatus;
    }

    public int getAtendio() {
        return Atendio;
    }

    public void setAtendio(int atendio) {
        Atendio = atendio;
    }

    public String getOrigen() {
        return Origen;
    }

    public void setOrigen(String origen) {
        Origen = origen;
    }

    public String getDestino() {
        return Destino;
    }

    public void setDestino(String destino) {
        Destino = destino;
    }

    public String getObservaciones() {
        return Observaciones;
    }

    public void setObservaciones(String observaciones) {
        Observaciones = observaciones;
    }

    public Date getFechaProgramada() {
        return FechaProgramada;
    }

    public void setFechaProgramada(Date fechaProgramada) {
        FechaProgramada = fechaProgramada;
    }

    public Date getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        FechaCreacion = fechaCreacion;
    }

    public double getCostoAdicional() {
        return CostoAdicional;
    }

    public void setCostoAdicional(double costoAdicional) {
        CostoAdicional = costoAdicional;
    }

    public double getCostoFinal() {
        return CostoFinal;
    }

    public void setCostoFinal(double costoFinal) {
        CostoFinal = costoFinal;
    }
}