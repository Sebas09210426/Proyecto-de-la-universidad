package controller;

import app.App;
import model.*;

import java.time.LocalDate;
import java.time.LocalTime;
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

    public boolean consultarExistenciaEstudiante(String identificacion) {
        return academia.buscarEstudiante(identificacion) != null;
    }

    public Estudiante buscarEstudiante(String identificacion) {
        return academia.buscarEstudiante(identificacion);
    }

    public boolean consultarExistenciaIdentificacion(String identificacion) {return academia.consultarExistenciaIdentificacion(identificacion);}

    public boolean crearProfesor(Profesor profesor) {
        return academia.agregarProfesor(profesor);
    }

    public boolean consultarExistenciaProfesor(String identificacion) {
        return academia.buscarProfesor(identificacion) != null;
    }

    public Profesor buscarProfesor(String identificacion) {
        return academia.buscarProfesor(identificacion);
    }

    public boolean eliminarProfesor(String identificacion) {
        return academia.eliminarProfesor(identificacion);
    }

    public LinkedList<Profesor> getProfesoresRegistrados() {
        return academia.getListaProfesores();
    }

    public LinkedList<Estudiante> getEstudiantesRegsitrados() {
        return academia.getListaEstudiantes();
    }

    public LinkedList<Usuario> getListaUsuarios() {
        return academia.getListaUsuarios();
    }

    public void agregarUsuario(String usuario, String contrasena, String identificacion, Rol rol) {
        academia.agregarUsuario(usuario, contrasena, identificacion, rol);
    }

    public boolean iniciarSesion(String usuario, String contrasena) {
        return academia.iniciarSesion(usuario, contrasena);
    }

    public LinkedList<Curso> getCursosRegistrados() {
        return academia.getListaCursos();
    }

    public boolean crearCurso(Curso nuevoCurso) {
        return academia.agregarCurso(nuevoCurso);
    }

    public boolean consultarExistenciaCurso(String codigo) {
        return academia.buscarCurso(codigo) != null;
    }

    public boolean eliminarCurso(String codigo) {
        return academia.eliminarCurso(codigo);
    }

    public LinkedList<Aula> getListaAulas() {return academia.getListaAulas();}

    public boolean asignarClase(ClaseAsignada claseAsignada, Aula aula) {
        return academia.asignarClase(claseAsignada, aula);
    }

    public boolean consultarDisponibilidadAula(String id, LocalDate fecha, LocalTime hora) {return academia.consultarDisponibilidadAula(id, fecha, hora);}

    public void guardarTodo() {
        DataManager.guardarDatos(academia);
    }
}