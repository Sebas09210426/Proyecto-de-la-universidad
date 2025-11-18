package model;

import java.io.Serializable;

public class AdministradorAcademico extends Persona implements Serializable {
    //Atributos no tiene

    //Constructor
    public AdministradorAcademico(String nombre, String apellido, String identificacion) {
        super(nombre, apellido, identificacion);
    }

    @Override
    public String toString() {
        return "AdministradorAcademico{" + "\n" +
                "nombre: " + getNombre() +
                ", apellido: " + getApellido() + "\n" +
                ", identificacion: " + getIdentificacion() + "\n" +
                "}";
    }
}
