package viewController;

import app.App;
import controller.AcademiaController;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class VentanaPersonalEstudianteViewController {

    App app;
    AcademiaController academiaController;

    @FXML
    private ChoiceBox<String> gestionChoiceBox;

    @FXML
    private VBox requisitosDeGestionDeEstudiantesVBox;

    @FXML
    private TitledPane informacionGestionEstudianteTitledPane;

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    private void initialize() {
        //Añadir opciones al ChoiceBox
        gestionChoiceBox.getItems().addAll("Consultar", "Actuailizar");
        gestionChoiceBox.setValue("Seleccione una opción");


    }

}
