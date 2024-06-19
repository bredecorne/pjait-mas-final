module com.github.bredecorne.masp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires jdk.jshell;
    requires java.desktop;

    opens com.github.bredecorne.masp to javafx.fxml;
    exports com.github.bredecorne.masp;
    exports com.github.bredecorne.masp.controller;
    opens com.github.bredecorne.masp.controller to javafx.fxml;
}