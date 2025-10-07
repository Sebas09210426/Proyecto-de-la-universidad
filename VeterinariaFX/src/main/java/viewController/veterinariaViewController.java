package viewController;

import app.App;
import controller.VeterinariaController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
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
    private VBox VBoxPropietariosRegistrados;

    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    public void initialize() {
        veterinariaController = new VeterinariaController(App.veterinaria);

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
            crearPropietario(textFieldNombre.getText(), textFieldApellidos.getText(), textFieldNumero.getText(), textFieldDireccion.getText());
        });

        VBoxRequisitosDeGestion.getChildren().clear();
        VBoxRequisitosDeGestion.getChildren().addAll(labelGestion, grid, crearPropietarioButton);
    }

    private void crearPropietario(String nombre, String apellido, String numero, String direccion) {
        Propietario nuevoPropietario = new Propietario(null, nombre, apellido, numero, direccion, 0);
        System.out.println("Creando Propietario");
        veterinariaController.agregarPropietario(nuevoPropietario);
        System.out.println("Propietario agregado");
    }

    private void mostrarPropietarioRegistrado() {

    }
}
