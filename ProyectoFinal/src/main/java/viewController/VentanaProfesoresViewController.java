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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import model.*;
import javafx.scene.control.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

public class VentanaProfesoresViewController {

    App app;
    AcademiaController academiaController;

    //Adquirir los elementos del fxml

    @FXML
    private ChoiceBox<String> gestionChoiceBox;

    @FXML
    private VBox requisitosDeGestionDeProfesoresVBox;

    @FXML
    private Button volverAlMenuAnteriorButton;

    @FXML
    private Button cerrarSesionButton;

    @FXML
    private TabPane ventanasTabPane;

    //Tabla de profesores registrados
    @FXML
    private TableView<Profesor> profesoresRegistradosTableView;

    @FXML
    private TableColumn<Profesor, String> nombreProfesoresRegistradosTableColumn;

    @FXML
    private TableColumn<Profesor, String> apellidoProfesoresRegistradosTableColumn;

    @FXML
    private TableColumn<Profesor, String> identificacionProfesoresRegistradosTableColumn;

    @FXML
    private TableColumn<Profesor, Integer> estudiantesAsignadosTableColumn;

    @FXML
    private TableColumn<Profesor, Integer> cursosAsignadosProfesoresRegistradosTableColumn;

    private ObservableList<Profesor> profesoresRegistradosObservableList = FXCollections.observableArrayList();

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
        //Preparar columnas de los profesores registrados
        nombreProfesoresRegistradosTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        apellidoProfesoresRegistradosTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getApellido()));
        identificacionProfesoresRegistradosTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIdentificacion()));
        estudiantesAsignadosTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty((cellData.getValue().getListaEstudiantesAsignados().size())).asObject());
        cursosAsignadosProfesoresRegistradosTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getListaCursosAsignados().size()).asObject());

        //Enlazar la lista de profesores al TableView
        profesoresRegistradosTableView.setItems(profesoresRegistradosObservableList);
        //Refrescar la tabla por si se cambia de pestana
        cargarListaProfesoresRegistrados();

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
        gestionChoiceBox.getItems().addAll("Crear profesor", "Eliminar profesor", "Modificar profesor", "Consultar profesor");

        //Obtener el cambio de seleccion del ChoiceBox
        gestionChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Crear profesor":
                    mostrarRequisitosCrearProfesor();
                    break;
                case "Eliminar profesor":
                    mostrarRequisitosEliminarProfesor();
                    break;
                case "Modificar profesor":
                    mostrarRequisitosModificarProfesor();
                    break;
                case "Consultar profesor":
                    mostrarRequisitosConsultarProfesor();
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
                    case "Cursos Registrados":
                        gestionChoiceBox.getItems().clear();
                        gestionChoiceBox.getItems().addAll("Crear Curso", "Eliminar Curso", "Consultar Curso", "Modificar Curso");
                        break;
                    case "Profesores Registrados":
                        gestionChoiceBox.getItems().clear();
                        gestionChoiceBox.getItems().addAll("Crear profesor", "Eliminar profesor", "Modificar profesor", "Consultar Estudiante");
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

    private void mostrarRequisitosCrearProfesor() {
        //Crear elementos del vbox
        Label label = new Label("Crear Profesor");

        Label nombreProfesorLabel = new Label("Nombre:");
        TextField nombreProfesorTextField = new TextField();
        Label apellidoProfesorLabel = new Label("Apellido:");
        TextField apellidoProfesorTextField = new TextField();
        Label identificacionProfesorLabel = new Label("Identificacion:");
        TextField identificacionProfesorTextField = new TextField();

        //Agregar los elementos creados a un gridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(nombreProfesorLabel, 0, 0);
        gridPane.add(nombreProfesorTextField, 1, 0);
        gridPane.add(apellidoProfesorLabel, 0, 1);
        gridPane.add(apellidoProfesorTextField, 1, 1);
        gridPane.add(identificacionProfesorLabel, 0, 2);
        gridPane.add(identificacionProfesorTextField, 1, 2);

        //Crear el boton para crear profesor
        Button boton = new Button("Crear Profesor");

        //Vincular la funcion al boton creado
        boton.setOnAction(event -> {

            //Verificar que los campos de String esten llenados
            if(nombreProfesorTextField.getText().isEmpty() || apellidoProfesorTextField.getText().isEmpty() || identificacionProfesorTextField.getText().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie todos los campos");
                return;
            }

            /* Esta logica puede ser util para despues
            //Convertir el String seleccionado a la clase Instrumento
            Instrumento instrumento = Instrumento.DEFAULT;
            switch (cursosAsignadosProfesorTextField.getSelectionModel().getSelectedItem()) {
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
            switch (estudiantesAsignadosProfesorTextField.getSelectionModel().getSelectedItem()) {
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
             */

            //Crear el profesor (Sin estudiantes asignados ni cursos asignados)
            crearProfesor(nombreProfesorTextField.getText(), apellidoProfesorTextField.getText(), identificacionProfesorTextField.getText());
        });

        //Mostrar los requisitos en el VBox
        requisitosDeGestionDeProfesoresVBox.getChildren().clear();
        requisitosDeGestionDeProfesoresVBox.getChildren().addAll(label, gridPane, boton);
    }

    private void crearProfesor(String nombre, String apellido, String identificacion) {
        Profesor nuevoProfesor = new Profesor(nombre, apellido, identificacion);

        if (academiaController.crearProfesor(nuevoProfesor)) {
            mostrarProfesorRegistrado(nuevoProfesor);
            crearUsuarioProfesor(identificacion, identificacion, identificacion, Rol.PROFESOR); //Por defecto, el usuario se crea con la misma identificacion, despues se puede modificar
            mostrarMensaje("Profesor creado", "Profesor creado exitosamente");
        } else {
            mostrarAlerta("Error al crear profesor", "El profesor ya existe");
        }

    }

    private void crearUsuarioProfesor(String usuario, String contrasena, String identificacion, Rol rol) {
        academiaController.agregarUsuario(usuario, contrasena, identificacion, rol);
    }

    private void mostrarProfesorRegistrado(Profesor profesor) {
        profesoresRegistradosObservableList.add(profesor);
    }

    private void mostrarRequisitosEliminarProfesor() {
        //Crear elementos del VBox
        Label label = new Label("Eliminar Profesor");
        Label identificacionProfesorLabel = new Label("Identificación:");
        TextField identificacionProfesorTextField = new TextField();

        //Asignar los elementos crados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(identificacionProfesorLabel, 0, 0);
        gridPane.add(identificacionProfesorTextField, 1, 0);

        Button boton = new Button("Eliminar");

        //Vincular la funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo del String este diligenciado
            if (identificacionProfesorTextField.getText().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie la identificacion del profesor");
                return;
            }
            eliminarProfesor(identificacionProfesorTextField.getText());
        });

        //Mostrar los requisitos en el VBox
        requisitosDeGestionDeProfesoresVBox.getChildren().clear();
        requisitosDeGestionDeProfesoresVBox.getChildren().addAll(label, gridPane, boton);
    }

    private void eliminarProfesor(String identificacion) {
        if(academiaController.eliminarProfesor(identificacion)) {
            quitarProfesorEliminado(identificacion);
            mostrarMensaje("Profesor eliminado", "Profesor eliminado exitosamente");
        } else {
            mostrarAlerta("Error al eliminar profesor", "El número no está registrado");
        }
    }

    private void quitarProfesorEliminado(String identificacion) {
        Profesor profesor = null;

        for (Profesor p : profesoresRegistradosObservableList) {
            if (p.getIdentificacion().equals(identificacion)) {
                profesor = p;
                break;
            }
        }

        profesoresRegistradosObservableList.remove(profesor);
    }

    private void mostrarRequisitosModificarProfesor() {
        //Crear elementos del Vbox
        Label label = new Label("Modificar Profesor");
        Label identificacionProfesorLabel = new Label("Identificacion:");
        TextField identificacionProfesorTextField = new TextField();
        Label modificacionProfesorLabel = new Label("Modificación:");
        ChoiceBox<String> modificacionProfesorChoiceBox = new ChoiceBox<>();
        //Añadir opciones al ChoiceBox
        modificacionProfesorChoiceBox.getItems().addAll("Cambiar nombre", "Cambiar apellido", "Cambiar identificación");

        //Asignar elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(identificacionProfesorLabel, 0, 0);
        gridPane.add(identificacionProfesorTextField, 1, 0);
        gridPane.add(modificacionProfesorLabel, 0, 1);
        gridPane.add(modificacionProfesorChoiceBox, 1, 1);

        //Crear boton del VBox
        Button boton = new Button("Modificar");

        //Asginar funciones al boton creado
        boton.setOnAction(e -> {
            //Verificar que los campos esten diligenciados
            if (identificacionProfesorTextField.getText().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie la identificación del profesor");
                return;
            } else if (modificacionProfesorChoiceBox.getSelectionModel().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, seleccione una opción de modificación");
                return;
            }

            //Mostrar los requisitos de la gestion seleccionada
            String gestionActual = "Defecto";
            switch (modificacionProfesorChoiceBox.getSelectionModel().getSelectedItem()) {
                case "Cambiar nombre":
                    //Verificar que exista el profesor con esa identificacion
                    if (consultarExistenciaProfesor(identificacionProfesorTextField.getText())) {
                        mostrarRequisitosModificarNombreProfesor(identificacionProfesorTextField.getText());
                        boton.setVisible(false); //Ocultar boton para evitar que el usuario pueda volver a crear la misma interfaz
                        boton.setDisable(true); //Desactivar el boton para evitar que el usuario pueda volver a crear la misma interfaz
                        gestionActual = "Cambiar nombre";
                        break;
                    } else {
                        mostrarAlerta("Profesor no encontrado", "La identificacion del profesor no esta registrada");
                        break;
                    }
                case "Cambiar apellido":
                    //Verificar que exista el profesor con esa identificacion
                    if (consultarExistenciaProfesor(identificacionProfesorTextField.getText())) {
                        mostrarRequisitosModificarApellidoProfesor(identificacionProfesorTextField.getText());
                        boton.setVisible(false); //Ocultar boton para evitar que el usuario pueda volver a crear la misma interfaz
                        boton.setDisable(true); //Desactivar el boton para evitar que el usuario pueda volver a crear la misma interfaz
                        gestionActual = "Cambiar apellido";
                        break;
                    } else {
                        mostrarAlerta("Profesor no encontrado", "La identificacion del profesor no esta registrada");
                        break;
                    }
                case "Cambiar identificación":
                    //Verificar que exista el profesor con esa identificacion
                    if (consultarExistenciaProfesor(identificacionProfesorTextField.getText())) {
                        mostrarRequisitosModificarIdentificacionProfesor(identificacionProfesorTextField.getText());
                        boton.setVisible(false); //Ocultar boton para evitar que el usuario pueda volver a crear la misma interfaz
                        boton.setDisable(true); //Desactivar el boton para evitar que el usuario pueda volver a crear la misma interfaz
                        gestionActual = "Cambiar identificacion";
                        break;
                    } else {
                        mostrarAlerta("Profesor no encontrado", "La identificacion del profesor no esta registrada");
                        break;
                    }
            }

            //Obtener el cambio de eleccion del ChoiceBox
            String finalGestionActual = gestionActual;
            modificacionProfesorChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
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
        requisitosDeGestionDeProfesoresVBox.getChildren().clear();
        requisitosDeGestionDeProfesoresVBox.getChildren().addAll(label, gridPane, boton, gestionElegidaVBox);
    }

    private void mostrarRequisitosModificarNombreProfesor(String identificacion) {
        //Crear elementos adicionales para el VBox
        Label label = new Label("Nuevo nombre:");
        TextField nuevoNombreProfesorTextField = new TextField();

        //Agregar los elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setMaxSize(300, 300);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(label, 0, 0);
        gridPane.add(nuevoNombreProfesorTextField, 1, 0);

        //Crear boton adicional
        Button boton = new Button("Confirmar");
        //Agregar funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo no este vacio
            if (nuevoNombreProfesorTextField.getText().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie el nuevo nombre del profesor");
                return;
            }
            modificarNombreProfesor(identificacion, nuevoNombreProfesorTextField.getText());
            mostrarMensaje("Profesor actualizado", "Profesor actualizado exitosamente");
        });

        VBox adicionalVBox = new VBox();
        adicionalVBox.getChildren().addAll(gridPane, boton);

        //Agregar los nuevos elementos al VBox
        requisitosDeGestionDeProfesoresVBox.getChildren().removeLast();
        requisitosDeGestionDeProfesoresVBox.getChildren().addAll(adicionalVBox);
    }

    private void modificarNombreProfesor(String identificacion, String nuevoNombre) {
        Profesor profesor = buscarProfesor(identificacion);
        profesor.setNombre(nuevoNombre);
        actualizarListaProfesoresRegistrados(); //Si no se actualiza la lista, no se ven reflejados los cambios
    }

    private void mostrarRequisitosModificarApellidoProfesor(String identificacion) {
        //Crear elementos adicionales para el VBox
        Label label = new Label("Nuevo apellido:");
        TextField nuevoApellidoProfesorTextField = new TextField();

        //Agregar los elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setMaxSize(300, 300);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(label, 0, 0);
        gridPane.add(nuevoApellidoProfesorTextField, 1, 0);

        //Crear boton adicional
        Button boton = new Button("Confirmar");
        //Agregar funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo no este vacio
            if (nuevoApellidoProfesorTextField.getText().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie el nuevo apellido del profesor");
                return;
            }
            modificarApellidoProfesor(identificacion, nuevoApellidoProfesorTextField.getText());
            mostrarMensaje("Profesor actualizado", "Profesor actualizado exitosamente");
        });

        VBox adicionalVBox = new VBox();
        adicionalVBox.getChildren().addAll(gridPane, boton);

        //Agregar los nuevos elementos al VBox
        requisitosDeGestionDeProfesoresVBox.getChildren().removeLast();
        requisitosDeGestionDeProfesoresVBox.getChildren().addAll(adicionalVBox);
    }

    private void modificarApellidoProfesor(String identificacion, String nuevoApellido) {
        Profesor profesor = buscarProfesor(identificacion);
        profesor.setApellido(nuevoApellido);
        actualizarListaProfesoresRegistrados(); //Si no se actualiza la lista, no se ven reflejados los cambios
    }

    private void mostrarRequisitosModificarIdentificacionProfesor(String identificacion) {
        //Crear elementos adicionales para el VBox
        Label label = new Label("Nueva  identificacion:");
        TextField nuevaIdentificacionProfesorTextField = new TextField();

        //Agregar los elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setMaxSize(300, 300);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(label, 0, 0);
        gridPane.add(nuevaIdentificacionProfesorTextField, 1, 0);

        //Crear boton adicional
        Button boton = new Button("Confirmar");
        //Agregar funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo no este vacio
            if (nuevaIdentificacionProfesorTextField.getText().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie la nueva identificación del profesor");
                return;
            }
            modificarIdentificacionProfesor(identificacion,  nuevaIdentificacionProfesorTextField.getText());
            mostrarMensaje("Profesor actualizado", "Profesor actualizado exitosamente");
        });

        VBox adicionalVBox = new VBox();
        adicionalVBox.getChildren().addAll(gridPane, boton);

        //Agregar los nuevos elementos al VBox
        requisitosDeGestionDeProfesoresVBox.getChildren().removeLast();
        requisitosDeGestionDeProfesoresVBox.getChildren().addAll(adicionalVBox);
    }

    private void modificarIdentificacionProfesor(String identificacion, String nuevaIdentificacion) {
        Profesor profesor = buscarProfesor(identificacion);
        profesor.setIdentificacion(nuevaIdentificacion);
        actualizarListaProfesoresRegistrados(); //Si no se actualiza la lista, no se ven reflejados los cambios
    }

    private void mostrarRequisitosConsultarProfesor() {
        //Crear elementos del VBox
        Label label = new Label("Consultar Profesor");
        Label identificacionProfesorLabel = new Label("Identificación:");
        TextField identificacionProfesorTextField = new TextField();

        //Asignar los elementos crados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(identificacionProfesorLabel, 0, 0);
        gridPane.add(identificacionProfesorTextField, 1, 0);

        Button boton = new Button("Consultar");

        //Vincular la funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo del String este diligenciado
            if (identificacionProfesorTextField.getText().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie la identificación del profesor");
                return;
            }
            consultarProfesor(identificacionProfesorTextField.getText());
        });

        //Mostrar los requisitos en el VBox
        requisitosDeGestionDeProfesoresVBox.getChildren().clear();
        requisitosDeGestionDeProfesoresVBox.getChildren().addAll(label, gridPane, boton);
    }

    private void consultarProfesor(String identificacion) {
        if (consultarExistenciaProfesor(identificacion)) {
            Profesor nuevoProfesor = buscarProfesor(identificacion);
            mostrarMensaje("Profesor Consultado", nuevoProfesor.toString());
        } else {
            mostrarAlerta("Profesor no existe", "La identificación del profesor no está registrada, por favor verifique la identificación diligenciada");
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
        aulaChoiceBox.getItems().addAll("Aula 1", "Aula 2", "Aula 3", "Aula 4", "Aula 5");
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

        requisitosDeGestionDeProfesoresVBox.getChildren().clear();
        requisitosDeGestionDeProfesoresVBox.getChildren().addAll(label, gridPane);
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
        requisitosDeGestionDeProfesoresVBox.getChildren().clear();
        requisitosDeGestionDeProfesoresVBox.getChildren().addAll(label, gridPane, boton);
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
        requisitosDeGestionDeProfesoresVBox.getChildren().clear();
        requisitosDeGestionDeProfesoresVBox.getChildren().addAll(label, gridPane, boton);
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
        modificacionCursoChoiceBox.getItems().addAll("Cambiar horario", "Cambiar capacidad", "Cambiar profesor", "Asignar profesor", "Quitar profesor");

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
                case "Cambiar profesor":
                    //Verificar que exista el curso con ese codigo
                    if (consultarExistenciaCurso(codigoCursoTextField.getText())) {
                        mostrarMensaje("Cambiar profesor", "Bien");
                        boton.setVisible(false); //Ocultar boton para evitar que el usuario pueda volver a crear la misma interfaz
                        boton.setDisable(true); //Desactivar el boton para evitar que el usuario pueda volver a crear la misma interfaz
                        gestionActual = "Cambiar profesor";
                        break;
                    } else {
                        mostrarAlerta("Curso no encontrado", "El código del curso no esta registrado");
                        break;
                    }
                case "Asignar profesor":
                    //Verificar que exista el curso con ese codigo
                    if (consultarExistenciaCurso(codigoCursoTextField.getText())) {
                        mostrarRequisitosAsignarProfesorCurso(codigoCursoTextField.getText());
                        boton.setVisible(false); //Ocultar boton para evitar que el usuario pueda volver a crear la misma interfaz
                        boton.setDisable(true);
                        gestionActual = "Asignar profesor";
                        break;
                    } else {
                        mostrarAlerta("Curso no encontrado", "El código del curso no esta registrado");
                        break;
                    }
                case "Quitar profesor":
                    //Verificar que exista el curso con ese codigo
                    if (consultarExistenciaCurso(codigoCursoTextField.getText())) {
                        mostrarRequisitosQuitarProfesorCurso(codigoCursoTextField.getText());
                        boton.setVisible(false); //Ocultar boton para evitar que el usuario pueda volver a crear la misma interfaz
                        boton.setDisable(true);
                        gestionActual = "Quitar profesor";
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
        requisitosDeGestionDeProfesoresVBox.getChildren().clear();
        requisitosDeGestionDeProfesoresVBox.getChildren().addAll(label, gridPane, boton, gestionElegidaVBox);
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
        requisitosDeGestionDeProfesoresVBox.getChildren().removeLast();
        requisitosDeGestionDeProfesoresVBox.getChildren().addAll(adicionalVBox);
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
//        requisitosDeGestionDeProfesoresVBox.getChildren().removeLast();
//        requisitosDeGestionDeProfesoresVBox.getChildren().addAll(adicionalVBox);
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
//        requisitosDeGestionDeProfesoresVBox.getChildren().removeLast();
//        requisitosDeGestionDeProfesoresVBox.getChildren().addAll(adicionalVBox);
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
        requisitosDeGestionDeProfesoresVBox.getChildren().removeLast();
        requisitosDeGestionDeProfesoresVBox.getChildren().addAll(adicionalVBox);
    }

    private void modificarCapacidadCurso(String codigo, int capacidad) {
        Curso curso = buscarCurso(codigo);
        curso.setCapacidad(capacidad);
        actualizarListaCursosRegistrados(); //Si no se actualiza la lista, no se ven reflejados los cambios
    }

    private void mostrarRequisitosAsignarProfesorCurso(String codigo) {
        //Crear elementos adicionales para el VBox
        Label label = new Label("Identificación del profesor:");
        TextField identificacionProfesorLabel = new TextField();

        //Agregar los elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setMaxSize(300, 300);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(label, 0, 0);
        gridPane.add(identificacionProfesorLabel, 1, 0);

        //Crear boton adicional
        Button boton = new Button("Confirmar");
        //Agregar funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo no este vacio
            if (identificacionProfesorLabel.getText() == null) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie la identificación del profesor");
                return;
            }
            //Verificar que exista un profesor con esa identificacion
            if (!consultarExistenciaProfesor(identificacionProfesorLabel.getText())) {
                mostrarAlerta("Profesor inexistente", "No se encontró un profesor registrado con la identificación suministrada");
                return;
            }
            asignarProfesorCurso(codigo, identificacionProfesorLabel.getText());
            mostrarMensaje("Curso actualizado", "Curso actualizado exitosamente");
        });

        VBox adicionalVBox = new VBox();
        adicionalVBox.getChildren().addAll(gridPane, boton);

        //Agregar los nuevos elementos al VBox
        requisitosDeGestionDeProfesoresVBox.getChildren().removeLast();
        requisitosDeGestionDeProfesoresVBox.getChildren().addAll(adicionalVBox);
    }

    private void asignarProfesorCurso(String codigo, String identificacion) {
        Curso curso = buscarCurso(codigo);
        Profesor profesor = buscarProfesor(identificacion);
        curso.setProfesor(profesor);
        profesor.getListaCursosAsignados().add(curso);
        actualizarListaProfesoresRegistrados();   //Si no se actualiza la lista, no se ven reflejados los cambios
        actualizarListaCursosRegistrados();   //Si no se actualiza la lista, no se ven reflejados los cambios
    }

    private void mostrarRequisitosQuitarProfesorCurso(String codigo) {
        //Crear elementos adicionales para el VBox
        Label label = new Label("Identificación del profesor:");
        TextField identificacionProfesorLabel = new TextField();

        //Agregar los elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setMaxSize(300, 300);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(label, 0, 0);
        gridPane.add(identificacionProfesorLabel, 1, 0);

        //Crear boton adicional
        Button boton = new Button("Confirmar");
        //Agregar funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo no este vacio
            if (identificacionProfesorLabel.getText() == null) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie la identificación del profesor");
                return;
            }
            //Verificar que exista un estudiante con esa identificacion
            if (!consultarExistenciaProfesor(identificacionProfesorLabel.getText())) {
                mostrarAlerta("Profesor inexistente", "No se encontró un profesor registrado con la identificación suministrada");
                return;
            }
            //Verificar si el profesor esta registrado en ese curso
            Curso curso = buscarCurso(codigo);
            Profesor profesor = buscarProfesor(identificacionProfesorLabel.getText());
            if (curso.getProfesor() != profesor) {
                mostrarAlerta("Profesor no asignado", "El profesor con la identificación suministrada no está asignado al curso");
                return;
            }
            quitarProfesorCurso(codigo);
            mostrarMensaje("Curso actualizado", "Curso actualizado exitosamente");
        });

        VBox adicionalVBox = new VBox();
        adicionalVBox.getChildren().addAll(gridPane, boton);

        //Agregar los nuevos elementos al VBox
        requisitosDeGestionDeProfesoresVBox.getChildren().removeLast();
        requisitosDeGestionDeProfesoresVBox.getChildren().addAll(adicionalVBox);
    }

    private void quitarProfesorCurso(String codigo) {
        Curso curso = buscarCurso(codigo);
        curso.setProfesor(null);
        actualizarListaCursosRegistrados(); //Si no se actualiza la lista, no se ven reflejados los cambios
    }



    private boolean consultarExistenciaCurso(String codigo) {
        return academiaController.consultarExistenciaCurso(codigo);
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

    private boolean consultarExistenciaProfesor(String identificacion) {
        return academiaController.consultarExistenciaProfesor(identificacion);
    }

    private Profesor buscarProfesor(String identificacion) {
        return academiaController.buscarProfesor(identificacion);
    }

    private boolean consultarDisponibildadAula(String id, LocalDate fecha, LocalTime hora) {
        return academiaController.consultarDisponibilidadAula(id, fecha, hora);
    }

    private void actualizarListaProfesoresRegistrados() {
        profesoresRegistradosTableView.refresh();
    }

    private void volverAlMenuAnterior() throws IOException {
        app.abrirVentanaPrincipal();
    }

    private void actualizarListaCursosRegistrados() {
        cursosRegistradosTableView.refresh();
    }

    private void cargarListaProfesoresRegistrados() {
        profesoresRegistradosObservableList.clear();
        profesoresRegistradosObservableList.addAll(academiaController.getProfesoresRegistrados());
        profesoresRegistradosTableView.setItems(profesoresRegistradosObservableList);
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
