package model;

import java.io.Serializable;

public enum Rol implements Serializable {
ESTUDIANTE("Estudiante"), PROFESOR("Profesor"), ADMINISTRADOR("Administrador"), DEFAULT("Default");

    private final String rol;
    Rol(String descripcion) {
        this.rol = descripcion;
    }

    @Override
    public String toString() {
        return this.rol;
    }
}
