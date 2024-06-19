module com.github.bredecorne.masp {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.kordamp.bootstrapfx.core;
    requires jdk.jshell;
    requires java.desktop;

    opens com.github.bredecorne.masp to javafx.fxml;
    opens com.github.bredecorne.masp.model;
    opens com.github.bredecorne.masp.controller;
    exports com.github.bredecorne.masp;
    
    exports com.github.bredecorne.masp.controller;
    
}