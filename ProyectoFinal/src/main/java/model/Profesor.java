package model;

import java.util.LinkedList;

public class Profesor extends Persona {
    //Atributos
    private LinkedList<Estudiante> listaEstudiantesAsignados;
    private LinkedList<Profesor> listaCursosAsignados;

    //Constructor
    public Profesor(String nombre, String apellido, String identificacion, LinkedList<Estudiante> listaEstudiantesAsignados, LinkedList<Profesor> listaCursosAsignados) {
        super(nombre, apellido, identificacion);
        this.listaEstudiantesAsignados = listaEstudiantesAsignados;
        this.listaCursosAsignados = listaCursosAsignados;
    }

    //Getters y Setters

    public LinkedList<Estudiante> getListaEstudiantesAsignados() {
        return listaEstudiantesAsignados;
    }

    public void setListaEstudiantesAsignados(LinkedList<Estudiante> listaEstudiantesAsignados) {
        this.listaEstudiantesAsignados = listaEstudiantesAsignados;
    }

    public LinkedList<Profesor> getListaCursosAsignados() {
        return listaCursosAsignados;
    }

    public void setListaCursosAsignados(LinkedList<Profesor> listaCursosAsignados) {
        this.listaCursosAsignados = listaCursosAsignados;
    }

    @Override
    public String toString() {
        return "Profesor {" + "\n" +
                "Nombre: " + getNombre() + "\n" +
                "Apellido: " + getApellido() + "\n" +
                "Identificacion: " + getIdentificacion() + "\n" +
                "Estudiantes Asignados=" + listaEstudiantesAsignados + "\n" +
                "Cursos Asignados=" + listaCursosAsignados + "\n" +
                '}';
    }
}
