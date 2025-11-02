package viewController;

import app.App;
import controller.AcademiaController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.Estudiante;
import model.Usuario;

import java.io.IOException;

import static viewController.PrimaryViewController.*;

public class VentanaPersonalEstudianteViewController {

    private App app;
    private AcademiaController academiaController;
    private Usuario usuarioActual;
    private Estudiante estudianteActual;

    @FXML
    private Label bienvenidoLabel;

    @FXML
    private ChoiceBox<String> gestionChoiceBox;

    @FXML
    private VBox requisitosGestionVBox;

    @FXML
    private TitledPane informacionGestionEstudianteTitledPane;

    @FXML
    private Button cerrarSesionButton;

    @FXML
    private Label nombreLabel;

    @FXML
    private Label apellidoLabel;

    @FXML
    private Label identificacionLabel;

    public void setApp(App app) {
        this.app = app;
    }

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    //Mostrar los datos del usuario
    public void cargarDatosUsuario() {
        //Obtener estudiante del usuario
        estudianteActual = academiaController.buscarEstudiante(usuarioActual.getIdentificacion());

        //Actualizar los datos
        bienvenidoLabel.setText("Bienvenido " + estudianteActual.getNombre());
        nombreLabel.setText("Nombre: " + estudianteActual.getNombre());
        apellidoLabel.setText("Apellido: " + estudianteActual.getApellido());
        identificacionLabel.setText("Identificacion: " + estudianteActual.getIdentificacion());
    }

