package viewController;

import app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.IOException;

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
}
