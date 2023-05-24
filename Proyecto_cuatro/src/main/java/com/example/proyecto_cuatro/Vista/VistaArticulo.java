package com.example.proyecto_cuatro.Vista;

import com.example.proyecto_cuatro.Modelo.Articulo;
import com.example.proyecto_cuatro.Modelo.ArticuloDAO;
import com.example.proyecto_cuatro.Modelo.FactoryDAO;
import com.example.proyecto_cuatro.Modelo.FactoryDAOImp;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class VistaArticulo implements Initializable {

    @FXML
    private Button closeButton;

    @FXML
    private TableView<Articulo> tabla;
    @FXML
    private TableColumn<Articulo, String> codigoArticulo;
    @FXML
    private TableColumn<Articulo, String> descripcion;
    @FXML
    private TableColumn<Articulo, Float> pvp;
    @FXML
    private TableColumn<Articulo, Double> gastosEnvio;
    @FXML
    private TableColumn<Articulo, Integer> preparacionMin;

    @FXML
    protected void mostrarArticuloClick() {
        FactoryDAO factory = new FactoryDAOImp();
        ArticuloDAO articuloDAO = factory.createArticuloDAO();

        List<Articulo> articulos = articuloDAO.getAll();

        Platform.runLater(() -> {
            list.clear();  // limpiar los datos existentes
            list.addAll(articulos);  // agregar los nuevos artículos
        });
    }

    ObservableList<Articulo> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        codigoArticulo.setCellValueFactory(new PropertyValueFactory<Articulo, String>("codigoArticulo"));
        descripcion.setCellValueFactory(new PropertyValueFactory<Articulo, String>("descripcion"));
        pvp.setCellValueFactory(new PropertyValueFactory<Articulo, Float>("pvp"));
        gastosEnvio.setCellValueFactory(new PropertyValueFactory<Articulo, Double>("gastosEnvio"));
        preparacionMin.setCellValueFactory(new PropertyValueFactory<Articulo, Integer>("preparacionMin"));

        tabla.setItems(list);
        mostrarArticuloClick();
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
}