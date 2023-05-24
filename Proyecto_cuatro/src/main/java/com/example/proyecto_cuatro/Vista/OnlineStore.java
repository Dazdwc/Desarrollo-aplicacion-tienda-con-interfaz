package com.example.proyecto_cuatro.Vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OnlineStore extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        OnlineStoreController v = new OnlineStoreController();
        FXMLLoader fxmlLoader = new FXMLLoader(v.getClass().getResource("welcome.fxml"));
        System.out.println("FXML location: " + fxmlLoader.getLocation());
        Parent root = fxmlLoader.load();
        primaryStage.setTitle("Online Store");
        primaryStage.setScene(new Scene(root, 800, 800));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
