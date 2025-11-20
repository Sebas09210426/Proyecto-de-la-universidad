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
    requires javafx.graphics;
    requires javafx.base;
    requires java.rmi;

    opens app to javafx.fxml;
    exports app;

    opens viewController to javafx.fxml;
    exports viewController;

    opens model to javafx.fxml;
    exports model;

    opens controller to javafx.fxml;
    exports controller;

    opens test to javafx.fxml;
    exports test;
}