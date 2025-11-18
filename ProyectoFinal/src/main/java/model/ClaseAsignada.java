package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;

public class ClaseAsignada implements Serializable {
    private Curso curso;
    private Profesor profesor;
    private Aula aula;
    private LocalDate fecha;
    private LocalTime hora;

    public ClaseAsignada(Curso curso, Profesor profesor, Aula aula, LocalDate fecha, LocalTime hora) {
        this.curso = curso;
        this.profesor = profesor;
        this.aula = aula;
        this.fecha = fecha;
        this.hora = hora;
    }

    public Curso getCurso() {
        return curso;
    }

    public void setCurso(Curso curso) {
        this.curso = curso;
    }

    public Profesor getProfesor() {
        return profesor;
    }

    public void setProfesor(Profesor profesor) {
        this.profesor = profesor;
    }

    public Aula getAula() {
        return aula;
    }

    public void setAula(Aula aula) {
        this.aula = aula;
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
