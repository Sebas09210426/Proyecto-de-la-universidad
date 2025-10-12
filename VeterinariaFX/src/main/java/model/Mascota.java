package model;

public abstract class Mascota {


    //Atributos
    protected String nombre;
    protected int edad;
    protected String identificacion;
    protected String nombrePropietario;
    protected String numeroPropietario;
    protected double peso;
    protected String testNull;


    //Constructor
    public Mascota(String nombre, int edad, double peso, String identificacion, String nombrePropietario, String numeroPropietario) {
        this.nombre = nombre;
        this.edad = edad;
        this.peso = peso;
        this.identificacion = identificacion;
        this.nombrePropietario = nombrePropietario;
        this.numeroPropietario = numeroPropietario;
        this.testNull = null;
    }


    //Getters y Setters
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public int getEdad() {
        return edad;
    }
    public void setEdad(int edad) {
        this.edad = edad;
    }
    public double getPeso() {
        return peso;
    }
    public void setPeso(double peso) {
        this.peso = peso;
    }
    public String getIdentificacion() {
        return identificacion;
    }
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    public String getNombrePropietario() {
        return nombrePropietario;
    }
    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }
    public String getNumeroPropietario() {
        return numeroPropietario;
    }
    public void setNumeroPropietario(String numeroPropietario) {
        this.numeroPropietario = numeroPropietario;
    }
    public String getTestNull() {
        return testNull;
    }

}
