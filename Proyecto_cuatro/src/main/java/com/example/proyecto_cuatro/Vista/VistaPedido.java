package com.example.proyecto_cuatro.Vista;

import com.example.proyecto_cuatro.Modelo.Pedido;
import com.example.proyecto_cuatro.Modelo.PedidoDAO;
import com.example.proyecto_cuatro.Modelo.FactoryDAO;
import com.example.proyecto_cuatro.Modelo.FactoryDAOImp;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class VistaPedido implements Initializable {
    @FXML
    private Button closeButton;
    @FXML
    private TextField numeroPedidoBorrar;
    @FXML
    private TableView<Pedido> tabla;
    @FXML
    private TableColumn<Pedido, Integer> numeroPedido;
    @FXML
    private TableColumn<Pedido, Integer> cantidad;
    @FXML
    private TableColumn<Pedido, String> fechaHora;
    @FXML
    private TableColumn<Pedido, String> mailCliente;
    @FXML
    private TableColumn<Pedido, String> nombreCliente;
    @FXML
    private TableColumn<Pedido, String> nifCliente;
    @FXML
    private TableColumn<Pedido, String> codigoArticulo;
    @FXML
    private TableColumn<Pedido, String> descripcionArticulo;
    @FXML
    private TableColumn<Pedido, Double> precioArticulo;
    @FXML
    private TableColumn<Pedido, Double> precioEnvio;
    @FXML
    private TableColumn<Pedido, Double> total;
    @FXML
    private TableColumn<Pedido, String> enviado;
    @FXML
    private TableColumn<Pedido, String> cancelableHasta;
    @FXML
    private TextField correoClienteFiltro;

    private PedidoDAO pedidoDAO;
    private ObservableList<Pedido> list;

    public VistaPedido() {
        FactoryDAO factory = new FactoryDAOImp();
        pedidoDAO = factory.createPedidoDAO();
    }

    @FXML
    protected void mostrarPedidoClick() {
        List<Pedido> pedidos = pedidoDAO.getPedidos();

        Platform.runLater(() -> {
            list.clear();  // limpiar los datos existentes
            list.addAll(pedidos);  // agregar los nuevos pedidos
        });
    }

    @FXML
    protected void mostrarPedidosPendientes() {
        List<Pedido> pedidosPendientes = pedidoDAO.getPedidosPorEstado(false);

        Platform.runLater(() -> {
            list.clear();  // limpiar los datos existentes
            list.addAll(pedidosPendientes);  // agregar los nuevos pedidos
        });
    }

    @FXML
    protected void mostrarPedidosEnviados() {
        List<Pedido> pedidosEnviados = pedidoDAO.getPedidosPorEstado(true);

        Platform.runLater(() -> {
            list.clear();  // limpiar los datos existentes
            list.addAll(pedidosEnviados);  // agregar los nuevos pedidos
        });
    }

    @FXML
    protected void mostrarPedidosPorCliente() {
        String correo = correoClienteFiltro.getText();
        List<Pedido> pedidos = pedidoDAO.mostrarPedidoFiltrado(correo);

        Platform.runLater(() -> {
            list.clear();  // limpiar los datos existentes
            list.addAll(pedidos);  // agregar los nuevos pedidos
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        numeroPedido.setCellValueFactory(new PropertyValueFactory<>("numeroPedido"));
        cantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        fechaHora.setCellValueFactory(new PropertyValueFactory<>("fechaHora"));
        mailCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getMail()));
        nombreCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNombre()));
        nifCliente.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCliente().getNif()));
        codigoArticulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArticulo().getCodigoArticulo()));
        descripcionArticulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getArticulo().getDescripcion()));
        precioArticulo.setCellValueFactory(celldata -> new SimpleDoubleProperty(celldata.getValue().getArticulo().getPvp()).asObject());
        precioEnvio.setCellValueFactory(celldata -> new SimpleDoubleProperty(celldata.getValue().precioEnvio()).asObject());
        total.setCellValueFactory(celldata -> new SimpleDoubleProperty(celldata.getValue().PrecioTotal()).asObject());
        enviado.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().pedidoEnviado() ? "Enviado" : "Pendiente"));
        enviado.setCellFactory(column -> new TableCell<Pedido, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                    setStyle(""); // Limpiar el estilo de la celda si está vacía
                } else {
                    setText(item);

                    if (item.equals("Enviado")) {
                        setStyle("-fx-background-color: #BDECB6;");
                    } else if (item.equals("Pendiente")) {
                        setStyle("-fx-background-color: #f7bd56;");
                    }
                }
            }
        });
        cancelableHasta.setCellValueFactory(cellData -> {
            Pedido pedido = cellData.getValue();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
            LocalDateTime fechaHora = LocalDateTime.parse(pedido.getFechaHora(), formatter);
            int preparacionMin = pedido.getArticulo().getPreparacionMin();
            LocalDateTime cancelableHasta = fechaHora.plusMinutes(preparacionMin);
            String cancelableHastaStr = cancelableHasta.format(formatter);
            return new SimpleStringProperty(cancelableHastaStr);
        });

        list = FXCollections.observableArrayList();
        tabla.setItems(list);
        mostrarPedidoClick();
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
    protected void borrarPedidoNoEnviado() {
        String numeroPedidoStr = numeroPedidoBorrar.getText();
        try {
            int numeroPedido = Integer.parseInt(numeroPedidoStr);
            String resultado = pedidoDAO.deleteIfNotSent(numeroPedido);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);  // Crear el objeto Alert

            switch (resultado) {
                case "BORRADO":
                    alert.setTitle("Pedido borrado");
                    alert.setHeaderText(null);
                    alert.setContentText("El pedido ha sido borrado con éxito.");
                    break;
                case "ENVIADO":
                    alert.setTitle("Pedido enviado");
                    alert.setHeaderText(null);
                    alert.setContentText("El pedido no se puede borrar porque ha sido enviado.");
                    break;
                case "NO_EXISTE":
                    alert.setTitle("Pedido inexistente");
                    alert.setHeaderText(null);
                    alert.setContentText("El pedido no existe.");
                    break;
                default:
                    alert.setTitle("Error desconocido");
                    alert.setHeaderText(null);
                    alert.setContentText("Ha ocurrido un error inesperado.");
                    break;
            }

            alert.showAndWait();  // Mostrar la alerta
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);  // Crear una alerta de error
            alert.setTitle("Error de entrada");
            alert.setHeaderText(null);
            alert.setContentText("El número de pedido ingresado no es válido.");
            alert.showAndWait();  // Mostrar la alerta
        }
    }
}