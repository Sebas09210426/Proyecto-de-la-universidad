package model;

public class Propietario {

    //Atributos
    private String nombres;
    private String apellidos;
    private String numero;
    private String dirrecion;
    private Mascota mascota;
    private int fidelidad;


    //Constructor
    public Propietario(Mascota mascota, String nombre, String apellidos, String numero, String dirrecion, int fidelidad) {
        this.mascota = mascota;
        this.nombres = nombre;
        this.apellidos = apellidos;
        this.numero = numero;
        this.dirrecion = dirrecion;
        this.fidelidad = fidelidad;
    }


    //Getters y Setters
    public String getNombres() {
        return nombres;
    }
    public void setNombres(String nombres) {
        this.nombres = nombres;
    }
    public String getApellidos() {
        return apellidos;
    }
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }
    public String getNumero() {
        return numero;
    }
    public void setNumero(String numero) {
        this.numero = numero;
    }
    public String getDirrecion() {
        return dirrecion;
    }
    public void setDirrecion(String dirrecion) {
        this.dirrecion = dirrecion;
    }
    public Mascota getMascota() {
        return this.mascota;
    }
    public void setMascota(Mascota mascota) {
        this.mascota = mascota;
    }
    public int getFidelidad() { return fidelidad;}
    public void setFidelidad(int fidelidad) { this.fidelidad = fidelidad;}

    @Override
    public String toString() {
        return "Propietario:\n"
                + "Nombres: " + nombres + "\n"
                + "Apellidos: " + apellidos + "\n"
                + "Numero: " + numero + "\n"
                + "Dirrecion: " + dirrecion + "\n"
                + "Mascota: " + mascota;
    }
}
