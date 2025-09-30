public class Veterinaria {

    //Atributos
    private String nombre;
    private String nit;
    private String ubicacion;


    //Lista
    private Mascota[] listaMascotas;


    //Constructor
    public Veterinaria(String nombre, String nit, String ubicacion) {
        this.nombre = nombre;
        this.nit = nit;
        this.ubicacion = ubicacion;
        listaMascotas = new Mascota[15];
    }


    //Getters y Setters
    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getNit() {
        return nit;
    }
    public void setNit(String nit) {
        this.nit = nit;
    }
    public String getUbicacion() {
        return ubicacion;
    }
    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }


    //CRUD
    public boolean agregarMascota(Mascota mascota) {

        for (int i = 0; i < listaMascotas.length; i++)
            if (listaMascotas[i] == null) {
                listaMascotas[i] = mascota;
                return true;
            } else if (listaMascotas[i] != null && listaMascotas[i].getIdentificacion().equals(mascota.getIdentificacion())) {
                return false;
            }
        return false;
    }

    public Mascota buscarMascotaPorId(String identificacion) {
        for (Mascota mascota : listaMascotas)
            if (mascota != null && mascota.getIdentificacion().equals(identificacion)) {
                return mascota;
            }
        return null;
    }

    public boolean eliminarMascotaPorId(String identificacion) {
        for (int i = 0; i < listaMascotas.length; i++) {
            if (listaMascotas[i] != null && listaMascotas[i].getIdentificacion().equals(identificacion)) {
                listaMascotas[i] = null;
                return true;
            }
        }
        return false;
    }
}
