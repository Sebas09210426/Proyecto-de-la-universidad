package model;


import java.util.LinkedList;

public class Academia {
    //Atributos
    private String nombre;
    private String nit;
    private LinkedList<Estudiante> listaEstudiantes;
    private LinkedList<Profesor> listaProfesores;
    private LinkedList<Curso> listaCursos;

    //Constructor

    public Academia(String nombre, String nit) {
        this.nombre = nombre;
        this.nit = nit;
        this.listaEstudiantes = new LinkedList<>();
        this.listaProfesores = new LinkedList<>();
        this.listaCursos = new LinkedList<>();
    }

    //Getters y Setters

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public LinkedList<Estudiante> getListaEstudiantes() {
        return listaEstudiantes;
    }

    public void setListaEstudiantes(LinkedList<Estudiante> listaEstudiantes) {
        this.listaEstudiantes = listaEstudiantes;
    }

    public LinkedList<Profesor> getListaProfesores() {
        return listaProfesores;
    }

    public void setListaProfesores(LinkedList<Profesor> listaProfesores) {
        this.listaProfesores = listaProfesores;
    }

    public LinkedList getListaCursos() {
        return listaCursos;
    }

    public void setListaCursos(LinkedList listaCursos) {
        this.listaCursos = listaCursos;
    }

    //CRUD
    public boolean agregarEstudiante(Estudiante estudiante){
        if (estudiante == null){
            return false;
        }
        for (Estudiante e : listaEstudiantes) {
            if (e.getIdentificacion().equals(estudiante.getIdentificacion())){
                return false;
            }
        }
        listaEstudiantes.add(estudiante);
        return true;
    }

    public boolean agregarProfesor(Profesor profesor){
        if (profesor == null){
            return false;
        }
        for (Profesor p : listaProfesores) {
            if (p.getIdentificacion().equals(profesor.getIdentificacion())){
                return false;
            }
        }
        listaProfesores.add(profesor);
        return true;
    }

    public Estudiante buscarEstudiante(String identificacion){
        for (Estudiante e : listaEstudiantes) {
            if (e.getIdentificacion().equals(identificacion)){
                return e;
            }
        }
        return null;
    }

    public Profesor buscarProfesor(String identificacion){
        for (Profesor p : listaProfesores) {
            if (p.getIdentificacion().equals(identificacion)){
                return p;
            }
        }
        return null;
    }

    public boolean modificarEstudiante(String identificacion, String modificacion, int opcion){
        Estudiante e = buscarEstudiante(identificacion);

        if (e == null) {
            return false;
        }

        switch (opcion){
            case 1:
                e.setNombre(modificacion);
                return  true;
            case 2:
                e.setApellido(modificacion);
                return  true;
            case 3:
                e.setIdentificacion(modificacion);
                return  true;
            case 4:
                switch (modificacion){
                    case "guitarra":
                        e.setInstrumento(Instrumento.GUITARRA);
                        return  true;
                    case "canto":
                        e.setInstrumento(Instrumento.CANTO);
                        return  true;
                    case "piano":
                        e.setInstrumento(Instrumento.PIANO);
                        return  true;
                    case "flauta":
                        e.setInstrumento(Instrumento.FLAUTA);
                        return  true;
                    case "violin":
                        e.setInstrumento(Instrumento.VIOLIN);
                        return  true;
                    default:
                        e.setInstrumento(Instrumento.OTRO);
                        return  true;
                }
            case 5:
                switch (modificacion){
                    case "basico":
                        e.setNivelDeEstudio(NivelDeEstudio.BASICO);
                        return  true;
                    case "medio":
                        e.setNivelDeEstudio(NivelDeEstudio.MEDIO);
                        return  true;
                    case "avanzado":
                        e.setNivelDeEstudio(NivelDeEstudio.AVANZADO);
                        return  true;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }

    public boolean modificarProfesor(String identificacion, String modificacion, int opcion){
        Profesor p = buscarProfesor(identificacion);
        if (p == null) {
            return false;
        }

        switch (opcion){
            case 1:
                p.setNombre(modificacion);
                return  true;
            case 2:
                p.setApellido(modificacion);
                return  true;
            case 3:
                p.setIdentificacion(modificacion);
                return  true;
            default:
                return false;
        }
    }

    public boolean eliminarEstudiante(String identificacion){
        for (Estudiante e : listaEstudiantes) {
            if (e.getIdentificacion().equals(identificacion)){
                listaEstudiantes.remove(e);
                return true;
            }
        }
        return false;
    }

    public boolean eliminarProfesor(String identificacion){
        for (Profesor p : listaProfesores) {
            if (p.getIdentificacion().equals(identificacion)){
                listaProfesores.remove(p);
                return true;
            }
        }
        return false;
    }

}
