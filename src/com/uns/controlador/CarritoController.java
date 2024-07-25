package com.uns.controlador;

import com.uns.modelo.Carrito;
import com.uns.modelo.CarritoDAO;
import com.uns.modelo.Producto;
import com.uns.modelo.ProductoDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class CarritoController {

    @FXML
    private VBox carritoItems;

    @FXML
    private Label totalLabel;

    private CarritoDAO carritoDAO;
    private ProductoDAO productoDAO;

    public CarritoController() {
        carritoDAO = new CarritoDAO();
        productoDAO = new ProductoDAO();
    }

    @FXML
    public void initialize() {
        cargarCarrito();
    }

    private void cargarCarrito() {
        carritoItems.getChildren().clear();
        List<Carrito> itemsCarrito = carritoDAO.obtenerTodosLosItemsDelCarrito();
        double total = 0.0;

        for (Carrito item : itemsCarrito) {
            Producto producto = productoDAO.obtenerProductoPorId(item.getIdProducto());

            HBox itemBox = new HBox();
            itemBox.setSpacing(10);
            itemBox.getStyleClass().add("carrito-item");

            ImageView productImage = new ImageView(new Image("file:src/com/uns/res/img/product.png"));
            productImage.setFitWidth(50);
            productImage.setFitHeight(50);

            Label productLabel = new Label(producto.getNombre() + " $ " + producto.getPrecio() + " x " + item.getCantidad() + " = $ " + (producto.getPrecio() * item.getCantidad()));

            Button menosButton = new Button("-");
            menosButton.setOnAction(e -> {
                if (item.getCantidad() > 1) {
                    item.setCantidad(item.getCantidad() - 1);
                    carritoDAO.actualizarItemDelCarrito(item);
                    cargarCarrito();
                }
            });

            Button masButton = new Button("+");
            masButton.setOnAction(e -> {
                item.setCantidad(item.getCantidad() + 1);
                carritoDAO.actualizarItemDelCarrito(item);
                cargarCarrito();
            });

            itemBox.getChildren().addAll(productImage, productLabel, menosButton, masButton);
            carritoItems.getChildren().add(itemBox);

            total += producto.getPrecio() * item.getCantidad();
        }

        totalLabel.setText("TOTAL: $ " + String.format("%.2f", total));
    }

    @FXML
    private void handlePagar() {
        // Lógica para procesar el pago
    }
}