package viewController;

import app.App;
import controller.AcademiaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import model.Estudiante;
import model.Instrumento;
import model.NivelDeEstudio;
import javafx.scene.control.*;

public class VentanaEstudiantesViewController {

    App app;

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
    }
}
