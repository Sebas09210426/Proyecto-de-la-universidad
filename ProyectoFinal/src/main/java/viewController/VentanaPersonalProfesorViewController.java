package viewController;

import app.App;
import controller.AcademiaController;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import model.*;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedList;

import static viewController.PrimaryViewController.*;
import static viewController.PrimaryViewController.mostrarAlerta;
import static viewController.PrimaryViewController.mostrarMensaje;

public class VentanaPersonalProfesorViewController implements Actualizable{
    //Declarar variables globales
    private App app;
    private AcademiaController academiaController;;
    private Usuario usuarioActual;
    private Profesor profesorActual;

    //Obtener elementos del fxml
    @FXML
    Label bienvenidoLabel;

    @FXML
    Button cerrarSesionButton;

    @FXML
    ChoiceBox<String> gestionChoiceBox;

    @FXML
    VBox requisitosGestionVBox;

    @FXML
    TabPane ventanasTabPane;

    @FXML
    Label nombreLabel;

    @FXML
    Label apellidoLabel;

    @FXML
    Label identificacionLabel;

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

    //Tabla de clases individuales
    @FXML
    private TableView<Curso> clasesIndividualesTableView;

    @FXML
    private TableColumn<Curso, LocalDate> fechaClaseIndividualTableColumn;

    @FXML
    private TableColumn<Curso, LocalTime> horaClaseIndividualTableColumn;

    @FXML
    private TableColumn<Curso, String> aulaClaseIndividualTableColumn;

    @FXML
    private TableColumn<Curso, String> instrumentoClaseIndividualTableColumn;

    @FXML
    private TableColumn<Curso, String> estudianteClaseIndividualTableColumn;

    @FXML
    private TableColumn<Curso, String> estadoClaseIndividualTableColumn;

    @FXML
    private TableColumn<Curso, String> codigoClaseIndividualTableColumn;

    private ObservableList<Curso> clasesIndividualesObservableList = FXCollections.observableArrayList();

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



    //Crear listeners de seleccion para evitar errores
    private ChangeListener<Curso> listenerCurso;
    private ChangeListener<Estudiante> listenerEstudiante;

    public void setApp(App app) {
        this.app = app;
    }

    public void setUsuario(Usuario usuario) {
        this.usuarioActual = usuario;
    }

