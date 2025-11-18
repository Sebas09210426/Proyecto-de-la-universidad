package viewController;

import app.App;
import controller.AcademiaController;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import model.Rol;
import model.Usuario;

import java.io.IOException;

import static viewController.PrimaryViewController.mostrarAlerta;
import static viewController.PrimaryViewController.mostrarMensaje;

public class ActualizarCredencialesViewController {
    private App app;
    private Usuario usuarioActual;
    private AcademiaController academiaController;

    @FXML
    private TextField usuarioTextField;

    @FXML
    private TextField confirmarUsuarioTextField;

    @FXML
    private PasswordField contrasenaPasswordField;

    @FXML
    private PasswordField confirmarContrasenaPasswordField;

    @FXML
    private Button actualizarButton;

    public void setApp(App app) {
        this.app = app;
    }

    public void setUsuario(Usuario usuarioActual) {
        this.usuarioActual = usuarioActual;
    }

    public void setAcademiaController(AcademiaController academiaController) {this.academiaController = academiaController;}

    @FXML
    public void initialize() {
        //Darle las funciones al boton
        actualizarButton.setOnAction(event -> {
            //Verificar que los campos esten llenos
            if (usuarioTextField.getText().isEmpty() || contrasenaPasswordField.getText().isEmpty() ||  confirmarContrasenaPasswordField.getText().isEmpty() || confirmarUsuarioTextField.getText().isEmpty()) {
                mostrarAlerta("Campo Vacío", "Por favor, diligencie todos los datos");
                return;
            }
            //Verificar que sean las mismas credenciales
            if (!usuarioTextField.getText().equals(confirmarUsuarioTextField.getText()) || !contrasenaPasswordField.getText().equals(confirmarContrasenaPasswordField.getText())) {
                mostrarAlerta("Usuario o contraseña incorrectos", "Por favor, verifique los datos diligenciados");
                return;
            }
            //Actualizar las credenciales
            if (actualizarCredenciales(usuarioTextField.getText(), contrasenaPasswordField.getText())) {
                mostrarMensaje("Credenciales actualizadas", "Credenciales actualizadas exitosamente");
                //Cambiar de ventana si actualiza los datos
                if (usuarioActual.getRol() == Rol.ESTUDIANTE) {
                    try {
                        app.abrirVentanaPersonalEstudiante(usuarioActual);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (usuarioActual.getRol() == Rol.ADMINISTRADOR) {
                    try {
                        app.abrirVentanaPrincipal();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                } else if (usuarioActual.getRol() == Rol.PROFESOR) {
                    try {
                        app.abrirVentanaPersonalProfesor(usuarioActual);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            } else {
                mostrarAlerta("Usuario no disponible", "El usuario no se encuentra disponible, intente con otro");
            }
        });

    }

    private boolean actualizarCredenciales(String usuario, String contrasena) {
        for (Usuario u : academiaController.getListaUsuarios()) {
            if (u.getUsuario().equals(usuario)) {
                return false;
            }
        }
        this.usuarioActual.setUsuario(usuario);
        this.usuarioActual.setContrasena(contrasena);
        this.usuarioActual.setPrimerInicio(false);
        return true;
    }

}
