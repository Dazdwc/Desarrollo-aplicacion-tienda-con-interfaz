package com.example.proyecto_cuatro.Vista;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OnlineStoreController implements Initializable {

    @FXML
    private Button closeButton;

    @FXML
    private void closeWindow(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    @FXML
    private void mostrarVistaArticulo(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VistaArticulo.fxml"));
            Parent vista = fxmlLoader.load();
            Scene scene = new Scene(vista);
            Stage nuevoStage = new Stage();
            nuevoStage.setTitle("Vista de Artículo");
            nuevoStage.setScene(scene);
            nuevoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void mostrarCrearArticulo(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CrearArticulo.fxml"));
            Parent vista = fxmlLoader.load();
            Scene scene = new Scene(vista);
            Stage nuevoStage = new Stage();
            nuevoStage.setTitle("Crear Artículo");
            nuevoStage.setScene(scene);
            nuevoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void mostrarVistaCliente(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VistaCliente.fxml"));
            Parent vista = fxmlLoader.load();
            Scene scene = new Scene(vista);
            Stage nuevoStage = new Stage();
            nuevoStage.setTitle("Ver Cliente");
            nuevoStage.setScene(scene);
            nuevoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void mostrarCrearCliente(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CrearCliente.fxml"));
            Parent vista = fxmlLoader.load();
            Scene scene = new Scene(vista);
            Stage nuevoStage = new Stage();
            nuevoStage.setTitle("Crear CLiente");
            nuevoStage.setScene(scene);
            nuevoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void mostrarVistaPedido(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("VistaPedido.fxml"));
            Parent vista = fxmlLoader.load();
            Scene scene = new Scene(vista);
            Stage nuevoStage = new Stage();
            nuevoStage.setTitle("Vista Pedidos");
            nuevoStage.setScene(scene);
            nuevoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void mostrarCrearPedido(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();

        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("CrearPedido.fxml"));
            Parent vista = fxmlLoader.load();
            Scene scene = new Scene(vista);
            Stage nuevoStage = new Stage();
            nuevoStage.setTitle("Crear Pedido");
            nuevoStage.setScene(scene);
            nuevoStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

