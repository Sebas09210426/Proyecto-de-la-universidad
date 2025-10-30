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

public class VentanaProfesoresViewController {

    App app;
    AcademiaController academiaController;

    //Adquirir los elementos del fxml

    @FXML
    private ChoiceBox<String> gestionChoiceBox;

    @FXML
    private VBox VBoxRequisitosDeGestion;

    @FXML
    private Button volverAlMenuAnteriorButton;

    @FXML
    private Button cerrarSesionButton;

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


    public void setApp(App app) {
        this.app = app;
    }

    @FXML
    private void initialize() {
        academiaController = new AcademiaController(App.academia);

        //Preparar columnas de los estudiantes registrados
        nombreProfesoresRegistradosTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getNombre()));
        apellidoProfesoresRegistradosTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getApellido()));
        identificacionProfesoresRegistradosTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleStringProperty(cellData.getValue().getIdentificacion()));
        estudiantesAsignadosTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty((cellData.getValue().getListaEstudiantesAsignados().size())).asObject());
        cursosAsignadosProfesoresRegistradosTableColumn.setCellValueFactory(cellData -> new javafx.beans.property.SimpleIntegerProperty(cellData.getValue().getListaCursosAsignados().size()).asObject());

        //Enlazar la lista de estudiantes al TableView
        profesoresRegistradosTableView.setItems(profesoresRegistradosObservableList);
        //Refrescar la tabla por si se cambia de pestana
        cargarListaProfesoresRegistrados();

        //Añadir opciones al ChoiceBox
        gestionChoiceBox.getItems().addAll("Crear profesor", "Eliminar profesor", "Modificar profesor");

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

    private boolean consultarExistenciaProfesor(String identificacion) {
        return academiaController.consultarExistenciaProfesor(identificacion);
    }

    private Profesor buscarProfesor(String identificacion) {
        return academiaController.buscarProfesor(identificacion);
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

        //Crear el boton para crear estudiante
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
        VBoxRequisitosDeGestion.getChildren().clear();
        VBoxRequisitosDeGestion.getChildren().addAll(label, gridPane, boton);
    }

    private void crearProfesor(String nombre, String apellido, String identificacion) {
        Profesor nuevoProfesor = new Profesor(nombre, apellido, identificacion);

        if (academiaController.crearProfesor(nuevoProfesor)) {
            mostrarProfesorRegistrado(nuevoProfesor);
            crearUsuarioProfesor(identificacion, identificacion, identificacion, Rol.PROFESOR); //Por defecto, el usuario se crea con la misma identificacion, despues se puede modificar
            mostrarMensaje("Estudiante creado", "Estudiante creado exitosamente");
        } else {
            mostrarAlerta("Error al crear estudiante", "El estudiante ya existe");
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
                mostrarAlerta("Campo vacío", "Por favor, diligencie la identificacion del estudiante");
                return;
            }
            eliminarProfesor(identificacionProfesorTextField.getText());
        });

        //Mostrar los requisitos en el VBox
        VBoxRequisitosDeGestion.getChildren().clear();
        VBoxRequisitosDeGestion.getChildren().addAll(label, gridPane, boton);
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
                        mostrarAlerta("Estudiante no encontrado", "La identificacion del profesor no esta registrada");
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
                        mostrarAlerta("Estudiante no encontrado", "La identificacion del profesor no esta registrada");
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
                        mostrarAlerta("Estudiante no encontrado", "La identificacion del profesor no esta registrada");
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
        VBoxRequisitosDeGestion.getChildren().clear();
        VBoxRequisitosDeGestion.getChildren().addAll(label, gridPane, boton, gestionElegidaVBox);
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
        VBoxRequisitosDeGestion.getChildren().removeLast();
        VBoxRequisitosDeGestion.getChildren().addAll(adicionalVBox);
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
        VBoxRequisitosDeGestion.getChildren().removeLast();
        VBoxRequisitosDeGestion.getChildren().addAll(adicionalVBox);
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
        VBoxRequisitosDeGestion.getChildren().removeLast();
        VBoxRequisitosDeGestion.getChildren().addAll(adicionalVBox);
    }

    private void modificarIdentificacionProfesor(String identificacion, String nuevaIdentificacion) {
        Profesor profesor = buscarProfesor(identificacion);
        profesor.setIdentificacion(nuevaIdentificacion);
        actualizarListaProfesoresRegistrados(); //Si no se actualiza la lista, no se ven reflejados los cambios
    }

    /* De momento no usaremos esta parte
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
        VBoxRequisitosDeGestion.getChildren().removeLast();
        VBoxRequisitosDeGestion.getChildren().addAll(adicionalVBox);
    }

    private void modificarInstrumentoEstudiante(String identificacion, Instrumento instrumento) {
        Estudiante estudiante = buscarProfesor(identificacion);
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
        VBoxRequisitosDeGestion.getChildren().removeLast();
        VBoxRequisitosDeGestion.getChildren().addAll(adicionalVBox);
    }

    private void modificarNivelDeEstudioEstudiante(String identificacion, NivelDeEstudio nivelDeEstudio) {
        Estudiante estudiante = buscarProfesor(identificacion);
        estudiante.setNivelDeEstudio(nivelDeEstudio);
        actualizarListaEstudiantesRegistrados(); //Si no se actualiza la lista, no se ven reflejados los cambios
    }

     */

    private void actualizarListaProfesoresRegistrados() {
        profesoresRegistradosTableView.refresh();
    }

    private void volverAlMenuAnterior() throws IOException {
        app.abrirVentanaPrincipal();
    }

    private void cargarListaProfesoresRegistrados() {
        profesoresRegistradosObservableList.clear();
        profesoresRegistradosObservableList.addAll(academiaController.getProfesoresRegistrados());
        profesoresRegistradosTableView.setItems(profesoresRegistradosObservableList);
    }

    private void volverAlPrimary() throws IOException {
        app.abrirPrimary();
    }
}
