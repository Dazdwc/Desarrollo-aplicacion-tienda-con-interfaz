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
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

public class CrearPedido {

    @FXML
    private TextField numeroPedido;
    @FXML
    private TextField cantidad;
    @FXML
    private TextField codigoArticulo;
    @FXML
    private TextField mailCliente;
    @FXML
    private Button guardarButton;
    @FXML
    private Button closeButton;
    @FXML
    private Button crearClienteButton;

    private FactoryDAO factory;

    public CrearPedido() {
        this.factory = new FactoryDAOImp();
    }

    @FXML
    protected void guardarPedido(ActionEvent event) {
        PedidoDAO pedidoDAO = factory.createPedidoDAO();
        ArticuloDAO articuloDAO = factory.createArticuloDAO();
        ClienteDAO clienteDAO = factory.createClienteDAO();

        int numPedido = Integer.parseInt(numeroPedido.getText());

        if (pedidoDAO.existePedido(numPedido)) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al crear el pedido");
            alert.setHeaderText(null);
            alert.setContentText("El número de pedido '" + numPedido + "' ya existe.");
            alert.showAndWait();
            return;
        }

        Articulo articulo = articuloDAO.getArticulo(codigoArticulo.getText());
        Cliente cliente = clienteDAO.getCliente(mailCliente.getText());

        if (articulo == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al crear el pedido");
            alert.setHeaderText(null);
            alert.setContentText("No se encontró un artículo con el código '" + codigoArticulo.getText() + "'.");
            alert.showAndWait();
            return;
        }

        if (cliente == null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Cliente no encontrado");
            alert.setHeaderText(null);
            alert.setContentText("No se encontró un cliente con el mail '" + mailCliente.getText() + "'. ¿Desea crear un nuevo cliente?");

            ButtonType crearClienteButton = new ButtonType("Crear Cliente");
            ButtonType cancelarButton = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
            alert.getButtonTypes().setAll(crearClienteButton, cancelarButton);

            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == crearClienteButton) {
                crearCliente();
            }

            return;
        }

        Pedido pedido = new Pedido();
        pedido.setNumeroPedido(numPedido);
        pedido.setCantidad(Integer.parseInt(cantidad.getText()));
        pedido.setArticulo(articulo);
        pedido.setCliente(cliente);

        pedidoDAO.create(pedido);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Pedido creado");
        alert.setHeaderText(null);
        alert.setContentText("Pedido creado con éxito.");
        alert.showAndWait();
    }

    @FXML
    private void closeButton(ActionEvent event) {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void crearCliente() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("CrearCliente.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                () -> !numeroPedido.getText().isEmpty() && !cantidad.getText().isEmpty() && !codigoArticulo.getText().isEmpty() && !mailCliente.getText().isEmpty(),
                numeroPedido.textProperty(), cantidad.textProperty(), codigoArticulo.textProperty(), mailCliente.textProperty()
        );

        guardarButton.disableProperty().bind(camposCompletosBinding.not());
        crearClienteButton.setVisible(false);
    }
}