package model;

public class Estudiante extends Persona {
    //Atributos
    private NivelDeEstudio nivelDeEstudio;
    private Instrumento instrumento;

    //Constructor
    public Estudiante(String nombre, String apellido, String identificacion, NivelDeEstudio nivelDeEstudio, Instrumento instrumento) {
        super(nombre, apellido, identificacion);
        this.nivelDeEstudio = nivelDeEstudio;
        this.instrumento = instrumento;
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

    @Override
    public String toString() {
        return "Estudiante {" + "\n" +
                "Nombre: " + getNombre() + "\n" +
                "Apellido: " + getApellido() + "\n" +
                "Identificacion: " + getIdentificacion() + "\n" +
                "NivelDeEstudio=" + nivelDeEstudio + "\n" +
                "Instrumento=" + instrumento + "\n" +
                '}';
    }
}
