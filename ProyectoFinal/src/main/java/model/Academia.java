package model;


import java.util.LinkedList;

public class Academia {
    //Atributos
    private String nombre;
    private String nit;
    private LinkedList<Estudiante> listaEstudiantes;
    private LinkedList<Profesor> listaProfesores;
    private LinkedList listaCursos;

    //Constructor

    public Academia(String nombre, String nit) {
        this.nombre = nombre;
        this.nit = nit;
        this. listaEstudiantes = new LinkedList<>();
        this.listaProfesores = new LinkedList<>();
        this.listaCursos = new LinkedList<>();
    }

    //Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public LinkedList<Estudiante> getListaEstudiantes() {
        return listaEstudiantes;
    }

    public void setListaEstudiantes(LinkedList<Estudiante> listaEstudiantes) {
        this.listaEstudiantes = listaEstudiantes;
    }

    public LinkedList<Profesor> getListaProfesores() {
        return listaProfesores;
    }

    public void setListaProfesores(LinkedList<Profesor> listaProfesores) {
        this.listaProfesores = listaProfesores;
    }

    public LinkedList getListaCursos() {
        return listaCursos;
    }

    public void setListaCursos(LinkedList listaCursos) {
        this.listaCursos = listaCursos;
    }
}
