package app;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import viewController.PrimaryViewController;

import java.io.IOException;

public class App extends Application {

    Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/assets/primary.fxml"));
        Scene scene = new Scene(loader.load());
        PrimaryViewController primaryViewController = loader.getController();
        primaryViewController.setApp(this);
        this.stage = stage;
        this.stage.setScene(scene);
        this.stage.setTitle("Musical");
        this.stage.show();
    }

    public static void main(String[] args) {launch(args);}
}
