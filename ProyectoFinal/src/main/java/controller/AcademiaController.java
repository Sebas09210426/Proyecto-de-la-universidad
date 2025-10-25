package controller;

import model.Academia;
import model.Estudiante;

public class AcademiaController {
    //Vincular academia con el viewController
    Academia academia;
    public AcademiaController(Academia academia) {
        this.academia = academia;
    }

    public boolean crearEstudiante(Estudiante estudiante) {
        return academia.agregarEstudiante(estudiante);
    }

    public boolean eliminarEstudiante(String identificacion) {
        return academia.eliminarEstudiante(identificacion);
    }

    public boolean consultarExistenciaEstudiante(String identificacion) {
        return academia.buscarEstudiante(identificacion) != null;
    }

    public Estudiante buscarEstudiante(String identificacion) {
        return academia.buscarEstudiante(identificacion);
    }

    //Conectar funciondes del model con el viewController
}
