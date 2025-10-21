package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class Curso {
    //Atributos
    private int capacidad;
    private LinkedList<Estudiante> estudiantesRegistrados;
    private LocalDate fecha;
    private LocalTime hora;

    //Constructor
    public Curso(int capacidad, LinkedList<Estudiante> estudiantesRegistrados, LocalDate fecha, LocalTime hora) {
        this.capacidad = capacidad;
        this.estudiantesRegistrados = estudiantesRegistrados;
        this.fecha = fecha;
        this.hora = hora;
    }

    //Getters y Setters
    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public LinkedList<Estudiante> getEstudiantesRegistrados() {
        return estudiantesRegistrados;
    }

    public void setEstudiantesRegistrados(LinkedList<Estudiante> estudiantesRegistrados) {
        this.estudiantesRegistrados = estudiantesRegistrados;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHora() {
        return hora;
    }

    public void setHora(LocalTime hora) {
        this.hora = hora;
    }
}
