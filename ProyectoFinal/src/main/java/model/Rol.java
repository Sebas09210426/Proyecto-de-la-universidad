package model;

public enum Rol {
ESTUDIANTE("Estudiante"), PROFESOR("Profesor"), ADMINISTRADOR("Administrador");

    private final String rol;
    Rol(String descripcion) {
        this.rol = descripcion;
    }

    @Override
    public String toString() {
        return this.rol;
    }
}
