package model;

import java.time.LocalTime;
import java.util.LinkedList;

public class Aula {
    private String id;
    private int capacidad;
    private LinkedList<ClaseAsignada> clasesAsignadas;

    public Aula(int capacidad) {
        this.capacidad = capacidad;
        this.clasesAsignadas = new LinkedList<>();
    }

    public int getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(int capacidad) {
        this.capacidad = capacidad;
    }

    public LinkedList<ClaseAsignada> getClasesAsignadas() {
        return clasesAsignadas;
    }

    public void setClasesAsignadas(LinkedList<ClaseAsignada> clasesAsignadas) {
        this.clasesAsignadas = clasesAsignadas;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean agregarClase(ClaseAsignada claseAsignada){
        for (ClaseAsignada a : clasesAsignadas) {
            if (a.getFecha().equals(claseAsignada.getFecha()) && a.getHora().equals(claseAsignada.getHora())) {
                return false;
            }
        }
        clasesAsignadas.addLast(claseAsignada);
        return true;
    }

    @Override
    public String toString() {
        return "Aula {" + "\n" +
                "ID: " + id + "\n" +
                "Capacidad: " + capacidad + "\n" +
                "Clases Asignadas: " + clasesAsignadas.size() + "\n" +
                "}";
    }
}