    @FXML
    private void initialize() {
        academiaController = new AcademiaController(App.academia);

        //Darle una funcion al boton de cerrar sesion
        cerrarSesionButton.setOnAction(event -> {
            if(mostrarConfirmacion("Confirmación", "¿Seguro que quiere salir de la ventana actual?")) {
                try {
                    usuarioActual = null;
                    volverAlPrimary();
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

        requisitosGestionVBox.getChildren().clear();
        requisitosGestionVBox.getChildren().addAll(label, gridPane, boton);
    }

    private void mostrarRequisitosActualizar() {
        //Crear elementos de requisitos
        Label label = new Label("Actualizar");

        Label consultaLabel = new Label("Seleccione su consulta:");
        ChoiceBox<String> consultaChoiceBox = new ChoiceBox<>();
        consultaChoiceBox.getItems().addAll("Actualizar nombre", "Actualizar apellido");
        consultaChoiceBox.setValue("Seleccione una opción");

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(consultaLabel, 0, 0);
        gridPane.add(consultaChoiceBox, 1, 0);

        Button boton = new Button("Confirmar");
        boton.setOnAction(event -> {
            //Decidir que mostrar dependiendo de la seleccion
            switch(consultaChoiceBox.getValue()) {
                case "Actualizar nombre":
                    mostrarRequisitosActualizarNombre();
                    break;
                case "Actualizar apellido":
                    mostrarRequisitosActualizarApellido();
                    break;
                /* Esta funcion deberia pertenecer a un admin
                case "Actualizar identificación":
                    mostrarRequisitosActualzarIdentificacion();
                    break;
                 */
                default:
                    mostrarAlerta("Opción no seleccionada", "Por favor, seleccione su actualización");
                    break;
            }
        });

        requisitosGestionVBox.getChildren().clear();
        requisitosGestionVBox.getChildren().addAll(label, gridPane, boton);
    }

    private void mostrarRequisitosActualizarNombre() {
        //Crear elementos del Vbox
        Label label = new Label("Actualizar nombre");
        Label nombreEstudianteLabel = new Label("Nuevo nombre:");
        TextField nombreEstudianteTextField = new TextField();

        //Asignar elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(nombreEstudianteLabel, 0, 0);
        gridPane.add(nombreEstudianteTextField, 1, 0);

        //Crear boton del VBox
        Button boton = new Button("Actualizar");

        //Asginar funciones al boton creado
        boton.setOnAction(e -> {
            //Verificar que los campos esten diligenciados
            if (nombreEstudianteTextField.getText().isEmpty()) {
                mostrarAlerta("Nombre vacío", "Por favor, diligencie el nuevo nombre");
                return;
            }
            actualizarNombre(usuarioActual.getIdentificacion(), nombreEstudianteTextField.getText());
            cargarDatosUsuario();
            mostrarMensaje("Nombre actualizado", "Nombre actualizado correctamente");
        });

        //VBox adicional para la gestion deseada
        VBox actualizacionVBox = new VBox();
        actualizacionVBox.setSpacing(10);

        //Mostrar los requisitos en el VBox
        requisitosGestionVBox.getChildren().clear();
        requisitosGestionVBox.getChildren().addAll(label, gridPane, boton, actualizacionVBox);
    }

    private void actualizarNombre(String identificacion, String nuevoNombre) {
        Estudiante estudiante = buscarEstudiante(identificacion);
        estudiante.setNombre(nuevoNombre);
    }

    private void mostrarRequisitosActualizarApellido() {
        //Crear elementos del Vbox
        Label label = new Label("Actualizar apellido");
        Label apellidoEstudianteLabel = new Label("Nuevo apellido:");
        TextField apeliidoEstudianteTextField = new TextField();

        //Asignar elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(apellidoEstudianteLabel, 0, 0);
        gridPane.add(apeliidoEstudianteTextField, 1, 0);

        //Crear boton del VBox
        Button boton = new Button("Actualizar");

        //Asginar funciones al boton creado
        boton.setOnAction(e -> {
            //Verificar que los campos esten diligenciados
            if (apeliidoEstudianteTextField.getText().isEmpty()) {
                mostrarAlerta("Apellido vacío", "Por favor, diligencie el nuevo apellido");
                return;
            }
            actualizarApellido(usuarioActual.getIdentificacion(), apeliidoEstudianteTextField.getText());
            cargarDatosUsuario();
            mostrarMensaje("Apellido actualizado", "Apellido actualizado correctamente");
        });

        //VBox adicional para la gestion deseada
        VBox actualizacionVBox = new VBox();
        actualizacionVBox.setSpacing(10);

        //Mostrar los requisitos en el VBox
        requisitosGestionVBox.getChildren().clear();
        requisitosGestionVBox.getChildren().addAll(label, gridPane, boton, actualizacionVBox);
    }

    private void actualizarApellido(String identificacion, String nuevoApellido) {
        Estudiante estudiante = buscarEstudiante(identificacion);
        estudiante.setApellido(nuevoApellido);
    }

    /*
    Esta funcion pertenece mayormente a un admin, por lo que solo vamos a dejarla comentada
    private void mostrarRequisitosActualizarIdentificacion() {
        //Crear elementos del Vbox
        Label label = new Label("Actualizar identificación");
        Label identificacionEstudianteLabel = new Label("Nueva identificación:");
        TextField identicacionEstudianteTextField = new TextField();

        //Asignar elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(identificacionEstudianteLabel, 0, 0);
        gridPane.add(identicacionEstudianteTextField, 1, 0);

        //Crear boton del VBox
        Button boton = new Button("Actualizar");

        //Asginar funciones al boton creado
        boton.setOnAction(e -> {
            //Verificar que los campos esten diligenciados
            if (identicacionEstudianteTextField.getText().isEmpty()) {
                mostrarAlerta("Identificación vacía", "Por favor, diligencie la nueva identificación");
                return;
            }
            actualizarApellido(usuarioActual.getIdentificacion(), identicacionEstudianteTextField.getText());
            cargarDatosUsuario();
            mostrarMensaje("Identificación actualizada", "Identificacion actualizada correctamente");
        });

        //VBox adicional para la gestion deseada
        VBox actualizacionVBox = new VBox();
        actualizacionVBox.setSpacing(10);

        //Mostrar los requisitos en el VBox
        requisitosGestionVBox.getChildren().clear();
        requisitosGestionVBox.getChildren().addAll(label, gridPane, boton, actualizacionVBox);
    }

    private void actualizarIdentificacion(String identificacion, String nuevaIdentificacion) {
        Estudiante estudiante = buscarEstudiante(identificacion);
        estudiante.setIdentificacion(nuevaIdentificacion);
    }
     */






    private Estudiante buscarEstudiante(String identificacion) {
        return academiaController.buscarEstudiante(identificacion);
    }

    private void volverAlPrimary() throws IOException {
        app.abrirPrimary();
    }

}
