package app;

import controller.AcademiaController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.*;
import viewController.*;

import java.io.IOException;
import java.util.LinkedList;

public class App extends Application {
    //Crear la academia de la aplicacion
    public static Academia academia = DataManager.cargarDatos();
    //Conseguir el controler de la academia
    private AcademiaController academiaController;

    //Iniciar la aplicacion
    Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        if (academia == null) {academia = new Academia("Muscial UQ", "123456789");}
        academiaController = new AcademiaController(academia);
        cargarDatosInicialesSiEsNecesario();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/primary.fxml"));
        Scene scene = new Scene(loader.load());
        PrimaryViewController primaryViewController = loader.getController();
        primaryViewController.setAcademiaController(academiaController);
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
        ventanaPrincipalViewController.setAcademiaController(academiaController);
        ventanaPrincipalViewController.setApp(this);
        stage.setScene(scene);
        stage.setTitle("Musical");
        stage.show();
    }

    //Cambiar a la ventana de estudiantes
    public void abrirVentanaEstudiantes() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/ventanaEstudiantes.fxml"));
        Scene scene = new Scene(loader.load());
        VentanaEstudiantesViewController ventanaEstudiantesViewController = loader.getController();
        ventanaEstudiantesViewController.setAcademiaController(academiaController);
        ventanaEstudiantesViewController.cargarDatos();
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
        ventanaProfesoresViewController.setAcademiaController(academiaController);
        ventanaProfesoresViewController.cargarDatos();
        ventanaProfesoresViewController.setApp(this);
        stage.setScene(scene);
        stage.setTitle("Musical getión de profesores");
        stage.show();
    }

    //Cambiar a la ventana personal del estudiante
    public void abrirVentanaPersonalEstudiante(Usuario usuario) throws IOException  {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/ventanaPersonalEstudiante.fxml"));
        Scene scene = new Scene(loader.load());
        VentanaPersonalEstudianteViewController ventanaPersonalEstudianteViewController = loader.getController();
        ventanaPersonalEstudianteViewController.setAcademiaController(academiaController);
        ventanaPersonalEstudianteViewController.setApp(this);
        ventanaPersonalEstudianteViewController.setUsuario(usuario);
        stage.setScene(scene);
        stage.setTitle("Gestion personal estudiante");
        stage.show();
        ventanaPersonalEstudianteViewController.cargarDatosUsuario();
        ventanaPersonalEstudianteViewController.cargarDatos();
    }

    //Cambiar a la ventana personal del profesor
    public void abrirVentanaPersonalProfesor(Usuario usuario) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/ventanaPersonalProfesor.fxml"));
        Scene scene = new Scene(loader.load());
        VentanaPersonalProfesorViewController ventanaPersonalProfesorViewController = loader.getController();
        ventanaPersonalProfesorViewController.setAcademiaController(academiaController);
        ventanaPersonalProfesorViewController.setApp(this);
        stage.setScene(scene);
        stage.setTitle("Gestion personal profesor");
        stage.show();
        ventanaPersonalProfesorViewController.setUsuario(usuario);
        ventanaPersonalProfesorViewController.cargarDatosUsuario();
        ventanaPersonalProfesorViewController.cargarDatos();
    }

    //Volver a abrir el login
    public void abrirPrimary() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/primary.fxml"));
        Scene scene = new Scene(loader.load());
        PrimaryViewController primaryViewController = loader.getController();
        primaryViewController.setAcademiaController(academiaController);
        primaryViewController.setApp(this);
        stage.setScene(scene);
        stage.setTitle("Musical");
        stage.show();
    }

    //Abrir la ventana de nuevas credenciales
    public void abrirActualizarCredenciales(Usuario usuario) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/actualizarCredenciales.fxml"));
        Scene scene = new Scene(loader.load());
        ActualizarCredencialesViewController actualizarCredencialesViewController = loader.getController();
        actualizarCredencialesViewController.setAcademiaController(academiaController);
        actualizarCredencialesViewController.setApp(this);
        actualizarCredencialesViewController.setUsuario(usuario);
        stage.setScene(scene);
        stage.setTitle("Actualizar credenciales");
        stage.show();
    }

    //Cargar datos si no se encuentran
    public static void cargarDatosInicialesSiEsNecesario() {
        if (academia.getListaUsuarios().isEmpty()) {
            //Crear usuarios de ejemplo
            academia.agregarUsuario("admin", "admin", "12345", Rol.ADMINISTRADOR);

            Estudiante nuevoEstudiante = new Estudiante("estudiante", "estudiante", "11111", NivelDeEstudio.BASICO, Instrumento.CANTO);
            academia.agregarEstudiante(nuevoEstudiante);
            academia.agregarUsuario("estudiante", "estudiante", "11111", Rol.ESTUDIANTE);

            Profesor nuevoProfesor = new Profesor("profesor", "profesor", "00000");
            academia.agregarProfesor(nuevoProfesor);
            academia.agregarUsuario("profesor", "profesor", "00000", Rol.PROFESOR);
        }
        if (academia.getListaAulas().isEmpty()) {
            //Crer aulas de ejemplo
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
    }


    @Override
    public void stop() {
        academiaController.guardarTodo();
    }
}
