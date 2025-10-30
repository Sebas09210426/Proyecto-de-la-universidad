package viewController;

import app.App;
import static viewController.PrimaryViewController.mostrarAlerta;
import static viewController.PrimaryViewController.mostrarMensaje;
import static viewController.PrimaryViewController.mostrarConfirmacion;
import controller.AcademiaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import model.*;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;

public class VentanaEstudiantesViewController {
    //Crear variables globales
    App app;
    AcademiaController academiaController;

    //Adquirir los elementos del fxml
    @FXML
    private ChoiceBox<String> gestionChoiceBox;

    @FXML
    private VBox requisitosDeGestionDeEstudiantesVBox;

    @FXML
    private Button volverAlMenuAnteriorButton;

    @FXML
    private Button cerrarSesionButton;

    //Tabla de estudiantes registrados
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


    //Tabla de cursos registrados
    @FXML
    private TableView<Curso> cursosRegistradosTableView;

    @FXML
    private TableColumn<Curso, LocalDate> fechaCursoTableColumn;

    @FXML
    private TableColumn<Curso, LocalTime> horaCursoTableColumn;

    @FXML
    private TableColumn<Curso, String> instrumentoCursoTableColumn;

    @FXML
    private TableColumn<Curso, String> nivelCursoTableColumn;

    @FXML
    private TableColumn<Curso, Integer> estudiantesCursoTableColumn;

    @FXML
    private TableColumn<Curso, Integer> cuposCursoTableColumn;

    @FXML
    private TableColumn<Curso, String> profesorCursoTableColumn;

