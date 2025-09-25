public class Veterinaria {

    //Atributos
    private String nombre;
    private String nit;
    private String ubicacion;


    //Listas
    private Mascota[] listaMascotas;
    private Propietario[] listaPropietarios;


    //Constructor
    public Veterinaria(String nombre, String nit, String ubicacion) {
        this.nombre = nombre;
        this.nit = nit;
        this.ubicacion = ubicacion;
        this.listaMascotas = new Mascota[15];
        this.listaPropietarios = new Propietario[15];
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
    public boolean agregarMascota(String nombre, String especie, String raza, String edad, String id) {
        Mascota nuevaMascota = new Mascota(nombre, especie, raza, edad, id);

        for (int i = 0; i < listaMascotas.length; i++) {
            if (listaMascotas[i] != null && listaMascotas[i].getId().equals(id)) {
                return false;
            } else if (listaMascotas[i] == null) {
                listaMascotas[i] = nuevaMascota;
                return true;
            }
        }
        return false;
    }

    public boolean agregarPropietario(String nombreMascota, String nombres, String apellidos, String numero, String direccion) {
        Propietario nuevoPropietario = new Propietario(nombreMascota, nombres, apellidos, numero, direccion);

        for (int i = 0; i < listaPropietarios.length; i++) {
            if (listaPropietarios[i] != null && listaPropietarios[i].getNumero().equals(numero)) {
                return false;
            } else if (listaPropietarios[i] == null) {
                listaPropietarios[i] = nuevoPropietario;
                return true;
            }
        }
        return false;
    }

    public Mascota buscarMascotaPorId(String id) {
        for (Mascota mascota : listaMascotas) {
            if (mascota != null && mascota.getId().equals(id)) {
                return mascota;
            }
        }
        return null;
    }

    public Propietario buscarPropietarioPorNumero(String numero) {
        for (Propietario propietario : listaPropietarios) {
            if (propietario != null && propietario.getNumero().equals(numero)) {
                return propietario;
            }
        }
        return null;
    }

    public boolean eliminarMascota(String id) {
        for (int i = 0; i < listaMascotas.length; i++) {
            if (listaMascotas[i] != null && listaMascotas[i].getId().equals(id)) {
                listaMascotas[i] = null;
                return true;
            }
        }
        return false;
    }

    public boolean eliminarPropietario(String numero) {
        for (int i = 0; i < listaPropietarios.length; i++) {
            if (listaPropietarios[i] != null && listaPropietarios[i].getNumero().equals(numero)) {
                listaPropietarios[i] = null;
                return true;
            }
        }
        return false;
    }

    public double evaluarCostoConsulta(double costo, String id) {
        Mascota mascota = buscarMascotaPorId(id);
        if (mascota != null) {
            String especie = mascota.getEspecie();
            switch (especie) {
                case "perro":
                    return costo + 15000;
                case "gato":
                    return costo + 10000;
                case"pajaro":
                    return costo + 20000;
                default:
                    return costo + 25000;
            }
        }
        return 0;
    }
}
