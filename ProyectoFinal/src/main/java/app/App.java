package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import viewController.*;

import java.io.IOException;
import java.util.LinkedList;

public class App extends Application {
    //Crear la academia de la aplicacion
    private static LinkedList<Aula> listaAulas = new LinkedList<>();
    public static Academia academia = new Academia("UQ Música", "12345");

    //Crear usuarios de ejemplo y aulas de ejemplo
    static {
        academia.agregarUsuario("admin", "admin", "12345", Rol.ADMINISTRADOR);

        Estudiante nuevoEstudiante = new Estudiante("estudiante", "estudiante", "11111", NivelDeEstudio.BASICO, Instrumento.CANTO);
        academia.agregarEstudiante(nuevoEstudiante);
        academia.agregarUsuario("estudiante", "estudiante", "11111", Rol.ESTUDIANTE);

        Profesor nuevoProfesor = new Profesor("profesor", "profesor", "00000");
        academia.agregarProfesor(nuevoProfesor);
        academia.agregarUsuario("profesor", "profesor", "00000", Rol.PROFESOR);

        Aula aula1 = new Aula(20);
        aula1.setId("001");
        Aula aula2 = new Aula(40);
        aula2.setId("002");
        Aula aula3 = new Aula(35);
        aula3.setId("003");
        Aula aula4 = new Aula(15);
        aula4.setId("004");
        Aula aula5 = new Aula(30);
        aula5.setId("005");
        academia.agregarAula(aula1);
        academia.agregarAula(aula2);
        academia.agregarAula(aula3);
        academia.agregarAula(aula4);
        academia.agregarAula(aula5);
    }

    //Iniciar la aplicacion
    Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/primary.fxml"));
        Scene scene = new Scene(loader.load());
        PrimaryViewController primaryViewController = loader.getController();
        primaryViewController.setApp(this);
        this.stage = stage;
        this.stage.setScene(scene);
        this.stage.setTitle("Musical");
        this.stage.show();
    }

    public static void main(String[] args) {launch(args);}

    //Cambiar de ventanas

    //Cambiar a la ventana principal
    public void abrirVentanaPrincipal() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/ventanaPrincipal.fxml"));
        Scene scene = new Scene(loader.load());
        VentanaPrincipalViewController ventanaPrincipalViewController = loader.getController();
        ventanaPrincipalViewController.setApp(this);
        stage.setScene(scene);
        stage.setTitle("Musical");
        stage.show();
    }

    //Cambiar a la ventana de estudiantes
    public void abrirVentanaEstudiantes() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/ventanaEstudiantes.fxml"));
        Scene scene = new Scene(loader.load());
        VentanaEstudiantesViewController  ventanaEstudiantesViewController = loader.getController();
        ventanaEstudiantesViewController.setApp(this);
        stage.setScene(scene);
        stage.setTitle("Musical gestión de estudiantes");
        stage.show();
    }

    //Cambiar a la ventana de profesores
    public void abrirVentanaProfesores() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/ventanaProfesores.fxml"));
        Scene scene = new Scene(loader.load());
        VentanaProfesoresViewController ventanaProfesoresViewController = loader.getController();
        ventanaProfesoresViewController.setApp(this);
        stage.setScene(scene);
        stage.setTitle("Musical getión de profesores");
        stage.show();
    }

    public void abrirVentanaPersonalEstudiante(Usuario usuario) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/ventanaPersonalEstudiante.fxml"));
        Scene scene = new Scene(loader.load());
        VentanaPersonalEstudianteViewController ventanaPersonalEstudianteViewController = loader.getController();
        ventanaPersonalEstudianteViewController.setApp(this);
        ventanaPersonalEstudianteViewController.setUsuario(usuario);
        ventanaPersonalEstudianteViewController.cargarDatosUsuario();
        stage.setScene(scene);
        stage.setTitle("Gestion personal estudiante");
        stage.show();
    }

    public void abrirVentanaPersonalProfesor(Usuario usuario) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/ventanaPersonalProfesor.fxml"));
        Scene scene = new Scene(loader.load());
        VentanaPersonalProfesorViewController ventanaPersonalProfesorViewController = loader.getController();
        ventanaPersonalProfesorViewController.setApp(this);
        ventanaPersonalProfesorViewController.setUsuario(usuario);
        ventanaPersonalProfesorViewController.cargarDatosUsuario();
        stage.setScene(scene);
        stage.setTitle("Gestion personal profesor");
        stage.show();
    }

    public void abrirPrimary() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/primary.fxml"));
        Scene scene = new Scene(loader.load());
        PrimaryViewController primaryViewController = loader.getController();
        primaryViewController.setApp(this);
        stage.setScene(scene);
        stage.setTitle("Musical");
        stage.show();
    }
}
