import javax.swing.*;

public class Main {

    public static void main(String[] args) {

        //Crear veterinaria
        Veterinaria nuevaVeterinaria = new Veterinaria("Vida Animal", "8455168", "Armenia");

        //Iniciar menu de opciones
        int opcion;
        do {
            opcion = Integer.parseInt(JOptionPane.showInputDialog(null, "MENU VETERINARIA\n\n" +
                    "1. Agregar mascota\n" +
                    "2. Buscar mascota\n" +
                    "3. Eliminar mascota\n" +
                    "4. Calcular precio de la consulta\n" +
                    "5. Agregar Propietario\n" +
                    "6. Buscar propietario\n" +
                    "7. Eliminar propietario\n" +
                    "8. Salir\n\n" +
                    "Seleccione una opción:"
            ));

            switch (opcion) {

                //Registrar mascota
                case 1:
                    //Pedir los datos de la mascota
                    String nombre =  JOptionPane.showInputDialog(null, "Introduce el nombre: ");
                    String especie = JOptionPane.showInputDialog(null, "Introduce la especie: ");
                    String raza = JOptionPane.showInputDialog(null, "Introduce la raza: ");
                    int edad = Integer.parseInt(JOptionPane.showInputDialog(null, "Introduce la edad: "));
                    double peso = Double.parseDouble(JOptionPane.showInputDialog(null, "Introduce la peso: "));
                    String nombrePropietario = JOptionPane.showInputDialog(null, "Introduce el nombre propietario: ");
                    String numeroPropietario = JOptionPane.showInputDialog(null, "Introduce el numero propietario: ");
                    String idRegistro = JOptionPane.showInputDialog(null, "Introduce la id: ");
                    //Crear mascota
                    Mascota nuevaMascota = new Mascota(nombre, especie, raza, edad, peso, idRegistro, nombrePropietario, numeroPropietario);
                    boolean mascotaCreada = nuevaVeterinaria.agregarMascota(nuevaMascota);
                    if (mascotaCreada) {
                        JOptionPane.showMessageDialog(null, "Mascota agregada.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Mascota no registrada.");
                    }
                    break;

                //Buscar mascota por Id
                case 2:
                    //Pedir el id de la mascota
                    String idBuscar = JOptionPane.showInputDialog(null, "Introduce el id:");
                    //Realizar busqueda
                    Mascota mascotaBuscada = nuevaVeterinaria.buscarMascotaPorId(idBuscar);
                    if (mascotaBuscada != null) {
                        JOptionPane.showMessageDialog(null, mascotaBuscada);
                    } else {
                        JOptionPane.showMessageDialog(null, "Mascota no encontrada.");
                    }
                    break;

                //Eliminar Mascota
                case 3:
                    //Pedir el id de la mascota
                    String idBuscar2 = JOptionPane.showInputDialog(null, "Introduce el id:");
                    //Realizar eliminacion
                    Boolean mascotaBorrada = nuevaVeterinaria.eliminarMascota(idBuscar2);
                    if (mascotaBorrada) {
                        JOptionPane.showMessageDialog(null, "Mascota eliminada.");
                    } else  {
                        JOptionPane.showMessageDialog(null, "Mascota no encontrada.");
                    }
                    break;

                //Calcular precio consulta
                case 4:
                    //Pedir el id de la mascota
                    String idBuscar3 = JOptionPane.showInputDialog(null, "Introduce el id:");
                    //Buscar la mascota
                    double precioFinal = nuevaVeterinaria.evaluarCostoConsulta(10000, idBuscar3);
                    if (precioFinal != 0) {
                        JOptionPane.showMessageDialog(null, precioFinal);
                    } else  {
                        JOptionPane.showMessageDialog(null, "Mascota no encontrada.");
                    }
                    break;

                //Agregar Popietario
                case 5:
                    //Pedir los datos del propietario
                    String mascotaDueno = JOptionPane.showInputDialog(null, "Introduce el mascota:");
                    String nombres = JOptionPane.showInputDialog(null, "Introduce los nombres: ");
                    String apellidos = JOptionPane.showInputDialog(null, "Introduce los apellidos: ");
                    String numero =  JOptionPane.showInputDialog(null, "Introduce el numero de celular: ");
                    String direccion = JOptionPane.showInputDialog(null, "Introduce la Direccion: ");
                    int fidelidad = Integer.parseInt(JOptionPane.showInputDialog(null, "Introduce la Fidelidad: "));
                    //Realizar Registro
                    boolean propietarioAgregado = nuevaVeterinaria.agregarPropietario(mascotaDueno, nombres, apellidos, numero, direccion, fidelidad);
                    if (propietarioAgregado) {
                        JOptionPane.showMessageDialog(null, "Propietario agregado.");
                    } else {
                        JOptionPane.showMessageDialog(null, "Propietario no agregado.");
                    }
                    break;

                //Buscar Propietario
                case 6:
                    String numeroBuscar = JOptionPane.showInputDialog(null, "Introduce el numero de celular: ");
                    Propietario propietario = nuevaVeterinaria.buscarPropietarioPorNumero(numeroBuscar);
                    if (propietario != null) {
                        JOptionPane.showMessageDialog(null, "Propietario encontrado. \n" + propietario);
                    } else {

                        JOptionPane.showMessageDialog(null, "Propietario no encontrado.");
                    }
                    break;

                //Eliminar Propietario
                case 7:
                    String numeroBuscar2 = JOptionPane.showInputDialog(null, "Introduce el numero de celular: ");
                    boolean propietarioEliminado = nuevaVeterinaria.eliminarPropietario(numeroBuscar2);
                    if (propietarioEliminado) {
                        JOptionPane.showMessageDialog(null, "Propietario eliminado.");
                    }  else {
                        JOptionPane.showMessageDialog(null, "Propietario no encontrado.");
                    }
                    break;

                //Salir
                case 8:
                    JOptionPane.showMessageDialog(null, "Saliendo...");
                    break;

                //Evitar otro numero
                default:
                    JOptionPane.showInputDialog(null, "Opcion invalida." + opcion);

            }
        } while (opcion != 8);
    }
}
