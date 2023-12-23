package com.example.testproject.models;

import java.util.Date;

public class serviciosModelo {
    String Descripcion, Imagen;
    int Status, IdServicio;
    double Costo;
    Date FechaCreacion;


    public serviciosModelo(int idServicio) {
        IdServicio = idServicio;
    }

    public serviciosModelo() {}

    public serviciosModelo(int idServicio, String descripcion) {
        IdServicio=idServicio;
        Descripcion=descripcion;
    }


    @Override
    public String toString() {
        return ""+IdServicio + '\n';
    }

    public serviciosModelo(String descripcion) {
        Descripcion = descripcion;
    }

    public serviciosModelo(int idServicio, String descripcion, String imagen, int status, double costo, Date fechaCreacion) {
        IdServicio = idServicio;
        Descripcion = descripcion;
        Imagen = imagen;
        Status = status;
        Costo = costo;
        FechaCreacion = fechaCreacion;
    }

    public serviciosModelo(String descripcion, String imagen, int status, double costo, Date fechaCreacion) {
        Descripcion = descripcion;
        Imagen = imagen;
        Status = status;
        Costo = costo;
        FechaCreacion = fechaCreacion;
    }

    public int getIdServicio() {
        return IdServicio;
    }

    public void setIdServicio(int idServicio) {
        this.IdServicio = idServicio;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String descripcion) {
        Descripcion = descripcion;
    }

    public String getImagen() {
        return Imagen;
    }

    public void setImagen(String imagen) {
        Imagen = imagen;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }

    public double getCosto() {
        return Costo;
    }

    public void setCosto(double costo) {
        Costo = costo;
    }

    public Date getFechaCreacion() {
        return FechaCreacion;
    }

    public void setFechaCreacion(Date fechaCreacion) {
        FechaCreacion = fechaCreacion;
    }
}