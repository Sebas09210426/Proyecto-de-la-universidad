public class Perro extends Mascota {
    //Atributos
    private String especie;
    private String raza;
    private String tamano;
    private int nivelDeAdiestramiento;
    private int necesidadDePaseosDiarioas;

    //Constructor
    public Perro(String nombre, int edad, double peso, String identificacion, String nombrePropietario, String numeroPropietario, String especie, String raza, String tamano, int nivelDeAdiestramiento, int necesidadDePaseosDiarioas) {
        super(nombre, edad, peso, identificacion, nombrePropietario, numeroPropietario);
        this.especie = especie;
        this.raza = raza;
        this.tamano = tamano;
        this.nivelDeAdiestramiento = nivelDeAdiestramiento;
        this.necesidadDePaseosDiarioas = necesidadDePaseosDiarioas;
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

    public String getTamano() {
        return tamano;
    }

    public void setTamano(String tamano) {
        this.tamano = tamano;
    }

    public int getNivelDeAdiestramiento() {
        return nivelDeAdiestramiento;
    }

    public void setNivelDeAdiestramiento(int nivelDeAdiestramiento) {
        this.nivelDeAdiestramiento = nivelDeAdiestramiento;
    }

    public int getNecesidadDePaseosDiarioas() {
        return necesidadDePaseosDiarioas;
    }

    public void setNecesidadDePaseosDiarioas(int necesidadDePaseosDiarioas) {
        this.necesidadDePaseosDiarioas = necesidadDePaseosDiarioas;
    }

    @Override
    public String toString() {
        return "Mascota: {" +
                "especie='" + especie + '\'' +
                ", raza='" + raza + '\'' +
                ", tamano='" + tamano + '\'' +
                ", nivelDeAdiestramiento=" + nivelDeAdiestramiento +
                ", necesidadDePaseosDiarioas=" + necesidadDePaseosDiarioas +
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
