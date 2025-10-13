package viewController;

import app.App;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.awt.*;
import java.util.ResourceBundle;

public class primaryViewController {

    App app;

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    private Button primaryButton;

    public void abrirCrudVeterinaria() {
        app.abrirCrudVeterinaria();
    }
}
