package viewController;

import app.App;
import controller.AcademiaController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Rol;
import model.Usuario;

import java.io.IOException;
import java.util.Optional;

public class PrimaryViewController {
    private App app;
    private AcademiaController academiaController;
    private VentanaPersonalEstudianteViewController ventanaPersonalEstudianteViewController;

    @FXML
    private TextField usuarioTextField;

    @FXML
    private PasswordField contrasenaPasswordField;

    @FXML
    private Button iniciarSesionButton;

    public void setApp(App app) {
        this.app = app;
    }

    public void setAcademiaController(AcademiaController academiaController) {
        this.academiaController = academiaController;
    }

    @FXML
    public void initialize() {

        //Darle las funciones al boton
        iniciarSesionButton.setOnAction(event -> {
            //Verificar que los campos esten llenos
            if (usuarioTextField.getText().isEmpty() || contrasenaPasswordField.getText().isEmpty()) {
                mostrarAlerta("Campo Vacío", "Por favor, diligencie los datos");
                return;
            }
            //Cambiar de ventana si el login es exitoso
            iniciarSesion(usuarioTextField.getText(), contrasenaPasswordField.getText());
        });
    }

    private void iniciarSesion(String usuario, String contrasena) {
        if (academiaController.iniciarSesion(usuario, contrasena)) {
            Usuario usuarioActual = null;
            Rol rolUsuario = Rol.DEFAULT;
            for (Usuario u : academiaController.getListaUsuarios()) {
                if (u.getUsuario().equals(usuario)) {
                    usuarioActual = u;
                    rolUsuario = u.getRol();
                    break;
                }
            }
            switch (rolUsuario) {
                case ADMINISTRADOR:
                    try {
                        app.abrirVentanaPrincipal();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case ESTUDIANTE:
                    try {
                        app.abrirVentanaPersonalEstudiante(usuarioActual);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;
                case PROFESOR:
                    try {
                        app.abrirVentanaPersonalProfesor(usuarioActual);
                    }  catch (IOException e) {
                        e.printStackTrace();
                    }
            }
        } else {
            mostrarAlerta("Usuario o contraseña incorrectos", "Por favor, verifique los datos ingresados. Si no tiene una cuenta registrada, por favor contacte a un administrador académico");
        }
    }



    public static void mostrarAlerta(String titulo, String mensaje){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static void mostrarMensaje(String titulo, String mensaje){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    public static boolean mostrarConfirmacion(String titulo, String mensaje){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(titulo);
        alert.setContentText(mensaje);

        //Obtener seleccion del usuario
        Optional<ButtonType> confirmacion = alert.showAndWait();
        //Retornar seleccion
        return confirmacion.isPresent() && confirmacion.get() == ButtonType.OK;
    }
}
