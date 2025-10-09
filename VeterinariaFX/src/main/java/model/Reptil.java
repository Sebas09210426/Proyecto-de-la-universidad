package model;

public class Reptil extends Mascota{
    //Atributos
    private String especie;
    private String raza;
    private String habitatRequerido;
    private String nivelDePeligrosidad;

    //Constructor
    public Reptil(String nombre, int edad, double peso, String identificacion, String nombrePropietario, String numeroPropietario, String especie, String raza, String habitatRequerido, String nivelDePeligrosidad) {
        super(nombre, edad, peso, identificacion, nombrePropietario, numeroPropietario);
        this.especie = especie;
        this.raza = raza;
        this.habitatRequerido = habitatRequerido;
        this.nivelDePeligrosidad = nivelDePeligrosidad;
    }


    //Getters y Setters

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getHabitatRequerido() {
        return habitatRequerido;
    }

    public void setHabitatRequerido(String habitatRequerido) {
        this.habitatRequerido = habitatRequerido;
    }

    public String getNivelDePeligrosidad() {
        return nivelDePeligrosidad;
    }

    public void setNivelDePeligrosidad(String nivelDePeligrosidad) {
        this.nivelDePeligrosidad = nivelDePeligrosidad;
    }

    //toString

    @Override
    public String toString() {
        return "Mascota{" +
                "especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                ", habitatRequerido='" + habitatRequerido + '\'' +
                ", nivelDePeligrosidad='" + nivelDePeligrosidad + '\'' +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", identificacion='" + identificacion + '\'' +
                ", nombrePropietario='" + nombrePropietario + '\'' +
                ", numeroPropietario='" + numeroPropietario + '\'' +
                ", peso=" + peso +
                ", testNull='" + testNull + '\'' +
                '}';
    }
}
