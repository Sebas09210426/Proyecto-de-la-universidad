package viewController;

import app.App;
import controller.AcademiaController;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.util.StringConverter;
import model.*;

import java.io.IOException;
import java.util.LinkedList;

import static viewController.PrimaryViewController.*;

public class VentanaPersonalEstudianteViewController implements Actualizable {

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
        gestionChoiceBox.getItems().addAll("Consultar", "Actualizar", "Solicitar clase individual");
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
                case "Solicitar clase individual":
                    mostrarRequisitosSolicitarClaseIndividual();
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
        consultaChoiceBox.getItems().addAll("Consultar horario", "Consultar notas", "Consultar asistencias");
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
                    consultarHorario();
                    break;
                case "Consultar notas":
                    consultarNotas();
                    break;
                case "Consultar asistencias":
                    consultarAsistencias();
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

    private void consultarHorario() {
        String horario = "";
        if (estudianteActual.getCursosAsignados().isEmpty()) {
            horario = "Sin clases";
        } else {
            for (Curso c :estudianteActual.getCursosAsignados()) {
                horario += "{"
                        + "Fecha: " + (c.getFecha() != null ? c.getFecha().toString() : "Sin asignar") + "\n"
                        + "Hora: " + (c.getHora() != null ? c.getHora().toString() : "Sin asignar") + "\n"
                        + "Aula: " + (c.getAulaAsignada() != null ? c.getAulaAsignada().getId() : "Sin asignar") + "\n"
                        + "Profesor: " + (c.getProfesor() != null ? c.getProfesor().getNombre() + " " + c.getProfesor().getApellido() : "Sin asignar") + "\n"
                        + "}";
            }
        }
        mostrarMensaje("Horario asignado", horario);
    }

    private void consultarNotas() {
        String notas = "";
        if (estudianteActual.getNotasRegistradas().isEmpty()) {
            notas = "Sin notas registradas";
        } else {
            for (Nota nota :  estudianteActual.getNotasRegistradas()) {
                notas += "{" + "\n" +
                        nota + nota.nota() + " / Comentario: " +  nota.comentario() + "\n" +
                        "}";
            }
        }
        mostrarMensaje("Notas registradas", notas);
    }

    private void consultarAsistencias() {
        String mensaje = "";
        mensaje += "Asistencias: " + estudianteActual.getAsistencias() + " / Inasistencias: " + estudianteActual.getInasitencias();
        mostrarMensaje("Asistencias", mensaje);
    }

    private void mostrarRequisitosSolicitarClaseIndividual() {
        //Crear elementos del VBox
        Label label = new Label("Solicitar clase individual");
        Label profesorLabel = new Label("Seleccione un profesor:");
        ChoiceBox<Profesor> profesoresChoiceBox = new ChoiceBox();
        //Anadir los profesores al choiceBox
        LinkedList<Profesor> profesores = academiaController.getProfesoresRegistrados();
        for (Profesor profesor : profesores) {
            profesoresChoiceBox.getItems().add(profesor);
        }
        //Convertir lo que muestra el choicebox
        profesoresChoiceBox.setConverter(new StringConverter<Profesor>() {
            @Override
            public String toString(Profesor object) {
                if (object == null) {
                    return "Seleccione un profesor";
                }
                return object.getNombre() + " " + object.getApellido();
            }
            //Ni idea que hace esto, pero lo exije poner
            @Override
            public Profesor fromString(String string) {
                return null;
            }
        });

        //Asignar los elementos crados a un GridPane
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(profesorLabel, 0, 0);
        gridPane.add(profesoresChoiceBox, 1, 0);

        Button boton = new Button("Confirmar");

        //Vincular la funcion al boton creado
        boton.setOnAction(e -> {
            //Verificar que el campo del String este diligenciado
            if (profesoresChoiceBox.getValue() == null) {
                mostrarAlerta("Campo vacío", "Por favor, seleccione un profesor");
                return;
            }
            solicitarClaseIndividual(profesoresChoiceBox.getValue().getIdentificacion());
        });

        requisitosGestionVBox.getChildren().add(new VBox());
        requisitosGestionVBox.getChildren().removeLast();
        //Mostrar los requisitos en el VBox
        requisitosGestionVBox.getChildren().clear();
        requisitosGestionVBox.getChildren().addAll(label, gridPane, boton);
    }

    private void solicitarClaseIndividual(String identificacion) {
        LinkedList<Estudiante> estudiante = new LinkedList<>();
        estudiante.add(estudianteActual);
        Profesor profesor = academiaController.buscarProfesor(identificacion);
        Curso nuevoCurso = new Curso(1, estudiante, null, null, estudianteActual.getInstrumento(), estudianteActual.getNivelDeEstudio(), profesor, null, null, "Pendiente");
        profesor.getListaClasesIndividuales().add(nuevoCurso);
        estudianteActual.getCursosAsignados().add(nuevoCurso);
        mostrarMensaje("Clase individual solicitada", "La clase individual ha sido solicitada, por favor, espere la respuesta del docente");
    }



    private Estudiante buscarEstudiante(String identificacion) {
        return academiaController.buscarEstudiante(identificacion);
    }

    private void volverAlPrimary() throws IOException {
        app.abrirPrimary();
    }

}
