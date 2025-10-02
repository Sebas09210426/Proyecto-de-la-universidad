public class Propietario {

    //Atributos
    private String nombres;
    private String apellidos;
    private String numero;
    private String dirrecion;
    private String mascota;


    //Constructor
    public Propietario( String mascota, String nombre, String apellidos, String numero, String dirrecion) {
        this.mascota = mascota;
        this.nombres = nombre;
        this.apellidos = apellidos;
        this.numero = numero;
        this.dirrecion = dirrecion;
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
