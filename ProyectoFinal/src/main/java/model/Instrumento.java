package model;

import java.io.Serializable;

public enum Instrumento implements Serializable {
    PIANO("Piano"), GUITARRA("Guitarra"), VIOLIN("Violín"), CANTO("Canto"), FLAUTA("Flauta"), OTRO("Otro"), DEFAULT("Predeterminado");

    private final String instrumento;

    Instrumento(String instrumento) {
        this.instrumento = instrumento;
    }

    @Override
    public String toString() {
        return instrumento;
    }
}
