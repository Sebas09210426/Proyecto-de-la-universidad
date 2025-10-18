package viewController;

import app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PrimaryViewController {
    private App app;

    @FXML
    private Label mensaje;

    public void setApp(App app) {
        this.app = app;
    }
}