    private ObservableList<Curso> cursosAsignadosObservableList = FXCollections.observableArrayList();


    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    private void initialize() {
        academiaController = new AcademiaController(App.academia);

        //Preparar columnas de los estudiantes registrados
        nombreEstudiantesRegistradosTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        apellidoEstudiantesRegistradosTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getApellido()));
        identificacionEstudiantesRegistradosTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIdentificacion()));
        nivelDeEstudioEstudiantesRegistradosTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNivelDeEstudio().toString()));
        instrumentoEstudiantesRegistradosTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getInstrumento().toString()));

        //Enlazar la lista de estudiantes al TableView
        estudiantesRegistradosTableView.setItems(estudiantesAsignadosObservableList);
        //Cargar lista de estudiantes registrados por si se cambia de pestana
        cargarListaEstudiantesRegistrados();

        //Preparar columnas de los cursos registrados

        //Añadir opciones al ChoiceBox
        gestionChoiceBox.getItems().addAll("Crear Estudiante", "Eliminar Estudiante", "Modificar Estudiante");

        //Obtener el cambio de eleccion del ChoiceBox
        gestionChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Crear Estudiante":
                    mostrarRequisitosCrearEstudiante();
                    break;
                case "Eliminar Estudiante":
                    mostrarRequisitosEliminarEstudiante();
                    break;
                case "Modificar Estudiante":
                    mostrarRequisitosModificarEstudiante();
                    break;
            }
        });

        //Configurar botones de volver atras y cerrar sesion
        volverAlMenuAnteriorButton.setOnAction(event -> {
            if(mostrarConfirmacion("Confirmación", "¿Seguro que quiere salir de la ventana actual?")) {
                try {
                    volverAlMenuAnterior();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        cerrarSesionButton.setOnAction(event -> {
            if(mostrarConfirmacion("COnfirmación", "¿Seguro que quiere cerrar sesión?")) {
                try {
                    volverAlPrimary();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void abrirVentanaPrincipal() throws IOException {
        app.abrirVentanaPrincipal();
    }

    private boolean consultarExistenciaEstudiante(String identificacion) {
        return academiaController.consultarExistenciaEstudiante(identificacion);
    }

    private Estudiante buscarEstudiante(String identificacion) {
        return academiaController.buscarEstudiante(identificacion);
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

        //Mostrar los requisitos en el VBox
        requisitosDeGestionDeEstudiantesVBox.getChildren().clear();
        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(label, gridPane, boton);
    }

    private void crearEstudiante(String nombre, String apellido, String identificacion, NivelDeEstudio nivelDeEstudio, Instrumento instrumento) {
        Estudiante nuevoEstudiante = new Estudiante(nombre, apellido, identificacion, nivelDeEstudio, instrumento);

        if (academiaController.crearEstudiante(nuevoEstudiante)) {
            mostrarEstudianteRegistrado(nuevoEstudiante);
            crearUsuarioEstudiante(identificacion, identificacion, identificacion, Rol.ESTUDIANTE); //Por defecto, el usuario se crea con la misma identificacion, despues se puede modificar
            mostrarMensaje("Estudiante creado", "Estudiante creado exitosamente");
        } else {
            mostrarAlerta("Error al crear estudiante", "El estudiante ya existe");
        }

    }

    private void crearUsuarioEstudiante(String usuario, String contrasena, String identificacion, Rol rol) {
        academiaController.agregarUsuario(usuario, contrasena, identificacion, rol);
    }

    private void mostrarEstudianteRegistrado(Estudiante estudiante) {
        estudiantesAsignadosObservableList.add(estudiante);
    }

    private void mostrarRequisitosEliminarEstudiante() {
        //Crear elementos del VBox
        Label label = new Label("Eliminar Estudiante");
        Label identificacionEstudianteLabel = new Label("Identificación:");
        TextField identificacionEstudianteTextField = new TextField();

        //Asignar los elementos crados a un GridPane
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
                mostrarAlerta("Campo vacío", "Por favor, diligencie la identificacion del estudiante");
                return;
            }
            eliminarEstudiante(identificacionEstudianteTextField.getText());
        });

        //Mostrar los requisitos en el VBox
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

    private void mostrarRequisitosModificarEstudiante() {
        //Crear elementos del Vbox
        Label label = new Label("Modificar Estudiante");
        Label identificacionEstudianteLabel = new Label("Identificacion:");
        TextField identificacionEstudianteTextField = new TextField();
        Label modificacionEstudianteLabel = new Label("Modificación:");
        ChoiceBox<String> modificacionEstudianteChoiceBox = new ChoiceBox<>();
        //Añadir opciones al ChoiceBox
        modificacionEstudianteChoiceBox.getItems().addAll("Cambiar nombre", "Cambiar apellido", "Cambiar identificación", "Cambiar instrumento", "Cambiar nivel de estudio");

        //Asignar elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(identificacionEstudianteLabel, 0, 0);
        gridPane.add(identificacionEstudianteTextField, 1, 0);
        gridPane.add(modificacionEstudianteLabel, 0, 1);
        gridPane.add(modificacionEstudianteChoiceBox, 1, 1);

        //Crear boton del VBox
        Button boton = new Button("Modificar");

        //Asginar funciones al boton creado
        boton.setOnAction(e -> {
            //Verificar que los campos esten diligenciados
            if (identificacionEstudianteTextField.getText().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie la identificación del estudiante");
                return;
            } else if (modificacionEstudianteChoiceBox.getSelectionModel().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, seleccione una opción de modificación");
                return;
            }

            //Mostrar los requisitos de la gestion seleccionada
            String gestionActual = "Defecto";
            switch (modificacionEstudianteChoiceBox.getSelectionModel().getSelectedItem()) {
                case "Cambiar nombre":
                    //Verificar que exista el estudiante con esa identificacion
                    if (consultarExistenciaEstudiante(identificacionEstudianteTextField.getText())) {
                        mostrarRequisitosModificarNombreEstudiante(identificacionEstudianteTextField.getText());
                        boton.setVisible(false); //Ocultar boton para evitar que el usuario pueda volver a crear la misma interfaz
                        boton.setDisable(true); //Desactivar el boton para evitar que el usuario pueda volver a crear la misma interfaz
                        gestionActual = "Cambiar nombre";
                        break;
                    } else {
                        mostrarAlerta("Estudiante no encontrado", "La identificacion del estudiante no esta registrada");
                        break;
                    }
                case "Cambiar apellido":
                    //Verificar que exista el estudiante con esa identificacion
                    if (consultarExistenciaEstudiante(identificacionEstudianteTextField.getText())) {
                        mostrarRequisitosModificarApellidoEstudiante(identificacionEstudianteTextField.getText());
                        boton.setVisible(false); //Ocultar boton para evitar que el usuario pueda volver a crear la misma interfaz
                        boton.setDisable(true); //Desactivar el boton para evitar que el usuario pueda volver a crear la misma interfaz
                        gestionActual = "Cambiar apellido";
                        break;
                    } else {
                        mostrarAlerta("Estudiante no encontrado", "La identificacion del estudiante no esta registrada");
                        break;
                    }
                case "Cambiar identificación":
                    //Verificar que exista el estudiante con esa identificacion
                    if (consultarExistenciaEstudiante(identificacionEstudianteTextField.getText())) {
                        mostrarRequisitosModificarIdentificacionEstudiante(identificacionEstudianteTextField.getText());
                        boton.setVisible(false); //Ocultar boton para evitar que el usuario pueda volver a crear la misma interfaz
                        boton.setDisable(true); //Desactivar el boton para evitar que el usuario pueda volver a crear la misma interfaz
                        gestionActual = "Cambiar identificacion";
                        break;
                    } else {
                        mostrarAlerta("Estudiante no encontrado", "La identificacion del estudiante no esta registrada");
                        break;
                    }
                case "Cambiar instrumento":
                    //Verificar que exista el estudiante con esa identificacion
                    if (consultarExistenciaEstudiante(identificacionEstudianteTextField.getText())) {
                        mostrarRequisitosModificarInstrumentoEstudiante(identificacionEstudianteTextField.getText());
                        boton.setVisible(false); //Ocultar boton para evitar que el usuario pueda volver a crear la misma interfaz
                        boton.setDisable(true); //Desactivar el boton para evitar que el usuario pueda volver a crear la misma interfaz
                        gestionActual = "Cambiar instrumento";
                        break;
                    } else {
                        mostrarAlerta("Estudiante no encontrado", "La identificacion del estudiante no esta registrada");
                        break;
                    }
                case "Cambiar nivel de estudio":
                    //Verificar que exista el estudiante con esa identificacion
                    if (consultarExistenciaEstudiante(identificacionEstudianteTextField.getText())) {
                        mostrarRequisitosModificarNivelDeEstudioEstudiante(identificacionEstudianteTextField.getText());
                        boton.setVisible(false); //Ocultar boton para evitar que el usuario pueda volver a crear la misma interfaz
                        boton.setDisable(true);
                        gestionActual = "Cambiar nivel de estudio";
                        break;
                    } else {
                        mostrarAlerta("Estudiante no encontrado", "La identificacion del estudiante no esta registrada");
                        break;
                    }
            }

            //Obtener el cambio de eleccion del ChoiceBox
            String finalGestionActual = gestionActual;
            modificacionEstudianteChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                //Habilitar boton nuevamente
                if(!newValue.equals(oldValue) && !newValue.equals(finalGestionActual)) {
                    boton.setVisible(true); // Volver a hacer visible el boton para cambiar de interfaz
                    boton.setDisable(false); // Volver a activar la funcion del boton para cambiar de interfaz
                } else  {
                    boton.setVisible(false);
                    boton.setDisable(true);
                }
            });

        });

        //VBox adicional para la gestion deseada
        VBox gestionElegidaVBox = new VBox();
        gestionElegidaVBox.setSpacing(10);

        //Mostrar los requisitos en el VBox
        requisitosDeGestionDeEstudiantesVBox.getChildren().clear();
        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(label, gridPane, boton, gestionElegidaVBox);
    }

    private void mostrarRequisitosModificarNombreEstudiante(String identificacion) {
        //Crear elementos adicionales para el VBox
        Label label = new Label("Nuevo nombre:");
        TextField nuevoNombreEstudianteTextField = new TextField();

        //Agregar los elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setMaxSize(300, 300);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(label, 0, 0);
        gridPane.add(nuevoNombreEstudianteTextField, 1, 0);

        //Crear boton adicional
        Button boton = new Button("Confirmar");
        //Agregar funcion al boton creado
        boton.setOnAction(e -> {
           //Verificar que el campo no este vacio
            if (nuevoNombreEstudianteTextField.getText().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie el nuevo nombre del estudiante");
                return;
            }
            modificarNombreEstudiante(identificacion,  nuevoNombreEstudianteTextField.getText());
            mostrarMensaje("Estudiante actualizado", "Estudiante actualizado exitosamente");
        });

        VBox adicionalVBox = new VBox();
        adicionalVBox.getChildren().addAll(gridPane, boton);

        //Agregar los nuevos elementos al VBox
        requisitosDeGestionDeEstudiantesVBox.getChildren().removeLast();
        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(adicionalVBox);
    }

    private void modificarNombreEstudiante(String identificacion, String nuevoNombre) {
        Estudiante estudiante = buscarEstudiante(identificacion);
        estudiante.setNombre(nuevoNombre);
        actualizarListaEstudiantesRegistrados(); //Si no se actualiza la lista, no se ven reflejados los cambios
    }

    private void mostrarRequisitosModificarApellidoEstudiante(String identificacion) {
        //Crear elementos adicionales para el VBox
        Label label = new Label("Nuevo apellido:");
        TextField nuevoApellidoEstudianteTextField = new TextField();

        //Agregar los elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setMaxSize(300, 300);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(label, 0, 0);
        gridPane.add(nuevoApellidoEstudianteTextField, 1, 0);

        //Crear boton adicional
        Button boton = new Button("Confirmar");
        //Agregar funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo no este vacio
            if (nuevoApellidoEstudianteTextField.getText().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie el nuevo apellido del estudiante");
                return;
            }
            modificarApellidoEstudiante(identificacion,  nuevoApellidoEstudianteTextField.getText());
            mostrarMensaje("Estudiante actualizado", "Estudiante actualizado exitosamente");
        });

        VBox adicionalVBox = new VBox();
        adicionalVBox.getChildren().addAll(gridPane, boton);

        //Agregar los nuevos elementos al VBox
        requisitosDeGestionDeEstudiantesVBox.getChildren().removeLast();
        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(adicionalVBox);
    }

    private void modificarApellidoEstudiante(String identificacion, String nuevoApellido) {
        Estudiante estudiante = buscarEstudiante(identificacion);
        estudiante.setApellido(nuevoApellido);
        actualizarListaEstudiantesRegistrados(); //Si no se actualiza la lista, no se ven reflejados los cambios
    }

    private void mostrarRequisitosModificarIdentificacionEstudiante(String identificacion) {
        //Crear elementos adicionales para el VBox
        Label label = new Label("Nueva  identificacion:");
        TextField nuevaIdentificacionEstudianteTextField = new TextField();

        //Agregar los elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setMaxSize(300, 300);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(label, 0, 0);
        gridPane.add(nuevaIdentificacionEstudianteTextField, 1, 0);

        //Crear boton adicional
        Button boton = new Button("Confirmar");
        //Agregar funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo no este vacio
            if (nuevaIdentificacionEstudianteTextField.getText().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie la nueva identificación del estudiante");
                return;
            }
            modificarIdentificacionEstudiante(identificacion,  nuevaIdentificacionEstudianteTextField.getText());
            mostrarMensaje("Estudiante actualizado", "Estudiante actualizado exitosamente");
        });

        VBox adicionalVBox = new VBox();
        adicionalVBox.getChildren().addAll(gridPane, boton);

        //Agregar los nuevos elementos al VBox
        requisitosDeGestionDeEstudiantesVBox.getChildren().removeLast();
        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(adicionalVBox);
    }

    private void modificarIdentificacionEstudiante(String identificacion, String nuevaIdentificacion) {
        Estudiante estudiante = buscarEstudiante(identificacion);
        estudiante.setIdentificacion(nuevaIdentificacion);
        actualizarListaEstudiantesRegistrados(); //Si no se actualiza la lista, no se ven reflejados los cambios
    }

    private void mostrarRequisitosModificarInstrumentoEstudiante(String identificacion) {
        //Crear elementos adicionales para el VBox
        Label label = new Label("Nuevo intrumento:");
        ChoiceBox<String> instrumentoEstudianteChoiceBox = new ChoiceBox<>();

        //Añadir opciones al ChoiceBox
        instrumentoEstudianteChoiceBox.getItems().addAll("Guitarra", "Canto", "Piano", "Violín", "Flauta", "Otro");

        //Agregar los elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setMaxSize(300, 300);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(label, 0, 0);
        gridPane.add(instrumentoEstudianteChoiceBox, 1, 0);

        //Crear boton adicional
        Button boton = new Button("Confirmar");
        //Agregar funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo no este vacio
            if (instrumentoEstudianteChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, seleccione el nuevo instrumento del estudiante");
                return;
            }

            //Convertir la seleccion del ChoiceBox a la clase Instrumento
            Instrumento nuevoInstrumento = Instrumento.DEFAULT;
            switch (instrumentoEstudianteChoiceBox.getSelectionModel().getSelectedItem()) {
                case "Guitarra":
                    nuevoInstrumento = Instrumento.GUITARRA;
                    break;
                case "Canto":
                    nuevoInstrumento = Instrumento.CANTO;
                    break;
                case "Piano":
                    nuevoInstrumento = Instrumento.PIANO;
                    break;
                case "Flauta":
                    nuevoInstrumento = Instrumento.FLAUTA;
                    break;
                case "Violín":
                    nuevoInstrumento = Instrumento.VIOLIN;
                    break;
                case "Otro":
                    nuevoInstrumento = Instrumento.OTRO;
            }
            modificarInstrumentoEstudiante(identificacion, nuevoInstrumento);
            mostrarMensaje("Estudiante actualizado", "Estudiante actualizado exitosamente");
        });

        VBox adicionalVBox = new VBox();
        adicionalVBox.getChildren().addAll(gridPane, boton);

        //Agregar los nuevos elementos al VBox
        requisitosDeGestionDeEstudiantesVBox.getChildren().removeLast();
        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(adicionalVBox);
    }

    private void modificarInstrumentoEstudiante(String identificacion, Instrumento instrumento) {
        Estudiante estudiante = buscarEstudiante(identificacion);
        estudiante.setInstrumento(instrumento);
        actualizarListaEstudiantesRegistrados(); //Si no se actualiza la lista, no se ven reflejados los cambios
    }

    private void mostrarRequisitosModificarNivelDeEstudioEstudiante(String identificacion) {
        //Crear elementos adicionales para el VBox
        Label label = new Label("Nuevo nivel de estudio:");
        ChoiceBox<String> nivelDeEstudioEstudianteChoiceBox = new ChoiceBox<>();

        //Añadir opciones al ChoiceBox
        nivelDeEstudioEstudianteChoiceBox.getItems().addAll("Básico", "Medio", "Avanzado");

        //Agregar los elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setMaxSize(300, 300);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(label, 0, 0);
        gridPane.add(nivelDeEstudioEstudianteChoiceBox, 1, 0);

        //Crear boton adicional
        Button boton = new Button("Confirmar");
        //Agregar funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo no este vacio
            if (nivelDeEstudioEstudianteChoiceBox.getSelectionModel().getSelectedItem().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, seleccione el nuevo nivel de estudio del estudiante");
                return;
            }

            //Convertir la seleccion del ChoiceBox a la clase Instrumento
            NivelDeEstudio nuevoNivelDeEstudio = NivelDeEstudio.DEFAULT;
            switch (nivelDeEstudioEstudianteChoiceBox.getSelectionModel().getSelectedItem()) {
                case "Básico":
                    nuevoNivelDeEstudio = NivelDeEstudio.BASICO;
                    break;
                case "Medio":
                    nuevoNivelDeEstudio = NivelDeEstudio.MEDIO;
                    break;
                case "Avanzado":
                    nuevoNivelDeEstudio = NivelDeEstudio.AVANZADO;
                    break;
            }
            modificarNivelDeEstudioEstudiante(identificacion, nuevoNivelDeEstudio);
            mostrarMensaje("Estudiante actualizado", "Estudiante actualizado exitosamente");
        });

        VBox adicionalVBox = new VBox();
        adicionalVBox.getChildren().addAll(gridPane, boton);

        //Agregar los nuevos elementos al VBox
        requisitosDeGestionDeEstudiantesVBox.getChildren().removeLast();
        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(adicionalVBox);
    }

    private void modificarNivelDeEstudioEstudiante(String identificacion, NivelDeEstudio nivelDeEstudio) {
        Estudiante estudiante = buscarEstudiante(identificacion);
        estudiante.setNivelDeEstudio(nivelDeEstudio);
        actualizarListaEstudiantesRegistrados(); //Si no se actualiza la lista, no se ven reflejados los cambios
    }

    private void actualizarListaEstudiantesRegistrados() {
        estudiantesRegistradosTableView.refresh();
    }

    private void volverAlMenuAnterior() throws IOException {
        app.abrirVentanaPrincipal();
    }

    private void cargarListaEstudiantesRegistrados() {
        estudiantesAsignadosObservableList.clear();
        estudiantesAsignadosObservableList.addAll(academiaController.getEstudiantesRegsitrados());
        estudiantesRegistradosTableView.setItems(estudiantesAsignadosObservableList);
    }

    private void volverAlPrimary() throws IOException {
        app.abrirPrimary();
    }
}
