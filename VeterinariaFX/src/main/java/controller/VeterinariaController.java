package controller;

import model.Veterinaria;
import model.Propietario;

public class VeterinariaController {
    Veterinaria veterinaria;

    public VeterinariaController(Veterinaria veterinaria) {
        this.veterinaria = veterinaria;
    }

    public void agregarPropietario(Propietario propietario) {
        veterinaria.agregarPropietario(propietario);
    }
}
