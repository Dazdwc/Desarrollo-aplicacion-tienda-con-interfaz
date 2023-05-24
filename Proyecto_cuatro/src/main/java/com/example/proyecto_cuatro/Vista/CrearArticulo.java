package com.example.proyecto_cuatro.Vista;

import com.example.proyecto_cuatro.Modelo.*;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CrearArticulo {

    @FXML
    private Button closeButton;
    @FXML
    private TextField codigoArticulo;
    @FXML
    private TextField descripcion;
    @FXML
    private TextField pvp;
    @FXML
    private TextField gastosEnvio;
    @FXML
    private TextField preparacionMin;
    @FXML
    private Button guardarButton;

    private class DuplicateArticleCodeException extends Exception {
        public DuplicateArticleCodeException(String message) {
            super(message);
        }
    }

    @FXML
    protected void guardarArticulo(ActionEvent event) {
        FactoryDAO factory = new FactoryDAOImp();
        ArticuloDAO articuloDAO = factory.createArticuloDAO();

        String codigo = codigoArticulo.getText();
        if (articuloDAO.existeArticulo(codigo)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al crear el artículo");
            alert.setHeaderText(null);
            alert.setContentText("El código de artículo '" + codigo + "' ya existe.");
            alert.showAndWait();
            return;
        }

        Articulo articulo = new Articulo();
        articulo.setCodigoArticulo(codigo);
        articulo.setDescripcion(descripcion.getText());
        articulo.setPvp(Float.parseFloat(pvp.getText()));
        articulo.setGastosEnvio(Double.parseDouble(gastosEnvio.getText()));
        articulo.setPreparacionMin(Integer.parseInt(preparacionMin.getText()));

        articuloDAO.create(articulo);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Artículo creado");
        alert.setHeaderText(null);
        alert.setContentText("Artículo creado con éxito.");
        alert.showAndWait();
    }

    @FXML
    private void closeButton(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void volver(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Welcome.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.setTitle("Online Store");
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void initialize() {
        BooleanBinding camposCompletosBinding = Bindings.createBooleanBinding(
                () -> !codigoArticulo.getText().isEmpty() && !descripcion.getText().isEmpty()
                        && !pvp.getText().isEmpty() && !gastosEnvio.getText().isEmpty()
                        && !preparacionMin.getText().isEmpty(),
                codigoArticulo.textProperty(), descripcion.textProperty(), pvp.textProperty(),
                gastosEnvio.textProperty(), preparacionMin.textProperty()
        );

        guardarButton.disableProperty().bind(camposCompletosBinding.not());
    }
}