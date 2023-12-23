package com.example.testproject.models;

public class UsuariosModelo {
    int IdUsuario, IdTipo;
    String Nombre, Login, contrasena, Telefono, Correo;

    public UsuariosModelo() {}

    public UsuariosModelo(int idUsuario, int idTipo, String nombre, String login, String contrasena, String telefono, String correo) {
        IdUsuario = idUsuario;
        IdTipo = idTipo;
        Nombre = nombre;
        Login = login;
        contrasena = contrasena;
        Telefono = telefono;
        Correo = correo;
    }

    public UsuariosModelo(String login, int idUsuario) {
        Login = login;
        IdUsuario = idUsuario;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        IdUsuario = idUsuario;
    }

    public int getIdTipo() {
        return IdTipo;
    }

    public void setIdTipo(int idTipo) {
        IdTipo = idTipo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getLogin() {
        return Login;
    }

    public void setLogin(String login) {
        Login = login;
    }

    public String getcontrasena() {
        return contrasena;
    }

    public void setcontrasena(String contrasena) {
        contrasena = contrasena;
    }

    public String getTelefono() {
        return Telefono;
    }

    public void setTelefono(String telefono) {
        Telefono = telefono;
    }

    public String getCorreo() {
        return Correo;
    }

    public void setCorreo(String correo) {
        Correo = correo;
    }

}