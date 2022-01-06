package com.company.wiew;
import com.company.wiew.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.Objects;

public class Main extends Application {

    public static void main(String[] args) {
/*
        Kostka kostka = new Kostka(6);

        new HraciPlocha(40, 4, 4, kostka);*/

        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hra.fxml")));
        stage.setTitle("Hello World");
        stage.setScene(new Scene(root, 600, 600));
        stage.show();
    }
}
