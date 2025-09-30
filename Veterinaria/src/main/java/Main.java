import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {

        //Crear veterinaria
        Veterinaria nuevaVeterinaria = new Veterinaria("Veterinaria Martinez", "12345678", "Armenia");


        //Agregar mascota
        Mascota nuevaMascota1 = new Mascota("Calo", "Perro", "Golden", "5", "001", "Sebas", "3459034891");
        boolean mascota1 = nuevaVeterinaria.agregarMascota(nuevaMascota1);

        if (mascota1){
            JOptionPane.showMessageDialog(null, "Mascota agregada correctamente");
        } else  {
            JOptionPane.showMessageDialog(null, "No se pudo agregar la mascota");
        }

        Mascota nuevaMascota2 = new Mascota("Luna", "Gato", "Quien sabe", "4", "002", "Carlos", "3594561649");
        boolean mascota2 = nuevaVeterinaria.agregarMascota(nuevaMascota2);

        if (mascota2){
            JOptionPane.showMessageDialog(null, "Mascota agregada correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo agregar la mascota");
        }


        //Buscar Mascota
        String idBuscar = JOptionPane.showInputDialog("Ingrese la identificacion de la mascota");
        Mascota busquedaMascota = nuevaVeterinaria.buscarMascotaPorId(idBuscar);
        if (busquedaMascota != null) {
            JOptionPane.showMessageDialog(null, "Mascota encontrada: \n"  + busquedaMascota.toString());
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo encontrar la mascota");
        }


        //Eliminar Mascota
        String idEliminar = JOptionPane.showInputDialog("Ingrese la identificacion de la mascota");
        boolean eliminacionMascota = nuevaVeterinaria.eliminarMascotaPorId(idEliminar);

        if (eliminacionMascota){
            JOptionPane.showMessageDialog(null, "Mascota eliminada correctamente");
        } else {
            JOptionPane.showMessageDialog(null, "No se pudo eliminar la mascota");
        }

    }
}
