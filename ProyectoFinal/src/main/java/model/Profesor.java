package model;

import java.util.LinkedList;

public class Profesor extends Persona {
    //Atributos
    private LinkedList<Estudiante> listaEstudiantesAsignados;
    private LinkedList<Curso> listaCursosAsignados;
    private LinkedList<Curso> listaClasesIndividuales;

    //Constructor
    public Profesor(String nombre, String apellido, String identificacion) {
        super(nombre, apellido, identificacion);
        this.listaEstudiantesAsignados = new LinkedList<Estudiante>();
        this.listaCursosAsignados = new LinkedList<Curso>();
        this.listaClasesIndividuales = new LinkedList<>();
    }

    //Getters y Setters

    public LinkedList<Estudiante> getListaEstudiantesAsignados() {
        return listaEstudiantesAsignados;
    }

    public void setListaEstudiantesAsignados(LinkedList<Estudiante> listaEstudiantesAsignados) {
        this.listaEstudiantesAsignados = listaEstudiantesAsignados;
    }

    public LinkedList<Curso> getListaCursosAsignados() {
        return listaCursosAsignados;
    }

    public void setListaCursosAsignados(LinkedList<Curso> listaCursosAsignados) {
        this.listaCursosAsignados = listaCursosAsignados;
    }

    public LinkedList<Curso> getListaClasesIndividuales() {
        return listaClasesIndividuales;
    }

    public void setListaClasesIndividuales(LinkedList<Curso> listaClasesIndividuales) {
        this.listaClasesIndividuales = listaClasesIndividuales;
    }

    @Override
    public String toString() {
        return "Profesor {" + "\n" +
                "Nombre: " + getNombre() + "\n" +
                "Apellido: " + getApellido() + "\n" +
                "Identificacion: " + getIdentificacion() + "\n" +
                "Estudiantes Asignados=" + listaEstudiantesAsignados.size() + "\n" +
                "Cursos Asignados=" + listaCursosAsignados.size() + "\n" +
                '}';
    }
}
