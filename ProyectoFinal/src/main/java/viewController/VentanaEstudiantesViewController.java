package viewController;

import app.App;
import controller.AcademiaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Estudiante;
import model.Instrumento;
import model.NivelDeEstudio;
import javafx.scene.control.*;

public class VentanaEstudiantesViewController {

    App app;

    //Adquirir los elementos del fxml

    @FXML
    ChoiceBox<String> gestionChoiceBox;

    @FXML
    VBox requisitosDeGestionDeEstudiantesVBox;

    @FXML
    private TableView<Estudiante> estudiantesRegistradosTableView;

    @FXML
    private TableColumn<Estudiante, String> nombreEstudiantesRegistradosTableColumn;

    @FXML
    private TableColumn<Estudiante, String> apellidoEstudiantesRegistradosTableColumn;

    @FXML
    private TableColumn<Estudiante, String> identificacionEstudiantesRegistradosTableColumn;

    @FXML
    private TableColumn<Estudiante, String> nivelDeEstudioEstudiantesRegistradosTableColumn;

    @FXML
    private TableColumn<Estudiante, String> instrumentoEstudiantesRegistradosTableColumn;

    private ObservableList<Estudiante> estudiantesAsignadosObservableList = FXCollections.observableArrayList();


    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    public void initialize() {
        AcademiaController academiaController = new AcademiaController(App.academia);

        //Preparar columnas de los estudiantes registrados
        nombreEstudiantesRegistradosTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        apellidoEstudiantesRegistradosTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getApellido()));
        identificacionEstudiantesRegistradosTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIdentificacion()));
        nivelDeEstudioEstudiantesRegistradosTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNivelDeEstudio().toString()));
        instrumentoEstudiantesRegistradosTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getInstrumento().toString()));

        //Enlazar la lista de estudiantes al TableView
        estudiantesRegistradosTableView.setItems(estudiantesAsignadosObservableList);

        //Añadir opciones al ChoiceBox
        gestionChoiceBox.getItems().addAll("Crear Estudiante", "Eliminar Estudiante");

        //Obtener el cambio de eleccion del ChoiceBox
        gestionChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Crear Estudiante":
                    mostrarRequisitosCrearEstudiante();
                    break;
                case "Eliminar Estudiante":
                    mostrarRequisitosEliminarEstudiante();
            }
        });
    }

    private void mostrarRequisitosCrearEstudiante() {
        Label label = new Label("Crear Estudiante");

        Label nombreEstudianteLabel = new Label("Nombre:");
        TextField nombreEstudianteTextField = new TextField();
        Label apellidoEstudianteLabel = new Label("Apellido:");
        TextField apellidoEstudianteTextField = new TextField();
        Label identificacionEstudianteLabel = new Label("Identificacion:");
        TextField identificacionEstudianteTextField = new TextField();
        Label instrumentoEstudianteLabel = new Label("Instrumento:");
        TextField instrumentoEstudianteTextField = new TextField();
        Label nivelDeEstudioLabel = new Label("Nivel de Estudio:");
        TextField nivelDeEstudioTextField = new TextField();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(nombreEstudianteLabel, 0, 0);
        gridPane.add(nombreEstudianteTextField, 1, 0);
        gridPane.add(apellidoEstudianteLabel, 0, 1);
        gridPane.add(apellidoEstudianteTextField, 1, 1);
        gridPane.add(identificacionEstudianteLabel, 0, 2);
        gridPane.add(identificacionEstudianteTextField, 1, 2);
        gridPane.add(instrumentoEstudianteLabel, 0, 3);
        gridPane.add(instrumentoEstudianteTextField, 1, 3);
        gridPane.add(nivelDeEstudioLabel, 0, 4);
        gridPane.add(nivelDeEstudioTextField, 1, 4);

        Button boton = new Button("Crear Estudiante");

        requisitosDeGestionDeEstudiantesVBox.getChildren().clear();
        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(label, gridPane, boton);
    }

    private void crearEstudiante() {

    }

    private void mostrarEstudianteRegistrado(Estudiante estudiante) {
        estudiantesAsignadosObservableList.add(estudiante);
    }

    private void mostrarRequisitosEliminarEstudiante() {
        Label label = new Label("Eliminar Estudiante");
        Label identificacionEstudianteLabel = new Label("Identificación:");
        TextField identificacionEstudianteTextField = new TextField();

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(identificacionEstudianteLabel, 0, 0);
        gridPane.add(identificacionEstudianteTextField, 1, 0);

        Button boton = new Button("Eliminar");

        requisitosDeGestionDeEstudiantesVBox.getChildren().clear();
        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(label, gridPane, boton);
    }
}
