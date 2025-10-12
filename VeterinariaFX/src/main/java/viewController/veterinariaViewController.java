package viewController;

import app.App;
import controller.VeterinariaController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import model.Propietario;

public class veterinariaViewController {
    App app;
    VeterinariaController veterinariaController;

    @FXML
    private ChoiceBox<String> seccionChoiceBox;

    @FXML
    private VBox VBoxOpcionesDeGestion;

    @FXML
    private VBox VBoxRequisitosDeGestion;

    @FXML
    private TableView<Propietario> TableViewPropietariosRegistrados;
    @FXML
    private TableColumn<Propietario, String> TableColumnNombresDePropietarios;
    @FXML
    private TableColumn<Propietario, String> TableColumnApellidosDePropietarios;
    @FXML
    private TableColumn<Propietario, String> TableColumnNumerosDePropietarios;
    @FXML
    private TableColumn<Propietario, String> TableColumnDireccionesDePropietarios;
    @FXML
    private TableColumn<Propietario, String> TableColumnMascotasDePropietarios;
    @FXML
    private TableColumn<Propietario, Number> TableColumnFidelidadesDePropietarios;

    private ObservableList<Propietario> ObservableListPropietariosRegistrados = FXCollections.observableArrayList();


    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    public void initialize() {
        veterinariaController = new VeterinariaController(App.veterinaria);

        //Preparar columnas de los propietarios registrados (ni idea de como funciona esto)
        TableColumnNombresDePropietarios.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombres()));
        TableColumnApellidosDePropietarios.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getApellidos()));
        TableColumnNumerosDePropietarios.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNumero()));
        TableColumnDireccionesDePropietarios.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getDirrecion()));
        TableColumnMascotasDePropietarios.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(
                (cellData.getValue().getMascota() != null) ? cellData.getValue().getMascota().toString() : "Sin mascota"
        ));
        TableColumnFidelidadesDePropietarios.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getFidelidad()));

        //Enlazar lista de propietarios al TableView
        TableViewPropietariosRegistrados.setItems(ObservableListPropietariosRegistrados);

        //Añadir opciones para elegir en el ChoiceBox
        seccionChoiceBox.getItems().addAll("Gestionar Propietarios", "Gestionar Mascotas");
        seccionChoiceBox.setValue("Seleccione una opcion");

        //Obtener evento de cambio de seleccion del ChoiceBox
        seccionChoiceBox.getSelectionModel().selectedItemProperty().addListener((obs, oldValue, newValue) -> {
            mostrarOpcionesDeGestion(newValue);
        });
    }

    private void mostrarOpcionesDeGestion(String opcion) {
        switch(opcion) {
            //Crear y mostrar botones si elije propietarios
            case "Gestionar Propietarios" -> {
                Label label = new Label("Seleccione una opcion");

                //Crear botones y asignar funciones de los botones
                Button crearPropietarioButton = new Button("Crear Propietario");
                crearPropietarioButton.setOnAction(event -> {
                    mostrarRequisitosDeCrearPropietario();
                });
                Button consultarPropietarioButton = new Button("Consultar Propietario");
                Button modificarPropietarioButton = new Button("Modificar Propietario");
                Button asignarMascotaButton = new Button("Asignar Mascota");
                Button eliminarPropietarioButton = new Button("Eliminar Propietario");
                eliminarPropietarioButton.setOnAction(event -> {
                    mostrarRequisitosDeEliminarPropietario();
                });
                VBoxOpcionesDeGestion.getChildren().clear();
                VBoxOpcionesDeGestion.getChildren().addAll(label, crearPropietarioButton, consultarPropietarioButton, modificarPropietarioButton, asignarMascotaButton, eliminarPropietarioButton);
            }

            //Crear y mostrar botones si elije mascotas
            case "Gestionar Mascotas" -> {
                Label label = new Label("Seleccione una opcion");
                Button crearMascotaButton = new Button("Crear Mascota");
                Button consultarMascotaButton = new Button("Consultar Mascota");
                Button modificarMascotaButton = new Button("Modificar Mascota");
                Button eliminarMascotaButton = new Button("Eliminar Mascota");
                VBoxOpcionesDeGestion.getChildren().clear();
                VBoxOpcionesDeGestion.getChildren().addAll(label, crearMascotaButton, consultarMascotaButton, modificarMascotaButton, eliminarMascotaButton);
            }
        }
    }

    private void mostrarRequisitosDeCrearPropietario() {
        //Crear elementos
        Label labelGestion = new Label("Crear Propietario");
        Label labelNombre = new Label("Ingrese el nombre:");
        TextField textFieldNombre = new TextField();
        Label labelApellidos = new Label("Ingrese los apellidos:");
        TextField textFieldApellidos = new TextField();
        Label labelNumero = new Label("Ingrese el número:");
        TextField textFieldNumero = new TextField();
        Label labelDireccion = new Label("Ingrese el dirección:");
        TextField textFieldDireccion = new TextField();

        //Crear y asignar los elementos al grid
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setMaxWidth(Double.MAX_VALUE);
        grid.setMaxHeight(Double.MAX_VALUE);
        grid.add(labelNombre, 0, 0);
        grid.add(textFieldNombre, 1, 0);
        grid.add(labelApellidos, 0, 1);
        grid.add(textFieldApellidos, 1, 1);
        grid.add(labelNumero, 0, 2);
        grid.add(textFieldNumero, 1, 2);
        grid.add(labelDireccion, 0, 3);
        grid.add(textFieldDireccion, 1, 3);

        //Crear boton y asignar una funcion
        Button crearPropietarioButton = new Button("Crear Propietario");
        crearPropietarioButton.setOnAction(event -> {
            if (textFieldApellidos.getText().isEmpty() || textFieldNombre.getText().isEmpty() || textFieldNumero.getText().isEmpty() || textFieldDireccion.getText().isEmpty()) {mostrarAlerta("Campo Vacío", "Por favor, complete todos los requisitos"); return;}
            crearPropietario(textFieldNombre.getText(), textFieldApellidos.getText(), textFieldNumero.getText(), textFieldDireccion.getText());
        });

        VBoxRequisitosDeGestion.getChildren().clear();
        VBoxRequisitosDeGestion.getChildren().addAll(labelGestion, grid, crearPropietarioButton);
    }

    private void crearPropietario(String nombre, String apellido, String numero, String direccion) {
        Propietario nuevoPropietario = new Propietario(null, nombre, apellido, numero, direccion, 0);

        if ((veterinariaController.agregarPropietario(nuevoPropietario))) {
            mostrarPropietarioRegistrado(nuevoPropietario);
        } else {
            mostrarAlerta("Error al agregar Propietario", "El número ya está registrado");
        }

    }

    private void mostrarPropietarioRegistrado(Propietario propietario) {
        ObservableListPropietariosRegistrados.add(propietario);
    }

    private void mostrarRequisitosDeEliminarPropietario() {
        //Crear elementos
        Label labelGestion = new Label("Eliminar Propietario");
        Label labelNumero = new Label("Ingrese el número:");
        TextField textFieldNumero = new TextField();

        //Crear boton y asignar la funcion
        Button buttonEliminarPropietario = new Button("Eliminar Propietario");
        buttonEliminarPropietario.setOnAction(event -> {
           if (textFieldNumero.getText().isEmpty()) {
               mostrarAlerta("Número vacío",  "Por favor, diligencie el número");
           } else {
               eliminarPropietario(textFieldNumero.getText());
           }
        });

        VBoxRequisitosDeGestion.getChildren().clear();
        VBoxRequisitosDeGestion.getChildren().addAll(labelGestion, labelNumero, textFieldNumero, buttonEliminarPropietario);
    }

    private void eliminarPropietario(String numero) {
        if (veterinariaController.eliminarPropietario(numero)) {
            quitarPropietarioEliminado(numero);
        } else {
            mostrarAlerta("Error al eliminar Propietario", "El número no está registrado");
        }
    }

    private void quitarPropietarioEliminado(String numero) {
        Propietario propietarioAEliminar = null;
        for (Propietario p : ObservableListPropietariosRegistrados) {
            if (p.getNumero().equals(numero)) {
                propietarioAEliminar = p;
            }
        }
        ObservableListPropietariosRegistrados.remove(propietarioAEliminar);
    }

    private void mostrarRequisitosDeConsultarPropietario() {
        Label labelGestion = new Label("Consultar Propietario");
        Label labelNumero = new Label("Ingrese el número:");
        TextField textFieldNumero = new TextField();
        Button buttonConsultarPropietario = new Button("Consultar Propietario");

        VBoxRequisitosDeGestion.getChildren().clear();
        VBoxRequisitosDeGestion.getChildren().addAll(labelGestion, labelNumero, textFieldNumero, buttonConsultarPropietario);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle("Advertencia");
        alerta.setHeaderText(titulo);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

}
