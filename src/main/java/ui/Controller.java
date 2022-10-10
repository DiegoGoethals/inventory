package ui;

import domain.Product;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import service.Service;
import util.ProductException;

import java.sql.SQLException;

public class Controller {

  private Service service;
  @FXML TableView<Product> tblProducts;
  @FXML TableColumn<Product, String> tblclmName;
  @FXML TableColumn<Product, Integer> tblclmQuantity;
  @FXML TextField txtName;
  @FXML TextField txtQuantity;

  public Controller() {
    service = new Service();
  }

  @FXML
  public void initialize() {
    tblclmName.setCellValueFactory(new PropertyValueFactory<>("name"));
    tblclmQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
    updateTable();
  }

  public void updateTable() {
    tblProducts.setItems(FXCollections.observableList(service.getProducts()));
  }

  public void addProduct() {
    try {
      service.addProduct(new Product(txtName.getText().trim(), Integer.parseInt(txtQuantity.getText())));
    } catch (SQLException ex) {
      throw new ProductException("Something went wrong, try again", ex);
    }
    updateTable();
  }

  public void increaseQuantity() {
    Product product = tblProducts.getSelectionModel().getSelectedItem();
    service.changeQuantity(product, 1);
    updateTable();
  }

  public void decreaseQuantity() {
    Product product = tblProducts.getSelectionModel().getSelectedItem();
    service.changeQuantity(product, -1);
    updateTable();
  }
}
