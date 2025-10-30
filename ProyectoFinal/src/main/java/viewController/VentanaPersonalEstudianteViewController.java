package viewController;

import app.App;
import controller.AcademiaController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Usuario;

import java.io.IOException;

import static viewController.PrimaryViewController.*;

public class VentanaPersonalEstudianteViewController {

    private App app;
    private AcademiaController academiaController;
    private Usuario usuarioActual;

    @FXML
    private ChoiceBox<String> gestionChoiceBox;

    @FXML
    private VBox requisitosDeGestionDeEstudianteVBox;

    @FXML
    private TitledPane informacionGestionEstudianteTitledPane;

    @FXML
    private Button cerrarSesionButton;

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    private void initialize() {
        //Darle una funcion al boton de cerrar sesion
        cerrarSesionButton.setOnAction(event -> {
            if(mostrarConfirmacion("Confirmación", "¿Seguro que quiere salir de la ventana actual?")) {
                try {
                    volverAlMenuAnterior();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        //Añadir opciones al ChoiceBox
        gestionChoiceBox.getItems().addAll("Consultar", "Actualizar");
        gestionChoiceBox.setValue("Seleccione una opción");

        //Obtener el cambio de seleccion del ChoiceBox
        gestionChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Consultar":
                    mostrarRequisitosConsultar();
                    break;
                case "Actualizar":
                    mostrarRequisitosActualizar();
                    break;
            }
        });
    }

    private void mostrarRequisitosConsultar() {
        //Crear elementos de requisitos
        Label label = new Label("Consultar");

        Label consultaLabel = new Label("Seleccione su consulta:");
        ChoiceBox<String> consultaChoiceBox = new ChoiceBox<>();
        consultaChoiceBox.getItems().addAll("Consultar horario", "Consultar cursos", "Consultar notas", "Consultar asistencia", "Consultar comentarios");
        consultaChoiceBox.setValue("Seleccione una opción");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(consultaLabel, 0, 0);
        gridPane.add(consultaChoiceBox, 1, 0);

        Button boton = new Button("Consultar");
        boton.setOnAction(event -> {
            //Decidir que mostrar dependiendo de la seleccion
            switch(consultaChoiceBox.getValue()) {
                case "Consultar horario":
                    mostrarMensaje("Consultar horario", "Hasta aqui todo bien");
                    break;
                case "Consultar cursos":
                    mostrarMensaje("Consultar cursos", "Hasta aqui bien");
                    break;
                case "Consultar notas":
                    mostrarMensaje("Consultar notas", "Hasta aqui bien");
                    break;
                case "Consultar asistencia":
                    mostrarMensaje("Consultar asistencia", "Hasta aqui bien");
                    break;
                case "Consultar comentarios":
                    mostrarMensaje("Consultar comentarios", "Hasta bien");
                    break;
                default:
                    mostrarAlerta("Consulta no seleccionada", "Por favor, seleccione su consulta");
                    break;
            }
        });

        requisitosDeGestionDeEstudianteVBox.getChildren().clear();
        requisitosDeGestionDeEstudianteVBox.getChildren().addAll(label, gridPane, boton);
    }

    private void mostrarRequisitosActualizar() {
        //Crear elementos de requisitos
        Label label = new Label("Actualizar");

        Label consultaLabel = new Label("Seleccione su consulta:");
        ChoiceBox<String> consultaChoiceBox = new ChoiceBox<>();
        consultaChoiceBox.getItems().addAll("Actualizar nombre", "Actualizar apellido", "Actualizar identificación");
        consultaChoiceBox.setValue("Seleccione una opción");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(consultaLabel, 0, 0);
        gridPane.add(consultaChoiceBox, 1, 0);

        Button boton = new Button("Consultar");
        boton.setOnAction(event -> {
            //Decidir que mostrar dependiendo de la seleccion
            switch(consultaChoiceBox.getValue()) {
                case "Actualizar nombre":
                    mostrarMensaje("Actualizar nombre", "Hasta aqui todo bien");
                    break;
                case "Actualizar apellido":
                    mostrarMensaje("Actualizar apellido", "Hasta aqui bien");
                    break;
                case "Actualizar identificación":
                    mostrarMensaje("Consultar identificacion", "Hasta aqui bien");
                    break;
                default:
                    mostrarAlerta("Opción no seleccionada", "Por favor, seleccione su actualización");
                    break;
            }
        });

        requisitosDeGestionDeEstudianteVBox.getChildren().clear();
        requisitosDeGestionDeEstudianteVBox.getChildren().addAll(label, gridPane, boton);
    }

    private void volverAlMenuAnterior() throws IOException {
        app.abrirVentanaPrincipal();
    }

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
    }
}
