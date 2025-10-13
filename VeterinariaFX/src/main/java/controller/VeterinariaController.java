package controller;

import model.Veterinaria;
import model.Propietario;

public class VeterinariaController {
    Veterinaria veterinaria;

    public VeterinariaController(Veterinaria veterinaria) {
        this.veterinaria = veterinaria;
    }

    public boolean agregarPropietario(Propietario propietario) {
        return veterinaria.agregarPropietario(propietario);
    }

    public boolean eliminarPropietario(String numero) {
        return veterinaria.eliminarPropietario(numero);
    }

    public Propietario consultarPropietario(String numero) {
        return veterinaria.consultarPropietario(numero);
    }
}