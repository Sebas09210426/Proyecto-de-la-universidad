package app;

import javafx.scene.layout.AnchorPane;
import model.Veterinaria;
import viewController.primaryViewController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.scene.Scene;
import viewController.veterinariaViewController;

import java.io.IOException;


public class App extends Application {
    public static Veterinaria veterinaria = new Veterinaria("Vida Animal", "12345678", "Armenia");
    Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/primary.fxml"));
        Scene scene = new Scene(loader.load());
        primaryViewController primaryViewController = loader.getController();
        primaryViewController.setApp(this);
        this.primaryStage = primaryStage;
        this.primaryStage.setScene(scene);
        this.primaryStage.setTitle("Veterinaria Vida Animal");
        this.primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }

    public void abrirCrudVeterinaria() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/veterinaria.fxml"));
            AnchorPane root = (AnchorPane) loader.load();
            veterinariaViewController veterinariaViewController = loader.getController();
            veterinariaViewController.setApp(this);
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gestión Veterinaria Vida Animal");
            primaryStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
