package model;

public class Ave extends Mascota {
    //Atributos
    private String especie;
    private String raza;
    private String tipoDePlumaje;
    private boolean capacidadDeVuelo;
    private int cantidadDeSonidos;

    //Constructor
    public Ave(String nombre, int edad, double peso, String identificacion, String nombrePropietario, String numeroPropietario, String especie, String raza, String tipoDePlumaje, boolean capacidadDeVuelo, int cantidadDeSonidos) {
        super(nombre, edad, peso, identificacion, nombrePropietario, numeroPropietario);
        this.especie = especie;
        this.raza = raza;
        this.tipoDePlumaje = tipoDePlumaje;
        this.capacidadDeVuelo = capacidadDeVuelo;
        this.cantidadDeSonidos = cantidadDeSonidos;
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

    public String getTipoDePlumaje() {
        return tipoDePlumaje;
    }

    public void setTipoDePlumaje(String tipoDePlumaje) {
        this.tipoDePlumaje = tipoDePlumaje;
    }

    public boolean isCapacidadDeVuelo() {
        return capacidadDeVuelo;
    }

    public void setCapacidadDeVuelo(boolean capacidadDeVuelo) {
        this.capacidadDeVuelo = capacidadDeVuelo;
    }

    public int getCantidadDeSonidos() {
        return cantidadDeSonidos;
    }

    public void setCantidadDeSonidos(int cantidadDeSonidos) {
        this.cantidadDeSonidos = cantidadDeSonidos;
    }

    //toString

    @Override
    public String toString() {
        return "Mascota{" +
                "especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                ", tipoDePlumaje='" + tipoDePlumaje + '\'' +
                ", capacidadDeVuelo=" + capacidadDeVuelo +
                ", cantidadDeSonidos=" + cantidadDeSonidos +
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
