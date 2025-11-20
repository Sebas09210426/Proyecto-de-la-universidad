package model;


public record Nota(double nota, String comentario, String codigo) {
    @Override
    public String toString() {
        return "{" + "\n" +
                "nota: " + nota +
                "comentario='" + comentario + "\n" +
                "codigo='" + codigo + "\n" +
                '}';
    }
}