    public void cargarDatosUsuario() {
        //Obtener profesor del usuario
        profesorActual = academiaController.buscarProfesor(usuarioActual.getIdentificacion());
        //Cargar datos de las clases individuales
        cargarListaClasesIndividuales();

        //Actualizar los datos
        bienvenidoLabel.setText("Bienvenido " + profesorActual.getNombre());
        nombreLabel.setText("Nombre: " + profesorActual.getNombre());
        apellidoLabel.setText("Apellido: " + profesorActual.getApellido());
        identificacionLabel.setText("Identificacion: " + profesorActual.getIdentificacion());

        //Verificar si es la primera vez que inicia sesion
        if (usuarioActual.getPrimerInicio()) {
            mostrarMensaje("Cambiar contraseña", "Por favor, cambie el usuario y contraseña");
            try {
                actualizarCredenciales();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setAcademiaController(AcademiaController academiaController) {
        this.academiaController = academiaController;
    }

    public void cargarDatos() {
        //Preparar columnas de los cursos asignados
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

        //Cargar lista de cursos registrados por si se cambia de pestana
        cargarListaCursosAsignados();

        //Preparar columnas de las clases individuales registradas
        fechaClaseIndividualTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getFecha()));
        horaClaseIndividualTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleObjectProperty<>(cellData.getValue().getHora()));
        aulaClaseIndividualTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getAulaAsignada() != null ? cellData.getValue().getAulaAsignada().getId() : "Sin asignar"));
        instrumentoClaseIndividualTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getInstrumento().toString()));
        estudianteClaseIndividualTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEstudiantesRegistrados().getFirst().getNombre() + " " + cellData.getValue().getEstudiantesRegistrados().getFirst().getApellido()));
        estadoClaseIndividualTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getEstado()));
        codigoClaseIndividualTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getCodigo() != null ? cellData.getValue().getCodigo() : "Sin asignar"));

        //Enlazar la lista de clases individuales al TableView
        clasesIndividualesTableView.setItems(clasesIndividualesObservableList);

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

    }

    @FXML
    private void initialize() {
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

        //Configurar acciones al cambiar de ventana
        ventanasTabPane.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newTab) -> {
            //Cambiar opciones del ChoiceBox
            if (newTab != null) {
                String nuevoTab = newTab.getText();
                switch (nuevoTab) {
                    case "Cursos asignados":
                        gestionChoiceBox.getItems().clear();
                        gestionChoiceBox.getItems().addAll("Crear Curso", "Consultar Curso", "Modificar Curso", "Gestionar notas curso");
                        break;
                    case "Datos personales":
                        gestionChoiceBox.getItems().clear();
                        gestionChoiceBox.getItems().addAll("Consultar", "Actualizar");
                        break;
                    case "Clases individuales":
                        gestionChoiceBox.getItems().clear();
                        gestionChoiceBox.getItems().addAll("Consultar clases pedidas", "Crear clase individual", "Modificar clase individual");
                        break;
                }
            }
        });

        //Obtener el cambio de seleccion del ChoiceBox
        gestionChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            switch (newValue) {
                case "Consultar":
                    mostrarRequisitosConsultar();
                    break;
                case "Actualizar":
                    mostrarRequisitosActualizarProfesor();
                    break;
                case "Crear Curso":
                    mostrarRequisitosCrearCurso();
                    break;
                case "Modificar Curso":
                    mostrarRequisitosModificarCurso();
                    break;
                case "Consultar Curso":
                    mostrarRequisitosConsultarCurso();
                    break;
                case "Modificar clases pedidas":
                    break;
                case "Consultar clases pedidas":
                    break;
                case "Gestionar notas curso":
                    mostrarRequisitosGestionarNotasCurso();
                    break;
            }
        });
    }

    @Override
    public void actualizarCredenciales() throws IOException {
        app.abrirActualizarCredenciales(usuarioActual);
    }

    private void mostrarRequisitosConsultar() {
        //Crear elementos de requisitos
        Label label = new Label("Consultar");

        Label consultaLabel = new Label("Seleccione su consulta:");
        ChoiceBox<String> consultaChoiceBox = new ChoiceBox<>();
        consultaChoiceBox.getItems().addAll("Consultar horario", "Consultar comentarios", "Consultar clases individuales");
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
                    consultarHorarioProfesor();
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

    private void mostrarRequisitosActualizarProfesor() {
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
                    mostrarRequisitosActualizarNombreProfesor();
                    break;
                case "Actualizar apellido":
                    mostrarRequisitosActualizarApellidoProfesor();
                    break;
//                case "Actualizar identificación":
//                    mostrarMensaje("Consultar identificacion", "Hasta aqui bien");
//                    break;
                default:
                    mostrarAlerta("Opción no seleccionada", "Por favor, seleccione su actualización");
                    break;
            }
        });

        requisitosGestionVBox.getChildren().clear();
        requisitosGestionVBox.getChildren().addAll(label, gridPane, boton);
    }

    private void consultarHorarioProfesor() {
        if (profesorActual.getListaCursosAsignados() != null) {
            String horario = "";
            for (Curso c : profesorActual.getListaCursosAsignados()) {
                horario += "{"
                        + "Fecha: " + (c.getFecha() != null ? c.getFecha().toString() : "Sin asignar") + "\n"
                        + "Hora: " + (c.getHora() != null ? c.getHora().toString() : "Sin asignar") + "\n"
                        + "Aula: " + (c.getAulaAsignada() != null ? c.getAulaAsignada().getId() : "Sin asignar") + "\n"
                        + "Estudiantes: " + (c.getEstudiantesRegistrados() != null ? c.getEstudiantesRegistrados().size() : "Sin asignar") + "\n"
                        + "}";

            }
            mostrarMensaje("Horario", horario);
        } else {
            mostrarMensaje("Horario", "Sin clases");
        }
    }

    private void mostrarRequisitosActualizarNombreProfesor() {
        //Crear elementos del Vbox
        Label label = new Label("Actualizar nombre");
        Label nombreProfesorLabel = new Label("Nuevo nombre:");
        TextField nombreProfesorTextField = new TextField();

        //Asignar elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(nombreProfesorLabel, 0, 0);
        gridPane.add(nombreProfesorTextField, 1, 0);

        //Crear boton del VBox
        Button boton = new Button("Actualizar");

        //Asginar funciones al boton creado
        boton.setOnAction(e -> {
            //Verificar que los campos esten diligenciados
            if (nombreProfesorTextField.getText().isEmpty()) {
                mostrarAlerta("Nombre vacío", "Por favor, diligencie el nuevo nombre");
                return;
            }
            actualizarNombreProfesor(usuarioActual.getIdentificacion(), nombreProfesorTextField.getText());
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

    private void actualizarNombreProfesor(String identificacion, String nuevoNombre) {
        Profesor profesor = buscarProfesor(identificacion);
        profesor.setNombre(nuevoNombre);
    }

    private void mostrarRequisitosActualizarApellidoProfesor() {
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
            actualizarApellidoProfesor(usuarioActual.getIdentificacion(), apeliidoEstudianteTextField.getText());
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

    private void actualizarApellidoProfesor(String identificacion, String nuevoApellido) {
        Profesor profesor = buscarProfesor(identificacion);
        profesor.setApellido(nuevoApellido);
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

        requisitosGestionVBox.getChildren().clear();
        requisitosGestionVBox.getChildren().addAll(label, gridPane);
    }

    private void crearCurso(int capacidad, LinkedList<Estudiante> estudiantes, LocalDate fecha, LocalTime hora, Instrumento instrumento, NivelDeEstudio nivelDeEstudio, String codigo, String idAula) {
        Aula aula = buscarAula(idAula);

        if (aula != null && aula.getCapacidad() < capacidad) {
            mostrarAlerta("Excede la capacidad del aula", "La cantidad de estudiantes del curso excede la capacidad del aula, por favor, elija otro aula");
            return;
        }

        Curso nuevoCurso = new Curso(capacidad, estudiantes, fecha, hora, instrumento, nivelDeEstudio,null, codigo, aula, "Programada");

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

        //Obtener la seleccion de la tabla de cursos registrados
        if (listenerCurso != null) {
            cursosRegistradosTableView.getSelectionModel().selectedItemProperty().removeListener(listenerCurso);
        }
        listenerCurso = (obs, oldValue, newValue) -> {
            if (newValue != null) {
                codigoCursoTextField.setText(newValue.getCodigo());
            }
        };
        //Asignar el listener al tableview
        cursosRegistradosTableView.getSelectionModel().selectedItemProperty().addListener(listenerCurso);

        Button boton = new Button("Consultar");

        //Vincular la funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo del String este diligenciado
            if (codigoCursoTextField.getText().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie el codigo del curso");
                return;
            }

            //Verificar que exista el curso
            if (!consultarExistenciaCurso(codigoCursoTextField.getText())) {
                mostrarAlerta("Curso no encontrado", "No se ha encontrado un curso registrado con el código suministrado, por favor, verifique el código");
                return;
            }
            consultarCurso(codigoCursoTextField.getText());
        });

        //Mostrar los requisitos en el VBox
        requisitosGestionVBox.getChildren().clear();
        requisitosGestionVBox.getChildren().addAll(label, gridPane, boton);
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

        //Obtener la seleccion de la tabla de cursos registrados
        if (listenerCurso != null) {
            cursosRegistradosTableView.getSelectionModel().selectedItemProperty().removeListener(listenerCurso);
        }
        listenerCurso = (obs, oldValue, newValue) -> {
            if (newValue != null) {
                codigoCursoTextField.setText(newValue.getCodigo());
            }
        };
        //Asignar el listener al tableview
        cursosRegistradosTableView.getSelectionModel().selectedItemProperty().addListener(listenerCurso);

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
        requisitosGestionVBox.getChildren().clear();
        requisitosGestionVBox.getChildren().addAll(label, gridPane, boton, gestionElegidaVBox);
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

            //Verificar que exista el curso
            if (!consultarExistenciaCurso(codigo)) {
                mostrarAlerta("Curso no encontrado", "No se ha encontrado un curso registrado con el código suministrado, por favor, verifique el código");
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
        requisitosGestionVBox.getChildren().removeLast();
        requisitosGestionVBox.getChildren().addAll(adicionalVBox);
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
            //Verificar que exista el curso
            if (!consultarExistenciaCurso(codigo)) {
                mostrarAlerta("Curso no encontrado", "No se ha encontrado un curso registrado con el código suministrado, por favor, verifique el código");
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
        requisitosGestionVBox.getChildren().removeLast();
        requisitosGestionVBox.getChildren().addAll(adicionalVBox);
    }

    private void modificarCapacidadCurso(String codigo, int capacidad) {
        Curso curso = buscarCurso(codigo);
        curso.setCapacidad(capacidad);
        actualizarListaCursosRegistrados(); //Si no se actualiza la lista, no se ven reflejados los cambios
    }

    private void mostrarRequisitosAsignarEstudianteCurso(String codigo) {
        //Crear elementos adicionales para el VBox
        Label label = new Label("Identificación del estudiante:");
        TextField identificacionEstudianteTextField = new TextField();

        //Agregar los elementos creados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setMaxSize(300, 300);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(label, 0, 0);
        gridPane.add(identificacionEstudianteTextField, 1, 0);

        //Obtener la seleccion de la tabla de estudiantes registrados
        if (listenerEstudiante != null) {
            estudiantesRegistradosTableView.getSelectionModel().selectedItemProperty().removeListener(listenerEstudiante);
        }
        listenerEstudiante = (obs, oldValue, newValue) -> {
            if (newValue != null) {
                identificacionEstudianteTextField.setText(newValue.getIdentificacion());
            }
        };
        //Asignar el listener al tableview
        estudiantesRegistradosTableView.getSelectionModel().selectedItemProperty().addListener(listenerEstudiante);


        //Crear boton adicional
        Button boton = new Button("Confirmar");
        //Agregar funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo no este vacio
            if (identificacionEstudianteTextField.getText() == null) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie la identificación del estudiante");
                return;
            }
            //Verificar que exista el curso
            if (!consultarExistenciaCurso(codigo)) {
                mostrarAlerta("Curso no encontrado", "No se ha encontrado un curso registrado con el código suministrado, por favor, verifique el código");
                return;
            }
            //Verificar que exista un estudiante con esa identificacion
            if (!consultarExistenciaEstudiante(identificacionEstudianteTextField.getText())) {
                mostrarAlerta("Estudiante inexistente", "No se encontró un estudiante registrado con la identificación suministrada");
                return;
            }
            //Verificar que el estudiante tenga el mismo instrumento que el curso
            Estudiante estudiante = buscarEstudiante(identificacionEstudianteTextField.getText());
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
                if (est.getIdentificacion().equals(identificacionEstudianteTextField.getText())) {
                    mostrarAlerta("Estudinate ya registrado", "El estudiante con la identificación suministrada ya está registrado en el curso");
                    return;
                }
            }
            asignarEstudianteCurso(codigo, identificacionEstudianteTextField.getText());
            mostrarMensaje("Curso actualizado", "Curso actualizado exitosamente");
        });

        VBox adicionalVBox = new VBox();
        adicionalVBox.getChildren().addAll(gridPane, boton);

        //Agregar los nuevos elementos al VBox
        requisitosGestionVBox.getChildren().removeLast();
        requisitosGestionVBox.getChildren().addAll(adicionalVBox);
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
            //Verificar que exista el curso
            if (!consultarExistenciaCurso(codigo)) {
                mostrarAlerta("Curso no encontrado", "No se ha encontrado un curso registrado con el código suministrado, por favor, verifique el código");
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
        requisitosGestionVBox.getChildren().removeLast();
        requisitosGestionVBox.getChildren().addAll(adicionalVBox);
    }

    private void quitarEstudianteCurso(String codigo, String identificacion) {
        Curso curso = buscarCurso(codigo);
        Estudiante estudiante = buscarEstudiante(identificacion);
        curso.getEstudiantesRegistrados().remove(estudiante);
        actualizarListaCursosRegistrados(); //Si no se actualiza la lista, no se ven reflejados los cambios
    }


    private void mostrarRequisitosGestionarNotasCurso() {
        //Crear elementos del VBox
        Label cursoLabel = new Label("Gestionar notas curso");
        Label codigoCursoLabel = new Label("Código:");
        TextField codigoCursoTextField = new TextField();

        //Asignar los elementos crados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(codigoCursoLabel, 0, 0);
        gridPane.add(codigoCursoTextField, 1, 0);

        Button boton = new Button("Confirmar");

        //Vincular la funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo del String este diligenciado
            if (codigoCursoTextField.getText().isEmpty()) {
                mostrarAlerta("Campo vacío", "Por favor, diligencie el codigo del curso");
                return;
            }

            //Verificar que exista el curso
            if (!consultarExistenciaCurso(codigoCursoTextField.getText())) {
                mostrarAlerta("Curso no encontrado", "No se ha encontrado un curso registrado con el código suministrado, por favor, verifique el código");
                return;
            }

            Label estudianteLabel = new Label("Seleccione un estudiante:");
            ChoiceBox<Estudiante> estudiantesChoiceBox = new ChoiceBox<>();
            //Obtener estudiantes registrados al curso
            LinkedList<Estudiante> estudiantes = buscarCurso(codigoCursoTextField.getText()).getEstudiantesRegistrados();
            for (Estudiante estudiante : estudiantes) {
                estudiantesChoiceBox.getItems().add(estudiante);
            }
            //Convertir lo que muestra el ChoiceBox
            estudiantesChoiceBox.setConverter(new StringConverter<Estudiante>() {
                @Override
                public String toString(Estudiante estudiante) {
                    if (estudiante == null) {
                        return "Seleccione un estudiante";
                    }
                    return estudiante.getNombre() + " " + estudiante.getApellido();
                }
                @Override
                public Estudiante fromString(String string) {return null;}
            });
            Label notaLabel = new Label("Nota:");
            TextField notaTextField = new TextField();
            Label comentarioLabel = new Label("Comentario:");
            TextField comentarioTextField = new TextField();

            GridPane gridPane1 = new GridPane();
            gridPane1.setHgap(10);
            gridPane1.setVgap(10);
            gridPane1.add(estudianteLabel, 0, 0);
            gridPane1.add(estudiantesChoiceBox, 1, 0);
            gridPane1.add(notaLabel, 0, 1);
            gridPane1.add(notaTextField, 1, 1);
            gridPane1.add(comentarioLabel, 0, 2);
            gridPane1.add(comentarioTextField, 1, 2);

            Button boton1 =  new Button("Gestionar");

            boton1.setOnAction(evento -> {
                //Verificar que esten diligenciados los datos
                if (estudiantesChoiceBox.getSelectionModel().getSelectedItem() == null || estudiantesChoiceBox.getSelectionModel().getSelectedItem().toString().equals("Seleccione un estudiante") || comentarioTextField.getText().isEmpty()) {
                    mostrarAlerta("Campo vacío", "Por favor, diligencie todos los datos");
                    return;
                }
                //Verificar que la nota sea valida
                if (Double.parseDouble(notaTextField.getText()) < 0 || Double.parseDouble(notaTextField.getText()) > 5.0) {
                    mostrarAlerta("Nota no válida", "La nota no es válida, por favor, verifique la nota");
                    return;
                }
                gestionarNotasCurso(Double.parseDouble(notaTextField.getText()) ,comentarioTextField.getText(), estudiantesChoiceBox.getValue().getIdentificacion());
                mostrarMensaje("Nota asignada", "Nota asignada al estudiante exitosamente");
            });

            //Obtener la seleccion de la tabla de cursos registrados
            cursosRegistradosTableView.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
                if (newSelection != null) {
                    //Poner automaticamente el codigo del curso seleccionado
                    codigoCursoTextField.setText(newSelection.getCodigo());
                }
            });

            requisitosGestionVBox.getChildren().addAll(gridPane1, boton1);
        });

        //Mostrar los requisitos en el VBox
        requisitosGestionVBox.getChildren().clear();
        requisitosGestionVBox.getChildren().addAll(cursoLabel, gridPane, boton);
    }

    private void gestionarNotasCurso(double nota, String comentario, String identificacion) {
        Estudiante estudiante = buscarEstudiante(identificacion);
        estudiante.getNotasRegistradas().add(new Nota(nota, comentario));
    }




    private Profesor buscarProfesor(String identificacion) {
        return academiaController.buscarProfesor(identificacion);
    }

    private boolean consultarExistenciaProfesor(String identificacion) {
        return academiaController.consultarExistenciaProfesor(identificacion);
    }

    private boolean consultarExistenciaCurso(String codigo) {
        return academiaController.consultarExistenciaCurso(codigo);
    }

    private boolean consultarExistenciaEstudiante(String identificacion) {
        return academiaController.consultarExistenciaEstudiante(identificacion);
    }

    private Estudiante buscarEstudiante(String identificacion) {
        return academiaController.buscarEstudiante(identificacion);
    }

    private void cargarListaCursosAsignados() {
        cursosRegistradosObservableList.clear();
        cursosRegistradosObservableList.addAll(profesorActual.getListaCursosAsignados());
        cursosRegistradosTableView.setItems(cursosRegistradosObservableList);
    }

    private void cargarListaClasesIndividuales() {
        clasesIndividualesObservableList.clear();
        clasesIndividualesObservableList.addAll(profesorActual.getListaClasesIndividuales());
        clasesIndividualesTableView.setItems(clasesIndividualesObservableList);
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

    private void cargarListaEstudiantesRegistrados() {
        estudiantesAsignadosObservableList.clear();
        estudiantesAsignadosObservableList.addAll(academiaController.getEstudiantesRegsitrados());
        estudiantesRegistradosTableView.setItems(estudiantesAsignadosObservableList);
    }

    private void actualizarListaCursosRegistrados() {
        cursosRegistradosTableView.refresh();
    }

    private void volverAlPrimary() throws IOException {
        app.abrirPrimary();
    }
}
