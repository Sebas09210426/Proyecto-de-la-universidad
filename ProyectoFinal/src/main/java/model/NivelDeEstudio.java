package model;

public enum NivelDeEstudio {
    BASICO("Básico"), MEDIO("Medio"), AVANZADO("Avanzado");

    private final String nivelDeEstudio;

    NivelDeEstudio(String nivelDeEstudio) {
        this.nivelDeEstudio = nivelDeEstudio;
    }

    @Override
    public String toString()
    {
        return this.nivelDeEstudio;
    }
}
