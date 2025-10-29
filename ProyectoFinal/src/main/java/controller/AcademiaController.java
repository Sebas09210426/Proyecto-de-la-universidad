package controller;

import model.Academia;
import model.Estudiante;
import model.Profesor;

import java.util.LinkedList;

public class AcademiaController {
    //Vincular academia con el viewController
    Academia academia;

    //Conectar funciones del model con el viewController
    public AcademiaController(Academia academia) {
        this.academia = academia;
    }

    public boolean crearEstudiante(Estudiante estudiante) {
        return academia.agregarEstudiante(estudiante);
    }

    public boolean eliminarEstudiante(String identificacion) {
        return academia.eliminarEstudiante(identificacion);
    }

    public boolean consultarExistenciaEstudiante(String identificacion) {return academia.buscarEstudiante(identificacion) != null;}

    public Estudiante buscarEstudiante(String identificacion) {
        return academia.buscarEstudiante(identificacion);
    }

    public boolean crearProfesor(Profesor profesor) {return academia.agregarProfesor(profesor);}

    public boolean consultarExistenciaProfesor(String identificacion) {return academia.buscarProfesor(identificacion) != null;}

    public Profesor buscarProfesor(String identificacion) {return academia.buscarProfesor(identificacion);}

    public boolean eliminarProfesor(String identificacion) {return academia.eliminarProfesor(identificacion);}

    public LinkedList<Profesor> getProfesoresRegistrados() {
        return academia.getListaProfesores();
    }

    public LinkedList<Estudiante> getEstudiantesRegsitrados() {return academia.getListaEstudiantes();}
}
