package model;

public class Gato extends Mascota{
    //Atributos
    private String especie;
    private String raza;
    private String estado;
    private double cantidadDeHorasDeSuenoEnPromedio;
    private int nivelDeIndependencia;

    //Consturctor

    public Gato(String nombre, int edad, double peso, String identificacion, String nombrePropietario, String numeroPropietario, String especie, String raza, String estado, double cantidadDeHorasDeSuenoEnPromedio, int nivelDeIndependencia) {
        super(nombre, edad, peso, identificacion, nombrePropietario, numeroPropietario);
        this.especie = especie;
        this.raza = raza;
        this.estado = estado;
        this.cantidadDeHorasDeSuenoEnPromedio = cantidadDeHorasDeSuenoEnPromedio;
        this.nivelDeIndependencia = nivelDeIndependencia;
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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public double getCantidadDeHorasDeSuenoEnPromedio() {
        return cantidadDeHorasDeSuenoEnPromedio;
    }

    public void setCantidadDeHorasDeSuenoEnPromedio(double cantidadDeHorasDeSuenoEnPromedio) {
        this.cantidadDeHorasDeSuenoEnPromedio = cantidadDeHorasDeSuenoEnPromedio;
    }

    public int getNivelDeIndependencia() {
        return nivelDeIndependencia;
    }

    public void setNivelDeIndependencia(int nivelDeIndependencia) {
        this.nivelDeIndependencia = nivelDeIndependencia;
    }

    @Override
    public String toString() {
        return "Mascota{" +
                "nombre='" + nombre + "\n" +
                ", edad=" + edad +
                ", identificacion='" + identificacion + "\n" +
                ", nombrePropietario='" + nombrePropietario + "\n" +
                ", numeroPropietario='" + numeroPropietario + "\n" +
                ", especie='" + especie + "\n" +
                ", raza='" + raza + "\n" +
                ", estado='" + estado + "\n" +
                ", cantidad de horas de sueño en promedio: " + cantidadDeHorasDeSuenoEnPromedio +
                ", nivel de independencia: " + nivelDeIndependencia +
                '}';
    }
}