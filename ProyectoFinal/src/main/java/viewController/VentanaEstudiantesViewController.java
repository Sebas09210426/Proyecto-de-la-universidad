package viewController;

import app.App;

import static java.lang.Integer.parseInt;
import static viewController.PrimaryViewController.mostrarAlerta;
import static viewController.PrimaryViewController.mostrarMensaje;
import static viewController.PrimaryViewController.mostrarConfirmacion;
import controller.AcademiaController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.*;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

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

    @FXML
    private TabPane ventanasTabPane;

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
    private TableColumn<Curso, String> aulaCursoTableColumn;

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

    @FXML
    private TableColumn<Curso, String> codigoCursoTableColumn;

    private ObservableList<Curso> cursosRegistradosObservableList = FXCollections.observableArrayList();


    public void setApp(App app) {
        this.app = app;
    }

    public void setAcademiaController(AcademiaController academiaController) {
        this.academiaController = academiaController;
    }

    public void cargarDatos() {

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
        fechaCursoTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getFecha()));
        horaCursoTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getHora()));
        aulaCursoTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAulaAsignada() != null ? cellData.getValue().getAulaAsignada().getId() : "Sin asignar"));
        instrumentoCursoTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getInstrumento().toString()));
        nivelCursoTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNivelDeEstudio().toString()));
        estudiantesCursoTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getEstudiantesRegistrados().size()).asObject());
        cuposCursoTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getCapacidad() - cellData.getValue().getEstudiantesRegistrados().size()).asObject());
        profesorCursoTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getProfesor() != null ? cellData.getValue().getProfesor().getNombre() + " " + cellData.getValue().getProfesor().getApellido() : "Sin asignar"));
        codigoCursoTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCodigo()));

        //Enlazar la lista de cursos al TableView
        cursosRegistradosTableView.setItems(cursosRegistradosObservableList);

        //Cargar lista de estudiantes registrados por si se cambia de pestana
        cargarListaCursosRegistrados();

    }

    @FXML
    private void initialize() {
        //Añadir opciones al ChoiceBox
        gestionChoiceBox.getItems().addAll("Crear Estudiante", "Eliminar Estudiante", "Modificar Estudiante", "Consultar Estudiante");

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
                case "Consultar Estudiante":
                    mostrarRequisitosConsultarEstudiante();
                    break;
                case "Crear Curso":
                    mostrarRequisitosCrearCurso();
                    break;
                case "Eliminar Curso":
                    mostrarRequisitosEliminarCurso();
                    break;
                case "Consultar Curso":
                    mostrarRequisitosConsultarCurso();
                    break;
                case "Modificar Curso":
                    mostrarRequisitosModificarCurso();
                    break;
            }
        });

        //Configurar acciones al cambiar de ventana
        ventanasTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newTab) -> {
            //Cambiar opciones del ChoiceBox
            if (newTab != null) {
                String nuevoTab = newTab.getText();
                switch (nuevoTab) {
                    case "Cursos registrados":
                        gestionChoiceBox.getItems().clear();
                        gestionChoiceBox.getItems().addAll("Crear Curso", "Eliminar Curso", "Consultar Curso", "Modificar Curso");
                        break;
                    case "Estudiantes Registrados":
                        gestionChoiceBox.getItems().clear();
                        gestionChoiceBox.getItems().addAll("Crear Estudiante", "Eliminar Estudiante", "Modificar Estudiante", "Consultar Estudiante");
                        break;
                }
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
            if(mostrarConfirmacion("Confirmación", "¿Seguro que quiere cerrar sesión?")) {
                try {
                    volverAlPrimary();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
            } else if (academiaController.consultarExistenciaIdentificacion(nuevaIdentificacionEstudianteTextField.getText())) {
                mostrarAlerta("Identificación existente", "Por favor, ingrese una identificación no existente");
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

    private void mostrarRequisitosConsultarEstudiante() {
        //Crear elementos del VBox
        Label label = new Label("Consultar Estudiante");
        Label identificacionEstudianteLabel = new Label("Identificación:");
        TextField identificacionEstudianteTextField = new TextField();

        //Asignar los elementos crados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(identificacionEstudianteLabel, 0, 0);
        gridPane.add(identificacionEstudianteTextField, 1, 0);

        Button boton = new Button("Consultar");

        //Vincular la funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo del String este diligenciado
            if (identificacionEstudianteTextField.getText().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie la identificacion del estudiante");
                return;
            }
            consultarEstudiante(identificacionEstudianteTextField.getText());
        });

        //Mostrar los requisitos en el VBox
        requisitosDeGestionDeEstudiantesVBox.getChildren().clear();
        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(label, gridPane, boton);
    }

    private void consultarEstudiante(String identificacion) {
        if (consultarExistenciaEstudiante(identificacion)) {
            Estudiante nuevoEstudiante = buscarEstudiante(identificacion);
            mostrarMensaje("Estudiante Consultado", nuevoEstudiante.toString());
        } else {
            mostrarAlerta("Estudiante no existe", "La identificación del estudiante no está registrada, por favor verifique la identificación diligenciada");
        }

    }

    private void mostrarRequisitosCrearCurso() {
        Label label = new Label("Crear Curso");

        Label fechaLabel = new Label("Fecha:");
        DatePicker fechaDatePicker = new DatePicker();

        Label horaLabel = new Label("Hora:");
        ChoiceBox<String> horaChoiceBox = new ChoiceBox<>();
        horaChoiceBox.getItems().addAll("08:00", "10:00", "12:00", "14:00", "16:00", "18:00");

        Label capacidadLabel = new Label("Capacidad:");
        TextField capacidadTextField = new TextField();

        Label instrumentoLabel = new Label("Instrumento:");
        ChoiceBox<String> instrumentoChoiceBox = new ChoiceBox<>();
        instrumentoChoiceBox.getItems().addAll("Guitarra", "Piano", "Canto", "Violín", "Flauta", "Otro");

        Label nivelDeEstudioLabel = new Label("Nivel de Estudio:");
        ChoiceBox<String> nivelDeEstudioChoiceBox = new ChoiceBox<>();
        nivelDeEstudioChoiceBox.getItems().addAll("Básico", "Medio", "Avanzado");

        Label aulaLabel = new Label("Aula:");
        ChoiceBox<String> aulaChoiceBox = new ChoiceBox<>();
        aulaChoiceBox.getItems().addAll("Aula 1", "Aula 2", "Aula 2", "Aula 3", "Aula 4", "Aula 5");
        Button boton = new Button("Crear Curso");
        HBox hBox = new HBox();
        hBox.setSpacing(10);
        hBox.getChildren().addAll(nivelDeEstudioChoiceBox, boton);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(fechaLabel, 0, 0);
        gridPane.add(fechaDatePicker, 1, 0);
        gridPane.add(horaLabel, 0, 1);
        gridPane.add(horaChoiceBox, 1, 1);
        gridPane.add(aulaLabel, 0, 2);
        gridPane.add(aulaChoiceBox, 1, 2);
        gridPane.add(capacidadLabel, 0, 3);
        gridPane.add(capacidadTextField, 1, 3);
        gridPane.add(instrumentoLabel, 0, 4);
        gridPane.add(instrumentoChoiceBox, 1, 4);
        gridPane.add(nivelDeEstudioLabel, 0, 5);
        gridPane.add(hBox, 1, 5);


        boton.setOnAction(event -> {
            // Validar y obtener los datos
            if (fechaDatePicker.getValue() == null ||
                    capacidadTextField.getText().isEmpty() ||
                    horaChoiceBox.getValue() == null || aulaChoiceBox.getValue() == null) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie todos los campos");
                return;
            }

            //Verificar que la fecha y la hora no sean pasadas
            if (fechaDatePicker.getValue().isBefore(LocalDate.now())) {
                mostrarAlerta("Fecha no válida", "Por favor, seleccione una fecha a partir de hoy");
                return;
            } else if (fechaDatePicker.getValue().isEqual(LocalDate.now()) && LocalTime.parse(horaChoiceBox.getSelectionModel().getSelectedItem()).isBefore(LocalTime.now())) {
                mostrarAlerta("Hora inválida", "Por favor, seleccione una hora a partir de ahora");
                return;
            }

            //Convertir el String seleccionado a la clase Instrumento
            Instrumento instrumento = Instrumento.DEFAULT;
            switch (instrumentoChoiceBox.getSelectionModel().getSelectedItem()) {
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

            //Consultar la disponibildad del aula seleccionada
            String idAula = "000";
            switch(aulaChoiceBox.getSelectionModel().getSelectedItem()) {
                case "Aula 1":
                    if (!consultarDisponibildadAula("001", fechaDatePicker.getValue(), LocalTime.parse(horaChoiceBox.getValue()))) {
                        mostrarAlerta("Aula no disponible", "Por favor, elija un horario diferente");
                        return;
                    }
                    idAula = "001";
                    break;
                case "Aula 2":
                    if (!consultarDisponibildadAula("002", fechaDatePicker.getValue(), LocalTime.parse(horaChoiceBox.getValue()))) {
                        mostrarAlerta("Aula no disponible", "Por favor, elija un horario diferente");
                        return;
                    }
                    idAula = "002";
                    break;
                case "Aula 3":
                    if (!consultarDisponibildadAula("003", fechaDatePicker.getValue(), LocalTime.parse(horaChoiceBox.getValue()))) {
                        mostrarAlerta("Aula no disponible", "Por favor, elija un horario diferente");
                        return;
                    }
                    idAula = "003";
                    break;
                case "Aula 4":
                    if (!consultarDisponibildadAula("004", fechaDatePicker.getValue(), LocalTime.parse(horaChoiceBox.getValue()))) {
                        mostrarAlerta("Aula no disponible", "Por favor, elija un horario diferente");
                        return;
                    }
                    idAula = "004";
                    break;
                case "Aula 5":
                    if (!consultarDisponibildadAula("005", fechaDatePicker.getValue(), LocalTime.parse(horaChoiceBox.getValue()))) {
                        mostrarAlerta("Aula no disponible", "Por favor, elija un horario diferente");
                        return;
                    }
                    idAula = "005";
                    break;
            }

            //Crear el Curso
            int capacidad = Integer.parseInt(capacidadTextField.getText());
            LocalDate fecha = fechaDatePicker.getValue();
            LocalTime hora = LocalTime.parse(horaChoiceBox.getValue());
            String codigo = crearCodigoCurso(fecha);
            crearCurso(capacidad, new LinkedList<>(), fecha, hora, instrumento, nivelDeEstudio, codigo, idAula);
        });

        requisitosDeGestionDeEstudiantesVBox.getChildren().clear();
        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(label, gridPane);
    }

    private void crearCurso(int capacidad, LinkedList<Estudiante> estudiantes, LocalDate fecha, LocalTime hora, Instrumento instrumento, NivelDeEstudio nivelDeEstudio, String codigo, String idAula) {
        Aula aula = buscarAula(idAula);

        if (aula != null && aula.getCapacidad() < capacidad) {
            mostrarAlerta("Excede la capacidad del aula", "La cantidad de estudiantes del curso excede la capacidad del aula, por favor, elija otro aula");
            return;
        }

        Curso nuevoCurso = new Curso(capacidad, estudiantes, fecha, hora, instrumento, nivelDeEstudio,null, codigo, aula, "Programado");

        if (academiaController.crearCurso(nuevoCurso)) {
            ClaseAsignada claseAsignada = new ClaseAsignada(nuevoCurso, null, nuevoCurso.getAulaAsignada(), nuevoCurso.getFecha(), nuevoCurso.getHora());
            if (!academiaController.asignarClase(claseAsignada, nuevoCurso.getAulaAsignada())) {
                mostrarAlerta("Error al asignar la clase al aula", "Por favor, verifique los datos registrados");
                return;
            }
            mostrarCursoRegistrado(nuevoCurso);
            mostrarMensaje("Curso creado", "Curso creado exitosamente");
        } else {
            mostrarAlerta("Error al crear Curso", "El curso ya existe");
        }
    }

    private void mostrarCursoRegistrado(Curso curso) {
        cursosRegistradosObservableList.add(curso);
    }

    private String crearCodigoCurso(LocalDate fecha) {
        int finalCodigo = 1;
        String codigo = fecha.toString() + finalCodigo;
        for(Curso curso : academiaController.getCursosRegistrados()) {
            if (curso.getCodigo().equals(codigo)) {
                finalCodigo ++;
                codigo = fecha.toString() + finalCodigo;
            }
        }
        return codigo;
    }

    private void mostrarRequisitosEliminarCurso() {
        //Crear elementos del VBox
        Label label = new Label("Eliminar Curso");
        Label codigoCursoLabel = new Label("Código:");
        TextField codigoCursoTextField = new TextField();

        //Asignar los elementos crados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(codigoCursoLabel, 0, 0);
        gridPane.add(codigoCursoTextField, 1, 0);

        Button boton = new Button("Eliminar");

        //Vincular la funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo del String este diligenciado
            if (codigoCursoTextField.getText().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie el codigo del curso");
                return;
            }
            if(mostrarConfirmacion("Confirmar borrar curso", "¿Está seguro de que quiere borrar el curso?")) {
                eliminarCurso(codigoCursoTextField.getText());
            }
        });

        //Mostrar los requisitos en el VBox
        requisitosDeGestionDeEstudiantesVBox.getChildren().clear();
        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(label, gridPane, boton);
    }

    private void eliminarCurso(String codigo) {
        if (academiaController.eliminarCurso(codigo)) {
            quitarCursoEliminado(codigo);
            mostrarMensaje("Curso eliminado", "Curso eliminado exitosamente");
        } else {
            mostrarAlerta("Curso no existe", "El código del curso no está registrada, por favor verifique el código diligenciada");
        }
    }

    private void quitarCursoEliminado(String codigo) {
        Curso curso = null;

        for (Curso c : cursosRegistradosObservableList) {
            if (c.getCodigo().equals(codigo)) {
                curso = c;
                break;
            }
        }

        cursosRegistradosObservableList.remove(curso);
    }

    private void mostrarRequisitosConsultarCurso() {
        //Crear elementos del VBox
        Label label = new Label("Consultar Curso");
        Label codigoCursoLabel = new Label("Código:");
        TextField codigoCursoTextField = new TextField();

        //Asignar los elementos crados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(codigoCursoLabel, 0, 0);
        gridPane.add(codigoCursoTextField, 1, 0);

        Button boton = new Button("Consultar");

        //Vincular la funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo del String este diligenciado
            if (codigoCursoTextField.getText().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie el codigo del curso");
                return;
            }
            consultarCurso(codigoCursoTextField.getText());
        });

        //Mostrar los requisitos en el VBox
        requisitosDeGestionDeEstudiantesVBox.getChildren().clear();
        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(label, gridPane, boton);
    }

    private void consultarCurso(String codigo) {
        if (consultarExistenciaCurso(codigo)) {
            Curso nuevoCurso = buscarCurso(codigo);
            mostrarMensaje("Curso Consultado", nuevoCurso.toString());
        } else {
            mostrarAlerta("Curso no existe", "El código del curso no está registrada, por favor verifique el código diligenciada");
        }
    }

    private void mostrarRequisitosModificarCurso() {
        //Crear elementos del Vbox
        Label label = new Label("Modificar Curso");
        Label codigoCuroLabel = new Label("Código:");
        TextField codigoCursoTextField = new TextField();
        Label modificacionCursoLabel = new Label("Modificación:");
        ChoiceBox<String> modificacionCursoChoiceBox = new ChoiceBox<>();
        //Añadir opciones al ChoiceBox
        modificacionCursoChoiceBox.getItems().addAll("Cambiar horario", "Cambiar fecha", "Cambiar hora", "Cambiar capacidad", "Asignar estudiante", "Quitar estudiante");

        //Asignar elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(codigoCuroLabel, 0, 0);
        gridPane.add(codigoCursoTextField, 1, 0);
        gridPane.add(modificacionCursoLabel, 0, 1);
        gridPane.add(modificacionCursoChoiceBox, 1, 1);

        //Crear boton del VBox
        Button boton = new Button("Modificar");

        //Asginar funciones al boton creado
        boton.setOnAction(e -> {
            //Verificar que los campos esten diligenciados
            if (codigoCursoTextField.getText().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie el código del curso");
                return;
            } else if (modificacionCursoChoiceBox.getSelectionModel().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, seleccione una opción de modificación");
                return;
            }

            //Mostrar los requisitos de la gestion seleccionada
            String gestionActual = "Defecto";
            switch (modificacionCursoChoiceBox.getSelectionModel().getSelectedItem()) {
                case "Cambiar horario":
                    //Verificar que exista el curso con ese codigo
                    if (consultarExistenciaCurso(codigoCursoTextField.getText())) {
                        mostrarRequisitosModificarHorarioCurso(codigoCursoTextField.getText());
                        boton.setVisible(false); //Ocultar boton para evitar que el usuario pueda volver a crear la misma interfaz
                        boton.setDisable(true); //Desactivar el boton para evitar que el usuario pueda volver a crear la misma interfaz
                        gestionActual = "Cambiar horario";
                        break;
                    } else {
                        mostrarAlerta("Curso no encontrado", "El código del curso no esta registrado");
                        break;
                    }
                case "Cambiar capacidad":
                    //Verificar que exista el curso con ese codigo
                    if (consultarExistenciaCurso(codigoCursoTextField.getText())) {
                        mostrarRequisitosModificarCapacidadCurso(codigoCursoTextField.getText());
                        boton.setVisible(false); //Ocultar boton para evitar que el usuario pueda volver a crear la misma interfaz
                        boton.setDisable(true); //Desactivar el boton para evitar que el usuario pueda volver a crear la misma interfaz
                        gestionActual = "Cambiar capacidad";
                        break;
                    } else {
                        mostrarAlerta("Curso no encontrado", "El código del curso no esta registrado");
                        break;
                    }
                case "Asignar estudiante":
                    //Verificar que exista el curso con ese codigo
                    if (consultarExistenciaCurso(codigoCursoTextField.getText())) {
                        mostrarRequisitosAsignarEstudianteCurso(codigoCursoTextField.getText());
                        boton.setVisible(false); //Ocultar boton para evitar que el usuario pueda volver a crear la misma interfaz
                        boton.setDisable(true);
                        gestionActual = "Asignar estudiante";
                        break;
                    } else {
                        mostrarAlerta("Curso no encontrado", "El código del curso no esta registrado");
                        break;
                    }
                case "Quitar estudiante":
                    //Verificar que exista el curso con ese codigo
                    if (consultarExistenciaCurso(codigoCursoTextField.getText())) {
                        mostrarRequisitosQuitarEstudianteCurso(codigoCursoTextField.getText());
                        boton.setVisible(false); //Ocultar boton para evitar que el usuario pueda volver a crear la misma interfaz
                        boton.setDisable(true);
                        gestionActual = "Quitar estudiante";
                        break;
                    } else {
                        mostrarAlerta("Curso no encontrado", "El código del curso no esta registrado");
                        break;
                    }
            }

            //Obtener el cambio de eleccion del ChoiceBox
            String finalGestionActual = gestionActual;
            modificacionCursoChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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

    private void mostrarRequisitosModificarHorarioCurso(String codigo) {
        //Crear elementos adicionales para el VBox
        Label aulaLabel = new Label("Nueva aula:");
        ChoiceBox<String> aulaChoiceBox = new ChoiceBox<>();
        aulaChoiceBox.getItems().addAll("Aula 1", "Aula 2", "Aula 3", "Aula 4", "Aula 5");
        Label fechaLabel = new Label("Nueva fecha:");
        DatePicker nuevaFechaCursoDatePicker = new DatePicker();
        Label horaLabel = new Label("Nueva hora:");
        ChoiceBox<String> horaChoiceBox = new ChoiceBox<>();
        horaChoiceBox.getItems().addAll("08:00", "10:00", "12:00", "14:00", "16:00", "18:00");

        //Agregar los elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setMaxSize(300, 300);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(aulaLabel, 0, 0);
        gridPane.add(aulaChoiceBox, 1, 0);
        gridPane.add(fechaLabel, 0, 1);
        gridPane.add(nuevaFechaCursoDatePicker, 1, 1);
        gridPane.add(horaLabel, 0, 2);
        gridPane.add(horaChoiceBox, 1, 2);

        //Crear boton adicional
        Button boton = new Button("Confirmar");
        //Agregar funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo no este vacio
            if (nuevaFechaCursoDatePicker.getValue() == null || aulaChoiceBox.getValue() == null || horaChoiceBox.getValue() == null) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie todos los requisitos");
                return;
            }

            //Verificar que la fecha y la hora no sean pasadas
            if (nuevaFechaCursoDatePicker.getValue().isBefore(LocalDate.now())) {
                mostrarAlerta("Fecha no válida", "Por favor, seleccione una fecha a partir de hoy");
                return;
            } else if (nuevaFechaCursoDatePicker.getValue().isEqual(LocalDate.now()) && LocalTime.parse(horaChoiceBox.getSelectionModel().getSelectedItem()).isBefore(LocalTime.now())) {
                mostrarAlerta("Hora inválida", "Por favor, seleccione una hora a partir de ahora");
                return;
            }

            //Consultar la disponibildad del aula seleccionada
            String idAula = "000";
            switch(aulaChoiceBox.getSelectionModel().getSelectedItem()) {
                case "Aula 1":
                    if (!consultarDisponibildadAula("001", nuevaFechaCursoDatePicker.getValue(), LocalTime.parse(horaChoiceBox.getValue()))) {
                        mostrarAlerta("Aula no disponible", "Por favor, elija un horario diferente");
                        return;
                    }
                    idAula = "001";
                    break;
                case "Aula 2":
                    if (!consultarDisponibildadAula("002", nuevaFechaCursoDatePicker.getValue(), LocalTime.parse(horaChoiceBox.getValue()))) {
                        mostrarAlerta("Aula no disponible", "Por favor, elija un horario diferente");
                        return;
                    }
                    idAula = "002";
                    break;
                case "Aula 3":
                    if (!consultarDisponibildadAula("003", nuevaFechaCursoDatePicker.getValue(), LocalTime.parse(horaChoiceBox.getValue()))) {
                        mostrarAlerta("Aula no disponible", "Por favor, elija un horario diferente");
                        return;
                    }
                    idAula = "003";
                    break;
                case "Aula 4":
                    if (!consultarDisponibildadAula("004", nuevaFechaCursoDatePicker.getValue(), LocalTime.parse(horaChoiceBox.getValue()))) {
                        mostrarAlerta("Aula no disponible", "Por favor, elija un horario diferente");
                        return;
                    }
                    idAula = "004";
                    break;
                case "Aula 5":
                    if (!consultarDisponibildadAula("005", nuevaFechaCursoDatePicker.getValue(), LocalTime.parse(horaChoiceBox.getValue()))) {
                        mostrarAlerta("Aula no disponible", "Por favor, elija un horario diferente");
                        return;
                    }
                    idAula = "005";
                    break;
            }

            LocalDate fecha = nuevaFechaCursoDatePicker.getValue();
            LocalTime hora = LocalTime.parse(horaChoiceBox.getValue());

            modificarHorarioCurso(codigo, fecha, hora, idAula);
            mostrarMensaje("Curso actualizado", "Curso actualizado exitosamente");
        });

        VBox adicionalVBox = new VBox();
        adicionalVBox.getChildren().addAll(gridPane, boton);

        //Agregar los nuevos elementos al VBox
        requisitosDeGestionDeEstudiantesVBox.getChildren().removeLast();
        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(adicionalVBox);
    }

    private void modificarHorarioCurso(String codigo, LocalDate fecha, LocalTime hora, String idAula) {
        Curso curso = buscarCurso(codigo);
        Aula aula = buscarAula(idAula);
        curso.setFecha(fecha);
        curso.setHora(hora);
        curso.setAulaAsignada(aula);
        String nuevoCodigo = crearCodigoCurso(fecha);
        curso.setCodigo(nuevoCodigo);
        actualizarListaCursosRegistrados(); //Si no se actualiza la lista, no se ven reflejados los cambios
    }

