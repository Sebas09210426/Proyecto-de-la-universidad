module java {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;

    opens controller to javafx.fxml;
    exports controller;

    opens model to javafx.fxml;
    exports model;

    opens viewController to javafx.fxml;
    exports viewController;

    opens app to javafx.fxml;
    exports app;

}