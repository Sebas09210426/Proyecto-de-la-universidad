package viewController;

import app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.Optional;

public class PrimaryViewController {
    private App app;

    @FXML
    private Label mensaje;

    @FXML
    private Button boton;

    public void setApp(App app) {
        this.app = app;
    }

    public void abrirVentanaPrincipal() throws IOException {
        app.abrirVentanaPrincipal();
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
