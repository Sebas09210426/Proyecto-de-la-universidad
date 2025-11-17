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
    private Instrumento instrumento;
    private NivelDeEstudio nivelDeEstudio;
    private Profesor profesor;
    private String codigo;
    private Aula aulaAsignada;
    private String estado;

    //Constructor
    public Curso(int capacidad, LinkedList<Estudiante> estudiantesRegistrados, LocalDate fecha, LocalTime hora, Instrumento intrumento, NivelDeEstudio nivelDeEstudio, Profesor profesor, String codigo, Aula aulaAsignada, String estado) {
        this.capacidad = capacidad;
        this.estudiantesRegistrados = estudiantesRegistrados;
        this.fecha = fecha;
        this.hora = hora;
        this.instrumento = intrumento;
        this.nivelDeEstudio = nivelDeEstudio;
        this.profesor = profesor;
        this.codigo = codigo;
        this.aulaAsignada = aulaAsignada;
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

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public NivelDeEstudio getNivelDeEstudio() {
        return nivelDeEstudio;
    }

    public void setNivelDeEstudio(NivelDeEstudio nivelDeEstudio) {
        this.nivelDeEstudio = nivelDeEstudio;
    }

    public Instrumento getInstrumento() {
        return instrumento;
    }

    public void setInstrumento(Instrumento instrumento) {
        this.instrumento = instrumento;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Aula getAulaAsignada() {
        return aulaAsignada;
    }

    public void setAulaAsignada(Aula aulaAsignada) {
        this.aulaAsignada = aulaAsignada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "Curso {" + "\n" +
                "Fecha: " + fecha.toString() + "\n" +
                "Hora: " + hora.toString() + "\n" +
                "Aula asignada: " + (aulaAsignada != null ? aulaAsignada.getId() : "Sin asignar") + "\n" +
                "Capacidad: " + capacidad + "\n" +
                "Estudiantes registrados: " + estudiantesRegistrados.size() + "\n" +
                "Cupos disponibles: " + (capacidad - estudiantesRegistrados.size()) + "\n" +
                "Instrumento: " + instrumento.toString() + "\n" +
                "Nivel de estudio: " + nivelDeEstudio.toString() + "\n" +
                "Profesor: " + (profesor != null ? profesor.getNombre() + profesor.getApellido() : "Sin asignar") + "\n" +
                "Codigo: " + codigo + "\n" +
                '}';
    }
}
