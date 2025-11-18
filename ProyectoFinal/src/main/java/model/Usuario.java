package model;

import java.io.Serializable;

public class Usuario implements Serializable {
    //Atributos
    private String usuario;
    private String contrasena;
    private String identificacion;
    private Rol rol;
    private boolean primerInicio;

    //Constructor

    public Usuario(String usuario, String contrasena, String identificacion, Rol rol) {
        this.usuario = usuario;
        this.contrasena = contrasena;
        this.identificacion = identificacion;
        this.rol = rol;
        this.primerInicio = true;
    }

    //Getters y Setters
    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    public Rol getRol() {
        return rol;
    }
    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public boolean getPrimerInicio() {
        return primerInicio;
    }

    public void setPrimerInicio(boolean primerInicio) {
        this.primerInicio = primerInicio;
    }

    @Override
    public String toString() {
        return "Usuario: " + this.usuario + "\n" +
        "Contraseña: " + this.contrasena + "\n" +
        "Identificacion: " + this.identificacion;
    }
}
