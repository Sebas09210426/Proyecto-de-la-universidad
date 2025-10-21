package viewController;

import app.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;

import java.io.IOException;

import static viewController.PrimaryViewController.mostrarAlerta;

public class VentanaPrincipalViewController {
    App app;


    @FXML
    ChoiceBox<String> choiceBox;

    @FXML
    Button boton;

    public void setApp(App app) {
        this.app = app;
    }


    @FXML
    public void initialize() {
        //Agregar opciones al choiceBox
        choiceBox.getItems().addAll("Gestionar Estudiantes", "Gestionar Profesores");
        choiceBox.setValue("Seleccione una opción");

        //Inicializar la funcion de cambiar ventanas en el boton
        boton.setOnAction((event) -> {
            try {
                cambiarVentanaDeGestion(choiceBox.getValue());
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        //Obtener cambio de seleccion del choiceBox
        choiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            //Asignar la funcion de cambiar ventanas al boton
            boton.setOnAction(event -> {
                try {
                    cambiarVentanaDeGestion(newValue);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        });

    }

    //Cambiar de ventana dependiendo de la seleccion del choiceBox
    private void cambiarVentanaDeGestion(String newValue) throws IOException {
        switch (newValue) {
            case "Gestionar Estudiantes":
                app.abrirVentanaEstudiantes();
                break;
            case "Gestionar Profesores":
                app.abrirVentanaProfesores();
                break;
            default:
                mostrarAlerta("Opción no seleccionada", "Por favor, seleccione una opción");
                break;
        }
    }
}
