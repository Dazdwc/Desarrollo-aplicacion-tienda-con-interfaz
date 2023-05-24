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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class CrearCliente {

    @FXML
    private ComboBox<String> tipoCliente;
    @FXML
    private Button closeButton;
    @FXML
    private TextField mail;
    @FXML
    private TextField nombre;
    @FXML
    private TextField nif;
    @FXML
    private TextField domicilio;
    @FXML
    private Button guardarButton;

    @FXML
    protected void guardarCliente(ActionEvent event) {
        FactoryDAO factory = new FactoryDAOImp();
        ClienteDAO clienteDAO = factory.createClienteDAO();

        Cliente existingClient = clienteDAO.getCliente(mail.getText());
        if (existingClient != null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al crear el cliente");
            alert.setHeaderText(null);
            alert.setContentText("El mail de cliente '" + mail.getText() + "' ya existe.");
            alert.showAndWait();
            return;
        }

        Cliente cliente;
        if (tipoCliente.getValue().equalsIgnoreCase("Premium")) {
            cliente = new ClientePremium();
        } else if (tipoCliente.getValue().equalsIgnoreCase("Standar")) {
            cliente = new ClienteStandar();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error al crear el cliente");
            alert.setHeaderText(null);
            alert.setContentText("Tipo de cliente no reconocido.");
            alert.showAndWait();
            return;
        }

        cliente.setMail(mail.getText());
        cliente.setNombre(nombre.getText());
        cliente.setNif(nif.getText());
        cliente.setDomicilio(domicilio.getText());

        clienteDAO.create(cliente);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cliente creado");
        alert.setHeaderText(null);
        alert.setContentText("Cliente creado con éxito.");
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
                () -> !mail.getText().isEmpty() && !nombre.getText().isEmpty() && !nif.getText().isEmpty()
                        && !domicilio.getText().isEmpty() && tipoCliente.getValue() != null,
                mail.textProperty(), nombre.textProperty(), nif.textProperty(),
                domicilio.textProperty(), tipoCliente.valueProperty()
        );

        guardarButton.disableProperty().bind(camposCompletosBinding.not());
    }
}