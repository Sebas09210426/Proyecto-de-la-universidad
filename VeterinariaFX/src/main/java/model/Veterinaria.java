package model;


import java.util.LinkedList;

public class Veterinaria {

    //Atributos
    private String nombre;
    private String nit;
    private String ubicacion;


    //Listas
    private LinkedList<Mascota> listaMascotas;
    private LinkedList<Propietario> listaPropietarios;


    //Constructor
    public Veterinaria(String nombre, String nit, String ubicacion) {
        this.nombre = nombre;
        this.nit = nit;
        this.ubicacion = ubicacion;
        this.listaMascotas = new LinkedList<>();
        this.listaPropietarios = new LinkedList<>();
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

/*
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
*/
    public boolean agregarPropietario(Propietario propietario) {
        for (int i =  0; i< listaPropietarios.size(); i++) {
            if(propietario.getNumero().equals(listaPropietarios.get(i).getNumero())){
                return false;
            }
        }
        listaPropietarios.add(propietario);
        return true;
    }

    public boolean eliminarPropietario(String numero) {
        for (int i =  0; i< listaPropietarios.size(); i++) {
            if(listaPropietarios.get(i).getNumero().equals(numero)){
                listaPropietarios.remove(i);
                return true;
            }
        }
        return false;
    }

    public Propietario consultarPropietario(String numero) {
        for (Propietario propietario : listaPropietarios) {
            if (propietario != null && propietario.getNumero().equals(numero)) {
                return propietario;
            }
        }
        return null;
    }
/*
    public Mascota buscarMascotaPorId(String id) {
        for (Mascota mascota : listaMascotas) {
            if (mascota != null && mascota.getIdentificacion().equals(id)) {
                return mascota;
            }
        }
        return null;
    }

    public boolean eliminarMascota(String id) {
        for (int i = 0; i < listaMascotas.length; i++) {
            if (listaMascotas[i] != null && listaMascotas[i].getIdentificacion().equals(id)) {
                listaMascotas[i] = null;
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

*/
}
