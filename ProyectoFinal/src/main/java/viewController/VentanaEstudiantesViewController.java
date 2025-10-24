package viewController;

import app.App;
import static viewController.PrimaryViewController.mostrarAlerta;
import static viewController.PrimaryViewController.mostrarMensaje;
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
    AcademiaController academiaController;

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
        academiaController = new AcademiaController(App.academia);

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
        //Crear elementos del vbox
        Label label = new Label("Crear Estudiante");

        Label nombreEstudianteLabel = new Label("Nombre:");
        TextField nombreEstudianteTextField = new TextField();
        Label apellidoEstudianteLabel = new Label("Apellido:");
        TextField apellidoEstudianteTextField = new TextField();
        Label identificacionEstudianteLabel = new Label("Identificacion:");
        TextField identificacionEstudianteTextField = new TextField();
        Label instrumentoEstudianteLabel = new Label("Instrumento:");
        //Agregarle opciones para los enums
        ChoiceBox<String> instrumentoEstudianteChoiceBox = new ChoiceBox<String>();
        instrumentoEstudianteChoiceBox.getItems().addAll("Guitarra", "Piano", "Canto", "Violín", "Flauta", "Otro");
        Label nivelDeEstudioLabel = new Label("Nivel de Estudio:");
        ChoiceBox<String> nivelDeEstudioChoiceBox = new ChoiceBox<String>();
        nivelDeEstudioChoiceBox.getItems().addAll("Básico", "Medio", "Avanzado");

        //Agregar los elementos creados a un gridPane
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
        gridPane.add(instrumentoEstudianteChoiceBox, 1, 3);
        gridPane.add(nivelDeEstudioLabel, 0, 4);
        gridPane.add(nivelDeEstudioChoiceBox, 1, 4);

        //Crear el boton para crear estudiante
        Button boton = new Button("Crear Estudiante");

        //Vincular la funcion al boton creado
        boton.setOnAction(event -> {

            //Verificar que los campos de String esten llenados
            if(nombreEstudianteTextField.getText().isEmpty() || apellidoEstudianteTextField.getText().isEmpty() || identificacionEstudianteTextField.getText().isEmpty()){
                mostrarAlerta("Campo vacío", "Por favor, diligencie todos los campos");
                return;
            }

            //Convertir el String seleccionado a la clase Instrumento
            Instrumento instrumento = Instrumento.DEFAULT;
            switch (instrumentoEstudianteChoiceBox.getSelectionModel().getSelectedItem()) {
                case "Guitarra":
                    instrumento = Instrumento.GUITARRA;
                    break;
                case "Piano":
                    instrumento = Instrumento.PIANO;
                    break;
                case "Canto":
                    instrumento = Instrumento.CANTO;
                    break;
                case "Flauta":
                    instrumento = Instrumento.FLAUTA;
                    break;
                case "Otro":
                    instrumento = Instrumento.OTRO;
                    break;
                case "Violín":
                    instrumento = Instrumento.VIOLIN;
                    break;
                case null:
                    mostrarAlerta("Instrumento no seleccionado", "Por favor, elija un instrumento");
                    return;
                default:
                    break;
            }

            //Convertir el String seleccionado a la clase NivelDeEstudio
            NivelDeEstudio nivelDeEstudio = NivelDeEstudio.DEFAULT;
            switch (nivelDeEstudioChoiceBox.getSelectionModel().getSelectedItem()) {
                case "Avanzado":
                    nivelDeEstudio = NivelDeEstudio.AVANZADO;
                    break;
                case "Básico":
                    nivelDeEstudio = NivelDeEstudio.BASICO;
                    break;
                case "Medio":
                    nivelDeEstudio = NivelDeEstudio.MEDIO;
                    break;
                case null:
                    mostrarAlerta("Nivel de estudio no seleccionado", "Por favor, elija un nivel de estudio");
                    return;
                default:
                    break;
            }

            //Crear el estuiante
            crearEstudiante(nombreEstudianteTextField.getText(), apellidoEstudianteTextField.getText(), identificacionEstudianteTextField.getText(), nivelDeEstudio, instrumento);
        });

        requisitosDeGestionDeEstudiantesVBox.getChildren().clear();
        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(label, gridPane, boton);
    }

    private void crearEstudiante(String nombre, String apellido, String identificacion, NivelDeEstudio nivelDeEstudio, Instrumento instrumento) {
        Estudiante nuevoEstudiante = new Estudiante(nombre, apellido, identificacion, nivelDeEstudio, instrumento);

        if (academiaController.crearEstudiante(nuevoEstudiante)) {
            mostrarEstudianteRegistrado(nuevoEstudiante);
            mostrarMensaje("Estudiante creado", "Estudiante creado exitosamente");
        } else {
            mostrarAlerta("Error al crear estudiante", "El estudiante ya existe");
        }

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

        //Vincular la funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo del String este diligenciado
            if (identificacionEstudianteTextField.getText().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, escriba la identificacion del estudiante");
                return;
            }
            eliminarEstudiante(identificacionEstudianteTextField.getText());
        });

        requisitosDeGestionDeEstudiantesVBox.getChildren().clear();
        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(label, gridPane, boton);
    }

    private void eliminarEstudiante(String identificacion) {
        if(academiaController.eliminarEstudiante(identificacion)) {
            quitarEstudianteEliminado(identificacion);
            mostrarMensaje("Estudiante eliminado", "Estudiante eliminado exitosamente");
        } else {
            mostrarAlerta("Error al eliminar estudiante", "El número no está registrado");
        }
    }

    private void quitarEstudianteEliminado(String identificacion) {
        Estudiante estudiante = null;

        for (Estudiante e : estudiantesAsignadosObservableList) {
            if (e.getIdentificacion().equals(identificacion)) {
                estudiante = e;
                break;
            }
        }

        estudiantesAsignadosObservableList.remove(estudiante);
    }
}
