package model;

import java.util.LinkedList;

public class Estudiante extends Persona {
    //Atributos
    private NivelDeEstudio nivelDeEstudio;
    private Instrumento instrumento;
    private LinkedList<Curso> cursosAsignados;
    private int asistencias;
    private int inasitencias;
    private LinkedList<Nota> notasRegistradas;

    //Constructor
    public Estudiante(String nombre, String apellido, String identificacion, NivelDeEstudio nivelDeEstudio, Instrumento instrumento) {
        super(nombre, apellido, identificacion);
        this.nivelDeEstudio = nivelDeEstudio;
        this.instrumento = instrumento;
        this.cursosAsignados = new LinkedList<>();
        this.asistencias = 0;
        this.inasitencias = 0;
        this.notasRegistradas = new LinkedList<>();
    }

    //Getters y Setters
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

    public LinkedList<Curso> getCursosAsignados() {
        return cursosAsignados;
    }

    public void setCursosAsignados(LinkedList<Curso> cursosAsignados) {
        this.cursosAsignados = cursosAsignados;
    }

    public int getAsistencias() {
        return asistencias;
    }

    public void setAsistencias(int asistencias) {
        this.asistencias = asistencias;
    }

    public int getInasitencias() {
        return inasitencias;
    }

    public void setInasitencias(int inasitencias) {
        this.inasitencias = inasitencias;
    }

    public LinkedList<Nota> getNotasRegistradas() {
        return notasRegistradas;
    }

    public void setNotasRegistradas(LinkedList<Nota> notasRegistradas) {
        this.notasRegistradas = notasRegistradas;
    }

    @Override
    public String toString() {
        return "Estudiante {" + "\n" +
                "Nombre: " + getNombre() + "\n" +
                "Apellido: " + getApellido() + "\n" +
                "Identificacion: " + getIdentificacion() + "\n" +
                "Cursos asignados: " + getCursosAsignados().size() + "\n" +
                "Nivel de estudio=" + nivelDeEstudio + "\n" +
                "Instrumento=" + instrumento + "\n" +
                '}';
    }
}
