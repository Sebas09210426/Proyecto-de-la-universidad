package model;

import java.util.LinkedList;

public record Nota(double nota, String comentario) {
    @Override
    public String toString() {
        return "{" + "\n" +
                "nota: " + nota +
                ", comentario='" + comentario + "\n" +
                '}';
    }
}
