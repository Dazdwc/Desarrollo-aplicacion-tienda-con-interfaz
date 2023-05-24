package com.example.proyecto_cuatro.Vista;

import com.example.proyecto_cuatro.Modelo.*;
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

public class VistaCliente implements Initializable {
    @FXML
    private Button btnPremium;

    @FXML
    private Button btnStandar;
    @FXML
    private Button closeButton;
    @FXML
    private TableView<Cliente> tabla;
    @FXML
    private TableColumn<Cliente, String> mail;
    @FXML
    private TableColumn<Cliente, String> nombre;
    @FXML
    private TableColumn<Cliente, String> nif;
    @FXML
    private TableColumn<Cliente, String> domicilio;
    @FXML
    private TableColumn<Cliente, String> tipoCliente;

    private ClienteDAO clienteDAO;

    public VistaCliente() {
        FactoryDAO factory = new FactoryDAOImp();
        clienteDAO = factory.createClienteDAO();
    }

    @FXML
    protected void mostrarClienteClick() {
        List<Cliente> clientes = clienteDAO.getAll();

        Platform.runLater(() -> {
            list.clear();  // limpiar los datos existentes
            list.addAll(clientes);  // agregar los nuevos clientes
        });
    }

    ObservableList<Cliente> list = FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        mail.setCellValueFactory(new PropertyValueFactory<Cliente, String>("mail"));
        nombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
        nif.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nif"));
        domicilio.setCellValueFactory(new PropertyValueFactory<Cliente, String>("domicilio"));
        tipoCliente.setCellValueFactory(new PropertyValueFactory<Cliente, String>("tipoCliente"));

        tabla.setItems(list);
        mostrarClienteClick();
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
    protected void mostrarClientesPremium() {
        List<ClientePremium> clientePremium = clienteDAO.getAllPremium();

        Platform.runLater(() -> {
            list.clear();  // limpiar los datos existentes
            list.addAll(clientePremium);  // agregar los nuevos clientes
        });
    }

    @FXML
    protected void mostrarClientesStandar() {
        List<ClienteStandar> clientesStandar = clienteDAO.getAllStandar();

        Platform.runLater(() -> {
            list.clear();  // limpiar los datos existentes
            list.addAll(clientesStandar);  // agregar los nuevos clientes
        });
    }
}