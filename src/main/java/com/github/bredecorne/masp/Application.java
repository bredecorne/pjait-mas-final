package com.github.bredecorne.masp;

import com.github.bredecorne.masp.model.*;
import com.github.bredecorne.masp.model.persons.LegalPerson;
import com.github.bredecorne.masp.utils.Repository;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

public class Application extends javafx.application.Application {
    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}