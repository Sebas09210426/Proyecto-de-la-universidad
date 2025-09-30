public class Mascota {

    //Atributos
    private String nombre;
    private String especie;
    private String raza;
    private String edad;
    private String identificacion;
    private String nombrePropietario;
    private String numeroPropietario;
    private String testNull;


    //Constructor
    public Mascota(String nombre,String especie, String raza, String edad, String identificacion, String nombrePropietario, String numeroPropietario) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
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
    public String getEdad() {
        return edad;
    }
    public void setEdad(String edad) {
        this.edad = edad;
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

    @Override
    public String toString() {
        return "Mascota{" +
                "nombre='" + nombre + "\n" +
                ", especie='" + especie + "\n" +
                ", raza='" + raza + "\n" +
                ", edad=" + edad +
                ", identificacion='" + identificacion + "\n" +
                ", nombrePropietario='" + nombrePropietario + "\n" +
                ", numeroPropietario='" + numeroPropietario + "\n" +
                '}';
    }
}
