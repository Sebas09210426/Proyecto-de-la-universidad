package model;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class DataManager {

    public static void guardarDatos(Academia academia) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("academia.dat"))) {
            oos.writeObject(academia);
            System.out.println("Datos guardados correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Academia cargarDatos() {
        try (ObjectInputStream ois =
                     new ObjectInputStream(new FileInputStream("academia.dat"))) {

            return (Academia) ois.readObject();

        } catch (Exception e) {
            System.out.println("No se encontraron datos guardados.");
            return null;
        }
    }
}
