public class Mascota {

    //Atributos
    private String nombre;
    private String especie;
    private String raza;
    private String edad;
    private String id;


    //Constructor
    public Mascota(String nombre, String especie, String raza, String edad, String id) {
        this.nombre = nombre;
        this.especie = especie;
        this.raza = raza;
        this.edad = edad;
        this.id = id;
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
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Mascota: \n" +
                "Nombre: " + nombre + "\n"
                + "Especie: " + especie + "\n"
                + "Raza: " + raza + "\n"
                + "Edad: " + edad + "\n"
                + "Id: " + id;
    }

}