//    private void mostrarRequisitosModificarFechaCurso(String codigo) {
//        //Crear elementos adicionales para el VBox
//        Label label = new Label("Nueva fecha:");
//        DatePicker nuevaFechaCursoDatePicker = new DatePicker();
//
//        //Agregar los elementos creados a un GridPane
//        GridPane gridPane = new GridPane();
//        gridPane.setMaxSize(300, 300);
//        gridPane.setHgap(10);
//        gridPane.setVgap(10);
//        gridPane.add(label, 0, 0);
//        gridPane.add(nuevaFechaCursoDatePicker, 1, 0);
//
//        //Crear boton adicional
//        Button boton = new Button("Confirmar");
//        //Agregar funcion al boton creado
//        boton.setOnAction(e -> {
//            //Verificar que el campo no este vacio
//            if (nuevaFechaCursoDatePicker.getValue() == null) {
//                mostrarAlerta("Campo vacío", "Por favor, diligencie la nueva fecha del curso");
//                return;
//            }
//            modificarFechaCurso(codigo, nuevaFechaCursoDatePicker.getValue());
//            mostrarMensaje("Curso actualizado", "Curso actualizado exitosamente");
//        });
//
//        VBox adicionalVBox = new VBox();
//        adicionalVBox.getChildren().addAll(gridPane, boton);
//
//        //Agregar los nuevos elementos al VBox
//        requisitosDeGestionDeEstudiantesVBox.getChildren().removeLast();
//        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(adicionalVBox);
//    }
//
//    private void modificarFechaCurso(String codigo, LocalDate fecha) {
//        Curso curso = buscarCurso(codigo);
//        curso.setFecha(fecha);
//        String nuevoCodigo = crearCodigoCurso(fecha);
//        curso.setCodigo(nuevoCodigo);
//        actualizarListaCursosRegistrados(); //Si no se actualiza la lista, no se ven reflejados los cambios
//    }
//
//    private void mostrarRequisitosModificarHoraCurso(String codigo) {
//        //Crear elementos adicionales para el VBox
//        Label label = new Label("Nueva hora:");
//        ChoiceBox<String> nuevaHoraChoiceBox = new ChoiceBox<>();
//        nuevaHoraChoiceBox.getItems().addAll("08:00", "10:00", "12:00", "14:00", "16:00", "18:00");
//
//        //Agregar los elementos creados a un GridPane
//        GridPane gridPane = new GridPane();
//        gridPane.setMaxSize(300, 300);
//        gridPane.setHgap(10);
//        gridPane.setVgap(10);
//        gridPane.add(label, 0, 0);
//        gridPane.add(nuevaHoraChoiceBox, 1, 0);
//
//        //Crear boton adicional
//        Button boton = new Button("Confirmar");
//        //Agregar funcion al boton creado
//        boton.setOnAction(e -> {
//            //Verificar que el campo no este vacio
//            if (nuevaHoraChoiceBox.getValue() == null) {
//                mostrarAlerta("Campo vacío", "Por favor, diligencie la nueva hora del curso");
//                return;
//            }
//            modificarHoraCurso(codigo, LocalTime.parse(nuevaHoraChoiceBox.getSelectionModel().getSelectedItem()));
//            mostrarMensaje("Curso actualizado", "Curso actualizado exitosamente");
//        });
//
//        VBox adicionalVBox = new VBox();
//        adicionalVBox.getChildren().addAll(gridPane, boton);
//
//        //Agregar los nuevos elementos al VBox
//        requisitosDeGestionDeEstudiantesVBox.getChildren().removeLast();
//        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(adicionalVBox);
//    }
//
//    private void modificarHoraCurso(String codigo, LocalTime hora) {
//        Curso curso = buscarCurso(codigo);
//        curso.setHora(hora);
//        actualizarListaCursosRegistrados(); //Si no se actualiza la lista, no se ven reflejados los cambios
//    }

    private void mostrarRequisitosModificarCapacidadCurso(String codigo) {
        //Crear elementos adicionales para el VBox
        Label label = new Label("Nueva capacidad:");
        TextField nuevaCapacidadTextField = new TextField();

        //Agregar los elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setMaxSize(300, 300);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(label, 0, 0);
        gridPane.add(nuevaCapacidadTextField, 1, 0);

        //Crear boton adicional
        Button boton = new Button("Confirmar");
        //Agregar funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo no este vacio
            if (nuevaCapacidadTextField.getText() == null) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie la nueva capacidad del curso");
                return;
            }
            //Verificar que la nueva capacidad no sea mayor que la capacidad del aula
            if (buscarCurso(codigo).getAulaAsignada().getCapacidad() < Integer.parseInt(nuevaCapacidadTextField.getText()) ) {
                mostrarAlerta("Capacidad excedida", "La capacidad del curso excede la capacidad del aula, por favor, diligencie una capacidad menor");
                return;
            }
            modificarCapacidadCurso(codigo, Integer.parseInt(nuevaCapacidadTextField.getText()));
            mostrarMensaje("Curso actualizado", "Curso actualizado exitosamente");
        });

        VBox adicionalVBox = new VBox();
        adicionalVBox.getChildren().addAll(gridPane, boton);

        //Agregar los nuevos elementos al VBox
        requisitosDeGestionDeEstudiantesVBox.getChildren().removeLast();
        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(adicionalVBox);
    }

    private void modificarCapacidadCurso(String codigo, int capacidad) {
        Curso curso = buscarCurso(codigo);
        curso.setCapacidad(capacidad);
        actualizarListaCursosRegistrados(); //Si no se actualiza la lista, no se ven reflejados los cambios
    }

    private void mostrarRequisitosAsignarEstudianteCurso(String codigo) {
        //Crear elementos adicionales para el VBox
        Label label = new Label("Identificación del estudiante:");
        TextField identificacionEstudianteLabel = new TextField();

        //Agregar los elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setMaxSize(300, 300);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(label, 0, 0);
        gridPane.add(identificacionEstudianteLabel, 1, 0);

        //Crear boton adicional
        Button boton = new Button("Confirmar");
        //Agregar funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo no este vacio
            if (identificacionEstudianteLabel.getText() == null) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie la identificación del estudiante");
                return;
            }
            //Verificar que exista un estudiante con esa identificacion
            if (!consultarExistenciaEstudiante(identificacionEstudianteLabel.getText())) {
                mostrarAlerta("Estudiante inexistente", "No se encontró un estudiante registrado con la identificación suministrada");
                return;
            }
            //Verificar que el estudiante tenga el mismo instrumento que el curso
            Estudiante estudiante = buscarEstudiante(identificacionEstudianteLabel.getText());
            Curso curso = buscarCurso(codigo);
            if (estudiante.getInstrumento() != curso.getInstrumento()) {
                mostrarAlerta("Instrumento no válido", "El instrumento del estudiante no coincide con el instrumento del curso, por favor, seleccione otro curso que conicida con el instrumento del estudiante");
                return;
            }
            //Verificar que el nivel de estudio sea el apropiado
            if (estudiante.getNivelDeEstudio() == NivelDeEstudio.BASICO) {
                if (curso.getNivelDeEstudio() ==  NivelDeEstudio.MEDIO || curso.getNivelDeEstudio() ==  NivelDeEstudio.AVANZADO) {
                    mostrarAlerta("Nivel de estudio no válido", "El estudiante tiene un nivel de estudio básico, por favor, seleccione un curso con nivel de estudio básico");
                    return;
                }
            } else if (estudiante.getNivelDeEstudio() == NivelDeEstudio.MEDIO) {
                if (curso.getNivelDeEstudio() ==  NivelDeEstudio.AVANZADO) {
                    mostrarAlerta("Nivel de estudio no válido", "El estudiante tiene un nivel de estudio medio, por favor, seleccione un curso con nivel de estudio básico o medio");
                    return;
                }
            }
            //Verificar si el estudiante esta registrado en ese curso
            for (Estudiante est : curso.getEstudiantesRegistrados()) {
                if (est.getIdentificacion().equals(identificacionEstudianteLabel.getText())) {
                    mostrarAlerta("Estudinate ya registrado", "El estudiante con la identificación suministrada ya está registrado en el curso");
                    return;
                }
            }
            asignarEstudianteCurso(codigo, identificacionEstudianteLabel.getText());
            mostrarMensaje("Curso actualizado", "Curso actualizado exitosamente");
        });

        VBox adicionalVBox = new VBox();
        adicionalVBox.getChildren().addAll(gridPane, boton);

        //Agregar los nuevos elementos al VBox
        requisitosDeGestionDeEstudiantesVBox.getChildren().removeLast();
        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(adicionalVBox);
    }

    private void asignarEstudianteCurso(String codigo, String identificacion) {
        Curso curso = buscarCurso(codigo);
        Estudiante estudiante = buscarEstudiante(identificacion);
        curso.getEstudiantesRegistrados().add(estudiante);
        actualizarListaCursosRegistrados(); //Si no se actualiza la lista, no se ven reflejados los cambios
    }

    private void mostrarRequisitosQuitarEstudianteCurso(String codigo) {
        //Crear elementos adicionales para el VBox
        Label label = new Label("Identificación del estudiante:");
        TextField identificacionEstudianteLabel = new TextField();

        //Agregar los elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setMaxSize(300, 300);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(label, 0, 0);
        gridPane.add(identificacionEstudianteLabel, 1, 0);

        //Crear boton adicional
        Button boton = new Button("Confirmar");
        //Agregar funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo no este vacio
            if (identificacionEstudianteLabel.getText() == null) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie la identificación del estudiante");
                return;
            }
            //Verificar que exista un estudiante con esa identificacion
            if (!consultarExistenciaEstudiante(identificacionEstudianteLabel.getText())) {
                mostrarAlerta("Estudiante inexistente", "No se encontró un estudiante registrado con la identificación suministrada");
                return;
            }
            //Verificar si el estudiante esta registrado en ese curso
            Curso curso = buscarCurso(codigo);
            for (Estudiante estudiante : curso.getEstudiantesRegistrados()) {
                if (!estudiante.getIdentificacion().equals(identificacionEstudianteLabel.getText())) {
                    mostrarAlerta("Estudinate no registrado", "El estudiante con la identificación suministrada no está registrado en el curso");
                    return;
                }
            }
            quitarEstudianteCurso(codigo, identificacionEstudianteLabel.getText());
            mostrarMensaje("Curso actualizado", "Curso actualizado exitosamente");
        });

        VBox adicionalVBox = new VBox();
        adicionalVBox.getChildren().addAll(gridPane, boton);

        //Agregar los nuevos elementos al VBox
        requisitosDeGestionDeEstudiantesVBox.getChildren().removeLast();
        requisitosDeGestionDeEstudiantesVBox.getChildren().addAll(adicionalVBox);
    }

    private void quitarEstudianteCurso(String codigo, String identificacion) {
        Curso curso = buscarCurso(codigo);
        Estudiante estudiante = buscarEstudiante(identificacion);
        curso.getEstudiantesRegistrados().remove(estudiante);
        actualizarListaCursosRegistrados(); //Si no se actualiza la lista, no se ven reflejados los cambios
    }




    private boolean consultarExistenciaCurso(String codigo) {
        return academiaController.consultarExistenciaCurso(codigo);
    }

    private Estudiante buscarEstudiante(String identificacion) {
        return academiaController.buscarEstudiante(identificacion);
    }

    private boolean consultarExistenciaEstudiante(String identificacion) {
        return academiaController.consultarExistenciaEstudiante(identificacion);
    }

    private Curso buscarCurso(String codigo) {
        for(Curso curso : academiaController.getCursosRegistrados()) {
            if (curso.getCodigo().equals(codigo)) {
            return curso;}
        }
        return null;
    }

    private Aula buscarAula(String id) {
        for (Aula aula : academiaController.getListaAulas()) {
            if (aula.getId().equals(id)) {
                return  aula;
            }
        }
        return null;
    }

    private boolean consultarDisponibildadAula(String id, LocalDate fecha, LocalTime hora) {
        return academiaController.consultarDisponibilidadAula(id, fecha, hora);
    }

    private void actualizarListaEstudiantesRegistrados() {
        estudiantesRegistradosTableView.refresh();
    }

    private void actualizarListaCursosRegistrados() {
        cursosRegistradosTableView.refresh();
    }

    private void volverAlMenuAnterior() throws IOException {
        app.abrirVentanaPrincipal();
    }

    private void cargarListaEstudiantesRegistrados() {
        estudiantesAsignadosObservableList.clear();
        estudiantesAsignadosObservableList.addAll(academiaController.getEstudiantesRegsitrados());
        estudiantesRegistradosTableView.setItems(estudiantesAsignadosObservableList);
    }

    private void cargarListaCursosRegistrados() {
        cursosRegistradosObservableList.clear();
        cursosRegistradosObservableList.addAll(academiaController.getCursosRegistrados());
        cursosRegistradosTableView.setItems(cursosRegistradosObservableList);
    }

    private void volverAlPrimary() throws IOException {
        app.abrirPrimary();
    }
}
